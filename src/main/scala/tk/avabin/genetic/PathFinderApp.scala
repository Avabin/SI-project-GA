package tk.avabin.genetic

import tk.avabin.genetic.ga.{Individual, Move, Point}

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.util.Random
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.effect.BoxBlur
import scalafx.scene.layout.{GridPane, HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

/**
  * @author avabin
  */
object PathFinderApp extends JFXApp{
  val version = 0.1
  stage = new PrimaryStage {
    width = 875
    height = 705
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

          val targetXInput = new GenericTextField(90)
          val targetYInput = new GenericTextField(90)
          val targetLabel = new Label("Target coordinates (125, 125)")
          val targetInputBox = new HBox() {
            alignment = Pos.BottomCenter
            children = Seq(
              targetXInput,
              targetYInput
            )
          }


          val startXInput = new GenericTextField(90)
          val StartYInput = new GenericTextField(90)
          val startLabel = new Label("Start point (0, 0)")
          val startInputBox = new HBox() {
            alignment = Pos.BottomCenter
            children = Seq(
              startXInput,
              StartYInput
            )
          }

          val controlInputs = new HBox() {
            alignment = Pos.BottomCenter
            val startB = new GenericButton(text = "Start")
            val pauseB = new GenericButton(text = "Pause")
            val stopB = new GenericButton(text = "Stop")

            startB.onMouseClicked = (_) => {

              }


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
            startLabel,
            startInputBox,
            controlInputs
          )
        }
          val canvas = new Canvas() {
            val gc: GraphicsContext = graphicsContext2D
            val animationTimer: GAAnimationTimer = new GAAnimationTimer()
            this.width = 650
            this.height = 650
            val centerX: Double = this.width() / 2
            val centerY: Double = centerX
            val random = new Random(System.nanoTime())
            val thisW: Double = this.width()
            val thisH: Double = this.height()
            Drawer gc = gc
            animationTimer.start()
          }

        add(controlBox, 0, 0)
        add(canvas, 1, 0, 2, 2)
      }
    }
  }

}
