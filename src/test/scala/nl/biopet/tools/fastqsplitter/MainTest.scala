package nl.biopet.tools.fastqsplitter

import org.scalatest.Matchers
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class MainTest extends TestNGSuite with Matchers {
  @Test
  def test(): Unit = {
    Main.main(Array("-i", "inputFile"))
  }
}
