package tk.avabin.genetic.ga
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape.{Circle, Shape}

class Individual(var chromosome: Array[Move], val point: Point = new Point(), val distancePerMove: Double = 1.0)
extends Drawable{
  var moveIndex = 0
  def genes: Array[Move] = chromosome

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder
    for (gene <- chromosome) {
      sb.append(gene)
    }
    sb.toString
  }

  def applyNextMove(): Boolean = {
    if(moveIndex == chromosome.length) return false
    applyMove(chromosome(moveIndex))
    moveIndex += 1
    true
  }

  def applyMove(move: Move): Unit = {
    move.directions match {
      case "00" => moveUp()
      case "01" => moveDown()
      case "10" => moveRight()
      case "11" => moveLeft()
    }
  }

  def moveUp(): Unit = {
    this.point.yInc(distancePerMove)
  }
  def moveDown(): Unit = {
    this.point.yDec(distancePerMove)
  }
  def moveRight(): Unit = {
    this.point.xInc(distancePerMove)
  }
  def moveLeft(): Unit = {
    this.point.xDec(distancePerMove)
  }

  override val fillPaint: Paint = Color.DarkRed
  override val strokePaint: Paint = Color.Black
  override val shape: String = "circle"

  override def getAsShape(): Shape = {
    new Circle() {
      centerX = point.x
      centerY = point.y
      radius = 15
    }
  }

  override val width: Double = 15
  override val height: Double = 15
}
