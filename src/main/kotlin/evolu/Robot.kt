package evolu

import kotlin.random.Random

/*
* - Robot
*     - Position, direction
*     - Genome
*       - String[54]
*     - Statistics
*       - collected diamonds
*       - used steps
*/

data class Point(var x: Int, var y: Int) {
    operator fun plus(inc: Point): Point {
        return Point(x+inc.x, y+inc.y)
    }
}

enum class Direction {
    North,
    East,
    South,
    West,
}

class Statistics {
    var usedSteps = 0
    var diamondsCollected = 0
    var executionErrors = 0
}

class Robot(var field: Field) {
    var currentPosition = Point(0,0)
    var currentDirection: Direction = Direction.North
    var genome: String = ""

    var statistics = Statistics()

    fun execute(spotCode: Int): Boolean {
        if (spotCode >= genome.length) {
            println("ERROR: Robot.execute(): Too big spotCode ($spotCode), maximum allowed is ${genome.length}.")
            statistics.executionErrors++
            return false
        }
        val instr = genome[spotCode]

        when (instr) {
            '1' -> pickDiamond()
            '2' -> moveForward()
            '3' -> turnLeftMoveForward()
            '4' -> turnRightMoveForward()
            '5' -> randomOperation()
            else -> {
                println("ERROR: Unknown inst '$instr'")
                statistics.executionErrors++
            }
        }
        statistics.usedSteps++

        // TODO check if there are diamonds left
        return true
    }

    fun pickDiamond() {
        val spot = field.getSpot(currentPosition)
        if (spot == Field.Spot.Diamond) {
            println("Got Diamond!")
            field.setSpot(currentPosition, Field.Spot.Empty)
            statistics.diamondsCollected++
            field.numOfDiamondsLeft--
        }
    }
    fun moveForward() {
        print("moveForward: $currentPosition")
        val newPoint = currentPosition + field.getPointWithDirection(Point(0,1), currentDirection)
        if (field.getSpot(newPoint) != Field.Spot.Wall) {
            currentPosition = newPoint
        }
        else {
            println("BADMOVE: Tried to walk to wall")
            statistics.executionErrors++

            // Turn to opposite direction
            currentDirection = when (currentDirection) {
                Direction.North -> Direction.South
                Direction.East -> Direction.West
                Direction.South -> Direction.North
                Direction.West -> Direction.East
            }

        }
        println(" -> pos=$currentPosition")
    }

    fun turnLeftMoveForward() {
        // Turning left
        print("turnLeftMoveForward: $currentDirection")
        currentDirection = when (currentDirection) {
            Direction.North -> Direction.West
            Direction.East -> Direction.North
            Direction.South -> Direction.East
            Direction.West -> Direction.South
        }
        println(" -> $currentDirection")

        moveForward()
    }

    fun turnRightMoveForward() {
        // Turning left
        print("turnRightMoveForward: $currentDirection")
        currentDirection = when (currentDirection) {
            Direction.North -> Direction.East
            Direction.East -> Direction.South
            Direction.South -> Direction.West
            Direction.West -> Direction.North
        }
        println(" -> $currentDirection")

        moveForward()
    }

    fun randomOperation() {
        println("randomOperation")
        when (Random.nextInt(4)) {
            1 -> pickDiamond()
            2 -> moveForward()
            3 -> turnLeftMoveForward()
            4 -> turnRightMoveForward()
        }
    }
}
