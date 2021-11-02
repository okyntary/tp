---
layout: page
title: Teo Keane's/okyntary's Project Portfolio Page
---

### Project: ePoch

ePoch helps overcommitted students keep track of their many contacts and events across various CCAs. The product allows students to link persons with CCAs and set reminders that repeat over a given time interval, to conveniently organise their commitments and overlapping social circles.

### Summary of contributions:

* **Code contributed**
  * [RepoSense link]((https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=t14-2&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=okyntary&tabRepo=AY2122S1-CS2103-T14-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false) 

* **Enhancements implemented**
  * Implement storage for all new features that require storage between sessions, so that important data is retained between sessions
    * Creation of new classes to store new objects and object types
      * `JsonAdaptedCca` to match new class `Cca`
      * `JsonAdaptedReminder` to match new class `Reminder`
    * Modification of existing classes to store new attributes for existing object types
      * `JsonSerializableAddressBook` to account for modified class `AddressBook`
      * `JsonAdaptedPerson` to match modified class `Person`
  * Add `addr` command for creating new `Reminder` objects
  * Modify `deletep`. `deletec` and `deleter` commands to match desired behaviour, according to ePoch's desired data structure

* **Documentation**:
  * User Guide:
    * Added documentation for the features section [\#31](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/31)
    * Proofread and edited lines over the entirety of the User Guide [\#120](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/120)
    * Changed documentation for `addc` and `editc` commands to reflect changes in the parameters accepted * [/#218](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/218)
  * Developer Guide:
    * Update documentation related to `Storage` component
      * Update UML diagram to reflect new and modified classes in `Storage` component [\#239](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/239) 
      * Added Sequence Diagram to illustrate `saveAddressBook()` method call [\#239](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/239)
      * Added explanation for `Storage` implementation details [\#239](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/239)
    * Added, proofread and edited Use Cases, Non-Functional Requirements, Glossary and Instructions for Manual Testing [\#35](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/35/files), [\#120](https://github.com/AY2122S1-CS2103-T14-2/tp/pull/120)
