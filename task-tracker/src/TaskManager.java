import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskManager {
    private final String PATH = "tasks.json";
    private final List<Task> tasks = new ArrayList<>();

    public TaskManager() {
        loadTasks();
    }

    private void loadTasks() {
        Path jsonPath = Paths.get(PATH);
        try {
            if(!Files.exists(jsonPath)){
                return;
            }

            String jsonData = Files.readString(jsonPath);

            if (jsonData.length() == 0) {
                return;
            }

            String tasksString = jsonData.substring(2, jsonData.length() - 2);
            String[] tasksListString = tasksString.split("},\\n\\{");

            Arrays.stream(tasksListString).forEach(taskJson -> {
                if (!taskJson.startsWith("{")) {
                    taskJson = "{" + taskJson;
                }

                if (!taskJson.endsWith("}")) {
                    taskJson += "}";
                }
                tasks.add(Task.jsonToTaskObject(taskJson));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        saveTasks();
        return task.getId();
    }

    private void saveTasks() {
        Path jsonPath = Paths.get(PATH);
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            jsonBuilder.append(task.toJson());

            if (i < tasks.size() - 1) {
                jsonBuilder.append(",\n");
            }
        }

        jsonBuilder.append("\n]");

        try {
            Files.writeString(jsonPath, jsonBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(String idArg, String description) {
        try {
            Task task = findTaskById(idArg);
            task.setDescription(description);
            saveTasks();
            System.out.println("TaskCLI: Task updated successfully (ID: " + idArg + ")");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateStateTask(String idArg, TaskStatus newStatus) throws IllegalArgumentException {
        try {
            Task task = findTaskById(idArg);
            task.setStatus(newStatus);
            saveTasks();
            System.out.println("TaskCLI: Task marked as " + newStatus + " successfully (ID: " + idArg + ")");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteTask(String idArg) throws IllegalArgumentException {
        try {
            Task task = findTaskById(idArg);
            tasks.remove(task);
            saveTasks();
            System.out.println("TaskCLI: Task deleted successfully (ID: " + idArg + ")");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void printTasks() {
        TaskPrinter.printTaskList(tasks, "TaskCLI: There are no tasks. Use 'TaskCLI add <description>' to add a task");
    }

    public void printTasks(TaskStatus status) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();

        String emptyMessage = "TaskCLI: There are no tasks with " + status.toString() + " status";
        TaskPrinter.printTaskList(filteredTasks, emptyMessage);
    }

    private Task findTaskById(String idArg) throws IllegalArgumentException {
        try {
            int id = Integer.parseInt(idArg);
            return tasks.stream()
                    .filter(task -> task.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("TaskCLI: Task with id " + id + " not found"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("TaskCLI: Invalid id format: " + idArg);
        }
    }
}
