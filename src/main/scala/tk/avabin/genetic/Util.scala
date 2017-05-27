package tk.avabin.genetic

/**
  * @author Avabin
  */
object Util {
  def calculateDistance(a: Point, b: Point): Double = {
    math.sqrt(math.pow(a.x - b.x, 2) + math.pow(a.y - b.y, 2))
  }
}
