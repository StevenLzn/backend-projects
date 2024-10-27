public class TaskCLI {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        if (args.length == 0) {
            System.out.println("TaskCLI: You must provide an action! e.g: 'TaskCLI <action> <argument>'");
            return;
        }

        switch (args[0]) {
            case "add":
                if (args.length == 1) {
                    System.out.println("TaskCLI: You must provide a description! e.g: 'TaskCLI add <description>'");
                    return;
                }
                int taskId = taskManager.addTask(args[1]);
                System.out.println("TaskCLI: Task added successfully (ID: " + taskId + ")");
                break;
            case "list":
                if (args.length == 1) {
                    taskManager.printTasks();
                } else {
                    try {
                        String listArgument = args[1].toUpperCase().replace("-", "_");
                        TaskStatus status = TaskStatus.valueOf(listArgument);
                        taskManager.printTasks(status);
                    } catch (IllegalArgumentException e) {
                        System.out.println("TaskCLI: You must provide a valid status! e.g: 'TaskCLI list <todo | in-progress | done>'");
                    }
                }
                break;

            case "update":
                if (args.length < 2) {
                    System.out.println("TaskCLI: You must provide an id and a description! e.g: 'TaskCLI update <id> <description>' (make sure to include quotes around the description)");
                    return;
                } else if (args.length < 3) {
                    System.out.println("TaskCLI: You must provide a description! e.g: 'TaskCLI update <id> <description>' (make sure to include quotes around the description)");
                    return;
                }
                taskManager.updateTask(args[1], args[2]);
                break;
            case "delete":
                if (args.length == 1) {
                    System.out.println("TaskCLI: You must provide an id! e.g: 'TaskCLI delete <id>'");
                    return;
                }
                taskManager.deleteTask(args[1]);
                break;
            case "mark-in-progress":
                if (args.length == 1) {
                    System.out.println("TaskCLI: You must provide an id! e.g: 'TaskCLI mark-in-progress <id>'");
                    return;
                }
                taskManager.updateStateTask(args[1], TaskStatus.IN_PROGRESS);
                break;
            case "mark-done":
                if (args.length == 1) {
                    System.out.println("TaskCLI: You must provide an id! e.g: 'TaskCLI mark-done <id>'");
                    return;
                }
                taskManager.updateStateTask(args[1], TaskStatus.DONE);
                break;
            case "help":
                String helpText = "TaskCLI add <description> \t\t\t\t\tAdd a task to TODO List\n" +
                        "TaskCLI list <todo | in-progress | done> \tShow tasks list\n" +
                        "TaskCLI update <id> <description> \t\t\tUpdate a task\n" +
                        "TaskCLI delete <id> \t\t\t\t\t\tDelete a task\n" +
                        "TaskCLI mark-in-progress <id> \t\t\t\tMark a task as in progress\n" +
                        "TaskCLI mark-done <id> \t\t\t\t\t\tMark a task as done";
                System.out.println(helpText);
                break;
            default:
                System.out.println("TaskCLI: '" + args[0] + "' is not a TaskCLI command. See 'TaskCLI help'");
                break;
        }
    }
}
