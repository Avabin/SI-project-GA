package tk.avabin.genetic

import tk.avabin.genetic.ga.{Evaluator, Individual, Population}

import scala.util.control.Breaks._


/**
  * @author avabin
  */
class GARunnerTask extends Thread {
  var delay = 0
  var population: Population = _
  var eval: Evaluator = _
  var generations: Int = 0
  var generation = 1
  var solution: Individual = _
  var solutionFitness:Double = 0
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
    Drawer.addDrawable(eval.target)
    for(i <- 0 until individuals.length - 1) {
      Drawer.addDrawable(individuals(i))
    }
    for (_ <- individuals(0).genes.indices) {
      Thread.sleep(delay)
      breakable {
        for (i <- 0 until individuals.length - 1) {
          val individual = individuals(i)
          if (eval.target.isPointIn(individual.point)) {
            break
          }
          individual.applyNextMove()
        }
      }
    }
    val fittest = eval.fittest(population)
    val fitness = eval.fitness(fittest)

    if(fitness > solutionFitness) {
      solutionFitness = fitness
      solution = fittest
    }

    println("generation: %4d | best distance %3.4f | fitness: %2.5f | chromosome: %s ".format(generation, Util.calculateDistance(fittest.point, eval.target.point), fitness, fittest))

    population = population.evolve(elitist = true, eval)

  }


  override def run(): Unit = {

    while(generation <= generations) {
      while(running) {
        nextRound()
        generation += 1
      }
    }
  }
}
