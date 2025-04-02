package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.model.student.Name.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.model.lesson.StudentNameLessonPredicate;
import seedu.address.model.student.Name;

public class ListLessonsCommandParserTest {

    private ListLessonsCommandParser parser = new ListLessonsCommandParser();

    @Test
    public void parse_emptyArg_showsAllLessons() {
        ListLessonsCommand expectedFindStudentCommand =
                new ListLessonsCommand(PREDICATE_SHOW_ALL_LESSONS);
        assertParseSuccess(parser, "     ", expectedFindStudentCommand);
    }

    @Test
    public void parse_validArgs_returnsListLessonsCommand() {
        // leading and trailing whitespaces
        ListLessonsCommand expectedFindStudentCommand =
                new ListLessonsCommand(new StudentNameLessonPredicate(new Name("Alice")), new Name("Alice"));
        assertParseSuccess(parser, " n/ Alice ", expectedFindStudentCommand);

        // blank keywords are not allowed
        assertParseFailure(parser, " n/ ", MESSAGE_CONSTRAINTS);
    }

}
