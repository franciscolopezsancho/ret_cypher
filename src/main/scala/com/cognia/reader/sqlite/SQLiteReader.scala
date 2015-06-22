package com.cognia.reader.sqlite

import java.sql._

import com.cognia.cypher.bo.DecodingIndex
import com.typesafe.config.ConfigFactory
import org.sqlite.jdbc4.JDBC4ResultSet

import scala.collection.immutable.Queue
import scala.collection.immutable.Stream._


/**
 * Created by Francisco on 22/06/2015.
 */
object SQLiteReader {



  implicit val conf = ConfigFactory.load

  //TODO fix implicit in getBook and indexes!
  implicit val connectionUrl = conf.getString("dbfile.path")
  implicit val bookTable = conf.getString("dbfile.content.book.table.name")
  implicit val btColumn = conf.getString("dbfile.content.book.table.columns.data")
  implicit val caesarTable = conf.getString("dbfile.content.decipher.table.name")
  implicit val ctStartCol = conf.getString("dbfile.content.decipher.table.columns.start")
  implicit val ctLengthCol = conf.getString("dbfile.content.decipher.table.columns.length")
  implicit val ctRotationCol = conf.getString("dbfile.content.decipher.table.columns.rotation")
  val columnsNames = ColumnsNames(ctStartCol,ctLengthCol,ctRotationCol)

  val select_from = "SELECT * FROM "

  def getTables(connectionUrl: String = connectionUrl):List[String]={
    val connection : Connection = DriverManager.getConnection(connectionUrl)
    val rs = connection.createStatement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'")
    var tables :List[String]= List()
    while(rs.next()) {
      tables = tables :+ rs.getString("name")
    }
    connection.close
    tables
  }

  def getColumns(connectionUrl: String = connectionUrl,table:String=caesarTable):List[String]={
    val connection : Connection = DriverManager.getConnection(connectionUrl)
    val rs = connection.createStatement.executeQuery(select_from + table)
    val jdbcRs = rs.asInstanceOf[JDBC4ResultSet]
    val  columns = (1 to  jdbcRs.getColumnCount()).map( x=> jdbcRs.getColumnLabel(x)).toList
    connection.close
    columns
  }




  def getBook( connectionUrl: String = connectionUrl,table:String = bookTable,column:String = btColumn):Stream[String]={
    val connection : Connection = DriverManager.getConnection(connectionUrl)
    val rs = connection.createStatement.executeQuery(select_from + table)
    var book :Stream[String]= Stream.empty
    while(rs.next()) {
      book = book :+ rs.getString(column)
    }
    connection.close
    book
  }




  def getCaesarMap( connectionUrl: String = connectionUrl,table:String = caesarTable,columns :ColumnsNames=columnsNames):Queue[DecodingIndex]={
    val connection : Connection = DriverManager.getConnection(connectionUrl)
    val rs = connection.createStatement.executeQuery("SELECT * FROM "+table)
    var caesarMap :Queue[DecodingIndex]= Queue.empty[DecodingIndex]
    while(rs.next()) {
      caesarMap = caesarMap :+ DecodingIndex(rs.getString(columns.start).toInt,rs.getString(columns.length).toInt,rs.getString(columns.rotate).toInt)
    }
    connection.close
    caesarMap
  }

}
