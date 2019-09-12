package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

import duke.parser.IncorrectFileFormatException;
import duke.parser.Parser;
import duke.task.Task;
import duke.ui.Ui;

/**
 * Represents a storage function of duke.
 * Has loading from file and saving to hard disk function.
 */
public class Storage {
    private String targetFilePath;
    private String printFilePath;

    public Storage(String filePath) {
        // Convert string filePath to actual filepath and store in FilePath class
        targetFilePath = filePath;

        printFilePath = "C:\\Users\\user\\Desktop\\CS2103_Git\\duke\\data\\print.txt";
    }

    /**
     * Returns task list of tasks from file,
     * convert input from file to task objects.
     *
     * @return task list from file.
     * @throws IncorrectFileFormatException If file format is incorrect.
     * @throws FileNotFoundException        if file is not found.
     */
    public ArrayList<Task> load(Ui ui) throws IncorrectFileFormatException, FileNotFoundException {
        File f;
        f = new File(targetFilePath);

        Scanner s = new Scanner(f, "Unicode");
        ArrayList<String> listInput = new ArrayList<>();

        while (s.hasNextLine()) {
            String i = s.nextLine();
            System.out.println(i);
            listInput.add(i);
        }

        ArrayList<Task> listTask = new ArrayList<>();
        for (String value : listInput) {
            try {
                listTask.add(Parser.parseFromFile(value, ui));

            } catch (IncorrectFileFormatException f1) {
                throw new IncorrectFileFormatException(ui.getLoadingError());

            } catch (NullPointerException n) {
                throw new NullPointerException();
            }
        }
        return listTask;
    }

    /**
     * Obtain list of tasks to print, save to hard disk.
     *
     * @param l List containing all string format tasks to save.
     */
    public void save(ArrayList<String> l) throws IOException {
        try {
            FileWriter fw = new FileWriter(printFilePath);
            for (String s : l) {
                fw.write(s + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw e;
        }
    }
}

