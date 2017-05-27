package tk.avabin.genetic

import tk.avabin.genetic.ga.Move

import scala.util.Random

/**
  * @author Avabin
  */
object MoveGenerator {
  val random = new Random()

  def next(): Move = {
    new Move(random.nextInt(2).toString + random.nextInt(2).toString)
  }

}
