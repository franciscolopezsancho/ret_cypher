package com.cognia.integration

import java.io.File

import com.cognia.cypher.{Decoder, RetHacker, RetCipher}
import com.cognia.reader.Reader
import com.cognia.reader.sqlite.SQLiteReader
import com.cognia.writer.Writer
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class ReadDecryptWriteTest extends FlatSpec with Matchers {


  "A Program" should "read from file, decipher and then write to another file" in {
    val trad = new RetHacker().trad(Reader.in.getLines().toStream.drop(19),RetCipher.decrypt)
    //Writer.inputToFile(trad,new File("puzzle.db"))
  }

  "A Program" should "read from file, decipher and then write to another file 2" in {

    val trad = new RetHacker().trad(SQLiteReader.getBook(),RetCipher.decrypt)
    //Writer.inputToFile(trad,new File("puzzle.db"))
  }

  "A Program" should "get book lines" in {

    SQLiteReader.getBook().flatMap(x=> x.split("\n")).length should be (5021)
    //Writer.inputToFile(trad,new File("puzzle.db"))
  }

  "A Program" should "read from file as sqlite, decipher and then write to another file" in {
    val datum = SQLiteReader.getBook()
    //val trad = Decoder.decodeAll(SQLiteReader.getBook(_,_,_)),datum.decodingIndexes,RetCipher.decrypt)
    val decipheredBook = Decoder.trad(datum,RetCipher.decrypt,SQLiteReader.getCaesarMap())
    Writer.inputToFile(decipheredBook,new File("AStudyInScarlet.txt"))

  }


}
