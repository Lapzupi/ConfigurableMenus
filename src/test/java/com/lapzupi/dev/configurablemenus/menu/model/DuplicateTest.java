package com.lapzupi.dev.configurablemenus.menu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sarhatabaot
 */
class DuplicateTest {

    @Test
    void asListFromString() {
        //1:1-3,2:1-4
        final String duplicateListString = "1:1-3,2:1-4";
        final List<Duplicate> expectedResult = List.of(new Duplicate(1,1,3), new Duplicate(2,1,4));

        assertEquals(expectedResult,Duplicate.asListFromString(duplicateListString));
    }

    @Test
    void fromString() {
        Duplicate trueExample = new Duplicate(1,1,3);

        assertEquals(trueExample,Duplicate.fromString("1:1-3"));
    }

    @Test
    void isDuplicateString() {
        final String multipleString = "1:1-2,2:3-4";
        final String singleString = "1:1-2";
        final String fakeString = "1:1";
        final String otherFakeString = "1:1,-";


        assertTrue(Duplicate.isDuplicateString(multipleString));
        assertTrue(Duplicate.isDuplicateString(singleString));

        assertFalse(Duplicate.isDuplicateString(fakeString));
        assertFalse(Duplicate.isDuplicateString(otherFakeString));
    }
}