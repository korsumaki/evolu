package evolu

/*
* - Robot
*     - Position, direction
*     - Genome
*       - String[54]
*     - Statistics
*       - collected diamonds
*       - used steps
*/

data class Point(var x: Int, var y: Int)

enum class Direction {
    North,
    East,
    South,
    West,
}

class Statistics {
    var usedSteps = 0
    var diamondsCollected = 0
}

class Robot {
    var currentPosition = Point(0,0)
    var currentDirection: Direction = Direction.North
    var genome: String = "asdf"

    var statistics = Statistics()

}
