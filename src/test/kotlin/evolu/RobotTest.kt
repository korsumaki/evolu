package evolu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class RobotTest {

    @Test
    fun execute() {
    }

    @Test
    fun testPickDiamond() {
        val field = Field(5,5)
        field.setSpot(2,3, Field.Spot.Diamond)
        val robot = Robot(field)

        robot.currentPosition = Point(2,2)
        assertEquals(0, robot.statistics.diamondsCollected)
        robot.pickDiamond()
        assertEquals(0, robot.statistics.diamondsCollected)

        robot.currentPosition = Point(2,3)
        robot.pickDiamond()
        assertEquals(1, robot.statistics.diamondsCollected)
    }

    @Test
    fun testMoveForward() {
        val field = Field(5,5)
        val robot = Robot(field)
        robot.currentPosition = Point(2,2)
        robot.moveForward()
        assertEquals(Point(2,3), robot.currentPosition)
        robot.currentDirection = Direction.East
        robot.moveForward()
        assertEquals(Point(3,3), robot.currentPosition)
    }

    @Test
    fun testTurnLeftMoveForward() {
        val field = Field(5,5)
        val robot = Robot(field)
        robot.currentPosition = Point(2,2)

        assertEquals(Direction.North, robot.currentDirection)
        robot.turnLeftMoveForward()
        assertEquals(Point(1,2), robot.currentPosition)
        assertEquals(Direction.West, robot.currentDirection)
    }

    @Test
    fun testTurnRightMoveForward() {
        val field = Field(5,5)
        val robot = Robot(field)
        robot.currentPosition = Point(2,2)

        assertEquals(Direction.North, robot.currentDirection)
        robot.turnRightMoveForward()
        assertEquals(Point(3,2), robot.currentPosition)
        assertEquals(Direction.East, robot.currentDirection)
    }

    @Test
    fun randomOperation() {
        val field = Field(5,5)
        val robot = Robot(field)
        robot.currentPosition = Point(2,2)
        robot.randomOperation()
    }

    @Test
    fun testGenerateGenome() {
        val field = Field(5,5)
        val robot1 = Robot(field)
        val robot2 = Robot(field)
        val robot3 = Robot(field)

        robot3.generateGenome(robot1.genome, robot2.genome)
        assertEquals(54, robot3.genome.length)
        assertNotEquals(robot1.genome, robot3.genome)
        assertNotEquals(robot2.genome, robot3.genome)
        assertNotEquals(robot1.genome, robot2.genome)
    }
}