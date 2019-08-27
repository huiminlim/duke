import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Exception;

enum Command {
    LIST, BYE, DONE, TODO, DEADLINE, EVENT, DELETE, ECHO;
}

public class Duke {
    private static final String HORIZONTAL_LINE = "\t____________________________________________________________";

    private static ArrayList<Task> list = new ArrayList<>();
    private static int listCounter = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String greeting = "Hello! I'm Duke\nWhat can I do for you?";
        String goodbye = "Bye. Hope to see you again soon!";

        System.out.println(greeting);

        // Start reading input
        while (sc.hasNextLine()) {
            try {
                String readInput = sc.next();

                // Check if input command exist
                boolean isExist = false;
                for (Command c : Command.values()) {
                    // If exists
                    if (c.name().equals(readInput.toUpperCase())) {
                        // Not recognized command
                        isExist = true;
                    }
                }
                if (!isExist) throw new CommandNotRecognizedException();

                // Command exist
                Command cmd = Command.valueOf(readInput.toUpperCase());

                if (cmd == Command.BYE) {
                    System.out.println(processText(goodbye));
                    break;
                } else if (cmd == Command.LIST) {
                    // Error Handling: ListEmpty
                    if (listCounter == 0) {
                        // Clear buffer of scanner
                        String i = sc.nextLine();

                        throw new EmptyListException("");
                    }

                    String printList = "Here are the tasks in your list:\n";

                    for (int i = 0; i < listCounter; i++) {
                        String temp = "\t\t" + (i + 1) + "." + list.get(i).getItemInfo();
                        printList = printList + temp;
                        if (i != listCounter - 1) printList = printList + "\n";
                    }
                    System.out.println(processText(printList));

                } else if (cmd == Command.TODO) {
                    String toDoItem = sc.nextLine().trim();
                    // Implies only word "deadline"
                    if (toDoItem.equals("")) {
                        throw new EmptyCommandField("todo");
                    }

                    list.add(new ToDo(toDoItem, false));
                    listCounter++;

                    String tempPrint = addedTaskText();
                    System.out.println(processText(tempPrint));

                } else if (cmd == Command.EVENT) {
                    String input = sc.nextLine().trim();

                    int indexFirstSlash = input.indexOf("/");
                    if(indexFirstSlash == -1) throw new CommandFieldFormatException("Incorrect command format");

                    list.add(new Event(input.substring(0, indexFirstSlash), input.substring(indexFirstSlash + 1),
                            false));
                    listCounter++;

                    String tempPrint = addedTaskText();
                    System.out.println(processText(tempPrint));

                } else if (cmd == Command.DEADLINE) {

                    String input = sc.nextLine().trim();

                    //String[] tokenList = input.split(" ");
                    int indexFirstSlash = input.indexOf("/");
                    if(indexFirstSlash == -1) throw new CommandFieldFormatException("Incorrect command format");

                    list.add(new Deadline(input.substring(0, indexFirstSlash), input.substring(indexFirstSlash + 1),
                            false));
                    listCounter++;

                    String tempPrint = addedTaskText();

                    System.out.println(processText(tempPrint));

                } else if (cmd == Command.DONE) {
                    // Cannot perform done in zero list
                    if (listCounter == 0) {
                        // Clear buffer of scanner
                        String i = sc.nextLine();

                        throw new EmptyListException("Cannot perform done.");
                    }

                    int indexDone = sc.nextInt();

                    // If indexDone exceed listCounter
                    if (indexDone > listCounter || indexDone < 0) {
                        throw new InvalidNumberException("Error exceeding item number");
                    }

                    list.get(--indexDone).setDone();

                    String doneMessage = "Nice! I've marked this task as done: \n\t\t";
                    System.out.println(processText(doneMessage + list.get(indexDone).getItemInfo()));
                } else if (cmd == Command.ECHO) {
                    // Read any remaining lines
                    String echoInput = sc.nextLine().trim();

                    if (echoInput.equals("")) {
                        throw new EmptyCommandField("echo");
                    }

                    // Store content
                    list.add(new Task(echoInput, false));
                    listCounter++;

                    String processedInput = Duke.processText(echoInput);
                    System.out.println(processedInput);

                } else if (cmd == Command.DELETE) {
                    // Incorrect format for delete
                    if (!sc.hasNextInt()) {
                        sc.nextLine();
                        throw new CommandFieldFormatException("delete");
                    }

                    int numberToDelete = sc.nextInt();

                    // Make sure number is valid
                    if (numberToDelete > listCounter || numberToDelete < 1) {
                        throw new InvalidNumberException("Number to delete does not exist");
                    }

                    Task deletedTask = list.remove(numberToDelete - 1);
                    listCounter--;
                    String tempPrint = "Noted. I've removed this task:\n\t\t" +
                            deletedTask.getItemInfo() + "\n\tNow you have " + listCounter + " tasks in the list.";
                    System.out.println(processText(tempPrint));
                }

            } catch (CommandNotRecognizedException c) {
                // Clear buffer of scanner
                String i = sc.nextLine();

                System.out.println(processText("\u263A OOPS!!! I'm sorry, but I don't know what that means :-("));

            } catch (EmptyCommandField e) {
                System.out.println(processText("\u263A The description of " + e.getMessage() + " cannot be empty."));

            } catch (EmptyListException l) {
                System.out.println(processText("\u263A List is empty! " + l.getMessage()));

            } catch (CommandFieldFormatException f) {
                System.out.println(processText("\u263A Description format is incorrect for " + f.getMessage() + "."));

            } catch (InvalidNumberException n) {
                System.out.println(processText("\u263A Invalid input number. " + n.getMessage()));
            }
        }
    }

    // Add in Indentation and horizontal lines
    private static String processText(String input) {
        return HORIZONTAL_LINE + "\n" + "\t" + input + "\n" + HORIZONTAL_LINE + "\n";
    }

    private static String addedTaskText() {
        return "Got it. I've added this task:\n\t\t" +
                list.get(listCounter - 1).getItemInfo() +
                "\n\tNow you have " + listCounter + " tasks in the list.";
    }
}

