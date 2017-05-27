package tk.avabin.genetic

import scala.util.Random


object Runner extends App{
  val generations = 75
  val evolver = new Evolver
  val target = new Target(new Point(100, 100), radius = 10)
  val evaluator = new Evaluator(target)
  val chromosomeSize = 75
  var sb: StringBuilder = new StringBuilder
  val random = new Random()
  val mutationRate: Double = 5.0 / chromosomeSize

  var population = new Population(populationSize = 1, chromosomeSize = chromosomeSize, mutationRate, crossoverRate = 0.5, distancePerMove = 4)
  population.populate()

  val solution: Individual = evolver.run(generations, evaluator, population)
  println("solution distance: %2.2f  %s".format(Util.calculateDistance(solution.point, target.point), solution))
}
