package com.cognia.integration

import java.io.File

import com.cognia.cypher.{Decoder, RetCipher, Guess}
import com.cognia.reader.{SQLiteReader, SqlReader, Reader}
import com.cognia.writer.Writer
import org.scalatest.{Matchers, FlatSpec}

/**
 * User: fran
 * Date: 21/06/2015
 */
class ReadDecryptWriteTest extends FlatSpec with Matchers {


  "A Program" should "read from file, decipher and then write to another file" in {
    val trad = new Guess().trad(Reader.in.getLines().toStream.drop(19),RetCipher.decrypt)
    Writer.inputToFile(trad,new File("puzzle.db"))
  }

  "A Program" should "read from file as sqlite, decipher and then write to another file" in {
    val datum = SQLiteReader.getBook()
    //val trad = Decoder.decodeAll(SQLiteReader.getBook(_,_,_)),datum.decodingIndexes,RetCipher.decrypt)
    Decoder.trad(datum,RetCipher.decrypt,SQLiteReader.getCaesarMap())
  }


}
