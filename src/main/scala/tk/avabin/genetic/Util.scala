package tk.avabin.genetic

import tk.avabin.genetic.ga.{Individual, Point, Target}

import scalafx.beans.property.DoubleProperty
import scalafx.scene.shape.Circle

/**
  * @author Avabin
  */
object Util {
  def calculateDistance(a: Point, b: Point): Double = {
    math.sqrt(math.pow(a.x - b.x, 2) + math.pow(a.y - b.y, 2))
  }

  def individual2circle(individual: Individual, r: Double): Circle = {
    new Circle() {
      centerX <== individual.point.xProperty
      centerY <== individual.point.yProperty
      radius = r
    }
  }

  def target2circle(target: Target, r: Double): Circle = {
    new Circle() {
      centerX <== target.point.xProperty
      centerY <== target.point.yProperty
      radius = r
    }
  }
}
