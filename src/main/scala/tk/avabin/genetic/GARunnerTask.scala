package tk.avabin.genetic

import tk.avabin.genetic.ga.{Evaluator, Population}


/**
  * @author avabin
  */
class GARunnerTask extends Thread {
  var delay = 0
  var population: Population = _
  var eval: Evaluator = _
  var generations: Int = 0
  var generation = 1

  def init(): Unit = {
    population.populate()
  }

  def nextRound(): Unit = {
    val individuals = population.pop
    Drawer.clearBuffer()
    Drawer.addDrawable(eval.target)
    for (_ <- individuals(0).genes.indices) {
      for (i <- 0 until individuals.length - 2) {
        val individual = individuals(i)
        Drawer.addDrawable(individual)
        individual.applyNextMove()
      }
    }
  }


  override def run(): Unit = {
    while(generation <= generations) {
      Thread.sleep(delay)
      nextRound()
      generation += 1
    }
  }
}
