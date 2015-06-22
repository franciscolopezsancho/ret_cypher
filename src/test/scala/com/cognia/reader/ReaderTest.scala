package com.cognia.reader

import com.cognia.reader.sqlite.{ColumnsNames, SQLiteReader}
import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class ReaderTest extends FlatSpec with Matchers{


  /*"A Detector" should "find 'Hello World' in the dictionary " in {
    Reader.inDictionary("Yes we can") should be (3)
  }*/
  val conf = ConfigFactory.load

  //TODO fix implicit in getBook and indexes!
   val connectionUrl = conf.getString("dbfile.path")
   val bookTable = conf.getString("dbfile.content.book.table.name")
   val btColumn = conf.getString("dbfile.content.book.table.columns.data")
   val caesarTable = conf.getString("dbfile.content.decipher.table.name")
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
    val book = SQLiteReader.getBook(connectionUrl,bookTable,btColumn)
    //book.map(println)
    book.isEmpty should  be (false)
  }

  "A Detector" should "find caesarMap " in {
    val caesarMap = SQLiteReader.getCaesarMap(connectionUrl,caesarTable,crColNames)
    //caesarMap.map(println)
    caesarMap.isEmpty should  be (false)
  }

  "A Detector" should "find columns " in {
    val caesarMap = SQLiteReader.getColumns(connectionUrl,caesarTable)
    //caesarMap.map(println)
    caesarMap should  be (List(crStartCol,crLengthCol,crRotationCol))
  }


}
