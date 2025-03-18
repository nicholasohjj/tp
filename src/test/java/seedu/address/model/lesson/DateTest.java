package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;


class DateTest {

    @Test
    void isValidDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");

        assertFalse(Date.isValidDate("12/07/2021")); // use of / is not allowed, only -
        assertFalse(Date.isValidDate("14-5-20")); // year must be 4 digits
        assertFalse(Date.isValidDate("12-07-2021")); // date cannot be in the past

        LocalDate now = LocalDate.now();
        assertFalse(Date.isValidDate(now.format(formatter))); // date cannot be today

        now = now.plusDays(1);
        assertTrue(Date.isValidDate(now.format(formatter))); //date is valid if its in the future

    }

    @Test
    void testToString() {
        assertEquals("10-12-2027", new Date("10-12-2027").toString()); //no change in the value
    }

    @Test
    void testEquals() {
        Date date = new Date("5-10-2026");

        //null object -> return false
        assertFalse(date.equals(null));

        //different date -> return false
        assertFalse(date.equals(new Date("5-11-2026")));

        //same object -> return true
        assertTrue(date.equals(date));

        //different types -> return false
        assertFalse(date.equals(5));
    }
}
