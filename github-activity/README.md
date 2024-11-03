# GitHubActivity App

Sample solution for the [github-user-activity](https://roadmap.sh/projects/github-user-activity) challenge from [roadmap.sh](https://roadmap.sh/).

This is a command-line interface application to list the recent activity of a GitHub user(commits, stars, issues, etc).

## Requirements

- **Java:** Ensure you have [Java 8+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) installed.

## Installation

1. **Download source code or clone the repository**

    ```bash
   git clone "https://github.com/StevenLzn/backend-projects"
   cd backend-projects
   cd github-activity
   cd src
   ```
2. **Compile source code:**
   ```bash
   javac GithubActivityCLI.java GithubActivityManager.java
    ```
3. **Use a command:**
    ```bash
   java GithubActivityCLI <username>
