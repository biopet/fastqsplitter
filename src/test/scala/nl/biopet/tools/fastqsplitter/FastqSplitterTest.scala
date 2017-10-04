package nl.biopet.tools.fastqsplitter

import java.io.File

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

class FastqSplitterTest extends BiopetTest {
  val fq: String = resourcePath("/paired01a.fq")

  @Test
  def testMain(): Unit = {
    val temp = File.createTempFile("out", ".fastq")
    temp.deleteOnExit()
    val args = Array("-I", fq, "-o", temp.getAbsolutePath)
    FastqSplitter.main(args)
  }

  @Test
  def testManyOutMain(): Unit = {
    val files = (0 until 10).map(_ => File.createTempFile("out", ".fastq"))
    files.foreach(_.deleteOnExit())
    var args = Array("-I", fq)
    files.foreach(x => args ++= Array("-o", x.getAbsolutePath))
    FastqSplitter.main(args)
  }
}
