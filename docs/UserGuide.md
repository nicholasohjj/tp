---
layout: page
title: User Guide
---

TutorTrack is a **desktop app designed for freelance tutors to manage students, lessons, and assignments efficiently**. It is optimized for use via a **Command Line Interface (CLI)** while still providing the benefits of a **Graphical User Interface (GUI)**. If you can type fast, TutorTrack can help you manage your tutoring tasks faster than traditional GUI apps.

### Target Users

TutorTrack is designed for:

- **Freelance tutors** who need to manage multiple students, lessons, and assignments.
- **Tech-savvy individuals** who prefer keyboard commands over mouse interactions.
- **Users who need a simple yet powerful tool** for tracking tutoring-related tasks.

### Assumptions about Users

- Users are familiar with basic CLI commands.
- Users have a basic understanding of file management (e.g., creating folders, moving files).
- Users are comfortable with Java-based applications.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Follow the installation guide [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from the [releases page](https://github.com/AY2425S2-CS2103T-T13-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TutorTrack.

1. Open a command terminal, navigate to the folder containing the `.jar` file in using the `cd` command, and run the application with the following command:
    ```bash
    java -jar TutorTrack.jar
    ```
   A GUI similar to the below should appear in a few seconds. The app comes preloaded with some sample data.<br>
   ![Ui](images/Ui.png)

1. Type commands in the command box and press `Enter` to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   - `help`: Opens the help window.
   - `list_students`: Lists all students.
   - `add_student n/John Doe p/91234567 e/johndoe@email.com s/Math`: Adds a new student.
   - `delete_student 1`: Deletes the 1st student in the list.
   - `clear`: Deletes all students.
   - `exit`: Exits the app.

1. Refer to the [Features](#features) section below for detailed instructions on each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- **Parameters in `UPPER_CASE`** are to be supplied by the user. Example: In `add_student n/STUDENT_NAME`, `STUDENT_NAME` can be replaced with `John Doe`.

- **Optional fields** are enclosed in square brackets `[]`. Example: `add_student n/NAME p/PHONE [s/SUBJECT]` can be used as `add_student n/John Doe p/91234567 s/Math` or `add_student n/John Doe p/91234567`.
- **Multiple uses** of a field are indicated by `…`.

  Example: `[s/SUBJECT]…` can be used as `s/Math`, `s/Math s/Science`, or omitted entirely.

- **Parameters can be in any order**.

  Example: `n/STUDENT_NAME p/PHONE_NUMBER` is equivalent to `p/PHONE_NUMBER n/STUDENT_NAME`.

- **Extraneous parameters** for commands like `help`, `list`, `exit`, and `clear` will be ignored.

  Example: `help 123` is interpreted as `help`.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`

![help message](images/helpMessage.png)

### Adding a student: `add_student`

Adds a student to the student list.

Format: `add_student n/STUDENT_NAME p/PHONE_NUMBER e/EMAIL [s/SUBJECT]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A student can have any number of subjects (including 0)
</div>

Examples:
* `add_student n/John Doe p/98765432 e/johndoe@email.com s/Math`
* `add_student n/Mary Jane p/12345678 e/maryjane@email.com s/Math s/Science`

### Adding a lesson: `add_lesson`

Adds a lesson to the lesson list.

Format: `add_lesson n/STUDENT_NAME s/SUBJECT d/DATE t/TIME​`

Example:
* `add_lesson n/Alice Chan d/17-09-2025 t/15:00 s/Math`

### Listing all students : `list_students`

Shows a list of all students in the student list.

Format: `list_students`

### Listing lessons : `list_lessons`

Shows a list of all lessons under a student in the lesson list. If no student is specified, shows all lessons in the list.

Format: `list_lessons n/STUDENT_NAME`

Example:
* `list_lessons n/John Lee`
* `list_lessons`

### Editing a student : `edit_student`

Edits an existing student in the student list.

Format: `edit_student INDEX [n/STUDENT_NAME] [p/PHONE] [e/EMAIL] [s/SUBJECT]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

* When editing subjects, the existing subjects of the student will be removed i.e adding of subjects is not cumulative.

* You can remove all the student’s subjects by typing `s/` without

    specifying any subjects after it.


Examples:

*  `edit_student 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.

*  `edit_student 2 n/Betsy Crower s/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing subjects.

### Locating students by name: `find_student`


Finds students whose names contain any of the given keywords.


Format: `find_student KEYWORD [MORE_KEYWORDS]`


* The search is case-insensitive. e.g `hans` will match `Hans`

* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`

* Only the name is searched.

* Only full words will be matched e.g. `Han` will not match `Hans`

* Students matching at least one keyword will be returned (i.e. `OR` search).

  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`


Examples:

* `find_student John` returns `john` and `John Doe`

* `find_student alex david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find_student alex david'](images/findAlexDavidResult.png)

### Deleting a student : `delete`

Deletes the specified student from the student list.

Format: `delete_student INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list_students` followed by `delete_student 2` deletes the 2nd student in the student list.
* `find_student Betsy` followed by `delete_student 1` deletes the 1st student in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the student list.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TutorTrack data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TutorTrack data are saved automatically as a JSON file `[JAR file location]/data/TutorTrack.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TutorTrack will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the TutorTrack to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q:** How do I transfer my data to another computer?

**A:**
Install the app on the new computer and replace the empty data file with the one from your previous TutorTrack folder.
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **Multiple Screens Issue:** If you move the app to a secondary screen and later switch to a single screen, the GUI may open off-screen. To fix, delete the `preferences.json` file before running the app again.
2. **Help Window Issue:** Minimizing the Help Window and running `help` again may not open a new window. Restore the minimized window manually.
--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add Student** | `add_student n/NAME p/PHONE_NUMBER e/EMAIL s/SUBJECT​` <br> e.g., `add_student n/James Ho p/22224444 e/jamesho@example.com s/Math`
**Clear** | `clear`
**Delete Students** | `delete_students INDEX`<br> e.g., `delete 3`
**Edit** | `edit_student INDEX [n/STUDENT_NAME] [p/PHONE] [e/EMAIL] [s/SUBJECT]…​`<br> e.g.,`edit_student 2 n/James Lee e/jameslee@example.com`
**Find** | `find_student KEYWORD [MORE_KEYWORDS]`<br> e.g., `find_student James Jake`
**List Students** | `list_students`
**List Lessons** | `list_lessons`
**Help** | `help`
