package tk.avabin.genetic

import tk.avabin.genetic.ga._

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.util.Random
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.{Button, Control, Label, TextField}
import scalafx.scene.input.MouseButton
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
        val obstacleXInput = new GenericTextField(90)
        val obstacleYInput = new GenericTextField(90)
        val obstacleLabel = new Label("Obstacle size (100, 100)")

        val gen: Label = new Label("Generation: ")
        val solutionMoves: Label = new Label("Best moves: ")
        val solFitness: Label = new Label("Best fitness: ")


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

          val clear: Button = new Button("Clear")


          val obstacleInputBox = new HBox() {
            alignment = Pos.BottomCenter
            children = Seq(
              obstacleXInput,
              obstacleYInput
            )
          }

          val controlInputs = new HBox() {
            alignment = Pos.TopCenter
            val startB = new GenericButton(text = "Start")
            val pauseB = new GenericButton(text = "Pause")
            val resumeB = new GenericButton(text = "Resume")

            startB.onMouseClicked = (_) => {
              var chroSize = 2000
              var popSize = 120
              var mutRate: Double = 5 / chroSize
              var crossRate: Double = 0.5
              var targetX, targetY: Double = 300
              var startX, startY: Double = 0
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

              delayInput.text() match {
                case "" =>
                case other => d = other.toInt
              }

              var target: Target = new Target(targetX, targetY, 35)
              var start = new Point(startX, startY)
              var pop = new Population(popSize, chroSize, mutRate, crossRate, distancePerMove = 10, start.copy())
              var evaluator = new Evaluator(target)

              runnerTask = new GARunnerTask() {
                delay = d
                population = pop
                eval = evaluator
              }

              runnerTask.generation.onChange((_, _, nv) => {
                gen.text = "Generation: %03d".format(nv.intValue())
              })
              runnerTask.solution.onChange((_,_,v) => {
                solFitness.text = "Best fitness: %3.6f".format(runnerTask.solutionFitness)
                var moves = v.finalMoveIndex
                if (moves <= v.moveIndex) moves = v.moveIndex
                solutionMoves.text = "Best moves: %04d".format(moves)
              })

              if(Drawer.persistentBuffer.isEmpty) Drawer.addPermDrawable(target)
              runnerTask.init()
              runnerTask.start()
            }

            pauseB.onMouseClicked = (_) => {
              runnerTask.pause()
            }

            resumeB.onMouseClicked = (_) => {
              runnerTask.res()
            }

            children = Seq(
              startB,
              pauseB,
              resumeB
            )
          }

          clear.onMouseClicked = (_) => {
            Drawer.clearBuffer()
            Drawer.clearPermBuffer()
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
            obstacleLabel,
            obstacleInputBox,
            clear,
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
            var obsW, obsH = 100.0


            this.onMouseClicked = (event) => {
              obstacleXInput.text() match {
                case "" =>
                case other => obsW = other.toDouble
              }

              obstacleYInput.text() match {
                case "" =>
                case other => obsH = other.toDouble
              }


              MouseButton(event.getButton) match {
                case MouseButton.Primary =>
                  val d = new Obstacle(event.getX, event.getY, obsW, obsH)
                  Drawer.addPermDrawable(d)
                case MouseButton.Secondary =>
                  val d = new Target(event.getX, event.getSceneY)
                  Drawer.addPermDrawable(d)
                case MouseButton.Middle => println("Middle on ( %3.3f , %3.3f )".format(event.getX, event.getY))
                case _ =>
              }

            }
            Drawer gc = gc
            animationTimer.start()
          }

          children = canvas
        }

        val genInfo: VBox = new VBox(5) {
          this.minWidth = 200
          padding = Insets(5, 10, 5, 10)
          alignment = Pos.BaselineLeft


          children = Seq(
            gen,
            solutionMoves,
            solFitness
          )

        }


        add(controlBox, 0, 0)
        add(canvasPane, 1, 0, 1, 2)
        add(genInfo, 2, 0)
      }
    }
  }

}
