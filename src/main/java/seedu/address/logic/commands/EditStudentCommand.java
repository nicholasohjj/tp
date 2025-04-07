package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import static seedu.address.logic.commands.EditLessonCommand.createEditedLesson;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditStudentCommand extends Command {

    public static final String COMMAND_WORD = "edit_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_EDIT_SUBJECT_DISALLOWED = "Error: Editing subjects directly is not allowed.";
    public static final String MESSAGE_NOT_EDITED = "Error: At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "Error: This student already exists in the address book.";
    private static final Logger logger = LogsCenter.getLogger(EditStudentCommand.class);

    private final Index index;
    private final EditStudentDescriptor editStudentDescriptor;

    /**
     * @param index of the student in the filtered student list to edit
     * @param editStudentDescriptor details to edit the student with
     */
    public EditStudentCommand(Index index, EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(index);
        requireNonNull(editStudentDescriptor);

        this.index = index;
        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
        logger.info("EditStudentCommand created for student index: " + index.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing EditStudentCommand for student index: " + index.getOneBased());

        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid student index: " + index.getOneBased());
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);
        assert editedStudent != null : "Edited student should not be null";

        boolean isDuplicate = model.getFilteredStudentList().stream()
                .filter(student -> !student.equals(studentToEdit)) // exclude the student being edited
                .anyMatch(student -> student.isSameStudent(editedStudent));

        if (isDuplicate) {
            logger.warning("Duplicate student detected: " + editedStudent.getName());
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        // Edit all lessons associated with the student
        List<Lesson> lessonsToEdit = model.getFilteredLessonList()
                .stream()
                .filter(lesson -> lesson.getStudentName().equals(studentToEdit.getName()))
                .toList();
        if (editStudentDescriptor.getName().isPresent()) {
            EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
            editLessonDescriptor.setName(editStudentDescriptor.getName().get());

            logger.info("Editing " + lessonsToEdit.size() + " associated lessons");
            lessonsToEdit.forEach(lesson -> {
                Lesson editedLesson = createEditedLesson(lesson, editLessonDescriptor);
                model.setLesson(lesson, editedLesson);
                logger.fine("Edited lesson: " + lesson);
            });
        }

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

        logger.info("Student successfully edited: " + editedStudent.getName());
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent)), true);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    public static Student createEditedStudent(Student studentToEdit,
                                              EditStudentDescriptor editStudentDescriptor) throws CommandException {
        requireNonNull(studentToEdit);
        requireNonNull(editStudentDescriptor);

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Phone updatedPhone = editStudentDescriptor.getPhone().orElse(studentToEdit.getPhone());
        Email updatedEmail = editStudentDescriptor.getEmail().orElse(studentToEdit.getEmail());
        Address updatedAddress = editStudentDescriptor.getAddress().orElse(studentToEdit.getAddress());
        Set<Subject> updatedSubjects = editStudentDescriptor.getSubjects().orElse(studentToEdit.getSubjects());
        UniqueAssignmentList updatedAssignments = editStudentDescriptor.getAssignments()
                .orElse(studentToEdit.getAssignments());

        return new Student(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedSubjects, updatedAssignments);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStudentCommand)) {
            return false;
        }

        EditStudentCommand otherEditStudentCommand = (EditStudentCommand) other;
        return index.equals(otherEditStudentCommand.index)
                && editStudentDescriptor.equals(otherEditStudentCommand.editStudentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editStudentDescriptor", editStudentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the student with. Each non-empty field assignmentName will replace the
     * corresponding field assignmentName of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Subject> subjects;
        private UniqueAssignmentList assignments;

        public EditStudentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code subjects} is used internally.
         */
        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setSubjects(toCopy.subjects);
            setAssignments(toCopy.assignments);
        }



        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address,
                    subjects);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code subjects} to this object's {@code subjects}.
         * A defensive copy of {@code subjects} is used internally.
         */
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
        }

        private void setAssignments(UniqueAssignmentList assignments) {
            this.assignments = assignments;
        }

        public Optional<UniqueAssignmentList> getAssignments() {
            return Optional.ofNullable(assignments);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudentDescriptor)) {
                return false;
            }

            EditStudentDescriptor otherEditStudentDescriptor = (EditStudentDescriptor) other;
            return Objects.equals(name, otherEditStudentDescriptor.name)
                    && Objects.equals(phone, otherEditStudentDescriptor.phone)
                    && Objects.equals(email, otherEditStudentDescriptor.email)
                    && Objects.equals(address, otherEditStudentDescriptor.address)
                    && Objects.equals(subjects, otherEditStudentDescriptor.subjects)
                    && Objects.equals(assignments, otherEditStudentDescriptor.assignments);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("subjects", subjects)
                    .add("assignments", assignments)
                    .toString();
        }

    }
}
