package com.cognia.cypher

import com.cognia.cypher.bo.DecodingIndex

import scala.collection.immutable.Queue
import scala.collection.immutable.Stream._

/**
 * User: fran
 * Date: 22/06/2015
 */
object Decoder {



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

    trad(lines,1,decodingIndex,empty).reverse

  }

}
