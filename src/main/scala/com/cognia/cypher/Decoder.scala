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
          (lineNum - (decodingIndex.head.start + decodingIndex.head.length)) match {
            //when reaches limit we'll use next decodingIndex
            case 0 => trad(xs,lineNum+1,decodingIndex.tail,decypher(26-decodingIndex.tail.head.rotation)(x) #:: tradStream)
            //otherwise the index that is in the range
            case _ => trad(xs,lineNum+1,decodingIndex,decypher(26-decodingIndex.head.rotation)(x) #:: tradStream)
          }
        }
      }
    }

    val res = trad(lines,1,decodingIndex,empty).reverse
    res.foreach(println)
    res
  }

}
