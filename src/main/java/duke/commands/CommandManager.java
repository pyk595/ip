package duke.commands;

import duke.exceptions.InvalidOperationException;
import duke.tasks.TaskManager;
import duke.exceptions.DateException;
import duke.exceptions.DukeException;
import duke.exceptions.TaskIndexException;
import duke.ui.*;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * CommandManager Object that handles initialisation,
 * receiving and dispatching of commands.
 */
public class CommandManager {
    private final TaskManager taskManager;
    private final UiManager uiManager;
    private final ListStorage listStorage;
    private final ListLoader listLoader;
    private boolean isOpen;


    /**
     * Constructs the CommandManager Object.
     */
    public CommandManager() {
        this.uiManager = new UiManager();
        this.taskManager = new TaskManager(this.uiManager);
        this.isOpen = true;
        this.listStorage = new ListStorage(this.taskManager);
        this.listLoader = new ListLoader(this.taskManager);
    }

    /**
     * Sets the boolean isOpen to false.
     */
    public void setClose() {
        this.isOpen = false;
    }

    /**
     * Initialises and runs the program.
     * Scans String input from console and issues
     * commands to different objects via switching.
     * Checks for and handles errors.
     * Saves task list into a ser file upon exit.
     */
    public void run() {
        uiManager.welcome();
        try {
            this.listLoader.loadList();
            uiManager.printList(taskManager);
        } catch (IOException e) {
            uiManager.printLoadFail();
        } catch (ClassNotFoundException e) {
            uiManager.showErrorMessage("Oops! There was a corrupted task in the previous list!");
        }
        Scanner sc = new Scanner(System.in);
        String s = "";
        do {
            s = sc.nextLine();
            try {
               String[] command = uiManager.parseCommand(s);
                switch (command[0]) {
                case "bye":
                    this.setClose();
                    listStorage.saveList();
                    uiManager.exit();
                    break;
                case "list":
                    uiManager.printList(taskManager);
                    break;
                case "todo":
                    AddTaskCommand todo = new AddTaskCommand(this.uiManager, this.taskManager, command[1], Type.TODO);
                    todo.execute();
                    break;
                case "deadline":
                    AddTaskCommand deadline =
                            new AddTaskCommand(this.uiManager, this.taskManager, command[1], Type.DEADLINE);
                    deadline.execute();
                    break;
                case "event":
                    AddTaskCommand event = new AddTaskCommand(this.uiManager, this.taskManager, command[1], Type.EVENT);
                    event.execute();
                    break;
                case "mark":
                    NumCommand mark = new NumCommand(this.uiManager, this.taskManager, command[1], Type.MARK);
                    mark.execute();
                    break;
                case "unmark":
                    NumCommand unmark = new NumCommand(this.uiManager, this.taskManager, command[1], Type.UNMARK);
                    unmark.execute();
                    break;
                case "delete":
                    NumCommand delete = new NumCommand(this.uiManager, this.taskManager, command[1], Type.DELETE);
                    delete.execute();
                    break;
                case "save":
                    SaveCommand save = new SaveCommand(this.uiManager, this.taskManager);
                    save.execute();
                    break;
                case "find":
                    FindCommand find = new FindCommand(this.uiManager, this.taskManager, command[1]);
                    find.execute();
                    break;
                default:
                    throw new DukeException();
                }
            } catch (InvalidOperationException | DateException | TaskIndexException | DukeException e) {
                uiManager.showErrorMessage(e.toString());
            } catch (NumberFormatException e) {
                uiManager.showErrorMessage("Give me the task number in numbers please!");
            } catch (IndexOutOfBoundsException e) {
                uiManager.showErrorMessage("I don't think we have that task!\nUse 'list' to check");
            } catch (DateTimeParseException e) {
                uiManager.showErrorMessage("Invalid date! Please use the format 'YYYY-MM-DD'");
            } catch (IOException e) {
                uiManager.showErrorMessage("Oops! There was a corrupted task in the previous list!");
            }
        } while (this.isOpen);
        sc.close();
    }
}
