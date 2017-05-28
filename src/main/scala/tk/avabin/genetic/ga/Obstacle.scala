package tk.avabin.genetic.ga
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape.{Rectangle, Shape}

/**
  * @author Avabin
  */
class Obstacle(val point: Point, val width: Double, val height: Double) extends Drawable{
  def this(x: Double, y: Double, w: Double, h: Double) = this(new Point(x, y), w, h)
  override val fillPaint: Paint = Color.Brown
  override val strokePaint: Paint = Color.Black
  override val shape: String = "rectangle"

  def w: Double = width
  def h: Double = height

  override def getAsShape: Shape = {
    new Rectangle() {
      this.x = point.x
      this.y = point.y
      width = w
      height = h
    }
  }
}
