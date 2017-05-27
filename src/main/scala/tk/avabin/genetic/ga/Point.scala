package tk.avabin.genetic.ga

import javafx.beans.property.SimpleDoubleProperty

import scalafx.beans.property.DoubleProperty

/**
  * @author Avabin
  */
class Point(var xProperty:DoubleProperty = new DoubleProperty(), var yProperty:DoubleProperty = new DoubleProperty()) {
  def this( x: Double, y: Double) = this(new DoubleProperty(){value = x}, new DoubleProperty(){value = y})
  def x: Double = xProperty.value
  def y: Double = yProperty.value
  def xInc(n: Double): Unit = xProperty.value = x + n
  def yInc(n: Double): Unit = yProperty.value = y + n
  def xDec(n: Double): Unit = xProperty.value = x - n
  def yDec(n: Double): Unit = yProperty.value = y - n
}
