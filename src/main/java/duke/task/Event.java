package duke.task;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an Event task.
 * Has functions to print the task information to a standard form,
 * to set and get the given time of the deadline.
 */
public class Event extends Task {
    private Date givenTime;
    private SimpleDateFormat df;

    /**
     * Constructor to create Event task object.
     *
     * @param taskDescription String input of command from user.
     * @param isDone          Boolean indicating if task is completed.
     * @param givenTime       String of given time of deadline.
     */
    public Event(String taskDescription, boolean isDone, String givenTime) {
        super(taskDescription, isDone);

        try {
            df = new SimpleDateFormat("dd/mm/yyyy Hm");
            this.givenTime = df.parse(givenTime, new ParsePosition(0));
            System.out.println(this.givenTime);

        } catch (NullPointerException n) {
            throw new NullPointerException();
        }
    }

    /**
     * Constructor to create Event task object.
     *
     * @param taskType        Character of first letter of task.
     * @param taskDescription String input of command from user.
     * @param isDone          Boolean indicating if task is completed.
     * @param givenTime       String of given time of deadline.
     */
    public Event(char taskType, String taskDescription, boolean isDone, String givenTime) {
        super(taskType, taskDescription, isDone);

        try {
            df = new SimpleDateFormat("dd/mm/yyyy Hm");
            this.givenTime = df.parse(givenTime, new ParsePosition(0));

        } catch (NullPointerException n) {
            throw new NullPointerException();
        }
    }

    /**
     * Returns string representation of task,
     * in terms of initial, icon of done, task description and given deadline.
     *
     * @return String representation of task.
     */
    @Override
    public String printTask() {
        return "[" + getFirstCharTask() + "][" + getIcon() + "] "
               + getTaskDescription() + " (at: " + getGivenTime() + ")";
    }

    /**
     * Set time for given deadline.
     *
     * @param time String representation of time.
     * @throws ParseException If format of time in string does not match specified format.
     */
    public void setGivenTime(String time) throws ParseException {
        this.givenTime = df.parse(time);
    }

    /**
     * Get time for given deadline.
     *
     * @return String of formatted time.
     */
    public String getGivenTime() {
        return df.format(givenTime);
    }
}
