package evolu

import kotlin.test.Test
import kotlin.test.assertEquals

internal class FieldTest {

    @Test
    fun getSpot_returnsCorrectSpots() {
        val field = Field(5, 5)

        assertEquals(Field.Spot.Wall, field.getSpot(-1,0))
        assertEquals(Field.Spot.Wall, field.getSpot(0,-1))
        assertEquals(Field.Spot.Empty, field.getSpot(0,0))
        assertEquals(Field.Spot.Empty, field.getSpot(4,4))
        assertEquals(Field.Spot.Wall, field.getSpot(5,4))
        assertEquals(Field.Spot.Wall, field.getSpot(4,5))
    }

    @Test
    fun setSpot_updatesSpotsToField() {
        val field = Field(5, 5)
        assertEquals(Field.Spot.Empty, field.getSpot(2,2))
        field.setSpot(2,2,Field.Spot.Diamond)
        assertEquals(Field.Spot.Diamond, field.getSpot(2,2))
    }

    @Test
    fun getSpotCode() {
        val field = Field(3, 3)
        //    012
        //   #####
        // 0 #   #
        // 1 #   #
        // 2 #   #
        //   #####
        assertEquals(0, field.getSpotCode(Point(1,1,), Direction.North))

        //field.setSpot(2,2,Field.Spot.Diamond)
        //fun getSpotCode(position: Point, direction: Direction): Int {

        //  2
        // 341

        }

    @Test
    fun testGetPointWithDirection() {
        val field = Field(3,3)
        val pointNorth = Point(0,1)
        val pointEast  = Point(1,0)
        val pointWest  = Point(-1,0)
        val pointSouth = Point(0,-1)

        assertEquals(pointNorth, field.getPointWithDirection(pointNorth, Direction.North))
        assertEquals(pointEast, field.getPointWithDirection(pointEast, Direction.North))
        assertEquals(pointWest, field.getPointWithDirection(pointWest, Direction.North))

        assertEquals(pointEast, field.getPointWithDirection(pointNorth, Direction.East))
        assertEquals(pointSouth, field.getPointWithDirection(pointEast, Direction.East))
        assertEquals(pointNorth, field.getPointWithDirection(pointWest, Direction.East))

        assertEquals(pointWest, field.getPointWithDirection(pointNorth, Direction.West))
        assertEquals(pointNorth, field.getPointWithDirection(pointEast, Direction.West))
        assertEquals(pointSouth, field.getPointWithDirection(pointWest, Direction.West))

        assertEquals(pointSouth, field.getPointWithDirection(pointNorth, Direction.South))
        assertEquals(pointWest, field.getPointWithDirection(pointEast, Direction.South))
        assertEquals(pointEast, field.getPointWithDirection(pointWest, Direction.South))
    }
}