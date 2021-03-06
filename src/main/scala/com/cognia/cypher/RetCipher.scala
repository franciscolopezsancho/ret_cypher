package com.cognia.cypher

/**
 * User: fran
 * Date: 19/06/2015
 */
object RetCipher {

  /**
   *
   * @param shftAmt
   * @param char
   * @return
   */
  def encryptChar(shftAmt: Int)(char: Char): Char = {

    val totalLetters = 26

    if (isLetter(char)){
        val encrypted = char.toInt +  shftAmt
        if(isOverLimit(char,shftAmt)){
          (encrypted - totalLetters).toChar
        }else if (isBelowLimit(char,shftAmt)){
          (encrypted + totalLetters).toChar
        }else {
            encrypted.toChar
        }
     } else{
       char
     }
    }

  //97 to 122 is a-z in ASCII
  def isOverLimit(char:Char,ret:Int): Boolean ={
     char.toLower.toInt + ret > 122
  }

  /**
   *
   * @param char
   * @param ret
   * @return
   */
  //65 to 90 is A-Z  in ASCII
  def isBelowLimit(char:Char,ret:Int): Boolean ={
    char.toUpper.toInt + ret < 65
  }

  /**
   *
   * @param char
   * @return
   */
  def isLetter(char:Char)={
   val upped =  char.toUpper
    upped <= 'Z' && upped >= 'A'
  }

  /**
   *
   * @param shftAmt
   * @param str
   * @return
   */
  def encrypt(shftAmt: Int)(str: String):String={
    str.toCharArray.map(encryptChar(shftAmt)(_)).mkString
  }

  /**
   *
   * @param shftAmt
   * @param char
   * @return
   */
  def decryptChar(shftAmt: Int)(char:Char): Char = {
     encryptChar(shftAmt * - 1)(char)
  }

  /**
   *
   * @param shftAmt
   * @param str
   * @return
   */
  def decrypt(shftAmt: Int)(str:String): String = {
    str.toCharArray.map(decryptChar(shftAmt * - 1)(_)).mkString
  }
}
