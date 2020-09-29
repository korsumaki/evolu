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

    operator fun plus(other: Statistics): Statistics {
        val stat = Statistics()
        stat.usedSteps = this.usedSteps + other.usedSteps
        stat.diamondsCollected = this.diamondsCollected + other.diamondsCollected
        stat.executionErrors = this.executionErrors + other.executionErrors
        return stat
    }
}

class Robot(var field: Field) {
    var currentPosition = Point(0,0)
    var currentDirection: Direction = Direction.North
    var genome: String = randomizeGenome()

    var statistics = Statistics()
    var statisticsAllTime = Statistics()

    private fun randomizeGenome() : String {
        var s = String()
        repeat(54) {
            s += Random.nextInt(1,6).toString()
        }
        println("randomizeGenome() -> $s")
        return s
    }

    fun randomizePosition() {
        currentPosition.x = Random.nextInt(0,field.width)
        currentPosition.y = Random.nextInt(0,field.height)
        //println("randomizePosition() -> $currentPosition")
    }

    fun randomizeDirection() {
        currentDirection = when (Random.nextInt(4)) {
            0 -> Direction.North
            1 -> Direction.East
            2 -> Direction.South
            3 -> Direction.West
            else -> Direction.North
        }
        //println("randomizeDirection() -> $currentDirection")
    }



    fun execute(spotCode: Int): Boolean {
        if (spotCode >= genome.length) {
            println("ERROR: Robot.execute(): Too big spotCode ($spotCode), maximum allowed is ${genome.length-1}.")
            statistics.executionErrors++
            return false
        }

        when (val instr = genome[spotCode]) {
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
        //println("pickDiamond")
        val spot = field.getSpot(currentPosition)
        if (spot == Field.Spot.Diamond) {
            //println("Got Diamond!")
            field.setSpot(currentPosition, Field.Spot.Empty)
            statistics.diamondsCollected++
            field.numOfDiamondsLeft--
        }
    }
    fun moveForward() {
        //print("moveForward: $currentPosition")
        val newPoint = currentPosition + field.getPointWithDirection(Point(0,1), currentDirection)
        if (field.getSpot(newPoint) != Field.Spot.Wall) {
            currentPosition = newPoint
        }
        else {
            //println("BadMove: Tried to walk to wall")
            statistics.executionErrors++

            // Turn to opposite direction
            currentDirection = when (currentDirection) {
                Direction.North -> Direction.South
                Direction.East -> Direction.West
                Direction.South -> Direction.North
                Direction.West -> Direction.East
            }

        }
        //println(" -> $currentPosition")
    }

    fun turnLeftMoveForward() {
        // Turning left
        //print("turnLeftMoveForward: $currentDirection")
        currentDirection = when (currentDirection) {
            Direction.North -> Direction.West
            Direction.East -> Direction.North
            Direction.South -> Direction.East
            Direction.West -> Direction.South
        }
        //println(" -> $currentDirection")

        moveForward()
    }

    fun turnRightMoveForward() {
        // Turning left
        //print("turnRightMoveForward: $currentDirection")
        currentDirection = when (currentDirection) {
            Direction.North -> Direction.East
            Direction.East -> Direction.South
            Direction.South -> Direction.West
            Direction.West -> Direction.North
        }
        //println(" -> $currentDirection")

        moveForward()
    }

    fun randomOperation() {
        //println("randomOperation")
        when (Random.nextInt(2,5)) {
            2 -> moveForward()
            3 -> turnLeftMoveForward()
            4 -> turnRightMoveForward()
            else -> println("ERROR: randomOperation else")
        }
    }
}
