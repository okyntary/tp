package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GreetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.cca.CcaAddCommand;
import seedu.address.logic.commands.cca.CcaDeleteCommand;
import seedu.address.logic.commands.cca.CcaEditCommand;
import seedu.address.logic.commands.cca.CcaEditCommand.EditCcaDescriptor;
import seedu.address.logic.commands.cca.CcaEnrolCommand;
import seedu.address.logic.commands.cca.CcaExpelCommand;
import seedu.address.logic.commands.cca.CcaFindCommand;
import seedu.address.logic.commands.person.PersonAddCommand;
import seedu.address.logic.commands.person.PersonDeleteCommand;
import seedu.address.logic.commands.person.PersonEditCommand;
import seedu.address.logic.commands.person.PersonEditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.person.PersonFindCommand;
import seedu.address.logic.commands.reminder.ReminderAddCommand;
import seedu.address.logic.commands.reminder.ReminderDeleteCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand.EditReminderDescriptor;
import seedu.address.logic.commands.reminder.ReminderFindCommand;
import seedu.address.logic.commands.reminder.ReminderSnoozeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.CcaUtil;
import seedu.address.testutil.EditCcaDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditReminderDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.ReminderUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    ///// person

    @Test
    public void parseCommand_personAdd() throws Exception {
        Person person = new PersonBuilder().build();
        PersonAddCommand command = (PersonAddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new PersonAddCommand(person), command);
    }

    @Test
    public void parseCommand_personDelete() throws Exception {
        PersonDeleteCommand command = (PersonDeleteCommand) parser.parseCommand(
                PersonDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PersonDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_personEdit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        PersonEditCommand command = (PersonEditCommand) parser.parseCommand(PersonEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new PersonEditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_personFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        PersonFindCommand command = (PersonFindCommand) parser.parseCommand(
                PersonFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new PersonFindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    ///// cca

    @Test
    public void parseCommand_ccaAdd() throws Exception {
        Cca cca = new CcaBuilder().build();
        CcaAddCommand command = (CcaAddCommand) parser.parseCommand(CcaUtil.getAddCommand(cca));
        assertEquals(new CcaAddCommand(cca), command);
    }

    @Test
    public void parseCommand_ccaDelete() throws Exception {
        CcaDeleteCommand command = (CcaDeleteCommand) parser.parseCommand(
                CcaDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_CCA.getOneBased());
        assertEquals(new CcaDeleteCommand(INDEX_FIRST_CCA), command);
    }

    @Test
    public void parseCommand_ccaEdit() throws Exception {
        Cca cca = new CcaBuilder().build();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(cca).build();
        CcaEditCommand command = (CcaEditCommand) parser.parseCommand(CcaEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CCA.getOneBased() + " " + CcaUtil.getEditCcaDescriptorDetails(descriptor));
        assertEquals(new CcaEditCommand(INDEX_FIRST_CCA, descriptor), command);
    }

    @Test
    public void parseCommand_ccaEnrol() throws Exception {
        CcaEnrolCommand command = (CcaEnrolCommand) parser.parseCommand(CcaUtil.getEnrolCommand(INDEX_FIRST_CCA,
                INDEX_FIRST_PERSON));
        assertEquals(new CcaEnrolCommand(INDEX_FIRST_CCA, INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_ccaExpel() throws Exception {
        CcaExpelCommand command = (CcaExpelCommand) parser.parseCommand(CcaUtil.getExpelCommand(INDEX_FIRST_CCA,
                INDEX_FIRST_PERSON));
        assertEquals(new CcaExpelCommand(INDEX_FIRST_CCA, INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_ccaFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        CcaFindCommand command = (CcaFindCommand) parser.parseCommand(
                CcaFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new CcaFindCommand(new CcaNameContainsKeywordsPredicate(keywords)), command);
    }


    ///// reminder

    @Test
    public void parseCommand_reminderAdd() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        reminder.setCcaName("");
        ReminderAddCommand command = (ReminderAddCommand) parser.parseCommand(ReminderUtil.getAddCommand(reminder));
        assertEquals(new ReminderAddCommand(reminder, INDEX_FIRST_CCA), command);
    }

    @Test
    public void parseCommand_reminderDelete() throws Exception {
        ReminderDeleteCommand command = (ReminderDeleteCommand) parser.parseCommand(
                ReminderDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new ReminderDeleteCommand(INDEX_FIRST_REMINDER), command);
    }

    @Test
    public void parseCommand_reminderEdit() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        reminder.setCcaName("");
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(reminder).build();
        descriptor.setCca(null);
        ReminderEditCommand command = (ReminderEditCommand) parser.parseCommand(
                ReminderEditCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased() + " "
                + ReminderUtil.getEditReminderDescriptorDetails(descriptor));
        assertEquals(new ReminderEditCommand(INDEX_FIRST_REMINDER, descriptor), command);
    }

    @Test
    public void parseCommand_reminderFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ReminderFindCommand command = (ReminderFindCommand) parser.parseCommand(
                ReminderFindCommand.COMMAND_WORD + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ReminderFindCommand(new ReminderNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_reminderSnooze() throws Exception {
        ReminderSnoozeCommand command = (ReminderSnoozeCommand) parser.parseCommand(
                ReminderSnoozeCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new ReminderSnoozeCommand(INDEX_FIRST_REMINDER), command);
    }

    ///// general

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_greet() throws Exception {
        assertTrue(parser.parseCommand(GreetCommand.COMMAND_WORD) instanceof GreetCommand);
        assertTrue(parser.parseCommand(GreetCommand.COMMAND_WORD + " 3") instanceof GreetCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
