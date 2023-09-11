package duke;
import duke.command.*;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    /**
     * Parses the input string into command which can be executed.
     * 
     * @param input String read by ui and to be parsed.
     * @return The corresponding command.
     * @throws DukeException If the input string has a wrong format.
     */
    public static Command parseCommand(String input) throws DukeException {
        String[] inputArr = input.split(" ", 2);
        String command = inputArr[0];
        switch (command) {

        case "list":
            return parseListCommand(inputArr);

        case "todo":
            return parseTodoCommand(inputArr);

        case "deadline":
            return parseDeadlineCommand(inputArr);

        case "event":
            return parseEventCommand(inputArr);

        case "mark":
            return parseMarkCommand(input, inputArr);

        case "unmark":
            return parseUnmarkCommand(input, inputArr);

        case "delete":
            return parseDeleteCommand(input, inputArr);

        case "find":
            return parseFindCommand(inputArr);

        case "exit":
            return new ExitCommand();

        case "":
            return new DoNothingCommand();

        default:
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static FindCommand parseFindCommand(String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a find cannot be empty.");
        }
        if (inputArr.length > 2) {
            throw new DukeException("You only allowed to type ONE keyword!");
        }
        return new FindCommand(inputArr[1]);
    }

    private static DeleteCommand parseDeleteCommand(String input, String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a delete cannot be empty.");
        }
        if (input.split(" ").length > 2) {
            throw new DukeException("Invalid delete command ?_? " +
                    "this command should follow by only ONE INTEGER");
        }
        int k;
        try {
            k = Integer.parseInt(inputArr[1]);
        } catch (NumberFormatException e) {
            throw new DukeException("Please type in INTEGER after this command ^v^");
        }
        return new DeleteCommand(k - 1);
    }

    private static UnmarkCommand parseUnmarkCommand(String input, String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a unmark cannot be empty.");
        }
        if (input.split(" ").length > 2) {
            throw new DukeException("Invalid unmark command ?_? " +
                    "this command should follow by only ONE INTEGER");
        }
        int index;
        try {
            index = Integer.parseInt(inputArr[1]);
        } catch (NumberFormatException e) {
            throw new DukeException("Please type in INTEGER after this command ^v^");
        }

        return new UnmarkCommand(index - 1);
    }

    private static MarkCommand parseMarkCommand(String input, String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a mark cannot be empty.");
        }
        if (input.split(" ").length > 2) {
            throw new DukeException("Invalid mark command ?_? " +
                    "this command should follow by only ONE INTEGER");
        }
        int index;
        try {
            index = Integer.parseInt(inputArr[1]);
        } catch (NumberFormatException e) {
            throw new DukeException("Please type in INTEGER after this command ^v^");
        }

        return new MarkCommand(index - 1);
    }

    private static EventCommand parseEventCommand(String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a event cannot be empty.");
        }
        String detail = inputArr[1];
        String[] detailArr = detail.split(" /from ", 2);
        if (detailArr.length <= 1) {
            throw new DukeException("Ouuuu, I think you miss some information, try again!");
        }
        String description = detailArr[0];
        String time = detailArr[1];
        String[] timeArr = time.split(" /to ", 2);
        if (timeArr.length <= 1) {
            throw new DukeException("Ouuuu, I think you miss some information, try again!");
        }
        String start = timeArr[0];
        String end = timeArr[1];
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        } catch (DateTimeParseException e) {
            throw new DukeException("There is something wrong with your date! format: yyyy-mm-dd");
        }

        return new EventCommand(description, startDate, endDate);
    }

    private static DeadlineCommand parseDeadlineCommand(String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        String details = inputArr[1];
        String[] detailsArr = details.split(" /by ", 2);
        if (detailsArr.length <= 1) {
            throw new DukeException("Do you forget to add a deadline for the task?");
        }
        String description = detailsArr[0];
        String by = detailsArr[1];
        LocalDate byDate;
        try {
            byDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new DukeException("There is something wrong with your date! format: yyyy-mm-dd");
        }
        return new DeadlineCommand(description, byDate);
    }

    private static TodoCommand parseTodoCommand(String[] inputArr) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        String des = inputArr[1];
        return new TodoCommand(des);
    }

    private static ListCommand parseListCommand(String[] inputArr) throws DukeException {
        if (inputArr.length > 1) {
            throw new DukeException("Invalid list command");
        }
        return new ListCommand();
    }

    /**
     * Parses the string with saving format into task.
     * @param input String with saving format.
     * @return The corresponding task.
     */
    public static Task parseSaveFile(String input) {
        String[] inputArr = input.split("/");
        Task temp;
        String des = inputArr[1];

        if (inputArr.length == 2) {
            temp = new Todo(des);
        } else if (inputArr.length == 3) {
            String by = inputArr[2];
            temp = new Deadline(des, LocalDate.parse(by));
        } else {
            String from = inputArr[2];
            String to = inputArr[3];
            temp = new Event(des, LocalDate.parse(from), LocalDate.parse(to));
        }
        if (inputArr[0].equals("1")) {
            temp.mark();
        }
        return temp;
    }
}
