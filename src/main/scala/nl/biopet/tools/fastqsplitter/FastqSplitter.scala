package nl.biopet.tools.fastqsplitter

import java.io.File

import htsjdk.samtools.fastq.{AsyncFastqWriter, BasicFastqWriter, FastqReader}
import nl.biopet.utils.tool.ToolCommand

import scala.collection.JavaConversions._

object FastqSplitter extends ToolCommand {
  def main(args: Array[String]): Unit = {
    val parser = new ArgsParser(toolName)
    val cmdArgs =
      parser.parse(args, Args()).getOrElse(throw new IllegalArgumentException)

    logger.info("Start")

    splitFastqFile(cmdArgs.inputFile, cmdArgs.outputFiles)

    logger.info("Done")
  }

  def splitFastqFile(inputFile: File,
                      outputFiles: List[File],
                      groupSize: Int = 100,
                      logLimit: Int = 1000000): Unit = {
    require(logLimit % groupSize == 0, "logLimit should be a multiplication of groupSize")

    val output = for (file <- outputFiles.toArray)
      yield new AsyncFastqWriter(new BasicFastqWriter(file), groupSize)
    val reader = new FastqReader(inputFile)

    logger.info("Starting to split fatsq file: " + inputFile)
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
}
