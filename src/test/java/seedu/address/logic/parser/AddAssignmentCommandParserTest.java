package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;

public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();
    private final String nonEmptyAssignment = "some assignment";

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD + " " + nonEmptyAssignment,
                expectedMessage);
    }
}
