package tk.avabin.genetic

import tk.avabin.genetic.ga.Obstacle

import scala.util.Random

/**
  * @author Avabin
  */
object ObstacleGenerator {
  private val r = new Random(System.nanoTime())
  var maxHeight = 100
  var minHeigth = 25
  var maxWidth = 100
  var minWidth = 25
  var maxX = 650
  var maxY = 650

  def next(): Obstacle = {
    new Obstacle(r.nextInt(maxX), r.nextInt(maxY), r.nextInt(maxWidth) + minWidth, r.nextInt(maxHeight) + minHeigth)
  }

}
