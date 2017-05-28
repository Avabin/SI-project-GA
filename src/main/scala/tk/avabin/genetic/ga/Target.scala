package tk.avabin.genetic.ga
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape.{Circle, Shape}

/**
  * @author Avabin
  */
class Target(val point: Point = new Point(), val radius: Double = 40) extends Drawable{
  def this(x:Double, y:Double) = this(new Point())
  def isPointIn(targetPoint: Point): Boolean = {
    val dx = math.abs(targetPoint.x - point.x)
    if (dx > radius) return false
    val dy = math.abs(targetPoint.y - point.y)
    if (dy > radius) return false
    if (dx + dy <= radius) return true
     dx * dx + dy * dy <= radius * radius
  }

  override val fillPaint: Paint = Color.Blue
  override val strokePaint: Paint = Color.Black
  override val shape: String = "circle"

  override def getAsShape(): Shape = {
    new Circle(){
      centerX <== point.xProperty
      centerY <== point.yProperty
      radius = getRadius
    }
  }

  def getRadius: Double = radius

  override val width: Double = radius
  override val height: Double = radius
}
