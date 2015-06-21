package com.cognia.writer

import java.nio.charset.StandardCharsets

import scala.io.Source

/**
 * User: fran
 * Date: 21/06/2015
 */
object Writer {

  val in = Source.fromFile("/Users/fran/Downloads/problem6/puzzle.db")(StandardCharsets.ISO_8859_1)

  val dictionary = Source.fromFile("/Users/fran/Downloads/problem6/words")(StandardCharsets.ISO_8859_1)

  val loadDictionary = dictionary.getLines().toVector.map(x=>x.toUpperCase)

}
