package com.cognia.cypher

import org.scalatest.{FlatSpec, Matchers}

/**
 * User: fran
 * Date: 19/06/2015
 */
class RetCipherTest  extends FlatSpec with Matchers {







  "A com.cognia.cypher.Decrypter" should "should replace each character by the one following it as  cipher (rot2)" in {
    val decrypt = RetCipher.decryptChar(2)(_)
    decrypt('A') should be ('Y')
    decrypt('Z') should be ('X')
    decrypt('C') should be ('A')


  }

  "A Encrypter" should "should replace each character by the one following it as  cipher (rot13)" in {
    val encrypt = RetCipher.encryptChar(2)(_)
    encrypt('Y') should be ('A')
    encrypt('X') should be ('Z')
    encrypt('A') should be ('C')


  }

  "A Encrypter" should "should replace each character by the one following it as cipher (rot13)" in {
    val encrypt = RetCipher.encryptChar(13)(_)


    encrypt('A') should be ('N')


  }

  "A Encrypter" should "should replace String as caesar cipher (rot13)" in {
    val encrypt = RetCipher.encrypt(13)(_)
    encrypt("Hello world") should be ("Uryyb jbeyq")
  }





  "A com.cognia.cypher.Decrypter" should "should replace String as caesar cipher (rot13)" in {
    val decrypt = RetCipher.decrypt(13)(_)
    decrypt("Lrf jr pna") should be ("Yes we can")
  }

//  "A com.cognia.cypher.Decrypter" should "should rot13 and find that the file contains 'hello world'" in {
//    val in = Source.fromFile("/Users/fran/Downloads/problem6/helloworld.db")(StandardCharsets.ISO_8859_1)
//    val stack = new com.cognia.cypher.Decrypter
//    val lines = stack.doSome(in)
//    lines should be ("hello world")
//
//  }

}
