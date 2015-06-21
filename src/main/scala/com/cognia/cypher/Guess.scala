package com.cognia.cypher

import com.cognia.reader.Reader

import scala.collection.immutable.Stream._

/**
 * User: fran
 * Date: 21/06/2015
 */
class Guess {


   //TODO pass functions!! with a signature (key)(word)
  def bestGuess(line:List[String],decypher:Int=>String=>String): Int ={
    def bestGuess(line:List[String],bestFoundRet:(Int,Int),ret:Int,decypher:Int=>String=>String):Int={
      if(    (bestFoundRet._1.toFloat./(line.length.toFloat)).toDouble.round == 1) return bestFoundRet._2
      if(ret == 0) bestFoundRet._2
      else {
        //counting total words we find in the dictionary
        val totalFound = line.foldLeft(0)( (acc,word) =>
          acc + Reader.inDictionary(decypher(ret)(word))
        )
        val best = if (bestFoundRet._1 > totalFound) bestFoundRet else (totalFound,ret)
        bestGuess(line,best,ret-1,decypher)
      }
    }
     //TODO avoid coupling!!!!!
    val best = bestGuess(line,(0,0),25,decypher)
    // println("best "+best)
    // println("line "+line)
    if(best <= 1){
      println("###########  "+best)
    }
    best
  }

  //TODO  check first word if not best guess
  def sameOld(word:String,guess:Int):Boolean={
    Reader.inDictionaryWord(Decrypter.decrypt(guess)(retainJustLetters(word)))
  }

  def firstGuess(line:List[String],guess:Int): Boolean ={
    //line.flatten match {case Nil => 1L}
    val ratio = line.foldLeft(0)( (acc,word) =>
      acc + Reader.inDictionary(Decrypter.decrypt(guess)(retainJustLetters(word)))
    )
    (ratio.toFloat./(line.length.toFloat)).toDouble.round == 1
  }

  def retainJustLetters(str:String): String ={
    str.toCharArray.filter(p => p.toUpper >= 'A' && p.toUpper <= 'Z').mkString
  }



//  def doSome(): Unit ={
//    println("first lines ------------ encoding")
//    Reader.in.getLines().toStream.take(19).foreach( f =>
//      println(f.getBytes.mkString)
//    )
//    println("first lines ------------ encoding")
//
//            Reader.in.getLines().toStream.drop(19).foreach( line =>
//              //val pastBestGuess =  bestGuess(line.split(" ").toList)
//                println(Decrypter.decrypt(bestGuess(line.split(" ").toList,Decrypter.decrypt))(line))
//
//            )
//
//
//
//  }
//
//  def doSomeTrad(): Unit ={
//    trad(Reader.in.getLines().toStream.drop(19),Decrypter.decrypt).foreach(println)
//  }

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
      if(words.isEmpty || (words.size == 1 && words.head.isEmpty) || sameOld(words.head,lastGuess) || firstGuess(words.toList,lastGuess)){
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


}
