package com.cognia.cypher

import java.io.File

import com.cognia.reader.sqlite.SQLiteReader
import com.cognia.writer.Writer
import com.typesafe.config.ConfigFactory

/**
 * User: fran
 * Date: 22/06/2015
 */
object Main extends App{

      val conf = ConfigFactory.load()

      args match {
        case Array("help") => {
          println("This app will look in its same directory for a any file")
          println("with extension '.db' and will decipher them content in" )
          println("a file called the same name as the original but with extension .txt" )
        }
        case _ => {
          listFiles().foreach { file =>
            val sqliteFileUrl = conf.getString("sqlite.driver")+file.getName
            val decipheredBook = Decoder.trad(SQLiteReader.getBook(sqliteFileUrl), RetCipher.decrypt, SQLiteReader.getCaesarMap(sqliteFileUrl))
            Writer.inputToFile(decipheredBook, new File(file.getName.substring(0,file.getName.indexOf("."))+"txt"))
          }




        }
      }

  def listFiles():Array[File]={
    new java.io.File(System.getProperty("user.dir")).listFiles.filter(_.getName.endsWith(".db"))
  }




}
