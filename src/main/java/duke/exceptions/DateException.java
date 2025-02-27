package duke.exceptions;

/**
 * A throwable object to indicate that the String provided
 * is invalid for use with a Deadline or Event Object.
 */
public class DateException extends Exception {
    private String taskType;

    /**
     * Constructs the DateException Object
     *
     * @param type String containing the type of the Object
     */
    public DateException(String type) {
        this.taskType = type;
    }

    /**
     * @return a String representation of the error message
     */
    @Override
    public String toString() {
        if (taskType.equals("deadline")) {
            return "OOPS!!! You didn't specify date!\n"
                    + "use the format:\n"
                    + "'deadline your task here /by date'";
        } else if (taskType.equals("event")) {
            return "OOPS!!! You didn't specify date!\n"
                    + "use the format:\n"
                    + "'event your event here /at date'";
        }
        return "";
    }
}
