package tk.avabin.genetic

import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.control.ButtonBase.sfxButtonBase2jfx

/**
  * @author avabin
  */
class GenericButton(width: Double = 60, text:String = "") extends Button {
  this.setText(text)
  margin = Insets(10, 0, 10, 0)
  this.setMinWidth(width)
  this.setMaxWidth(width)
}
