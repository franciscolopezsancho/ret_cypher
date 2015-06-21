package com.cognia.cypher


import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

/**
 * User: fran
 * Date: 19/06/2015
 */
object Decrypter {







  Files.write(Paths.get("/Users/fran/Downloads/problem6/unpuzzlez.db"), "file contents".getBytes(StandardCharsets.UTF_8))


  /**
   * @param shftAmt the amount of characters that str should be shifted
   * @return an encryptd string based on the given shftAmt
   */
  def encryptChar(shftAmt: Int)(char: Char): Char = {

    val totalLetters = 26
    //65 to 90 is A-Z  in ASCII
    //97 to 122 is a-z in ASCII
    if (isLetter(char)){
      val encrypted = char.toInt +  shftAmt
      if(isOverLimit(char,shftAmt)){
        (encrypted - totalLetters).toChar
         }
      else if (isBelowLimit(char,shftAmt)){
        (encrypted + totalLetters).toChar
      } else {
          encrypted.toChar
      }
     } else{
       char
     }
    }

  def isOverLimit(char:Char,ret:Int): Boolean ={
     (char.toLower.toInt + ret > 122)
  }

  def isBelowLimit(char:Char,ret:Int): Boolean ={
   ( char.toUpper.toInt + ret < 65)
  }

  def isLetter(char:Char)={
   val upped =  char.toUpper
    upped <= 'Z' && upped >= 'A'
  }

  def encrypt(shftAmt: Int)(str: String):String={
    str.toCharArray.map(encryptChar(shftAmt)(_)).mkString
  }

  /**
   * @param shftAmt the amount of characters that str should be shifted
   * @return a decryptd string based on the given shftAmt
   */
  def decryptChar(shftAmt: Int)(char:Char): Char = {
     encryptChar(shftAmt * - 1)(char)
  }

  def decrypt(shftAmt: Int)(str:String): String = {
    str.toCharArray.map(decryptChar(shftAmt * - 1)(_)).mkString
  }
}
