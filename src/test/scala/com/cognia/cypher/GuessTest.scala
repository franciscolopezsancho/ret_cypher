package com.cognia.cypher

import com.cognia.reader.Reader
import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class GuessTest  extends FlatSpec with Matchers {


//  "A Stack" should "pop values in last-in-first-out order" in {
//    new Guess().doSome()
//  }

  "A Stack" should "trad" in {

    new Guess().trad(Reader.in.getLines().toStream.drop(19),Decrypter.decrypt).foreach(println)
  }



  //   "A Stack" should "do par" in {
  //    val stack = new com.cognia.cypher.Decrypter()
  //    stack.createCode()  }

//
//  "A Stack" should "just print" in {
//    val in = Source.fromFile("/Users/fran/Downloads/problem6/puzzle.db")(StandardCharsets.ISO_8859_1)
//
//    new Guess().doSome(in)
//  }









  "A Detector" should "should find is 13 the best value when search by 'Lrf jr pna' which is 'Yes we can'" in {
    new Guess().bestGuess(List("Lrf jr pna"),Decrypter.decrypt) should be(13)
  }


}
