package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandTest {

    // Create a concrete implementation of Command for testing
    private static class ConcreteCommand extends Command {
        private final boolean shouldSucceed;
        private final String feedback;
        private final boolean shouldThrow;

        ConcreteCommand(boolean shouldSucceed, String feedback, boolean shouldThrow) {
            this.shouldSucceed = shouldSucceed;
            this.feedback = feedback;
            this.shouldThrow = shouldThrow;
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            if (shouldThrow) {
                throw new CommandException("Test exception");
            }
            return new CommandResult(feedback, shouldSucceed);
        }
    }

    @Test
    public void execute_successfulCommand_returnsCorrectResult() throws CommandException {
        String expectedFeedback = "Test success feedback";
        Command command = new ConcreteCommand(true, expectedFeedback, false);
        Model model = new ModelManager();

        CommandResult result = command.execute(model);

        assertEquals(expectedFeedback, result.getFeedbackToUser());
    }

    @Test
    public void execute_unsuccessfulCommand_returnsCorrectResult() throws CommandException {
        String expectedFeedback = "Test failure feedback";
        Command command = new ConcreteCommand(false, expectedFeedback, false);
        Model model = new ModelManager();

        CommandResult result = command.execute(model);

        assertEquals(expectedFeedback, result.getFeedbackToUser());
    }

    @Test
    public void execute_commandThrowsException_throwsCommandException() {
        Command command = new ConcreteCommand(false, "", true);
        Model model = new ModelManager();

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void commandResult_creationWithShowHelp_setsFlagCorrectly() {
        CommandResult result = new CommandResult("test", true, true);
        assertTrue(result.isShowHelp());
    }

    @Test
    public void commandResult_creationWithExit_setsFlagCorrectly() {
        CommandResult result = new CommandResult("test", true, true);
        assertTrue(result.isExit());
    }

    @Test
    public void commandResult_equalityCheck_returnsCorrectResults() {
        CommandResult result1 = new CommandResult("test");
        CommandResult result2 = new CommandResult("test");
        CommandResult result3 = new CommandResult("different");

        assertEquals(result1, result2);
        assertTrue(!result1.equals(result3));
    }
}