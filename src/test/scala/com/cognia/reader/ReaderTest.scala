package com.cognia.reader

import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 21/06/2015
 */
class ReaderTest extends FlatSpec with Matchers{


  "A Detector" should "find 'Hello World' in the dictionary " in {
    Reader.inDictionary("Yes we can") should be (3)
  }
}
