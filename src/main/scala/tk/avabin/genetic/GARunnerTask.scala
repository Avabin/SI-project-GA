package tk.avabin.genetic

import java.util.concurrent.Callable


/**
  * @author avabin
  */
class GARunnerTask extends Callable[Individual] {
  override def call(): Individual = {
    //TODO nextRun() gather information to draw
    new Individual(new Array[Move](1))
  }
}
