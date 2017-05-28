package tk.avabin.genetic.ga
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape.{Circle, Rectangle, Shape}

/**
  * @author Avabin
  */
class Target(val point: Point, val radius: Double) extends Drawable{
  def this(x:Double, y:Double) = this(new Point(x, y), 25)
  def this(x:Double, y:Double, r:Double) = this(new Point(x, y), r)

  def isPointIn(targetPoint: Point): Boolean = {
    val dx = math.abs(targetPoint.x - point.x)
    val dy = math.abs(targetPoint.y - point.y)
     dx * dx + dy * dy <= radius * radius
  }

  override val fillPaint: Paint = Color.Blue
  override val strokePaint: Paint = Color.Black
  override val shape: String = "rectangle"

  override def getAsShape(): Shape = {
    new Rectangle(new javafx.scene.shape.Rectangle(x, y, width, height))
  }

  def getRadius: Double = radius

  override val width: Double = radius
  override val height: Double = radius
}
