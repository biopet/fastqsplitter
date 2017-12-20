package nl.biopet.tools.fastqsplitter

import java.io.File

import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}

class ArgsParser(toolCommand: ToolCommand[Args])
    extends AbstractOptParser[Args](toolCommand) {
  opt[File]('I', "inputFile")
    .required()
    .valueName("<file>")
    .action((x, c) => c.copy(inputFile = x))
    .text("Path to input file")
  opt[File]('o', "outputFile")
    .required()
    .unbounded()
    .valueName("<file>")
    .action((x, c) => c.copy(outputFiles = x :: c.outputFiles))
    .text("Path to output file. Multiple output files can be specified.")
}
