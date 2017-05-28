package tk.avabin.genetic.ga

import javafx.beans.property.SimpleDoubleProperty

import scalafx.beans.property.DoubleProperty

/**
  * @author Avabin
  */
class Point(var x: Double, var y: Double) {
  def copy(): Point = {
    new Point(x, y)
  }
  def xInc(n: Double): Unit = x = x + n
  def yInc(n: Double): Unit = y = y + n
  def xDec(n: Double): Unit = x = x - n
  def yDec(n: Double): Unit = y = y - n
}
