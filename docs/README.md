# <span style="color:lightskyblue">Lax Chatbot User Guide</span>

Lax is your lazy personal assistant, designed to help you manage tasks and notes effortlessly. Keep track of your
to-dos, deadlines, events, and personal notes, all in one place.

- <span style="color:lightskyblue">Getting started</span>
- <span style="color:lightskyblue">Features</span>
    - <ins>General commands:</ins>
        - Viewing help: `help`
        - Exiting application: `bye`
    - <ins>Task management:</ins>
        - Adding to-dos: `task todo`
        - Adding deadlines: `task deadline`
        - Adding events: `task event`
        - Listing tasks: `task list`
        - Marking tasks: `task mark`
        - Unmarking tasks: `task unmark`
        - Deleting tasks: `task delete`
        - Finding tasks: `task find`
        - Filtering tasks: `task filter`
    - <ins>Note management:</ins>
        - Adding notes: `note add`
        - Listing notes: `note list`
        - Deleting notes: `note delete`
        - Finding notes: `note find`
        - Filtering notes: `note filter`

## <span style="color:lightskyblue">Getting Started</span>

Lax commands generally follow a simple structure: `<prefix> <command> <details>`.

- `<prefix>` indicates whether you're managing tasks (`task`) or notes (`note`), seeking for help (`help`) or exiting
  the application (`bye`).
- `<command>` specifies the action you want to perform (e.g., `add`, `list`, `delete`).
- `<details>` provides additional information needed for the command (e.g., task description, note content).

Lax commands are case-insensitive, with trailing and leading spaces ignored.\
After every command, the list would be saved automatically into a `txt` file in the local directory.

## <span style="color:lightskyblue">Features</span>

**Notes about the command formatting:**

- words in `UPPER_CASE` are placeholders for user input.
- words in `lowercase` are fixed keywords that must be typed exactly.
- dateTime format to be keyed in: `dd-MM-yyyy HHmm` (24-hour format)
- date format to be keyed in: `dd-MM-yyyy`

### <ins>General Commands</ins>

- The commands in this section only consists of prefix.

<span style="color:lightskyblue">**Viewing help:**</span> <code style="color:lightskyblue">help</code>

Shows a list of available commands and their descriptions.

Format: `help`

<span style="color:lightskyblue">**Exiting application:**</span> <code style="color:lightskyblue">bye</code>

Exits the Lax application.

Format: `bye`

### <ins>Task Management</ins>

<span style="color:lightskyblue">**Adding to-dos:**</span> <code style="color:lightskyblue">task todo</code>

Adds a to-do task with a _**description**_.

Format: `task todo DESCRIPTION`\
Example: `task todo Read a book`

<span style="color:lightskyblue">**Adding deadlines:**</span> <code style="color:lightskyblue">task deadline</code>

Adds a task with a _**description**_ and a _**deadline**_. The _**deadline**_ must be after the current time.

Format: `task deadline DESCRIPTION /by DATE_TIME`\
Example: `task deadline Submit assignment /by 20-09-2023 2359`

<span style="color:lightskyblue">**Adding events:**</span> <code style="color:lightskyblue">task event</code>

Adds an event with a _**description**_, _**startDateTime**_ and _**endDateTime**_. The **_startDateTime_** and
**_endDateTime_** must be after the current time, and **_startDateTime_** must not be after _**endDateTime**_.

Format: `task event DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME`\
Example: `task event Team meeting /from 15-09-2023 1400 /to 15-09-2023 1500`

<span style="color:lightskyblue">**Listing tasks:**</span> <code style="color:lightskyblue">task list</code>

Displays all tasks in the task list.

Format: `task list`

<span style="color:lightskyblue">**Marking tasks:**</span> <code style="color:lightskyblue">task mark</code>

Marks a task as completed using its _**taskIndex**_. The **_taskIndex_** must be a positive integer limited by the
total size of the task list.

Format: `task mark TASK_INDEX`\
Example: `task mark 2`

<span style="color:lightskyblue">**Unmarking tasks:**</span> <code style="color:lightskyblue">task unmark</code>

Unmarks a task as not completed using its _**taskIndex**_. The **_taskIndex_** must be a positive integer limited by
the total size of the task list.

Format: `task unmark TASK_INDEX`\
Example: `task unmark 2`

<span style="color:lightskyblue">**Deleting tasks:**</span> <code style="color:lightskyblue">task delete</code>

Deletes a task using its _**taskIndex**_. The _**taskIndex**_ must be a positive integer limited by the total size of
the task list.

Format: `task delete TASK_INDEX`\
Example: `task delete 3`

<span style="color:lightskyblue">**Finding tasks:**</span> <code style="color:lightskyblue">task find</code>

Searches for tasks containing the specified _**keyword**_ in their description. The _**keyword**_ is case-insensitive.

Format: `task find KEYWORD`\
Example: `task find book`

<span style="color:lightskyblue">**Filtering tasks:**</span> <code style="color:lightskyblue">task filter</code>

Filters tasks based on the _**dateTime**_.

Format: `task filter DATE_TIME`\
Example: `task filter 20-09-2023 2359`

### <ins>Note Management</ins>

<span style="color:lightskyblue">**Adding notes:**</span> <code style="color:lightskyblue">note add</code>

Adds a note with a _**content**_.

Format: `note add CONTENT`\
Example: `note add Buy groceries`

<span style="color:lightskyblue">**Listing notes:**</span> <code style="color:lightskyblue">note list</code>

Displays all notes in the note list.

Format: `note list`

<span style="color:lightskyblue">**Deleting notes:**</span> <code style="color:lightskyblue">note delete</code>

Deletes a note using its _**noteIndex**_. The _**noteIndex**_ must be a positive integer limited by the total size of
the notes list.

Format: `note delete NOTE_INDEX`\
Example: `note delete 1`

<span style="color:lightskyblue">**Finding notes:**</span> <code style="color:lightskyblue">note find</code>

Searches for notes containing the specified _**keyword**_ in their content. The _**keyword**_ is case-insensitive.

Format: `note find KEYWORD`\
Example: `note find groceries`

<span style="color:lightskyblue">**Filtering notes:**</span> <code style="color:lightskyblue">note filter</code>

Filters notes based on the _**date**_ they were created.

Format: `note filter DATE`\
Example: `note filter 15-09-2023`