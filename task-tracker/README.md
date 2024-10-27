# TaskCLI App

Sample solution for the [task-tracker](https://roadmap.sh/projects/task-tracker) challenge from [roadmap.sh](https://roadmap.sh/).

This is a command-line interface application to track and manage your tasks. You can add, update, list and change task
statuses using the terminal. Tasks are saved in a JSON file, which helps with data persisting.

## Features

- **Add a task:** Add a task to TODO list.
- **List tasks:** Show all task or filter by task status.
- **Update a task:** Update a description of a task.
- **Delete a task:** Delete a task from list.
- **Update task status:** Update a task status as "in-progress" or "done".
- **Persist data:** Tasks are saved in a local JSON file.

## Requirements

- **Java:** Ensure you have [Java 8+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) installed.

## Installation

1. **Download source code or clone the repository**

    ```bash
   git clone "https://github.com/StevenLzn/backend-projects"
   cd backend-projects
   cd task-tracker
   cd src
   ```
2. **Compile source code:**
   ```bash
   javac Task.java TaskCLI.java TaskManager.java TaskPrinter.java TaskStatus.java
    ```
3. **Use a command:**
    ```bash
   java TaskCLI <action> <argument>
   ```

## Usage
```bash
# Adding a new task
java TaskCLI add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Updating and deleting tasks
java TaskCLI update 1 "Buy groceries and cook dinner"
java TaskCLI delete 1

# Marking a task as in progress or done
java TaskCLI mark-in-progress 1
java TaskCLI mark-done 1

# Listing all tasks
java TaskCLI list

# Listing tasks by status
java TaskCLI list done
java TaskCLI list todo
java TaskCLI list in-progress
```
    