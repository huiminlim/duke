package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;


/**
 * General command class,
 * contains basic abstract command actions, such as execute, check if exit command
 * and get command type and pending tasks of command.
 */
public abstract class Command {
    private COMMAND_TYPE commandType;
    private Task pendingTask;

    public Command(String command, Task pendingTask) {
        try {
            this.commandType = COMMAND_TYPE.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException i) {
            throw new IllegalArgumentException();
        } catch (NullPointerException n) {
            throw new NullPointerException();
        }

        if (pendingTask == null) throw new NullPointerException();
        else this.pendingTask = pendingTask;
    }

    /**
     * Perform command actions.
     */
    public abstract void execute(TaskList list, Ui ui, Storage storage) throws IOException;

    /**
     * Return boolean indicating if command is exit command.
     *
     * @return boolean flag indicating if is exit command.
     */
    public abstract boolean isExit();

    /**
     * Return task object held by command.
     *
     * @return task held in pendingTask.
     */
    public Task getPendingTask() {
        return pendingTask;
    }

    /**
     * Return type of command from enum COMMAND_TYPE.
     *
     * @return COMMAND_TYPE enum constant of commandType.
     */
    public COMMAND_TYPE getCommandType() {
        return commandType;
    }

}


