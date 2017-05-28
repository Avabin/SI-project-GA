package tk.avabin.genetic.ga

import tk.avabin.genetic.MoveGenerator

import scala.util.Random

class Population(val populationSize: Integer, val chromosomeSize: Int, mutationRate: Double, crossoverRate: Double, distancePerMove: Double, startPoint: Point) {
  var pop: Array[Individual] = _


  /**
   * Create the space for the population but don't populate
   */
  def initialise(): Unit = {
    pop = new Array[Individual](populationSize + 1)
  }

  /**
   * Populate with organisms
   */
  def populate(): Unit = {
    initialise()

    for( i <- 0 until populationSize) {

      val moves = new Array[Move](chromosomeSize)

      for( j <- 0 until chromosomeSize) {
        moves(j) = MoveGenerator.next()
      }

      val individual = new Individual(moves, startPoint, distancePerMove)
      pop(i) = individual
    }
  }

  /**
   * Return the population size
   */
  def size: Int = {
    pop.length
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder

    sb.append("[")
    for (organism <- pop) {
      sb.append(organism + ", ")
    }
    sb.dropRight(2)
    sb.append("]")

    sb.toString
  }

  /**
   * Add an organism to a particular location in a population
   */
  def addIndividual(index: Int, individual: Individual): Unit = {
    pop(index) = individual
  }

  /**
   * Evolve the population by crossover and mutation
   * @param elitist If true, the fittest organism passes to the next generation
   * @param evaluator The evaluator to use
   */
  def evolve(elitist: Boolean, evaluator: Evaluator): Population = {
    val nextGeneration = new Population(pop.length, chromosomeSize, mutationRate, crossoverRate, distancePerMove, startPoint)
    nextGeneration.initialise()

    var offset = 0

    if (elitist) {
      val elite = evaluator.fittest(this)
      nextGeneration.addIndividual(0, mutate(elite))
      offset += 1
    }

    for(index <- offset to pop.length) {
      val parent1: Individual = select(evaluator)
      val parent2: Individual = select(evaluator)
      val child: Individual = crossover(parent1, parent2)

      nextGeneration.addIndividual(index, mutate(child))
    }

    nextGeneration
  }

  /**
   * Mutate an organism with a random rate of 0.015
   */
  def mutate(organism: Individual): Individual = {
    val c: Array[Move] = organism.genes

    for(index <- c.indices) {

      if (Math.random <= mutationRate) {
        c(index) = MoveGenerator.next()
      }
    }

    new Individual(c, startPoint, distancePerMove)
  }

  /**
   * Create a child organism from two parents
   */
  def crossover(parent1: Individual, parent2: Individual): Individual = {
    val otherParentGenes = parent2.genes
    val chromosomes = new Array[Move](otherParentGenes.length)

    var index: Int = 0
    for (gene <- parent1.genes) {

      if (Math.random <= crossoverRate) {
        chromosomes(index) = gene
      } else {
        chromosomes(index) = otherParentGenes(index)
      }
      index += 1
    }

    new Individual(chromosomes, startPoint, distancePerMove)
  }

  /**
   * Select an organism from the population using stochastic universal sampling
   */
  def select(evaluator: Evaluator): Individual = {
    val numberOfRounds = 10

    val tournament = new Population(numberOfRounds, chromosomeSize, mutationRate, crossoverRate, distancePerMove, startPoint)
    tournament.initialise()

    for (i <- 0 to numberOfRounds) {
      val randomIndividual = pop(Random.nextInt(populationSize))
      tournament.addIndividual(i, randomIndividual)
    }

    evaluator.fittest(tournament)
  }
}
