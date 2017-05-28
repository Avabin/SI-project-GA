package tk.avabin.genetic

import tk.avabin.genetic.ga.Drawable

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

/**
  * @author Avabin
  */
object Drawer {
  var gc: GraphicsContext = _
  var buffer: ArrayBuffer[Drawable] = new ArrayBuffer[Drawable]()

  def addDrawable(drawable: Drawable): Unit = buffer.append(drawable)

  def clearBuffer(): Unit = buffer = new ArrayBuffer[Drawable]()

  def clearCanvas(): Unit = {
    gc.clearRect(0, 0, gc.canvas.getWidth, gc.canvas.getHeight)
  }

  def drawAll(): Unit = {
    buffer.foreach((d) => draw(d))
  }

  def draw(d: Drawable): Unit = {
    gc stroke = d.strokePaint
    gc fill = d.fillPaint
    d.shape match {
      case "circle" =>
        gc.strokeOval(d.point.x, d.point.y, d.width, d.height)
        gc.fillOval(d.point.x, d.point.y, d.width, d.height)
      case "rectangle" =>
        gc.strokeRect(d.point.x, d.point.y, d.width, d.height)
        gc.fillRect(d.point.x, d.point.y, d.width, d.height)
    }
  }


  def drawCartesian(offset: Double): Unit = {
    gc stroke = Color.DarkBlue
    gc.strokeLine(0, 0, 0, gc.canvas.getHeight)
    gc.strokeLine(0, 0, gc.canvas.getWidth, 0)
    gc lineWidth = 1
    for(i <- 0 until (gc.canvas.getHeight / offset).toInt) {
      val x: Double = i*offset
      gc.strokeLine(0, gc.canvas.getHeight - x, gc.canvas.getWidth, gc.canvas.getHeight - x)
      gc.strokeLine(gc.canvas.getWidth - x, 0, gc.canvas.getHeight - x, gc.canvas.getHeight)
    }
  }

}
