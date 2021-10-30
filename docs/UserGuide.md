---
layout: user
title: User Guide
---

ePoch is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type quickly, ePoch can get your contact management tasks done faster than traditional GUI apps.

It has been designed for the busy NUS student. It allows the user to create contacts for persons and to create CCAs, link those persons and CCAs together, and to create periodic reminders for those CCAs.

It is intended to be used by NUS students, to help keep track of the students and organizations that they encounter.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java 11 or above installed in your Computer.

1. Download the JAR file. **The download location to be confirmed later.**

1. Copy the file to the folder you want to use as the _home folder_ for your ePoch app.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.

1. Enter commands in the command box and press Enter to execute them. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all persons, CCAs and reminders currently stored in ePoch.

   * **`addp n/Tan Wei Yang a/CAPT e/tanweiyang@u.nus.edu.sg p/94492210`** Adds a person named `Tan Wei Yang`, with address `CAPT`, email `tanweiyang@u.nus.edu.sg`, and phone number `94492210` to ePoch.

   * **`deletep`**` 3` : Deletes the person with id 3, ie. shown as third in the list of persons.

   * **`clear`** : Deletes all data (persons, CCAs and reminders) from ePoch.

   * **`exit`** : Exits ePoch.

1. Refer to the [Features](##features) segment below for details of each command.

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

### Adding a CCA: `addc`

Adds a CCA.

Format: `addc n/CCA_NAME`

### Editing a CCA: `editc`

Edits the name of a CCA.

Format: `editc CCA_ID n/CCA_NAME`

### Deleting a CCA: `deletec`

Deletes a CCA.

Format: `deletec CCA_ID`

### Finding a CCA: `findc`

Filters all CCAs with names that contain a given string, as well as people enrolled in these CCAs and reminders associated with these CCAs.

Format: `findc STRING`

### Adding a reminder to a CCA: `addr`

Adds a reminder to a CCA.

Format: `addr cid/CCA_ID n/REMINDER_NAME sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`

### Editing a reminder: `editr`

Edits a reminder.

Format: `editr cid/CCA_ID n/REMINDER_NAME sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`

### Deleting a reminder: `deleter`

Deletes a reminder.

Format: `deleter REMINDER_ID`

### Finding the title of a reminder: `findr`

Filters all reminders that fit a given string.

Format: `findr STRING`

### Snoozing a reminder: `snoozer`

Snoozes the reminder.

Format: `snoozer REMINDER_ID`

### Enrolling a person from a CCA: `enrol`

Enrols a person into a CCA.

Format: `enrol cid/CCA_ID pid/PERSON_ID`

### Expelling a person from a CCA: `expel`

Expels a person from a CCA.

Format: `expel cid/CCA_ID pid/PERSON_ID`

### Changing the colour of a non-CCA tag: `colourt`

Changes the colour of a non-CCA tag.

Format: `colourt n/TAG_NAME c/RED GREEN BLUE`

### Changing the colour of all CCA tags: `colourc`

Changes the colour of all CCA tags.

Format: `colourc c/RED GREEN BLUE`

### Delete all data from ePoch: `clear`

Deletes all data (persons, CCAs, reminders) from ePoch.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Saving the data

ePoch data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ePoch data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ePoch home folder.

**Q**: Can I create a reminder without linking it to a CCA?
**A**: No. We do not support that functionality: all reminders must be linked to a CCA in some way

--------------------------------------------------------------------------------------------------------------------

## Summary of commands

Action | Format
-------|------------------
**List all data** | `list`
**Delete all data** | `clear`
**Add person** | `addp n/PERSON_NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`
**Edit person data** | `editp PERSON_ID [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`
**Delete person** | `deletep PERSON_ID`
**Find person** | `findp [pid/PERSON_ID] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [cca/CCA_NAME]`
**Add CCA** | `addc n/CCA_NAME`
**Edit CCA name** | `editc CCA_ID n/CCA_NAME`
**Delete CCA** | `deletec CCA_ID`
**Find CCA** | `findc STRING`
**Add reminder** | `addr cid/CCA_ID n/REMINDER_NAME sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`
**Edit reminder title** | `editr cid/CCA_ID n/REMINDER_NAME sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`
**Delete reminder** | `deleter REMINDER_ID`
**Find reminder** | `findr STRING`
**Snooze reminder** | `snoozer REMINDER_ID`
**Enrols a person into CCA** | `enrol cid/CCA_ID pid/PERSON_ID`
**Removes a person from a CCA** | `expel cid/CCA_ID pid/PERSON_ID`
**Changes the colour of a non-CCA tag** | `colourt n/TAG_NAME c/RED GREEN BLUE`
**Changes the colour of all CCA tags** | `colourc c/RED GREEN BLUE`
**View help page** | `help`
**Exit the app** | `exit`
