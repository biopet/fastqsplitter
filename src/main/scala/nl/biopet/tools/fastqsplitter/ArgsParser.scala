package nl.biopet.tools.fastqsplitter

import java.io.File

import nl.biopet.utils.tool.AbstractOptParser

class ArgsParser(cmdName: String) extends AbstractOptParser[Args](cmdName) {
  opt[File]('I', "inputFile")
    .unbounded()
    .required()
    .valueName("<file>")
    .action((x, c) => c.copy(inputFile = x))
    .text("Path to input file")
  opt[File]('o', "output")
    .required()
    .unbounded()
    .valueName("<file>")
    .action((x, c) => c.copy(outputFiles = x :: c.outputFiles))
    .text("Path to output file")
}
