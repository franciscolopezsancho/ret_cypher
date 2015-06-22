package com.cognia.writer

/**
 * User: fran
 * Date: 21/06/2015
 */
object Writer {

  /**
   *
   * @param is
   * @param f
   */
  def inputToFile(is: Stream[String], f: java.io.File) {
    val out = new java.io.PrintWriter(f)
    try { is.foreach(out.println) }
    finally { out.close }
  }

}
