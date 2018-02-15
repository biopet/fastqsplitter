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
