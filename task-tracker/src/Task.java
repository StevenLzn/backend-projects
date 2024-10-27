import java.time.LocalDateTime;
import java.util.Arrays;

public class Task {
    private static int lastId = 0;
    private final int id;
    private String description;
    private TaskStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String description) {
        this.id = ++lastId;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(int id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(TaskStatus status){
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public StringBuilder toJson() {
        StringBuilder taskJsonBuilder = new StringBuilder();
        taskJsonBuilder.append("{")
                .append("\"id\": \"")
                .append(id)
                .append("\", \"description\": \"")
                .append(description)
                .append("\", \"status\": \"")
                .append(status.name())
                .append("\", \"createdAt\": \"")
                .append(createdAt.toString())
                .append("\", \"updatedAt\": \"")
                .append(updatedAt.toString())
                .append("\"}");
        return taskJsonBuilder;
    }

    public static Task jsonToTaskObject(String taskJson) {
        String[] taskJsonProperties = taskJson.replace("{", "")
                .replace("}", "")
                .split(",");

        int id = 0;
        String description = null;
        TaskStatus status = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String taskKeyValueString : taskJsonProperties) {
            String[] propertyKeyValue = taskKeyValueString.split("\":");
            String key = propertyKeyValue[0].replace("\"", "").trim();
            String value = propertyKeyValue[1].replace("\"", "").trim();

            switch (key) {
                case "id" -> {
                    id = Integer.parseInt(value);
                    if (id > lastId) {
                        lastId = id;
                    }
                }
                case "description" -> description = value;
                case "status" -> status = TaskStatus.valueOf(value);
                case "createdAt" -> createdAt = LocalDateTime.parse(value);
                case "updatedAt" -> updatedAt = LocalDateTime.parse(value);
            }
        }

        return new Task(id, description, status, createdAt, updatedAt);
    }

    public void printRow() {
        System.out.printf("| %-6s | %-50s | %-11s | %-29s | %-29s |%n", id, description, status, createdAt, updatedAt);
    }
}
