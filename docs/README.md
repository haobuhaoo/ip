# Lax Chatbot User Guide

Lax is your lazy personal assistant, designed to help you manage tasks and notes effortlessly. Keep track of your
to-dos, deadlines, events, and personal notes, all in one place.

- [Getting started](#getting-started)
- [Features](#features)
    - [General commands:](#general-commands)
        - [Viewing help: `help`](#viewing-help-help)
        - [Exiting application: `bye`](#exiting-application-bye)
    - [Task management:](#task-management)
        - [Adding to-dos: `task todo`](#adding-to-dos-task-todo)
        - [Adding deadlines: `task deadline`](#adding-deadlines-task-deadline-)
        - [Adding events: `task event`](#adding-events-task-event-)
        - [Listing tasks: `task list`](#listing-tasks-task-list-)
        - [Marking tasks: `task mark`](#marking-tasks-task-mark-)
        - [Unmarking tasks: `task unmark`](#unmarking-tasks-task-unmark-)
        - [Deleting tasks: `task delete`](#deleting-tasks-task-delete-)
        - [Finding tasks: `task find`](#finding-tasks-task-find-)
        - [Filtering tasks: `task filter`](#filtering-tasks-task-filter-)
    - [Note management:](#note-management)
        - [Adding notes: `note add`](#adding-notes-note-add-)
        - [Listing notes: `note list`](#listing-notes-note-list-)
        - [Deleting notes: `note delete`](#deleting-notes-note-delete-)
        - [Finding notes: `note find`](#finding-notes-note-find-)
        - [Filtering notes: `note filter`](#filtering-notes-note-filter-)

---

## Getting Started

Lax commands generally follow a simple structure: `<prefix> <command> <details>`.

- `<prefix>` indicates whether you're managing tasks (`task`) or notes (`note`), seeking for help (`help`) or exiting
  the application (`bye`).
- `<command>` specifies the action you want to perform (e.g., `add`, `list`, `delete`).
- `<details>` provides additional information needed for the command (e.g., task description, note content).

Lax commands are case-insensitive, with trailing and leading spaces ignored.\
After every command, the list would be saved automatically into a `txt` file in the local directory.

---

## Features

**Notes about the command formatting:**

- words in `UPPER_CASE` are placeholders for user input.
- words in `lowercase` are fixed keywords that must be typed exactly.
- dateTime format to be keyed in: `dd-MM-yyyy HHmm` (24-hour format)
- date format to be keyed in: `dd-MM-yyyy`

---

### General Commands

- The commands in this section only consists of prefix.

#### Viewing help: `help`

Shows a list of available commands and their descriptions.

Format: `help`

#### Exiting application: `bye`

Exits the Lax application.

Format: `bye`

---

### <ins>Task Management</ins>

#### Adding to-dos: `task todo`

Adds a to-do task with a _**description**_.

Format: `task todo DESCRIPTION`\
Example: `task todo Read a book`

#### Adding deadlines: `task deadline`

Adds a task with a _**description**_ and a _**deadline**_. The _**deadline**_ must be after the current time.

Format: `task deadline DESCRIPTION /by DATE_TIME`\
Example: `task deadline Submit assignment /by 20-09-2023 2359`

#### Adding events: `task event`

Adds an event with a _**description**_, _**startDateTime**_ and _**endDateTime**_. The **_startDateTime_** and
**_endDateTime_** must be after the current time, and **_startDateTime_** must not be after _**endDateTime**_.

Format: `task event DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME`\
Example: `task event Team meeting /from 15-09-2023 1400 /to 15-09-2023 1500`

#### Listing tasks: `task list`

Displays all tasks in the task list.

Format: `task list`

#### Marking tasks: `task mark`

Marks a task as completed using its _**taskIndex**_. The **_taskIndex_** must be a positive integer limited by the
total size of the task list.

Format: `task mark TASK_INDEX`\
Example: `task mark 2`

#### Unmarking tasks: `task unmark`

Unmarks a task as not completed using its _**taskIndex**_. The **_taskIndex_** must be a positive integer limited by
the total size of the task list.

Format: `task unmark TASK_INDEX`\
Example: `task unmark 2`

#### Deleting tasks: `task delete`

Deletes a task using its _**taskIndex**_. The _**taskIndex**_ must be a positive integer limited by the total size of
the task list.

Format: `task delete TASK_INDEX`\
Example: `task delete 3`

#### Finding tasks: `task find`

Searches for tasks containing the specified _**keyword**_ in their description. The _**keyword**_ is case-insensitive.

Format: `task find KEYWORD`\
Example: `task find book`

#### Filtering tasks: `task filter`

Filters tasks based on the _**dateTime**_.

Format: `task filter DATE_TIME`\
Example: `task filter 20-09-2023 2359`

---

### <ins>Note Management</ins>

#### Adding notes: `note add`

Adds a note with a _**content**_.

Format: `note add CONTENT`\
Example: `note add Buy groceries`

#### Listing notes: `note list`

Displays all notes in the note list.

Format: `note list`

#### Deleting notes: `note delete`

Deletes a note using its _**noteIndex**_. The _**noteIndex**_ must be a positive integer limited by the total size of
the notes list.

Format: `note delete NOTE_INDEX`\
Example: `note delete 1`

#### Finding notes: `note find`

Searches for notes containing the specified _**keyword**_ in their content. The _**keyword**_ is case-insensitive.

Format: `note find KEYWORD`\
Example: `note find groceries`

#### Filtering notes: `note filter`

Filters notes based on the _**date**_ they were created.

Format: `note filter DATE`\
Example: `note filter 15-09-2023`