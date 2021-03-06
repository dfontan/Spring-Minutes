package com.springminutes.example

import grails.test.*

class CowTests extends GrailsUnitTestCase {

    void testConstraints() {
        mockForConstraintsTests(Cow)

        // Good cow
        def cow1 = new Cow(breed: Breed.TasmanianGrey, color: "mottled", legs: 7)
        assertTrue cow1.validate()
        assertEquals 0, cow1.errors.allErrors.size()
        // Cow with unknown legs
        def cow2 = new Cow(breed: Breed.TexasLonghorn, color: "blue")
        assertFalse cow2.validate()
        assertEquals "nullable", cow2.errors['legs']
        // Cow with blank color
        def cow3 = new Cow(breed: Breed.TexasLonghorn, color: "", legs: 1)
        assertFalse cow3.validate()
        assertEquals "blank", cow3.errors['color']
        // Custom validator
        // Negative legs
        def cow4 = new Cow(breed: Breed.Guernsey, color: "wonky", legs: -1)
        assertFalse cow4.validate()
        assertEquals "invalid.range", cow4.errors['legs']
        // Too many legs for a Guernsey
        cow4.legs = 7
        assertFalse cow4.validate()
        assertEquals "invalid.range", cow4.errors['legs']
        cow4.breed = Breed.Holstein
        // same cow, after correcting the breed
        assertTrue cow4.validate()
        assertNull cow4.errors['legs']
    }
}
