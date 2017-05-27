package tk.avabin.genetic

import java.util.concurrent.Callable

import tk.avabin.genetic.ga.{Individual, Move}


/**
  * @author avabin
  */
class GARunnerTask extends Callable[Individual] {
  override def call(): Individual = {
    //TODO nextRun() gather information to draw
    new Individual(new Array[Move](1))
  }
}
