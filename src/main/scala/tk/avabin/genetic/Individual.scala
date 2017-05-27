package tk.avabin.genetic

class Individual(var chromosome: Array[Move], val point: Point = new Point(), val distancePerMove: Double = 1.0) {
  var finalMoveIndex = 0
  def genes: Array[Move] = chromosome
  def noteMove(index: Int): Unit = {
    finalMoveIndex = index
      val newarray = new Array[Move](finalMoveIndex)
      for(j <- newarray.indices) {
        newarray(j) = chromosome(j)
      }
    chromosome = newarray
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder
    for (gene <- chromosome) {
      sb.append(gene)
    }
    sb.toString
  }

  def applyMove(move: Move): Unit = {
    move.directions match {
      case "00" => moveUp()
      case "01" => moveDown()
      case "10" => moveRight()
      case "11" => moveLeft()
    }
  }

  def moveUp(): Unit = {
    this.point.y += distancePerMove
  }
  def moveDown(): Unit = {
    this.point.y -= distancePerMove
  }
  def moveRight(): Unit = {
    this.point.x += distancePerMove
  }
  def moveLeft(): Unit = {
    this.point.x -= distancePerMove
  }
}
