package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.address.testutil.EditStudentDescriptorBuilder;

public class EditStudentCommandParserTest {

    private final EditStudentCommandParser parser = new EditStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = Index.fromOneBased(1);
        String userInput = "1 n/Alice p/91234567 e/alice@example.com a/123, Main Street";

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("123, Main Street")
                .build();

        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        String userInput = "1";
        assertParseFailure(parser, userInput, EditStudentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_missingIndex_failure() {
        String userInput = "n/Bob";
        String expectedMessage = ParserUtil.MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "a n/Bob";
        String expectedMessage = ParserUtil.MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, userInput, expectedMessage);
    }


    @Test
    public void parse_duplicatePrefix_failure() {
        String userInput = "1 n/Alice n/Bob";
        assertParseFailure(parser, userInput, MESSAGE_DUPLICATE_FIELDS + "n/");
    }

    @Test
    public void parse_editSubject_failure() {
        String userInput = "1 s/Math";
        assertParseFailure(parser, userInput,
                EditStudentCommand.MESSAGE_EDIT_SUBJECT_DISALLOWED);
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = " ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE));
    }
}
