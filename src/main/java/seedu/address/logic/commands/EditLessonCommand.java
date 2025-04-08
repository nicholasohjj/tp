package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddLessonCommand.MESSAGE_LESSON_CONFLICT;
import static seedu.address.logic.commands.AddLessonCommand.MESSAGE_STUDENT_NOT_FOUND;
import static seedu.address.logic.commands.AddLessonCommand.MESSAGE_SUBJECT_MISMATCH;
import static seedu.address.logic.commands.AddLessonCommand.VALID_ADDRESS;
import static seedu.address.logic.commands.AddLessonCommand.VALID_EMAIL;
import static seedu.address.logic.commands.AddLessonCommand.VALID_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Edits the details of an existing lesson in the address book.
 */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "edit_lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson identified "
            + "by the index number used in the displayed lesson list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: LESSON INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "20-7-2026 "
            + PREFIX_TIME + "19:00";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Error: At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LESSON = "Error: This lesson already exists in the address book.";
    private static final Logger logger = LogsCenter.getLogger(EditLessonCommand.class);

    private final Index index;
    private final EditLessonDescriptor editLessonDescriptor;

    /**
     * @param index of the student in the filtered lesson list to edit
     * @param editLessonDescriptor details to edit the lesson with
     */
    public EditLessonCommand(Index index, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
        logger.info("EditLessonCommand created for lesson index: " + index.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing EditLessonCommand for lesson index: " + index.getOneBased());
        List<Lesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid lesson index: " + index.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }


        Lesson lessonToEdit = lastShownList.get(index.getZeroBased());
        Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);
        assert editedLesson != null : "Edited lesson should not be null";

        //check duplicate
        if (!lessonToEdit.equals(editedLesson) && model.hasLesson(editedLesson)) {
            logger.warning("Duplicate lesson detected: " + editedLesson);
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        //check whether after editing, lesson clashes with any lesson in the addressbook
        if (model.hasLessonConflict(editedLesson)) {
            if (!lessonToEdit.isConflict(editedLesson)) {
                logger.warning("Lesson conflict detected for: " + editedLesson);
                throw new CommandException(MESSAGE_LESSON_CONFLICT);
            }
        }
        //check whether student exists in the addressbook
        Student dummyStudent = new Student(editedLesson.getStudentName(), VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                new HashSet<>(), new UniqueAssignmentList());
        if (!model.hasStudent(dummyStudent)) {
            logger.warning("Student not found: " + editedLesson.getStudentName());
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }
        //check whether student has the subject
        if (!model.hasStudentSubject(dummyStudent, editedLesson.getSubject())) {
            throw new CommandException(MESSAGE_SUBJECT_MISMATCH);
        }
        model.setLesson(lessonToEdit, editedLesson);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        logger.info("Lesson successfully edited: " + editedLesson);
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson)), true);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    public static Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null : "Lesson to edit cannot be null";
        assert editLessonDescriptor != null : "Edit descriptor cannot be null";
        assert editLessonDescriptor.isAnyFieldEdited() : "At least one field should be edited";

        Name updatedName = editLessonDescriptor.getName().orElse(lessonToEdit.getStudentName());
        Date updatedDate = editLessonDescriptor.getDate().orElse(lessonToEdit.getDate());
        Time updatedTime = editLessonDescriptor.getTime().orElse(lessonToEdit.getTime());
        Subject updatedSubject = editLessonDescriptor.getSubject().orElse(lessonToEdit.getSubject());

        return new Lesson(updatedSubject, updatedName, updatedDate, updatedTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLessonCommand)) {
            return false;
        }

        EditLessonCommand otherEditCommand = (EditLessonCommand) other;
        return index.equals(otherEditCommand.index)
                && editLessonDescriptor.equals(otherEditCommand.editLessonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editLessonDescriptor", editLessonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the lesson with.
     */
    public static class EditLessonDescriptor {
        private Name name;
        private Date date;
        private Time time;
        private Subject subject;

        public EditLessonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code subjects} is used internally.
         */
        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setSubject(toCopy.subject);
        }



        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, time, subject);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }
        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Subject> getSubject() {
            return (subject != null) ? Optional.of(subject) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            EditLessonDescriptor otherEditLessonDescriptor = (EditLessonDescriptor) other;
            return Objects.equals(name, otherEditLessonDescriptor.name)
                    && Objects.equals(date, otherEditLessonDescriptor.date)
                    && Objects.equals(time, otherEditLessonDescriptor.time)
                    && Objects.equals(subject, otherEditLessonDescriptor.subject);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("subjects", subject)
                    .add("name", name)
                    .add("date", date)
                    .add("time", time)
                    .toString();
        }

    }
}
