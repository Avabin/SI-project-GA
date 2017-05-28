package tk.avabin.genetic.ga

import scalafx.scene.paint.Paint
import scalafx.scene.shape.Shape

/**
  * @author Avabin
  */
trait Drawable {
  val point: Point
  val width: Double
  val height: Double
  val fillPaint: Paint
  val strokePaint: Paint
  val shape: String

  def x: Double = point.x - width / 2
  def y: Double = point.y - height / 2

  def getAsShape: Shape

}
