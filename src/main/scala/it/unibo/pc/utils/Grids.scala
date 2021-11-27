package it.unibo.pc.utils

object Grids {
  // creates the useful grid-like neighboring relation
  def createRectangularGrid(n: Int, m: Int): Map[(Int, Int), Set[(Int, Int)]] =
    (for {
      i <- (0 until n).toSet[Int]
      j <- 0 until m
      (k, l) <- Set((i - 1, j), (i + 1, j), (i, j - 1), (i, j + 1))
      if k >= 0 && k < n && l >= 0 && l < m
    } yield ((i, j), (k, l))).groupBy(_._1).map((k, v) => (k, v.map(_._2)))

  // pretty printing a grid
  def gridLikeToString(rows: Int, cols: Int, obs: (Int, Int) => String): String =
    (for {
      j <- 0 to rows
      str = (for (i <- 0 to cols; s = obs(i, j)) yield s).mkString("\t")
    } yield str).mkString("\n")
}