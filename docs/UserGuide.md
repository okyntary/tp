---
layout: page
title: User Guide
---

ePoch is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type quickly, ePoch can get your contact management tasks done faster than traditional GUI apps.

It has been designed for the busy NUS student. It allows the user to create contacts for persons and to create CCAs, link those persons and CCAs together, and to create periodic reminders for those CCAs.

It is intended to be used by NUS students, to help keep track of the students and organizations that they encounter.

![User interface](images/Ui.png)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java 11 or above installed in your Computer.

1. Download the JAR file, which can be found [here](https://github.com/AY2122S1-CS2103-T14-2/tp/releases/tag/v1.3.1).

1. Copy the file to the folder you want to use as the _home folder_ for your ePoch app.

1. Double-click the file to start the app. The GUI, similar to the one shown above, should appear in a few seconds. Note how the app contains some sample data.

1. Enter commands in the command box and press Enter to execute them. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all persons, CCAs and reminders currently stored in ePoch.

   * **`addp n/Tan Wei Yang a/CAPT e/tanweiyang@u.nus.edu.sg p/94492210`** Adds a person named `Tan Wei Yang`, with address `CAPT`, email `tanweiyang@u.nus.edu`, and phone number `94492210` to ePoch.

   * **`deletep`**` 3` : Deletes the person with id 3, ie. shown as third in the list of persons.

   * **`clear`** : Deletes all data (persons, CCAs and reminders) from ePoch.

   * **`exit`** : Exits ePoch.

1. Refer to the [Features](##features) segment below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Attributes in ePoch

Attribute Name | Type | Argument Tag | Example
---------------|------|--------------|--------
Index (CCA ID, Person ID or Reminder ID) | Integer | (NONE) | `1`, `5`, `10`
Name | String (alphanumeric characters or spaces) | `n/` | `n/Alice`, `n/NUSSO`
Phone Number | String containing integers only | `p/` | `p/91234567`, `p/0123`
Email | String | `e/` | `e/alice@mail.com`, `e/nusso123@nus`
Address | String | `a/` | `a/22 College Avenue East`
Start date | Date in yyyy-MM-dd format | `sd/` | `sd/2021-10-5`
Frequency | A positive integer followed by a time period; the time period is either `d` (daily), `w` (weekly), `m` (monthly) or `y` (yearly); the integer may range from 1 to 100 (inclusive) | `f/` | `f/3d`, `f/2w`, `f/6m`, `f/1y`
Occurrences | Positive integer from 1 to 50 (inclusive) | `o/` | `o/10`
Person ID | Integer | `pid/` | `pid/1`
CCA ID | Integer | `cid/` | `cid/1`
Reminder ID | Integer | `rid/` | `rid/1`
Tag | String (alphanumeric characters with no spaces) | `t/` | `t/friend`

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addc n/CCA_NAME [t/TAG]`, `NAME` is a parameter which can be used as `addc n/NUSSO`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/NUSHackers t/cool` or as `n/NUSHackers`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

Note: ePoch has a capacity of 1,000,000,000 persons, CCAs and reminders each.

_All commands, with the exception of find-related commands, will refresh the UI to show all data in ePoch, in order to avoid confusing the user._

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Listing all persons : `list`

Shows a list of all persons, CCAs, reminders in ePoch.

Format: `list`

### Adding a person: `addp`

Adds a person to ePoch.

Format: `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]... ​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person must have at least their name, phone number, email, and address added.
</div>

Examples:
* `addp n/Jovyn Tan Li Shyan p/98765432 e/email@mail.com a/NUS`
* `addp n/Neo Wei Qing p/91231234 e/wei_qing_official_email_real@gmail.com a/Cinnamon College t/friend`

### Editing a person: `editp`

Edits an existing person in ePoch.

Format: `editp PERSON_INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]...​`

* Edits the person at the specified by displayed index. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values, while unchanged fields will remain the same.
* If any new tag is added, all existing tags will be replaced with new tags.

Examples:
*  `editp 1 p/91234567 e/jiveshrealemail@yahoo.com` Edits the phone number and email address of the 1st person to be `91234567` and `jiveshrealemail@yahoo.com` respectively.
*  `editp 2 n/weiq t/friend t/groupmate` Edits the name of the 2nd person to be `weiq`, clears existing tags, and sets the person's tags to `friend` and `groupmate`. 

### Deleting a person: `deletep`

Deletes the specified person from ePoch.

Format: `deletep PERSON_INDEX`

* Deletes the person with the specified index `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `deletep 2` deletes the person in the address book that has an index of 2.

### Finding a person: `findp`

Finds all the people in ePoch whose name matches any of the search keywords, along with all CCAs they are enrolled in, 
as well as all reminders associated with these CCAs.

Format: `findp FIRST_WORD [SECOND_WORD] ....`

* Finds the people whose name matches any of the given space-separated keywords.
* At least one keyword must be provided.

### Adding a CCA: `addc`

Adds a CCA.

Format: `addc n/CCA_NAME [t/TAG]...`

### Editing a CCA: `editc`

Edits the details of a CCA.

Format: `editc CCA_ID [n/CCA_NAME] [t/TAG]...`

### Deleting a CCA: `deletec`

Deletes a CCA.

Format: `deletec CCA_ID`

### Finding a CCA: `findc`

Finds all CCAs with names that match any of the search keywords, as well as people enrolled in these CCAs and reminders associated with these CCAs.

Format: `findc FIRST_WORD [SECOND_WORD] ....`

### Enrolling a person from a CCA: `enrol`

Enrols a person into a CCA.

Format: `enrol cid/CCA_ID pid/PERSON_ID`

### Expelling a person from a CCA: `expel`

Expels a person from a CCA.

Format: `expel cid/CCA_ID pid/PERSON_ID`

### Adding a reminder to a CCA: `addr`

Adds a reminder to a CCA.
If frequency and occurrences are specified, the reminder will repeat at the specified frequency; else, the reminder will be treated as a once-off event.

Format: `addr cid/CCA_ID n/REMINDER_NAME sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`

* The maximum possible number of occurrences is 50.
* The maximum possible period of the frequency (integer part of the frequency) is 100.
* The year of the start date of a reminder must be before 3000.

`FREQUENCY` should be specified as an integer followed immediately (no space) by a letter, either d, w, m, or y.

Letter | Meaning
-------|------------------
**d** | day
**w** | week
**m** | month
**y** | year

### Editing a reminder: `editr`

Edits a reminder.

Format: `editr INDEX [n/REMINDER_NAME] [sd/START_DATE] [f/FREQUENCY] [o/OCCURRENCES]`

* If none of the optional fields are specified (e.g. editr 1), then the reminder will not be edited (since no edits have been specified).

### Deleting a reminder: `deleter`

Deletes a reminder.

Format: `deleter REMINDER_ID`

### Finding the title of a reminder: `findr`

Finds all reminders that match any of the given keywords, as well as CCAs associated with these reminders.

Format: `findr FIRST_WORD [SECOND_WORD] ....`

### Snoozing a reminder: `snoozer`

Snoozes the reminder.
Snoozing a reminder means that the reminder will be shifted to the date of its next occurrence (if any).

Format: `snoozer REMINDER_ID`

* If the reminder is on its last occurrence (occurrences = 1), snoozing the reminder will result in it being removed entirely (as it will have no more occurrences left after being snoozed).
* If the reminder is not on its last occurrence (occurrences > 1), snoozing the reminder will shift it to the date of its next occurrence.
* The date of next occurrence is calculated from the current date which the reminder occurs and its frequency.

### Delete all data from ePoch: `clear`

Deletes all data (persons, CCAs, reminders) from ePoch.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

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
**A**: Install the app in the other computer and overwrite the empty data file it creates with the `addressbook.json` file that contains the data from your previous ePoch home folder.

**Q**: Can I create a reminder without linking it to a CCA?<br>
**A**: No. We do not support that functionality: all reminders must be linked to a CCA in some way.

--------------------------------------------------------------------------------------------------------------------

## Summary of commands

Action | Format
-------|------------------
**View help page** | `help`
**List all data** | `list`
**Add person** | `addp n/PERSON_NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS`
**Edit person data** | `editp PERSON_INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`
**Delete person** | `deletep PERSON_INDEX`
**Find person** | `findp FIRST_WORD [SECOND_WORD] ....`
**Add CCA** | `addc n/CCA_NAME [t/TAG]...`
**Edit CCA data** | `editc CCA_ID [n/CCA_NAME] [t/TAG]...`
**Delete CCA** | `deletec CCA_ID`
**Enrols a person into CCA** | `enrol cid/CCA_ID pid/PERSON_ID`
**Removes a person from a CCA** | `expel cid/CCA_ID pid/PERSON_ID`
**Find CCA** | `findc FIRST_WORD [SECOND_WORD] ....`
**Add reminder** | `addr cid/CCA_ID n/REMINDER_NAME sd/START_DATE [f/FREQUENCY] [o/OCCURRENCES]`
**Edit reminder data** | `editr INDEX [n/REMINDER_NAME] [sd/START_DATE] [f/FREQUENCY] [o/OCCURRENCES]`
**Delete reminder** | `deleter REMINDER_ID`
**Find reminder** | `findr FIRST_WORD [SECOND_WORD] ....`
**Snooze reminder** | `snoozer REMINDER_ID`
**Delete all data** | `clear`
**Exit the app** | `exit`
