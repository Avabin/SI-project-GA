package tk.avabin.genetic

import tk.avabin.genetic.ga._

import scala.language.postfixOps
import scala.util.Random
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.layout.{GridPane, HBox, StackPane, VBox}

/**
  * @author avabin
  */
object PathFinderApp extends JFXApp {
  var runnerTask: GARunnerTask = _
  val version = 0.1
  stage = new PrimaryStage {
    resizable = false
    width = 1075
    height = 705
    scene = new Scene() {
      title = s"PathFinder app $version"
      content = new GridPane() {
        padding = Insets(10)
        val controlBox = new VBox() {
          alignment = Pos.BottomCenter

          val chromosomeSizeLabel: Label = new Label("Chromosome size (2000)")
          val chromosomeSizeInput: TextField = new GenericTextField()

          val popSizeLabel: Label = new Label("Population size (120)")
          val popSizeInput: TextField = new GenericTextField()

          val mutationRateLabel: Label = new Label("Mutation rate (0.015)")
          val mutationRateInput: TextField = new GenericTextField()

          val crossRateLabel: Label = new Label("Crossover rate (0.5)")
          val crossRateInput: TextField = new GenericTextField()

          val delayLabel: Label = new Label("Delay (3ms)")
          val delayInput: TextField = new GenericTextField()

          val numOfObstaclesLabel: Label = new Label("Obstacles (8)")
          val numOfObstaclesInput: TextField = new GenericTextField()

          val targetXInput = new GenericTextField(90)
          val targetYInput = new GenericTextField(90)
          val targetLabel = new Label("Target coordinates (300, 300)")
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
            alignment = Pos.TopCenter
            val startB = new GenericButton(text = "Start")
            val pauseB = new GenericButton(text = "Pause")
            val resetB = new GenericButton(text = "Reset")

            startB.onMouseClicked = (_) => {
              var chroSize = 2000
              var popSize = 120
              var mutRate: Double = 5 / chroSize
              var crossRate: Double = 0.5
              var targetX, targetY: Double = 300
              var startX, startY: Double = 0
              var obstacles = 8
              var d: Int = 3

              chromosomeSizeInput.text() match {
                case "" =>
                case other => chroSize = other.toInt
              }

              popSizeInput.text() match {
                case "" =>
                case other => popSize = other.toInt
              }

              mutationRateInput.text() match {
                case "" =>
                case other => mutRate = other.toDouble
              }
              crossRateInput.text() match {
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
            numOfObstaclesLabel,
            numOfObstaclesInput,
            controlInputs
          )
        }
        val canvasPane = new StackPane() {
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

          children = canvas
        }

        val genInfo: VBox = new VBox(5) {
          this.minWidth = 200
          padding = Insets(5, 10, 5, 10)
          alignment = Pos.BaselineLeft
          val genNumberLabel = new Label("Generation Number: 0")
          val bestMoves = new Label("Best moves: ")
          val bestDistance = new Label("Best distance: ")
          val bestFitness = new Label("Best fitness: ")

          children = Seq(
            genNumberLabel,
            bestMoves,
            bestDistance,
            bestFitness
          )

        }


        add(controlBox, 0, 0)
        add(canvasPane, 1, 0, 1, 2)
        add(genInfo, 2, 0)
      }
    }
  }

}
