package nl.biopet.tools.fastqsplitter

import java.io.File

/**
  * Arg for commandline program
  * @param inputFile input fastq file
  * @param outputFiles output fastq files
  */
case class Args(inputFile: File = null, outputFiles: List[File] = Nil)
