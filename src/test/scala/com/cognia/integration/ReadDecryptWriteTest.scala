package com.cognia.integration

import com.cognia.cypher.{Decoder, RetCipher, Hacker}
import com.cognia.reader.sqlite.SQLiteReader
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class ReadDecryptWriteTest extends FlatSpec with Matchers {




  "A Program" should "read from puzzle.d and decipher it by hacking it" in {
    val hacked = Hacker.trad(SQLiteReader.getBook(),RetCipher.decrypt)
    hacked.mkString.take(500) should be (scala.io.Source.fromFile("src/main/resources/AStudyInScarlet.txt").mkString.take(500))
  }



  "A Program" should "read from file as sqlite, decipher it finding the encoding into the file itself" in {
    val decipheredBook = Decoder.trad(SQLiteReader.getBook(),RetCipher.decrypt,SQLiteReader.getCaesarMap())
    decipheredBook.mkString.take(500) should be (scala.io.Source.fromFile("src/main/resources/AStudyInScarlet.txt").mkString.take(500))
    //Just to check the writer
    //Writer.inputToFile(decipheredBook,new File("AStudyInScarlet.txt"))

  }


}
