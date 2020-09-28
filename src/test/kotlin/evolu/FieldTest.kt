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
    fun testGetSpotCode() {
        val field = Field(3, 3)
        //    012
        //   #####
        // 0 # 2 #
        // 1 #341#
        // 2 #   #
        //   #####
        assertEquals(0, field.getSpotCode(Point(1,1), Direction.North))

        // diamond with factor 1
        field.setSpot(2,1, Field.Spot.Diamond)
        assertEquals(1, field.getSpotCode(Point(1,1), Direction.North))
        assertEquals(1 *3, field.getSpotCode(Point(1,1), Direction.East))
        assertEquals(1 *3*3, field.getSpotCode(Point(1,1), Direction.South))

        // Test diamond at our current place
        assertEquals(1 *3*3*3, field.getSpotCode(Point(2,1), Direction.West))

        // Test walls with factor 2
        field.setSpot(2,1, Field.Spot.Wall)
        assertEquals(2, field.getSpotCode(Point(1,1), Direction.North))
        assertEquals(2 *3, field.getSpotCode(Point(1,1), Direction.East))
        assertEquals(2 *3*3, field.getSpotCode(Point(1,1), Direction.South))

        // Two walls, two diamonds
        field.setSpot(1,0, Field.Spot.Diamond)
        field.setSpot(2,0, Field.Spot.Diamond)
        assertEquals(2 + 2*3 + 1*3*3 + 1*3*3*3, field.getSpotCode(Point(2,0), Direction.North))
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

    @Test
    fun testFieldCopying() {
        val originalField = Field(5, 5)
        val duplicateField = originalField.copy()
        assertEquals(Field.Spot.Empty, originalField.getSpot(2,2))

        duplicateField.setSpot(2,2,Field.Spot.Diamond)
        assertEquals(Field.Spot.Diamond, duplicateField.getSpot(2,2))
        assertEquals(Field.Spot.Empty, originalField.getSpot(2,2))
    }
}