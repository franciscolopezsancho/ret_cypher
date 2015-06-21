package com.cognia.cypher

import com.cognia.reader.Reader

import scala.collection.immutable.Stream._

/**
 * User: fran
 * Date: 21/06/2015
 */
class Guess {








  //in order to check the last line avoiding state
  //and therefore concurrency race conditions
  //while we can use last bestGuess of last line
  def trad(lines:Stream[String],decypher:Int=>String=>String): Stream[String] ={
    def trad(lines:Stream[String],lastBestGuess:Int,tradStream:Stream[String]): Stream[String] ={
      lines match {
        case Stream.Empty => tradStream
        case x #:: xs => {
          //avoiding here execute best guess checking first if lastguess
          //works well for this line
          val ret = tryLast(x.split(" ").toList,lastBestGuess)
          println(decypher(ret)(x))
          trad(xs,ret,Stream(decypher(ret)(x)))
        }
      }
    }
    def tryLast(words:List[String],lastGuess:Int): Int = {
      if(words.isEmpty || (words.size == 1 && words.head.isEmpty) || sameOld(words.head,lastGuess) ){
        lastGuess
      }  else {
        val best = bestGuess(words,decypher)
        if(best == 1){
          lastGuess
        }  else{
          best
        }
      }
    }
    val res = trad(lines,0,empty)
    res
  }

  //Look up a dictionary in order to detect
  //after iterating over diff keys what's
  //the best guess. The key that after decrypt
  // finds more words in a pretty basic dictionary
  def bestGuess(line:List[String],decypher:Int=>String=>String): Int = {
    def bestGuess(line: List[String], bestFoundRet: (Int, Int), ret: Int, decypher: Int => String => String): Int = {
      if ((bestFoundRet._1.toFloat./(line.length.toFloat)).toDouble.round == 1) return bestFoundRet._2
      if (ret == 0) bestFoundRet._2
      else {
        //counting total words we find in the dictionary
        val totalFound = line.foldLeft(0)((acc, word) =>
          acc + Reader.inDictionary(decypher(ret)(word))
        )
        val best = if (bestFoundRet._1 > totalFound) bestFoundRet else (totalFound, ret)
        bestGuess(line, best, ret - 1, decypher)
      }
    }
    bestGuess(line, (0, 0), 25, decypher)
  }


  //TODO  check a word is after decrypted in a dictiory
  //mainly used to avoid to do a best guess if first word
  //of a line can be found in the dictionary using the last
  //guess
  def sameOld(word:String,lastGuess:Int):Boolean={
    Reader.inDictionaryWord(Decrypter.decrypt(lastGuess)(retainJustLetters(word)))
  }


//  def firstGuess(line:List[String],guess:Int): Boolean ={
//    val ratio = line.foldLeft(0)( (acc,word) =>
//      acc + Reader.inDictionary(Decrypter.decrypt(guess)(retainJustLetters(word)))
//    )
//    (ratio.toFloat./(line.length.toFloat)).toDouble.round == 1
//  }

  def retainJustLetters(str:String): String ={
    str.toCharArray.filter(p => p.toUpper >= 'A' && p.toUpper <= 'Z').mkString
  }

}
