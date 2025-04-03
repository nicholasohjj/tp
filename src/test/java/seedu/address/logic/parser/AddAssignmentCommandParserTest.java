package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREAMBLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_AMY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder(ASSIGNMENT_AMY).build();

        assertParseSuccess(parser, VALID_PREAMBLE + ASSIGNMENT_NAME_DESC_AMY + DATE_DESC_AMY,
                new AddAssignmentCommand(Index.fromOneBased(1), expectedAssignment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD + ASSIGNMENT_NAME_DESC_BOB,
                expectedMessage);

        // missing assignment prefix
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD + VALID_PREAMBLE + DATE_DESC_AMY,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddAssignmentCommand.COMMAND_WORD + VALID_PREAMBLE + ASSIGNMENT_NAME_DESC_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid assignment
        assertParseFailure(parser, VALID_PREAMBLE + INVALID_ASSIGNMENT_NAME_DESC + DATE_DESC_BOB,
                Assignment.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, VALID_PREAMBLE + ASSIGNMENT_NAME_DESC_AMY + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS);
    }
}
