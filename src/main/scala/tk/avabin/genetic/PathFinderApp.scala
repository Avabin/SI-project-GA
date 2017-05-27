package tk.avabin.genetic

import tk.avabin.genetic.ga.{Individual, Move, Point}

import scala.collection.mutable.ArrayBuffer
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
  val circles = new ArrayBuffer[Circle]()
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
            val individual: Individual = new Individual(new Array[Move](0), new Point(100, 100), 50)
            alignment = Pos.BottomCenter
            val startB = new GenericButton(text = "Start")
            val pauseB = new GenericButton(text = "Pause")
            val stopB = new GenericButton(text = "Stop")

            circles.append(Util.individual2circle(individual, 100))

            startB.onMouseClicked = (_) => {
                individual.applyMove(new Move("01"))
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
            controlInputs
          )
        }
          val canvas = new Canvas() {

            val gc: GraphicsContext = graphicsContext2D
            val animationTimer: GAAnimationTimer = new GAAnimationTimer(circles, gc)
            this.width = 650
            this.height = 650
            val centerX: Double = this.width() / 2
            val centerY: Double = centerX
            val random = new Random(System.nanoTime())
            val thisW: Double = this.width()
            val thisH: Double = this.height()

            graphicsContext2D.setStroke(Color.DarkBlue)
            graphicsContext2D.strokeLine(0, 0, 0, this.height())
            graphicsContext2D.strokeLine(0, 0, this.width(), 0)
            graphicsContext2D.lineWidth = 1
            for(i <- 0 until (this.height().toInt / 25)) {
              val x: Double = i*25
              graphicsContext2D.strokeLine(0, this.height() - x, this.width(), this.height() - x)
              graphicsContext2D.strokeLine(this.width() - x, 0, this.height() - x, this.height())
            }


            for (i <- 0 until 20) {
              animationTimer.addCircle(new Circle(){
                radius = 10
                centerX = random.nextInt(thisW.toInt)
                centerY = random.nextInt(thisH.toInt)
                this.fill = Color.Red
              })
            }
            animationTimer.addCircle(new Circle(){
              radius = 25
              centerX = 400
              centerY = 100
            })
            animationTimer.start()
            Thread.sleep(2000)

          }

        add(controlBox, 0, 0)
        add(canvas, 1, 0, 2, 2)
      }
    }
  }

}
