package com.cognia.cypher

import com.cognia.reader.Dictionary

import scala.collection.immutable.Stream._

/**
 * User: fran
 * Date: 21/06/2015
 */
object Hacker {


  /**
   * in order to check the last line but avoiding state
   * and therefore concurrency race conditions
   * while we can use last bestGuess of last line
   * @param lines
   * @param decypher
   * @return
   */
  def trad(lines:Stream[String],decypher:Int=>String=>String): Stream[String] ={
    def trad(lines:Stream[String],lastBestGuess:Int,tradStream:Stream[String]): Stream[String] ={
      lines match {
        case Stream.Empty => tradStream
        case x #:: xs => {
          val ret = bestGuess(x.split(" ").toList,decypher)
          trad(xs,ret,decypher(ret)(x) #:: tradStream)
        }
      }
    }
    trad(lines,0,empty).reverse
  }




  /**
   *  Look up a dictionary in order to detect
   *  after iterating over diff shifts what's
   *  the best guess. The shifts that after decrypt
   *  finds more words in a pretty basic dictionary
   *  is the best guess
   * @param line
   * @param decypher
   * @return
   */
  def bestGuess(line:List[String],decypher:Int=>String=>String): Int = {
    def bestGuess(line: List[String], bestMarkShift: (Int, Int), ret: Int, decypher: Int => String => String): Int = {
      //once zero has been processed (we get over -1 and exit)
      if (ret == -1) bestMarkShift._2
      else {
        //counting total words.txt we find in the dictionary
        val totalFound = line.foldLeft(0)((acc, word) => acc + Dictionary.inDictionary(decypher(ret)(word)))
        val best = if (bestMarkShift._1 > totalFound) bestMarkShift else (totalFound, ret)
        bestGuess(line, best, ret - 1, decypher)
      }
    }
    bestGuess(line, (0, 0), 25, decypher)
  }






  /**
   *
   * @param str
   * @return
   */
  def retainJustLetters(str:String): String ={
    str.toCharArray.filter(p => p.toUpper >= 'A' && p.toUpper <= 'Z').mkString
  }

}
