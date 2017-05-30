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
  var persistentBuffer: ArrayBuffer[Drawable] = new ArrayBuffer[Drawable]()

  def addDrawable(drawable: Drawable): Unit = buffer.append(drawable)
  def addPermDrawable(drawable: Drawable): Unit = persistentBuffer.append(drawable)

  def clearBuffer(): Unit = buffer = new ArrayBuffer[Drawable]()
  def clearPermBuffer(): Unit = persistentBuffer = new ArrayBuffer[Drawable]()

  def clearCanvas(): Unit = {
    gc.clearRect(0, 0, gc.canvas.getWidth, gc.canvas.getHeight)
  }

  def drawAll(): Unit = {
    persistentBuffer.foreach((d) => draw(d))
    buffer.foreach((d) => draw(d))
  }

  def draw(d: Drawable): Unit = {
    gc stroke = d.strokePaint
    gc fill = d.fillPaint
    val x = d.x
    val y = d.y
    val w = d.width
    val h = d.height
    d.shape match {
      case "circle" =>
        gc.strokeOval(x, y, w, h)
        gc.fillOval(x, y, w, h)
      case "rectangle" =>
        gc.strokeRect(x, y, w, h)
        gc.fillRect(x, y, w, h)
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
