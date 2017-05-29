package tk.avabin.genetic.ga

import tk.avabin.genetic.Util

class Evaluator(val target: Target) {

  /**
   * Return the fittest organism in a population
   */
  def fittest(population: Population): Individual = {
    var o: Individual = population.pop(0)

    for (i <- 1 until population.size - 1) {
      val nextIndividual = population.pop(i)

      if (fitness(nextIndividual) > fitness(o)) {
        o = population.pop(i)
      }
    }

    o
  }

  /**
  * Calculate an organism's fitness by comparing it to the optimal solution
  */
  def fitness(individual: Individual): Double = {
    if(individual.isInTarget) return 1 / (Util.calculateDistance(individual.point, target.point) + individual.finalMoveIndex) * 1.5
    (1 / ((Util.calculateDistance(individual.point, target.point) * 1.7) + individual.finalMoveIndex)) * 0.2
  }
}