class TimeDate {
    private int time;
    private int day;
    private int month;
    private int year;

    public TimeDate() {
        time = 0;
        day = 0;
        month = 0;
        year = 0;
    }

    public TimeDate(int day, int month, int year, int time){
        this.time = time;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String print(){
        return this.day + "/" + this.month + "/" + this.year + " " +this.time;
    }

    public boolean isValid(){
        if(this.time < 0 || this.time >= 2400) return false;
        else if(this.day <= 0 || this.day > 31) return false;
        else if (this.month <= 0 || this.month > 12) return false;
        else if (this.year < 0) return false;

        return true;
        // yet to handle leap, 31 months etc
    }

}

class Task {
    protected static final String ICON_TICK = "\u2713";
    protected static final String ICON_CROSS = "\u2718";

    protected boolean isDone;
    protected String taskItem;

    // Default Constructor
    public Task() {
        isDone = false;
    }

    // Non-default Constructor
    public Task(String taskItem, boolean isDone) {
        this.isDone = isDone;
        this.taskItem = taskItem.trim();
    }

    public void setDone() {
        isDone = true;
    }

    public String getStatusIcon() {
        if (isDone) return "[" + ICON_TICK + "]";
        else return "[" + ICON_CROSS + "]";
    }

    public String getTaskItem() {
        return taskItem;
    }

    public String getItemInfo() {
        return getStatusIcon() + " " + getTaskItem();
    }
}

class ToDo extends Task {

    public ToDo(String toDo, boolean isDone) {
        super(toDo.trim(), isDone);
    }

    @Override
    public String getStatusIcon() {
        if (isDone) return "[T][" + ICON_TICK + "]";
        else return "[T][" + ICON_CROSS + "]";
    }
}

class Deadline extends Task {
    private TimeDate timeDateGiven;

    public Deadline(String event, String timing, boolean isDeadlineProcessed) {
        super(event.trim(), isDeadlineProcessed);

        try {
            String[] str = timing.replace("by ", "").replace(" ", "/").split("/");
            int[] temp = new int[str.length];
            for(int i = 0; i < str.length; i++){
                temp[i] = Integer.parseInt(str[i]);
            }

            timeDateGiven = new TimeDate(temp[0], temp[1], temp[2], temp[3]);

            if(timeDateGiven.isValid()) throw new InvalidTimeDateException();

        } catch(InvalidTimeDateException e){
            System.out.println("Time and date entered is invalid");
        }
    }

    @Override
    public String getStatusIcon() {
        if (isDone) return "[D][" + ICON_TICK + "]";
        else return "[D][" + ICON_CROSS + "]";
    }

    public String getDeadline(){
        return timeDateGiven.print();
    }


    @Override
    public String getItemInfo() {
        return getStatusIcon() + " " + getTaskItem() + " (by: " + getDeadline() + ")";
    }
}

class Event extends Task {
    private TimeDate timeDateGiven;

    public Event(String event, String timing, boolean isDone) {
        super(event.trim(), isDone);

        try {
            String[] str = timing.replace("at ", "").replace(" ", "/").split("/");
            int[] temp = new int[str.length];
            for(int i = 0; i < str.length; i++){
                temp[i] = Integer.parseInt(str[i]);
            }

            timeDateGiven = new TimeDate(temp[0], temp[1], temp[2], temp[3]);

            if(timeDateGiven.isValid()) throw new InvalidTimeDateException();

        } catch(InvalidTimeDateException e){
            System.out.println("Time and date entered is invalid");
        }
    }

    @Override
    public String getStatusIcon() {
        if (isDone) return "[E][" + ICON_TICK + "]";
        else return "[E][" + ICON_CROSS + "]";
    }

    public String getTiming() {
        return timeDateGiven.print();
    }

    @Override
    public String getItemInfo() {
        return getStatusIcon() + " " + getTaskItem() + " (at: " + getTiming() + ")";
    }

}

class InvalidTimeDateException extends Exception {

}

class CommandNotRecognizedException extends Exception {
    public CommandNotRecognizedException() {
        super("Error");
    }
}

// For:
// (1) Error printing empty list
// (2) Error performing done on empty list
class EmptyListException extends Exception {
    public EmptyListException(String info) {
        super(info);
    }
}

// Format of command is incorrect: not using "/"
class CommandFieldFormatException extends Exception {
    public CommandFieldFormatException(String command) {
        super(command);
    }
}

// Exceeded number of items in list
class InvalidNumberException extends Exception {
    public InvalidNumberException(String info) {
        super(info);
    }
}

class EmptyCommandField extends Exception {
    public EmptyCommandField(String command) {
        super(command);
    }
}