import java.util.List;

public class TaskPrinter {

    static public void printTaskList(List<Task> tasks, String emptyMessage) {
        printTableHeader();
        printRows(tasks, emptyMessage);
        System.out.printf("---------------------------------------------------------------------------------------------------------------------------------------------%n");
    }

    static private void printTableHeader() {
        System.out.printf("---------------------------------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-6s | %-50s | %-11s | %-29s | %-29s |%n", "id", "description", "status", "createdAt", "updatedAt");
        System.out.printf("---------------------------------------------------------------------------------------------------------------------------------------------%n");
    }

    static private void printRows(List<Task> tasks, String emptyMessage) {
        if (tasks.isEmpty()) {
            System.out.println(emptyMessage);
        } else {
            tasks.forEach(Task::printRow);
        }
    }
}
