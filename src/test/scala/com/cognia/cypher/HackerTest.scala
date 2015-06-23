package com.cognia.cypher

import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class HackerTest  extends FlatSpec with Matchers {





  "A Detector" should "should find is 13 the best value when search by 'Lrf jr pna' which is 'Yes we can'" in {
    Hacker.bestGuess(List("Lrf jr pna"),RetCipher.decrypt) should be(13)
  }

  "A Detector" should "should find is 13 the best value when search by 'XIZB Q.\r' which is ...nothing really" in {
    Hacker.bestGuess(List("XIZB Q.\r"),RetCipher.decrypt) should be(0)
  }






}
