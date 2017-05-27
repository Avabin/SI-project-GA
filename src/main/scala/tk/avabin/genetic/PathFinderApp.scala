package tk.avabin.genetic

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.layout.{GridPane, HBox, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

/**
  * @author avabin
  */
object PathFinderApp extends JFXApp{
  val version = 0.1
  stage = new PrimaryStage {
    width = 860
    height = 640
    scene = new Scene() {
      title = s"PathFinder app $version"
      content = new GridPane() {
        padding = Insets(10)
        val controlBox = new VBox() {
          alignment = Pos.BottomCenter
          val generationNumberLabel: Label = new Label("Number of generations (40)")
          val generationNumberInput: TextField = new GenericTextField()

          val chromosomeSizeLabel: Label = new Label("Chromosome size (50)")
          val chromosomeSizeInput: TextField = new GenericTextField()

          val popSizeLabel: Label = new Label("Population size (20)")
          val popSizeInput: TextField = new GenericTextField()

          val mutationRateLabel: Label = new Label("Mutation rate (5 / chromosome size)")
          val mutationRateInput: TextField = new GenericTextField()

          val crossRateLabel: Label = new Label("Crossover rate (0.5)")
          val crossRateInput: TextField = new GenericTextField()

          val targetLabel = new Label("Target coordinates (25, 25)")
          val targetInputBox = new HBox() {
            alignment = Pos.BottomCenter
            val xInput = new GenericTextField(60)
            val yInput = new GenericTextField(60)
            children = Seq(
              xInput,
              yInput
            )
          }

          val controlInputs = new HBox() {
            alignment = Pos.BottomCenter
            val startB = new GenericButton(text = "Start")
            val pauseB = new GenericButton(text = "Pause")
            val stopB = new GenericButton(text = "Stop")

            children = Seq(
              startB,
              pauseB,
              stopB
            )
          }


          children = Seq(
            generationNumberLabel,
            generationNumberInput,
            chromosomeSizeLabel,
            chromosomeSizeInput,
            popSizeLabel,
            popSizeInput,
            mutationRateLabel,
            mutationRateInput,
            crossRateLabel,
            crossRateInput,
            targetLabel,
            targetInputBox,
            controlInputs
          )
        }
          val canvas = new Canvas(new javafx.scene.canvas.Canvas(400, 400)) {
            graphicsContext2D.fillRect(0, 0, this.width.value, this.height.value)
          }
        add(controlBox, 0, 0)
        add(canvas, 1, 0)
      }
    }
  }

}
