---
layout: page
title: User Guide
---

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

### Target Users

AB3 is designed for:

- **Tech-savvy individuals** who prefer keyboard commands over mouse interactions.
- **Users who need to manage a large number of contacts** and require quick access to contact information.
- **Students, professionals, and administrators** who need a simple yet powerful tool for contact management.

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

1. Download the latest `.jar` file from the [releases page](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, navigate to the folder containing the `.jar` file in using the `cd` command, and run the application with the following command:
    ```bash
    java -jar addressbook.jar
    ```
   A GUI similar to the below should appear in a few seconds. The app comes preloaded with some sample data.<br>
   ![Ui](images/Ui.png)

1. Type commands in the command box and press `Enter` to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   - `help`: Opens the help window.
   - `list`: Lists all contacts.
   - `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`: Adds a new contact.
   - `delete 3`: Deletes the 3rd contact in the list.
   - `clear`: Deletes all contacts.
   - `exit`: Exits the app.

1. Refer to the [Features](#features) section below for detailed instructions on each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- **Parameters in `UPPER_CASE`** are to be supplied by the user.

  Example: In `add n/NAME`, `NAME` can be replaced with `John Doe`.

- **Optional fields** are enclosed in square brackets `[]`.

  Example: `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or `n/John Doe`.

- **Multiple uses** of a field are indicated by `…`.

  Example: `[t/TAG]…` can be used as `t/friend`, `t/friend t/family`, or omitted entirely.

- **Parameters can be in any order**.

  Example: `n/NAME p/PHONE_NUMBER` is equivalent to `p/PHONE_NUMBER n/NAME`.

- **Extraneous parameters** for commands like `help`, `list`, `exit`, and `clear` will be ignored.

  Example: `help 123` is interpreted as `help`.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`

![help message](images/helpMessage.png)

### Adding a student: `add`

Adds a student to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A student can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all students : `list`

Shows a list of all students in the address book.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
* You can remove all the student’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a student : `delete`

Deletes the specified student from the address book.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q:** How do I transfer my data to another computer?

**A:**
Install the app on the new computer and replace the empty data file with the one from your previous AddressBook folder.
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **Multiple Screens Issue:** If you move the app to a secondary screen and later switch to a single screen, the GUI may open off-screen. To fix, delete the `preferences.json` file before running the app again.
2. **Help Window Issue:** Minimizing the Help Window and running `help` again may not open a new window. Restore the minimized window manually.
--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
