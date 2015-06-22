package com.cognia.cypher

/**
 * User: fran
 * Date: 21/06/2015
 */
trait Cipher {

  def encrypt(key: Int)(str: String):String

  def decrypt(key: Int)(str:String): String

}
