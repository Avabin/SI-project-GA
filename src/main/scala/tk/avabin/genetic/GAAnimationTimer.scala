package tk.avabin.genetic

import javafx.animation.AnimationTimer

/**
  * @author Avabin
  */
class GAAnimationTimer() extends AnimationTimer {
  override def handle(now: Long): Unit = {
    Drawer.clearCanvas()
    Drawer.drawCartesian(25)
    Drawer.drawAll()
  }
}
