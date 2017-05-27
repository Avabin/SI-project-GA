package tk.avabin.genetic

import javafx.animation.AnimationTimer

import scala.collection.mutable.ArrayBuffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.shape.Circle

/**
  * @author Avabin
  */
class GAAnimationTimer(var circles: ArrayBuffer[Circle] = new ArrayBuffer[Circle](), gc: GraphicsContext) extends AnimationTimer {

  def addCircle(circle: Circle): Unit = {
    this.circles.append(circle)
  }

  override def handle(now: Long): Unit = {
    for(circle <- circles) {
      gc stroke = Color.color(1, 0, 0)
      gc.strokeOval(circle.centerX(), circle.centerY(), circle.radius(), circle.radius())
      gc.fill = Color.color(1, 0.3, 0.1)
      gc.fillOval(circle.centerX(), circle.centerY(), circle.radius()-1, circle.radius()-1)
    }
  }
}
