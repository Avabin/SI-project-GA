package tk.avabin.genetic

import tk.avabin.genetic.ga._

import scala.collection.mutable.ArrayBuffer
import scalafx.beans.property.{DoubleProperty, IntegerProperty, ObjectProperty}


/**
  * @author avabin
  */
class GARunnerTask extends Thread {
  var delay = 0
  var population: Population = _
  var eval: Evaluator = _
  var generation: IntegerProperty = new IntegerProperty() {value = 1}
  var solution: ObjectProperty[Individual] = new ObjectProperty[Individual]()
  var solutionFitness: Double = 0
  var colliders: ArrayBuffer[Drawable] = _
  var running = true

  def init(): Unit = {
    population.populate()
    colliders = Drawer.persistentBuffer
  }

  def pause(): Unit = {
    running = false
  }

  def res(): Unit = {
    running = true
  }

  def nextRound(): Unit = {
    val individuals = population.pop
    Drawer.clearBuffer()
    for (i <- 0 until individuals.length - 1) {
      Drawer.addDrawable(individuals(i))
      val individual = individuals(i)
      colliders.copyToBuffer(individual.colliders)
    }
    for (_ <- individuals(0).genes.indices) {
        Thread.sleep(delay)
        for (i <- 0 until individuals.length - 1) {
          val individual = individuals(i)
          if (!individual.collided)
            individual.applyNextMove()
      }
    }
    val fittest = eval.fittest(population)
    val fitness = eval.fitness(fittest)

    if (fitness > solutionFitness) {
      solutionFitness = fitness
      solution() = fittest
    }
    var moves = fittest.finalMoveIndex
    if (moves <= fittest.moveIndex) moves = fittest.moveIndex

    println("generation: %4d | best distance %5.4f | fitness: %2.5f | moves: %5d | chromosome: %s ".format(generation(),
      Util.calculateDistance(fittest.point, eval.target.point),
      fitness, moves - 1, fittest))

    population = population.evolve(elitist = true, eval)
    generation() += 1

  }


  override def run(): Unit = {
    while (running)
      nextRound()
  }
}
