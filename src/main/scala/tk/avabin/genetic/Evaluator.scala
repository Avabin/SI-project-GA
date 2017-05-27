package tk.avabin.genetic

class Evaluator(val target: Target) {
  var solutionBytes: Array[Byte] = _

  /**
   * Return the fittest organism in a population
   */
  def fittest(population: Population): Individual = {
    var o: Individual = population.pop(0)

    for (i <- 1 to population.size - 2) {
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
    1.0 / Util.calculateDistance(individual.point, target.point)
  }
}
