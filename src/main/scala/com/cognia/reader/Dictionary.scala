package com.cognia.reader

import java.nio.charset.StandardCharsets

import com.typesafe.config.ConfigFactory

import scala.io.Source

/**
 * User: fran
 * Date: 21/06/2015
 */
object Dictionary {

  implicit val conf = ConfigFactory.load

  val dictionary = Source.fromFile(conf.getString("dictionary.path"))(StandardCharsets.ISO_8859_1)

  val loadDictionary = dictionary.getLines().toVector.map(x=>x.toUpperCase)

  def inDictionary(phrase:String):Int={
    val tot = phrase.split(" ").filter(x => loadDictionary.contains(x.toUpperCase)).length
    tot
  }

  def inDictionaryWord(word:String):Boolean={
    loadDictionary.contains(word.toUpperCase)
  }

}
