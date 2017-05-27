package tk.avabin.genetic

import scala.util.control.Breaks._

/**
  * @author Avabin
  */
class Evolver {
  def run(generations: Int, evaluator: Evaluator, population: Population): Individual = {
    def run(pop: Population, generation: Int): Individual = {
      val individuals = pop.pop
      for(i <- 0 until individuals.length - 2) {
        val individual = individuals(i)
        val genes = individual.chromosome
        breakable {
          for (j <- genes.indices) {
            if (evaluator.target.isPointIn(individual.point)) {
              break
            }
            individual.applyMove(genes(j))
          }
        }
      }
      val fittest = evaluator.fittest(pop)
      val fitness = evaluator.fitness(fittest)

      println("generation: %4d fitness: %2.2f chromosome: %s ".format(generation, fitness, fittest))

      if (generation >= generations)
        fittest
      else
        run(
          pop.evolve(elitist = true, evaluator),
          generation + 1
        )
    }
    run(population, 1)
  }

}
