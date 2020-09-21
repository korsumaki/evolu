package evolu

class Field(val width: Int, val height: Int) {
    val numOfDiamonds = 7

    enum class Spot {
        Empty,
        Wall,
        Diamond
    }

    var array: ByteArray = ByteArray(width * height)

    fun getSpot(x: Int, y: Int): Spot {
        if (x < 0 || x >= width) return Spot.Wall
        if (y < 0 || y >= height) return Spot.Wall

        return Spot.Empty
    }

    fun setSpot(x: Int, y: Int, value: Spot) {

    }

    fun randomize() {

    }
    fun visualize() {

    }
}
