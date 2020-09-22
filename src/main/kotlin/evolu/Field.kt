package evolu

class Field(val width: Int, val height: Int) {
    private val numOfDiamonds = 7
    private var array = ArrayList<Spot>(width * height)

    // Initialize field with Empty spots
    init {
        for (x in 1..width) {
            for (y in 1..height) {
                array.add(Spot.Empty)
            }
        }
    }

    enum class Spot {
        Empty,
        Diamond,
        Wall,
    }

    fun getSpot(x: Int, y: Int): Spot {
        if (x < 0 || x >= width) return Spot.Wall
        if (y < 0 || y >= height) return Spot.Wall

        return array[x + y*height]
    }

    fun setSpot(x: Int, y: Int, value: Spot) {
        if (x < 0 || x >= width) return
        if (y < 0 || y >= height) return

        array[x + y * height] = value
    }

    fun getPointWithDirection(pointDelta: Point, direction: Direction): Point {
        return when (direction) {
            Direction.North -> Point( pointDelta.x,  pointDelta.y)
            Direction.East  -> Point( pointDelta.y, -pointDelta.x)
            Direction.South -> Point(-pointDelta.x, -pointDelta.y)
            Direction.West  -> Point(-pointDelta.y,  pointDelta.x)
        }
    }

    fun getSpotCode(position: Point, direction: Direction): Int {
        //  2
        // 341

        return 0
    }

    fun randomize() {
        var diamondsLeft = numOfDiamonds
        while (diamondsLeft > 0) {
            val x = (Math.random() * width).toInt()
            val y = (Math.random() * height).toInt()
            if (getSpot(x, y) == Spot.Empty) {
                setSpot(x, y, Spot.Diamond)
                diamondsLeft--
            }
        }
    }

    private val emptyCharacter = ' '
    private val wallCharacter = '#'
    private val diamondCharacter = '$'
    private val robotCharacter = 'x'

    fun visualize(robotPosition: Point) {
        // Start from -1 to draw also walls
        for (x in -1..width) {
            for (y in -1..height) {
                when (getSpot(x, y)) {
                    Spot.Empty -> print(emptyCharacter)
                    /*{
                        if (robotPosition.x == x && robotPosition.y == y)
                            print(robotCharacter)
                        else
                            print(emptyCharacter)
                    }*/
                    Spot.Wall -> print(wallCharacter)
                    Spot.Diamond -> print(diamondCharacter)
                }
                print(' ') // Give more space between columns
            }
            println()
        }
    }
}
