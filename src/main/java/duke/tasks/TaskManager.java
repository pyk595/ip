package duke.tasks;

import duke.exceptions.InvalidOperationException;
import duke.ui.UiManager;
import java.util.ArrayList;

/**
 * TaskManager Object that handles storage of Task Objects and changes in state of
 * Task Objects.
 */
public class TaskManager {
    private UiManager uiManager;
    private ArrayList<Task> tasks;
    private String taskList;

    /**
     * Constructs the TaskManager Object.
     *
     * @param manager UiManager object that is used to handle user input and output
     */
    public TaskManager(UiManager manager) {
        this.uiManager = manager;
        this.tasks = new ArrayList<>();
    }

    /**
     * Appends Tasks Objects to the end of the ArrayList.
     * Upon appending, the task description and ArrayList size will be printed.
     *
     * @param task Task Object to be appended to the list
     */
    public void addTask(Task task) {
        tasks.add(task);
        uiManager.printAdd(task, this.size());
    }

    /**
     * Marks Task Objects at a given index as done.
     * Upon successfully marking the Task Object, the Task description would
     * be printed.
     *
     * @param num Integer used for referencing Task Object
     * @throws InvalidOperationException if Task Object is already marked as done.
     */
    public void mark(Integer num) throws InvalidOperationException {
        Task task = tasks.get(num);
        task.mark();
        uiManager.printMark(task);
    }

    /**
     * Marks Task Objects at a given index as not done.
     * Upon successfully marking the Task Object, the Task description would
     * be printed.
     *
     * @param num Integer used for referencing Task Object
     * @throws InvalidOperationException if Task Object is already unmarked.
     */
    public void unmark(Integer num) throws InvalidOperationException {
        Task task = tasks.get(num);
        task.unmark();
        uiManager.printUnmark(task);
    }

    /**
     * Removes the Task Object at the specified position in the Arraylist.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     * Upon deletion, the task description is printed.
     *
     * @param num Index of the element to be removed
     */
    public void delete(int num) {
        String taskDetails = tasks.get(num).toString();
        tasks.remove(num);
        uiManager.printDelete(taskDetails, tasks.size());
    }

    /**
     * Searches for the tasks containing a keyword.
     *
     * @param string String representing the keyword.
     * @return a string containing the task list
     */
    public String findTasks(String string) {
        String tasksFound = "";
        for (int i = 0; i < this.tasks.size(); i++) {
            Task currTask = tasks.get(i);
            if (currTask.toString().contains(string)) {
                tasksFound += "\n" + currTask.toString() ;
            }
        }
        return tasksFound;
    }


    /**
     * Returns the number of Task Objects in the ArrayList.
     *
     * @return the number of Task Objects in the ArrayList
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns ArrayList containing Task Objects.
     *
     * @return ArrayList containing Task Objects
     */
    public ArrayList<Task> getList() {
        return this.tasks;
    }

    /**
     * Loads a saved task list into the tasks ArrayList.
     *
     * @param taskList ArrayList containing saved Tasked Objects
     */
    public void loadList(ArrayList<Task> taskList) {
        this.tasks = taskList;
    }

    /**
     * @return a String representation of the list of Task Objects
     */
    @Override
    public String toString() {
        String string = uiManager.getLine() + "\n";
        string += "Here's your list, Good Sir:\n";
        for (int i = 0; i < tasks.size(); i++) {
            string += i + 1 + ". "+ tasks.get(i).toString() + "\n";
        }
        string += uiManager.getLine();
        this.taskList = string;
        return string;
    }
}
