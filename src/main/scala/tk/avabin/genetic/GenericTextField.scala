package tk.avabin.genetic

import scalafx.geometry.Insets
import scalafx.scene.control.TextField

/**
  * @author avabin
  */
class GenericTextField(width: Double = 180.0) extends TextField {
  margin = Insets(10, 0, 10, 0)
  this.setMaxWidth(width)
  this.setMinWidth(width)

}
