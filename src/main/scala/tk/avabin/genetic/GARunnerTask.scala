package tk.avabin.genetic

import tk.avabin.genetic.ga._

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._


/**
  * @author avabin
  */
class GARunnerTask extends Thread {
  var delay = 0
  var population: Population = _
  var eval: Evaluator = _
  var generations: Int = _
  var generation: Int = 1
  var solution: Individual = _
  var solutionFitness:Double = 0
  var nOfObstacles = 0
  var colliders: ArrayBuffer[Drawable] = _
  var running = true

  def init(): Unit = {
    population.populate()
  }

  def pause(): Unit = {
    running = false
  }

  def nextRound(): Unit = {
    val individuals = population.pop
    Drawer.clearBuffer()
    for(c <- colliders)
      Drawer.addDrawable(c)
    for(i <- 0 until individuals.length - 1) {
      Drawer.addDrawable(individuals(i))
    }
    for (_ <- individuals(0).genes.indices) {
      Thread.sleep(delay)
          for (i <- 0 until individuals.length - 1) {
            val individual = individuals(i)
            individual.colliders = colliders
            individuals(i).applyNextMove()
        }
      }
    val fittest = eval.fittest(population)
    val fitness = eval.fitness(fittest)

    if(fitness > solutionFitness) {
      solutionFitness = fitness
      solution = fittest
    }
    var moves = fittest.finalMoveIndex
    if(moves <= fittest.moveIndex) moves = fittest.moveIndex

    println("generation: %4d | best distance %5.4f | fitness: %2.5f | moves: %5d | chromosome: %s ".format(generation,
      Util.calculateDistance(fittest.point, eval.target.point),
      fitness, moves - 1, fittest))

    population = population.evolve(elitist = true, eval)

  }


  override def run(): Unit = {
    colliders = new ArrayBuffer[Drawable](nOfObstacles + 1)
    for(i <- 0 until nOfObstacles)
      colliders.append(ObstacleGenerator.next())
    colliders.append(eval.target)
    while(generation <= generations) {
      while(running) {
        nextRound()
        generation += 1
      }
    }
  }
}
