package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.student.Name;
import seedu.address.model.subject.Subject;

public class EditLessonCommandParserTest {

    private final EditLessonCommandParser parser = new EditLessonCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setName(new Name("Alice"));
        descriptor.setDate(new Date("01-04-2030"));
        descriptor.setTime(new Time("12:00"));
        descriptor.setSubject(new Subject("Math"));

        String userInput = "1 n/Alice d/01-04-2030 t/12:00 s/Math";

        assertParseSuccess(parser, userInput, new EditLessonCommand(INDEX_FIRST, descriptor));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a n/Alice", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, "n/Alice", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        assertParseFailure(parser, "1", EditLessonCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String input = "1 n/Alice n/Bob";
        assertParseFailure(parser, input, MESSAGE_DUPLICATE_FIELDS + "n/");
    }
}
