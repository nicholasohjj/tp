package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {

    @Test
    public void constructor_defaultValues() {
        CommandResult commandResult = new CommandResult("feedback");

        assertEquals("feedback", commandResult.getFeedbackToUser());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertFalse(commandResult.isUpdateList());
    }

    @Test
    public void constructor_allFields() {
        CommandResult commandResult = new CommandResult("feedback", true, true);

        assertEquals("feedback", commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertTrue(commandResult.isExit());
        assertFalse(commandResult.isUpdateList());
    }

    @Test
    public void constructor_updateList() {
        CommandResult commandResult = new CommandResult("feedback", true);

        assertEquals("feedback", commandResult.getFeedbackToUser());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertTrue(commandResult.isUpdateList());
    }

    @Test
    public void equals_sameValues() {
        CommandResult commandResult1 = new CommandResult("feedback", true, false);
        CommandResult commandResult2 = new CommandResult("feedback", true, false);

        assertTrue(commandResult1.equals(commandResult2));
    }

    @Test
    public void equals_differentFeedback() {
        CommandResult commandResult1 = new CommandResult("feedback1", true, false);
        CommandResult commandResult2 = new CommandResult("feedback2", true, false);

        assertFalse(commandResult1.equals(commandResult2));
    }

    @Test
    public void equals_differentShowHelp() {
        CommandResult commandResult1 = new CommandResult("feedback", true, false);
        CommandResult commandResult2 = new CommandResult("feedback", false, false);

        assertFalse(commandResult1.equals(commandResult2));
    }

    @Test
    public void equals_differentExit() {
        CommandResult commandResult1 = new CommandResult("feedback", false, true);
        CommandResult commandResult2 = new CommandResult("feedback", false, false);

        assertFalse(commandResult1.equals(commandResult2));
    }

    @Test
    public void equals_differentUpdateList() {
        CommandResult commandResult1 = new CommandResult("feedback", true);
        CommandResult commandResult2 = new CommandResult("feedback", false);

        assertFalse(commandResult1.equals(commandResult2));
    }

    @Test
    public void equals_null() {
        CommandResult commandResult = new CommandResult("feedback");

        assertFalse(commandResult.equals(null));
    }

    @Test
    public void equals_differentType() {
        CommandResult commandResult = new CommandResult("feedback");

        assertFalse(commandResult.equals("feedback"));
    }

    @Test
    public void hashCode_sameValues() {
        CommandResult commandResult1 = new CommandResult("feedback", true, false);
        CommandResult commandResult2 = new CommandResult("feedback", true, false);

        assertEquals(commandResult1.hashCode(), commandResult2.hashCode());
    }

    @Test
    public void hashCode_differentValues() {
        CommandResult commandResult1 = new CommandResult("feedback1", true, false);
        CommandResult commandResult2 = new CommandResult("feedback2", true, false);

        assertNotEquals(commandResult1.hashCode(), commandResult2.hashCode());
    }

    @Test
    public void toString_allFields() {
        CommandResult commandResult = new CommandResult("feedback", true, true);
        String expected = "seedu.address.logic.commands.CommandResult{"
                + "feedbackToUser=feedback, "
                + "showHelp=true, "
                + "exit=true, "
                + "updateList=false}";

        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void toString_updateList() {
        CommandResult commandResult = new CommandResult("feedback", true);
        String expected = "seedu.address.logic.commands.CommandResult{"
                + "feedbackToUser=feedback, "
                + "showHelp=false, "
                + "exit=false, "
                + "updateList=true}";

        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void getters() {
        CommandResult commandResult = new CommandResult("test feedback", true, false);

        assertEquals("test feedback", commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertFalse(commandResult.isUpdateList());
    }
}
