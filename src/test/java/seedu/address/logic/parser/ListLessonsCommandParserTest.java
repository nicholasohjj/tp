package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.model.lesson.StudentNameLessonPredicate;

public class ListLessonsCommandParserTest {

    private ListLessonsCommandParser parser = new ListLessonsCommandParser();

    @Test
    public void parse_emptyArg_showsAllLessons() {
        ListLessonsCommand expectedFindStudentCommand =
                new ListLessonsCommand(PREDICATE_SHOW_ALL_LESSONS);
        assertParseSuccess(parser, "     ", expectedFindStudentCommand);
    }

    @Test
    public void parse_validArgs_returnsFindStudentCommand() {
        // leading and trailing whitespaces
        ListLessonsCommand expectedFindStudentCommand =
                new ListLessonsCommand(new StudentNameLessonPredicate("Alice"));
        assertParseSuccess(parser, " n/ Alice ", expectedFindStudentCommand);

        // blank keywords are not allowed
        assertParseFailure(parser, " n/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLessonsCommand.MESSAGE_USAGE));
    }

}
