package duke.ui;

import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Ui
 * Contains functions to interact with user.
 */
public class Ui {
    private Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Display welcome message.
     */
    public void showWelcome() {
        String greeting = "Hello! I'm Duke\nWhat can I do for you?";
        System.out.println(greeting);
    }

    public String getWelcome(){
        return "Hello! I'm Duke, what can I do for you?";
    }

    /**
     * Display line.
     */
    public void showLine() {
        final String horizontalLine = "____________________________________________________________";
        System.out.println(horizontalLine);
    }

    /**
     * Display bye message.
     */
    public void showBye() {
        final String goodbye = "Bye. Hope to see you again soon!";
        sc.close();
        System.out.println(goodbye);
    }

    public String getBye(){
       return "Bye. Hope to see you again soon!";
    }

    /**
     * Display loading error message.
     */
    public void showLoadingError() {
        System.out.println("Error in loading tasks into Duke.");
    }

    /**
     * Read user input from system input.
     *
     * @return String User input.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Display added task message.
     */
    public void showAddedTask(char firstAlphabet, boolean isDone, String taskDescription, int numberOfItems) {
        char icon;
        if(isDone){
            icon = '✓';
        }
        else{
            icon = '❌';
        }

        System.out.println("Got it. I've added this task: ");
        System.out.println("\t[" + firstAlphabet + "][" + icon + "] " + taskDescription);
        System.out.println("Now you have " + numberOfItems + " in the list.");
    }

    public String getAddedTask(char firstAlphabet, boolean isDone, String taskDescription, int numberOfItems) {
        char icon;
        if(isDone){
            icon = '✓';
        }
        else{
            icon = '❌';
        }
        return "Got it. I've added this task: " + "\t[" + firstAlphabet + "][" + icon + "] " + taskDescription + "Now" +
               " you " + "have " + numberOfItems + " in the list.";
    }

    /**
     * Display list message.
     */
    public void showList(ArrayList<String> listToPrint) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < listToPrint.size(); i++) {
            System.out.println((i + 1) + "." + listToPrint.get(i));
        }
    }

    public String getList(ArrayList<String> listToPrint) {
        String result = "Here are the tasks in your list:";
        for (int i = 0; i < listToPrint.size(); i++) {
           result = result + (i + 1) + "." + listToPrint.get(i);
        }
        return result;
    }

    /**
     * Display deleted task message.
     */
    public void showDeletedTask(Task t, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(t.printTask());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public String getDeletedTask(Task t, int taskCount) {
        return "Noted. I've removed this task:" + t.printTask() + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Display task done message.
     */
    public void showDoneTask(Task t) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t.printTask());
    }

    public String getDoneTask(Task t) {
        return "Nice! I've marked this task as done:" + t.printTask();
    }

    /**
     * Display index error message.
     */
    public void showIndexError() {
        System.out.println("Invalid task number. Please check again.");
    }

    /**
     * Display input error message.
     */
    public void showInputError() {
        System.out.println("Error! Please check input again");
    }

    /**
     * Display command not found message.
     */
    public void showCommandNotFoundError() {
        System.out.println("Command not found. Please check command again.");
    }

    /**
     * Display list of tasks found with matching keyword.
     */
    public void showMatchingTaskList(ArrayList<String> listFound) {
        if (listFound.size() == 0) {
            System.out.println("No matching result from your list.");
            return;
        }

        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < listFound.size(); i++) {
            System.out.println((i + 1) + "." + listFound.get(i));
        }
    }

    public String getMatchingTaskList(ArrayList<String> listFound) {
        if (listFound.size() == 0) {
            return "No matching result from your list.";
        }

        String result;
        result = "Here are the matching tasks in your list:";
        for (int i = 0; i < listFound.size(); i++) {
            result = result + (i + 1) + "." + listFound.get(i);
        }
        return result;
    }

    public void showIncorrectNumberOfArgument() {
        System.out.println("Incorrect number of arguments in command.");
    }
}
