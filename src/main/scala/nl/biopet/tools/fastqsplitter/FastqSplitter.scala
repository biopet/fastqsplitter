/*
 * Copyright (c) 2014 Biopet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.biopet.tools.fastqsplitter

import java.io.File

import htsjdk.samtools.fastq.{AsyncFastqWriter, BasicFastqWriter, FastqReader}
import nl.biopet.utils.tool.ToolCommand

import scala.collection.JavaConversions._

object FastqSplitter extends ToolCommand[Args] {
  def emptyArgs: Args = Args()
  def argsParser = new ArgsParser(this)

  /**
    * This is main entry point for the tool [[FastqSplitter]]
    * @param args for detail fix this look into [[ArgsParser]]
    */
  def main(args: Array[String]): Unit = {
    val cmdArgs = cmdArrayToArgs(args)

    logger.info("Start")

    splitFastqFile(cmdArgs.inputFile, cmdArgs.outputFiles)

    logger.info("Done")
  }

  /**
    * This method will split a fastq file
    * @param inputFile input fastq file
    * @param outputFiles output fastq files
    * @param groupSize Number fastq records to write to a file
    * @param logLimit Per how much records a status log is outputed
    */
  def splitFastqFile(inputFile: File,
                     outputFiles: List[File],
                     groupSize: Int = 100,
                     logLimit: Int = 1000000): Unit = {
    require(logLimit % groupSize == 0,
            "logLimit should be a multiplication of groupSize")

    val output = for (file <- outputFiles.toArray)
      yield new AsyncFastqWriter(new BasicFastqWriter(file), groupSize)
    val reader = new FastqReader(inputFile)

    logger.info("Starting to split fastq file: " + inputFile)
    logger.info("Output files: " + outputFiles.mkString(", "))

    var counter: Long = 0
    for ((group, i) <- reader.iterator().grouped(groupSize).zipWithIndex) {
      val writer = output(i % output.length)
      group.foreach(writer.write)
      counter += group.length
      if (counter % logLimit == 0)
        logger.info(counter + " reads processed")
    }
    output.foreach(_.close())
    reader.close()
    logger.info("Done, " + counter + " reads processed")
  }

  def descriptionText: String =
    s"""
      |This tool divides a fastq file into smaller fastq files, based on the number of output files specified. For ecample,
      |if one specifies 5 output files, it will split the fastq into 5 files of equal size. This can be very useful if one
      |wants to use the chunking option in a pipeline: $toolName can generate the exact number of fastq files
      |(chunks) as needed.
      |
      |$toolName will read groups of reads (100 reads per group)
      |and distribute this evenly over the output FASTQ
      |files. $toolName will iterate over all the output files while writing the
      |read groups.
      |
      |Example:
      |A fastq file is split with a group size of 100 and three output files.
      |read 1-100 will be assigned to output1
      |read 101-200 will be assigned to output2
      |read 201-300 will be assigned to output3
      |read 301-400 will be assigned to output1
      |read 401-500 will be assigned to output2
      |etc.
      |
      |This will make sure the output fastq files are of equal size and there is no positional bias in each
      |output file.
      """.stripMargin

  def manualText: String =
    s"""
       |$toolName needs an input file and as many output files as are required. If
       |five output files are given, the input file will be split in five files.
     """.stripMargin

  def exampleText: String =
    s"""
       |To split a file into three different files of roughly equal size:
       |${example("-I",
                  "myfastQ.fastq",
                  "-o",
                  "mySplittedFastq_1.fastq",
                  "-o",
                  "mySplittedFastq_2.fastq",
                  "-o",
                  "mySplittedFastq_3.fastq")}
     """.stripMargin
}
