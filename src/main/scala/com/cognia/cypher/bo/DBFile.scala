package com.cognia.cypher.bo

import java.util

/**
 * User: fran
 * Date: 22/06/2015
 */
case class DBFile(data:StringBuilder,decodingIndexes: util.Queue[DecodingIndex])
