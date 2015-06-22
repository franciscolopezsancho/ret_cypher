package com.cognia.cypher

import com.cognia.reader.Reader
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class RetHackerTest  extends FlatSpec with Matchers {



  "A Stack" should "trad" in {
    new RetHacker().trad(Reader.in.getLines().toStream.drop(19),RetCipher.decrypt).foreach(println)
  }

  "A Detector" should "should find is 13 the best value when search by 'Lrf jr pna' which is 'Yes we can'" in {
    new RetHacker().bestGuess(List("Lrf jr pna"),RetCipher.decrypt) should be(13)
  }

  "A Detector" should "should find is 13 the best value when search by 'XIZB Q.\r' which is ...nothing really" in {
    new RetHacker().bestGuess(List("XIZB Q.\r"),RetCipher.decrypt) should be(0)
  }






}
