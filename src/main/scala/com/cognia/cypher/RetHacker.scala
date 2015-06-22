package com.cognia.cypher

import com.cognia.reader.Reader

import scala.collection.immutable.Stream._

/**
 * User: fran
 * Date: 21/06/2015
 */
class RetHacker {


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
          //avoiding here execute best guess checking first if
          //last guess works well for this line
         // val ret = tryLast(x.split(" ").toList,lastBestGuess)
          val ret = bestGuess(x.split(" ").toList,decypher)
          println(decypher(ret)(x))
          trad(xs,ret,decypher(ret)(x) #:: tradStream)
        }
      }
    }
    def tryLast(words:List[String],lastGuess:Int): Int = {
      if(words.isEmpty || (words.size == 1 && !isWord(words.head)) || sameOld(words.head,lastGuess,decypher) ){
        lastGuess
      }  else {
        val best = bestGuess(words,decypher)
        if(best == 0){
          lastGuess
        }else{
            best
        }
      }
    }
    trad(lines,0,empty).reverse
  }

  def isWord(word:String):Boolean={
      !word.toCharArray.filter(p => RetCipher.isLetter(p)).isEmpty
  }


  /**
   *  Look up a dictionary in order to detect
   *  after iterating over diff shifts what's
   *  the best guess. The key that after decrypt
   *  finds more words.txt in a pretty basic dictionary
   * @param line
   * @param decypher
   * @return
   */
  def bestGuess(line:List[String],decypher:Int=>String=>String): Int = {
    def bestGuess(line: List[String], bestMarkShift: (Int, Int), ret: Int, decypher: Int => String => String): Int = {
     // if ((bestMarkKey._1.toFloat./(line.length.toFloat)).toDouble.round == 1) return bestMarkKey._2
      if (ret == -1) bestMarkShift._2
      else {
        //counting total words.txt we find in the dictionary
        val totalFound = line.foldLeft(0)((acc, word) =>
          acc + Reader.inDictionary(decypher(ret)(word))
        )
        val best = if (bestMarkShift._1 > totalFound) bestMarkShift else (totalFound, ret)
        bestGuess(line, best, ret - 1, decypher)
      }
    }
    bestGuess(line, (0, 0), 25, decypher)
  }



  /**
   *  Mainly used to avoid to do a best guess if first word
   *  of a line can be found in the dictionary using the last
   *  guess
   * @param word
   * @param lastGuess
   * @param decypher
   * @return
   */
  def sameOld(word:String,lastGuess:Int,decypher:Int=>String=>String):Boolean={
    Reader.inDictionaryWord(decypher(lastGuess)(retainJustLetters(word)))
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
