package com.cognia.cypher

import java.util

import com.cognia.cypher.bo.DecodingIndex

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

}
