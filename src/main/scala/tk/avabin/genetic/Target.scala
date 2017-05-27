package tk.avabin.genetic

/**
  * @author Avabin
  */
class Target(val point: Point = new Point(), val radius: Int = 2) {
  def isPointIn(targetPoint: Point): Boolean = {
    val dx = math.abs(targetPoint.x - point.x)
    if (dx > radius) return false
    val dy = math.abs(targetPoint.y - point.y)
    if (dy > radius) return false
    if (dx + dy <= radius) return true
     dx * dx + dy * dy <= radius * radius
  }
}
