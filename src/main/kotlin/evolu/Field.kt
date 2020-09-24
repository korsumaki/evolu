package evolu

class Field(private val width: Int, private val height: Int) {
    private val numOfDiamonds = 7
    var numOfDiamondsLeft = numOfDiamonds
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

    fun getSpot(point: Point): Spot {
        return getSpot(point.x, point.y)
    }

    fun getSpot(x: Int, y: Int): Spot {
        if (x < 0 || x >= width) return Spot.Wall
        if (y < 0 || y >= height) return Spot.Wall

        return array[x + y*height]
    }

    fun setSpot(point: Point, value: Spot) {
        return setSpot(point.x, point.y, value)
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
        val pointNorth = Point(0,1)
        val pointEast  = Point(1,0)
        val pointWest  = Point(-1,0)

        val directedNorth = getPointWithDirection(pointNorth, direction) + position
        val directedEast  = getPointWithDirection(pointEast, direction)  + position
        val directedWest  = getPointWithDirection(pointWest, direction)  + position

        // Factors
        // ' 2 '
        // '341'
        var result = 0
        result += getSpot(directedEast).ordinal * 1
        result += getSpot(directedNorth).ordinal * 3
        result += getSpot(directedWest).ordinal * 3*3
        result += getSpot(position).ordinal * 3*3*3

        return result
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
        for (y in height downTo -1) {
            for (x in -1..width) {
                when (getSpot(x, y)) {
                    Spot.Empty -> //print(emptyCharacter)
                    {
                        if (robotPosition.x == x && robotPosition.y == y)
                            print(robotCharacter)
                        else
                            print(emptyCharacter)
                    }
                    Spot.Wall -> print(wallCharacter)
                    Spot.Diamond -> print(diamondCharacter)
                }
                print(' ') // Give more space between columns
            }
            println()
        }
    }
}
