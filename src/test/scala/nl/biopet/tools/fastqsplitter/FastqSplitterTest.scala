package nl.biopet.tools.fastqsplitter

import java.io.File

import nl.biopet.utils.test.tools.ToolTest
import org.testng.annotations.Test

class FastqSplitterTest extends ToolTest[Args] {
  def toolCommand: FastqSplitter.type = FastqSplitter
  val fq: String = resourcePath("/paired01a.fq")

  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      FastqSplitter.main(Array())
    }
  }

  @Test
  def testWrongGroupSize(): Unit = {
    val temp = File.createTempFile("out", ".fastq")
    temp.deleteOnExit()
    intercept[IllegalArgumentException] {
      FastqSplitter.splitFastqFile(new File(fq), List(temp), 3, 7)
    }.getMessage shouldBe "requirement failed: logLimit should be a multiplication of groupSize"
  }

  @Test
  def testMethod(): Unit = {
    val temp = File.createTempFile("out", ".fastq")
    temp.deleteOnExit()
    FastqSplitter.splitFastqFile(new File(fq), List(temp), 1, 1)
  }

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
