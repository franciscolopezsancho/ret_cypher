package com.cognia.cypher

import java.util

import com.cognia.cypher.bo.DecodingIndex

import scala.collection.immutable.Queue
import scala.collection.immutable.Stream._

/**
 * User: fran
 * Date: 22/06/2015
 */
object Decoder {

  def decodeAll(data:String,decoders:util.Queue[DecodingIndex],decypher:Int=>String=>String): Stream[String] ={
    def decodeAll(data:String,decoders:util.Queue[DecodingIndex],tradStream:Stream[String]): Stream[String] ={
      decoders.poll() match {
        case _ => tradStream
        case d:DecodingIndex =>
        decodeAll (data, decoders, decypher (d.rotation) (data.substring (d.start, d.length) ) #:: tradStream)
      }
    }
    var res = decodeAll(data,decoders,empty)
    res.foreach(println)
    res
  }

  def trad(lines:Stream[String],decypher:Int=>String=>String,decodingIndex:Queue[DecodingIndex]): Stream[String] ={
    def trad(lines:Stream[String],lineNum:Int,decodingIndex:Queue[DecodingIndex],tradStream:Stream[String]): Stream[String] ={
      lines match {
        case Stream.Empty => tradStream
        case x #:: xs => {
          //when reaches limit we'll use next decodingIndex
          //TODO in case there's a wrong number of decodings?!
          println("####################line numbers "+lineNum)
          (lineNum - (decodingIndex.head.start + decodingIndex.head.length)) match {
            case 0 => trad(xs,lineNum+1,decodingIndex.tail,decypher(decodingIndex.head.rotation)(x) #:: tradStream)
            case _ => trad(xs,lineNum+1,decodingIndex,decypher(decodingIndex.head.rotation)(x) #:: tradStream)
          }
        }
      }
    }

    val res = trad(lines,0,decodingIndex,empty).reverse
    res.foreach(println)
    res
  }

}
