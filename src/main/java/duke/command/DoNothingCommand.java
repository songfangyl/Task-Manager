package duke.command;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.UiManager;

/**
 * This is a command that do nothing.
 */
public class DoNothingCommand extends Command {

    @Override
    public String execute(TaskList taskList, UiManager uiManager, Storage storage) throws DukeException {
        return "Try to say something!";
    }
}
