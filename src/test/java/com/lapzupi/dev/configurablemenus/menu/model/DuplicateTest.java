package com.lapzupi.dev.configurablemenus.menu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sarhatabaot
 */
class DuplicateTest {

    @Test
    void fromString() {
        Duplicate trueExample = new Duplicate(1,1,3);

        assertEquals(trueExample,Duplicate.fromString("1:1-3"));
    }

    @Test
    void isDuplicateString() {
        final String actualString = "1:1-2,2:3-4";
        final String fakeString = "1:1"; //this will fail if we do something like 1:1,-

        assertTrue(Duplicate.isDuplicateString(actualString));
        assertFalse(Duplicate.isDuplicateString(fakeString));
    }
}