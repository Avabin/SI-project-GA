package tk.avabin.genetic.ga
import scala.collection.mutable.ArrayBuffer
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape.{Circle, Rectangle, Shape}

class Individual(var chromosome: Array[Move], val point: Point, val distancePerMove: Double)
extends Drawable{
  var colliders = new ArrayBuffer[Drawable]()
  var finalMoveIndex: Int = 1
  var moveIndex = 0
  var collided = false
  var isInTarget = false
  def genes: Array[Move] = chromosome

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder
    for (gene <- chromosome) {
      sb.append(gene)
    }
    sb.toString
  }

  def noteMove(): Unit = {
    finalMoveIndex = moveIndex + 1
  }

  def applyNextMove(): Boolean = {
    colliders.foreach((d) => {
      if(d.isCollision(this)) {
        collided = true
        d match {
          case _: Target => isInTarget = true
          case _ =>
        }
      }
    })
    if(collided || (moveIndex >= finalMoveIndex && finalMoveIndex > 0) || moveIndex == chromosome.length) {
      noteMove()
      return false
    }
    moveIndex += 1
    finalMoveIndex += 1
    applyMove(chromosome(moveIndex))
    moveIndex += 1
    finalMoveIndex += 1
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
  override val shape: String = "rectangle"

  override def getAsShape(): Shape = {
    new Rectangle(new javafx.scene.shape.Rectangle(x, y, width, height)) {}
  }

  override val width: Double = 5
  override val height: Double = 5
}
