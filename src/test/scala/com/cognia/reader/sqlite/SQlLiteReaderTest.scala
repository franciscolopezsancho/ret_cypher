package com.cognia.reader.sqlite

import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class SQlLiteReaderTest extends FlatSpec with Matchers{


  val conf = ConfigFactory.load

   val connectionUrl = conf.getString("dbfile.path")
   val crStartCol = conf.getString("dbfile.content.decipher.table.columns.start")
   val crLengthCol = conf.getString("dbfile.content.decipher.table.columns.length")
   val crRotationCol = conf.getString("dbfile.content.decipher.table.columns.rotation")

  val crColNames = ColumnsNames(crStartCol,crLengthCol,crRotationCol)

  "A Detector" should "find tables " in {
    SQLiteReader.getTables(connectionUrl) should be (List("b0dy","crypt"))
  }

  "A Detector" should "find data " in {
    SQLiteReader.getTables(connectionUrl) should be (List("b0dy","crypt"))
  }
  "A Detector" should "find book " in {
    val book = SQLiteReader.getBook(connectionUrl,conf.getString("dbfile.content.book.table.name"),conf.getString("dbfile.content.book.table.columns.data"))
    //book.map(println)
    book.isEmpty should  be (false)
  }

  "A Detector" should "find caesarMap " in {
    val caesarMap = SQLiteReader.getCaesarMap(connectionUrl,conf.getString("dbfile.content.decipher.table.name"),crColNames)
    //caesarMap.map(println)
    caesarMap.isEmpty should  be (false)
  }

  "A Detector" should "find columns " in {
    val caesarMap = SQLiteReader.getColumns(connectionUrl,conf.getString("dbfile.content.decipher.table.name"))
    //caesarMap.map(println)
    caesarMap should  be (List(crStartCol,crLengthCol,crRotationCol))
  }


}
