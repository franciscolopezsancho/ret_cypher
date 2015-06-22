package com.cognia.cypher

import com.cognia.reader.Reader
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class GuessTest  extends FlatSpec with Matchers {



  "A Stack" should "trad" in {
    new Guess().trad(Reader.in.getLines().toStream.drop(19),RetCipher.decrypt).foreach(println)
  }

  "A Detector" should "should find is 13 the best value when search by 'Lrf jr pna' which is 'Yes we can'" in {
    new Guess().bestGuess(List("Lrf jr pna"),RetCipher.decrypt) should be(13)
  }






}
