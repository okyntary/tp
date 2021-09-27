---
layout: user
title: User Guide
---

ePoch is a desktop app for managing contacts, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ePoch can get your contact management tasks done faster than traditional GUI apps.
=======
ePoch is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ePoch can get your contact management tasks done faster than traditional GUI apps.

It has been designed for the busy NUS student. It allows the user to create contacts for persons and to create CCAs, link those persons and CCAs together, and to create periodic reminders for those CCAs.

It is intended to be used by NUS students, to help keep track of the students and organizations that they encounter.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java 11 or above installed in your Computer.

1. Download the JAR file. *The download location will be confirmed later*.

1. Copy the file to the folder you want to use as the home folder for your ePoch.
=======
1. Download the JAR file. **The download location will be confirmed later.**

1. Copy the file to the folder you want to use as the _home folder_ for your ePoch.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all persons, CCAs and reminders.

   * **`add`** `n/Tan Wei Yang id/e27182818` : Adds a contact named `Tan Wei Yang` with id `e27182818` to the Address Book.
=======
   * **`addp`**`n/Tan Wei Yang:` Adds a person named `Tan Wei Yang` to ePoch.

   * **`deletep`**`pid/3` : Deletes the person with id 3.

   * **`clear`** : Deletes all data (persons, CCAs, reminders) from ePoch.

   * **`exit`** : Exits the app.

1. Refer to the [Features](##features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addp n/NAME`, `NAME` is a parameter which can be used as `addp n/John Doe`.
  
* Items in square brackets are optional.<br>
  e.g `n/NAME [e/EMAIL]` can be used as `n/Johnny Doe [e/EMAIL]` or as `n/Johnny Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>


### Listing all persons : `list`

Shows a list of all persons, CCAs, reminders in ePoch.

Format: `list`

### Adding a person: `addp`

Adds a person to ePoch.

Format: `addp n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person must have at least their name added.
</div>

Examples:
* `addp n/Jovyn Tan Li Shyan`
* `addp n/Neo Wei Qing e/wei_qing_official_email_real@gmail.com a/Cinnamon College at/NUSSO @nussymphonyorchestra thanks`

### Editing a person: `editp`

Edits an existing person in ePoch.

Format: `editp pid/PERSON_ID [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]​`

* Edits the person at the specified by their person id (`pid`). The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit pid/1 p/91234567 e/jiveshrealemail@yahoo.com` Edits the phone number and email address of the 1st person to be `91234567` and `jiveshrealemail@yahoo.com` respectively.
*  `edit 2 n/weiq dt/NUSSO @nussymphonyorchestra thanks` Edits the name of the 2nd person to be `weiq` and clears the specified tag.

### Deleting a person: `deletep`

Deletes the specified person from ePoch.

Format: `deletep pid/PERSON_ID`

* Deletes the person with the specified person ID (`pid`).
* The person ID refers to the index number shown in the displayed person list.
* The person ID **must be a positive integer** 1, 2, 3, …​

Examples:
* `deletep pid/2` deletes the person in the address book that has person ID of 2.

### Finding a person: `findp`

Finds all the people in ePoch that match all the specified fields.

Format: `findp [pid/PERSON_ID] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [cca/CCA_NAME]`

* Finds the people who have valid matches for all the specified fields.
* At least one of the optional fields must be provided.

### Adding a cca: `addc`

Adds a CCA.

Format: `addc n/CCA_NAME`

### Editing a cca: `editc`

Edits the name of a CCA.

Format: `editc cid/CCA_ID n/CCA_NAME`

### Deleting a cca: `deletec`

Deletes a CCA.

Format: `deletec cid/CCA_ID`

### Deleting a cca: `deletec`

Deletes a CCA.

Format: `deletec cid/CCA_ID`

### Finding a cca: `findc`

Filters all CCAs with names that contain a given string.

Format: `findc s/STRING`

### Adding a reminder to a CCA: `addr`

Adds a reminder to a CCA.

Format: `addr cid/CCA_ID t/REMINDER_TITLE sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`

