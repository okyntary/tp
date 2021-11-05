package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cca.CcaEditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.PersonEditCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;
import seedu.address.model.util.Frequency;
import seedu.address.testutil.EditCcaDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditReminderDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_MUSIC = "Music";
    public static final String VALID_TAG_USP = "USP";
    public static final String VALID_TAG_SPORTS = "sports";

    public static final String VALID_CCA_NAME_NUSSO = "NUSSO";
    public static final String VALID_CCA_NAME_USKICK = "USKick";
    public static final String VALID_CCA_NAME_TRACK = "USP Track";
    public static final String VALID_CCA_NAME_USP_ULTIMATE = "USP Ultimate";
    public static final String VALID_CID_FIRST_CCA = "1";
    public static final String VALID_CID_SECOND_CCA = "2";
    public static final String VALID_CCA_TAG_INTEREST_GROUP = "InterestGroup";
    public static final String VALID_CCA_TAG_DISC_PROVIDED = "DiscProvided";
    public static final String VALID_CCA_TAG_INTERVIEW_NEEDED = "InterviewNeeded";

    public static final String VALID_REMINDER_NAME_MEETING = "Meeting";
    public static final String VALID_REMINDER_NAME_REHEARSAL = "rehearsal";
    public static final String VALID_REMINDER_NAME_PROJECT = "project";
    public static final String VALID_REMINDER_NAME_CLASS = "Class";
    public static final String VALID_START_DATE_MEETING = "2021-10-30";
    public static final String VALID_START_DATE_CLASS = "2021-11-01";
    public static final String VALID_FREQUENCY_MEETING = "1w";
    public static final String VALID_FREQUENCY_CLASS = "2d";
    public static final String VALID_OCCURRENCES_MEETING = "30";
    public static final String VALID_OCCURRENCES_CLASS = "50";
    public static final Frequency VALID_TIME_PERIOD_DAY = Frequency.DAY;
    public static final Frequency VALID_TIME_PERIOD_WEEK = Frequency.WEEK;
    public static final Frequency VALID_TIME_PERIOD_YEAR = Frequency.YEAR;
    public static final int VALID_NUM_TIME_PERIOD_1 = 1;
    public static final int VALID_NUM_TIME_PERIOD_10 = 10;
    public static final int VALID_NUM_TIME_PERIOD_2 = 2;
    public static final int VALID_OCCURENCES_5 = 5;
    public static final int VALID_OCCURENCES_10 = 10;
    public static final int VALID_OCCURENCES_30 = 30;
    public static final int VALID_OCCURRENCES_50 = 50;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String CCA_NAME_DESC_NUSSO = " " + PREFIX_NAME + VALID_CCA_NAME_NUSSO;
    public static final String CCA_NAME_DESC_TRACK = " " + PREFIX_NAME + VALID_CCA_NAME_TRACK;
    public static final String CCA_NAME_DESC_USKICK = " " + PREFIX_NAME + VALID_CCA_NAME_USKICK;
    public static final String TAG_DESC_USP = " " + PREFIX_TAG + VALID_TAG_USP;
    public static final String TAG_DESC_MUSIC = " " + PREFIX_TAG + VALID_TAG_MUSIC;
    public static final String TAG_DESC_SPORTS = " " + PREFIX_TAG + VALID_TAG_SPORTS;

    public static final String REMINDER_CID_FIRST_CCA = " " + PREFIX_CCA_ID + VALID_CID_FIRST_CCA;
    public static final String REMINDER_CID_SECOND_CCA = " " + PREFIX_CCA_ID + VALID_CID_SECOND_CCA;
    public static final String REMINDER_NAME_DESC_MEETING = " " + PREFIX_NAME + VALID_REMINDER_NAME_MEETING;
    public static final String REMINDER_NAME_DESC_CLASS = " " + PREFIX_NAME + VALID_REMINDER_NAME_CLASS;
    public static final String REMINDER_START_DATE_DESC_MEETING = " " + PREFIX_START_DATE + VALID_START_DATE_MEETING;
    public static final String REMINDER_START_DATE_DESC_CLASS = " " + PREFIX_START_DATE + VALID_START_DATE_CLASS;
    public static final String REMINDER_FREQUENCY_DESC_MEETING = " " + PREFIX_FREQUENCY + VALID_FREQUENCY_MEETING;
    public static final String REMINDER_FREQUENCY_DESC_CLASS = " " + PREFIX_FREQUENCY + VALID_FREQUENCY_CLASS;
    public static final String REMINDER_OCCURRENCES_DESC_MEETING = " " + PREFIX_OCCURRENCES + VALID_OCCURRENCES_MEETING;
    public static final String REMINDER_OCCURRENCES_DESC_CLASS = " " + PREFIX_OCCURRENCES + VALID_OCCURRENCES_CLASS;

    public static final String INVALID_REMINDER_NAME_DESC = " " + PREFIX_NAME + "Meeting&"; // '&' not allowed in names
    public static final String INVALID_START_DATE_DESC = " " + PREFIX_START_DATE + "2020-02-30";
    public static final String INVALID_FREQUENCY_DESC = " " + PREFIX_FREQUENCY + "1dw";
    public static final String INVALID_OCCURRENCES_DESC = " " + PREFIX_OCCURRENCES + "1w0";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_CCA_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in CCA names

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final PersonEditCommand.EditPersonDescriptor DESC_AMY;
    public static final PersonEditCommand.EditPersonDescriptor DESC_BOB;
    public static final ReminderEditCommand.EditReminderDescriptor DESC_REHEARSAL;
    public static final ReminderEditCommand.EditReminderDescriptor DESC_MEETING;
    public static final CcaEditCommand.EditCcaDescriptor DESC_TRACK;
    public static final CcaEditCommand.EditCcaDescriptor DESC_USKICK;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_REHEARSAL = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_REHEARSAL)
                .withStartDate(new Date()).withFrequency(VALID_TIME_PERIOD_DAY, VALID_NUM_TIME_PERIOD_10)
                .withOccurrence(VALID_OCCURENCES_5).withCcaName(VALID_CCA_NAME_NUSSO).build();
        DESC_MEETING = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_MEETING)
                .withStartDate(new Date()).withFrequency(VALID_TIME_PERIOD_YEAR, VALID_NUM_TIME_PERIOD_2)
                .withOccurrence(VALID_OCCURENCES_10).withCcaName(VALID_CCA_NAME_USKICK).build();
        DESC_TRACK = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_TRACK)
                .withTags(VALID_TAG_SPORTS).build();
        DESC_USKICK = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_USKICK)
                .withTags(VALID_TAG_USP, VALID_TAG_SPORTS).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the CCA at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCcaAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCcaList().size());

        Cca cca = model.getFilteredCcaList().get(targetIndex.getZeroBased());
        final String[] splitName = cca.getName().fullName.split("\\s+");
        model.updateFilteredCcaList(new CcaNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCcaList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the reminder at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showReminderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredReminderList().size());

        Reminder reminder = model.getFilteredReminderList().get(targetIndex.getZeroBased());
        final String[] splitName = reminder.getName().fullName.split("\\s+");
        model.updateFilteredReminderList(new ReminderNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredReminderList().size());
    }
}
