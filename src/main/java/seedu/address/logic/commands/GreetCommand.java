package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in ePoch to the user.
 */
public class GreetCommand extends Command {

    public static final String COMMAND_WORD = "greet";

    public static final String MESSAGE_SUCCESS = "Hello! I am ePoch!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
