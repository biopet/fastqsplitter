# Manual

## Introduction
This tool divides a fastq file into smaller fastq files, based on the number of output files specified. For ecample,
if one specifies 5 output files, it will split the fastq into 5 files of equal size. This can be very useful if one
wants to use chunking option in one of our pipelines: FastqSplitter can generate the exact number of fastq files
(chunks) as needed. This tool is used internally in our pipelines as required.

## Example
To run this tool:
```bash
java -jar FastqSplitter-version.jar --inputFile myFastq.fastq \
--output mySplittedFastq_1.fastq --output mySplittedFastq_2.fastq --output mySplittedFastq_3.fastq
```
The above invocation will split the input file into 3 fastq files of equal size.


To get help:
```bash
java -jar FastqSplitter-version.jar --help
Usage: FastqSplitter [options]

  -l <value> | --log_level <value>
        Log level
  -h | --help
        Print usage
  -v | --version
        Print version
  -I <file> | --inputFile <file>
        out is a required file property
  -o <file> | --output <file>
        out is a required file property
```

## Ouput
Multiple fastq files based on the number of outputFiles specified.