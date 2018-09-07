# FastqSplitter


This tool divides a fastq file into smaller fastq files, based on the number of output files specified. For ecample,
if one specifies 5 output files, it will split the fastq into 5 files of equal size. This can be very useful if one
wants to use the chunking option in a pipeline: FastqSplitter can generate the exact number of fastq files
(chunks) as needed.

FastqSplitter will read groups of reads (100 reads per group)
and distribute this evenly over the output FASTQ
files. FastqSplitter will iterate over all the output files while writing the
read groups.

Example:
A fastq file is split with a group size of 100 and three output files.
read 1-100 will be assigned to output1
read 101-200 will be assigned to output2
read 201-300 will be assigned to output3
read 301-400 will be assigned to output1
read 401-500 will be assigned to output2
etc.

This will make sure the output fastq files are of equal size and there is no positional bias in each
output file.
      

# Documentation

For documentation and manuals visit our [github.io page](https://biopet.github.io/fastqsplitter).

# About


FastqSplitter is part of BIOPET tool suite that is developed at LUMC by [the SASC team](http://sasc.lumc.nl/).
Each tool in the [BIOPET tool suite](https://github.com/biopet/) is meant to offer a standalone function that can be used to perform a
dedicate data analysis task or added as part of a pipeline, for example the SASC team's [biowdl pipelines](https://github.com/biowdl).

All tools in the BIOPET tool suite are [Free/Libre](https://www.gnu.org/philosophy/free-sw.html) and
[Open Source](https://opensource.org/osd) Software.
    

# Contact


<p>
  <!-- Obscure e-mail address for spammers -->
For any question related to FastqSplitter, please use the
<a href='https://github.com/biopet/fastqsplitter/issues'>github issue tracker</a>
or contact
 <a href='http://sasc.lumc.nl/'>the SASC team</a> directly at: <a href='&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#97;&#115;&#99;&#64;&#108;&#117;&#109;&#99;&#46;&#110;&#108;'>
&#115;&#97;&#115;&#99;&#64;&#108;&#117;&#109;&#99;&#46;&#110;&#108;</a>.
</p>

     

