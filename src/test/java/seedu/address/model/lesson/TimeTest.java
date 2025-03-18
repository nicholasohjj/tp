package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.datetimeutil.Time;

class TimeTest {

    @Test
    void isValidTime() {
        assertFalse(Time.isValidTime("12.08")); // use of . is not allowed, only :
        assertFalse(Time.isValidTime("7:00")); // not within hours of 8am-9pm
        assertFalse(Time.isValidTime("21:02")); // not within hours of 8am-9pm

        assertTrue(Time.isValidTime("20:00")); //valid and within hours
        assertTrue(Time.isValidTime("21:00"));
        assertTrue(Time.isValidTime("8:00"));
    }

    @Test
    void testToString() {
        assertEquals("10:15", new Time("10:15").toString()); //no change in the value
    }

    @Test
    void testEquals() {
        Time time = new Time("16:00");

        //null object -> return false
        assertFalse(time.equals(null));

        //different date -> return false
        assertFalse(time.equals(new Time("17:00")));

        //same object -> return true
        assertTrue(time.equals(time));

        //different types -> return false
        assertFalse(time.equals(7));
    }
}
