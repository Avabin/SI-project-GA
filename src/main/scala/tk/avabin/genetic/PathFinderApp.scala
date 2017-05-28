package tk.avabin.genetic

import tk.avabin.genetic.ga._

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
  var runnerTask: GARunnerTask = _
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

          val generationNumberLabel: Label = new Label("Number of generations (35)")
          val generationNumberInput: TextField = new GenericTextField()

          val chromosomeSizeLabel: Label = new Label("Chromosome size (300)")
          val chromosomeSizeInput: TextField = new GenericTextField()

          val popSizeLabel: Label = new Label("Population size (35)")
          val popSizeInput: TextField = new GenericTextField()

          val mutationRateLabel: Label = new Label("Mutation rate (0.015)")
          val mutationRateInput: TextField = new GenericTextField()

          val crossRateLabel: Label = new Label("Crossover rate (0.5)")
          val crossRateInput: TextField = new GenericTextField()

          val delayLabel: Label = new Label("Delay (10ms)")
          val delayInput: TextField = new GenericTextField()

          val numOfObstaclesLabel: Label = new Label("Obstacles (3)")
          val numOfObstaclesInput: TextField = new GenericTextField()

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
          val startYInput = new GenericTextField(90)
          val startLabel = new Label("Start point (0, 0)")
          val startInputBox = new HBox() {
            alignment = Pos.BottomCenter
            children = Seq(
              startXInput,
              startYInput
            )
          }

          val controlInputs = new HBox() {
            alignment = Pos.BottomCenter
            val startB = new GenericButton(text = "Start")
            val pauseB = new GenericButton(text = "Pause")
            val resetB = new GenericButton(text = "Reset")

            startB.onMouseClicked = (_) => {
              var genSize = 40
              var chroSize = 50
              var popSize = 20
              var mutRate: Double = 5/chroSize
              var crossRate: Double = 0.5
              var targetX, targetY: Double = 300
              var startX, startY: Double = 0
              var obstacles = 3
              var d: Int = 10

              generationNumberInput.text() match{
                case "" =>
                case other => genSize = other.toInt
              }
              chromosomeSizeInput.text() match{
                case "" =>
                case other => chroSize = other.toInt
              }

              popSizeInput.text() match{
                case "" =>
                case other => popSize = other.toInt
              }

              mutationRateInput.text() match{
                case "" =>
                case other => mutRate = other.toDouble
              }
              crossRateInput.text() match{
                case "" =>
                case other => crossRate = other.toDouble
              }

              targetXInput.text() match {
                case "" =>
                case other => targetX = other.toDouble
              }

              targetYInput.text() match {
                case "" =>
                case other => targetY = other.toDouble
              }
              startXInput.text() match {
                case "" =>
                case other => startX = other.toDouble
              }
              startYInput.text() match {
                case "" =>
                case other => startY = other.toDouble
              }

              delayInput.text() match {
                case "" =>
                case other => d = other.toInt
              }

              numOfObstaclesInput.text() match {
                case "" =>
                case other => obstacles = other.toInt
              }

              var target: Target = new Target(targetX, targetY, 35)
              var start = new Point(startX, startY)
              var pop = new Population(popSize, chroSize, mutRate, crossRate, distancePerMove = 10, start.copy())
              var evaluator = new Evaluator(target)

              runnerTask = new GARunnerTask() {
                delay = d
                population = pop
                eval = evaluator
                generations = genSize
                nOfObstacles = obstacles
              }

              runnerTask.init()
              runnerTask.start()
            }

            pauseB.onMouseClicked = (_) => {
              runnerTask.pause()
            }

            children = Seq(
              startB,
              pauseB,
              resetB
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
            delayLabel,
            delayInput,
            targetLabel,
            targetInputBox,
            startLabel,
            startInputBox,
            controlInputs,
            numOfObstaclesLabel,
            numOfObstaclesInput
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
