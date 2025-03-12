package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.student.Assignment;

public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();
    private final String nonEmptyAssignment = "some assignment";

    @Test
    public void parse_indexSpecified_success() {
        // have assignment
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ASSIGNMENT + nonEmptyAssignment;
        AddAssignmentCommand expectedCommand = new AddAssignmentCommand(targetIndex, new Assignment(nonEmptyAssignment));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no assignment
        userInput = targetIndex.getOneBased() + " " + PREFIX_ASSIGNMENT;
        expectedCommand = new AddAssignmentCommand(targetIndex, new Assignment(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD +  " " + nonEmptyAssignment,
                expectedMessage);
    }
}
