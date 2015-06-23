package com.cognia.writer

/**
 * User: fran
 * Date: 21/06/2015
 */
object Writer {

  /**
   *
   * @param stream
   * @param file
   */
  def inputToFile(stream: Stream[String], file: java.io.File) {
    val out = new java.io.PrintWriter(file)
    try { out.println(stream.mkString) }
    finally { out.close }
  }

}
