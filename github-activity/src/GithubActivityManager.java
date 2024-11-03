import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GithubActivityManager {
    public static void getUserActivity(String userName) {
        try {
            String GITHUB_ENDPOINT = "https://api.github.com/users/" + userName + "/events";
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GITHUB_ENDPOINT))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                System.out.println("GithubActivityCLI: User '" + userName + "' not found. You must provide a valid username.");
                return;
            }

            if (response.statusCode() == 200) {
                showUserActivity(response.body());
            }

        } catch (JsonProcessingException e) {
            System.out.println("Error processing json response");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void showUserActivity(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode githubActivity = objectMapper.readTree(jsonString);

        if (githubActivity.size() == 0) {
            System.out.println("GithubActivityCLI: No recent activity found");
            return;
        }

        List<String> pushEvents = new ArrayList<>();
        List<String> watchEvents = new ArrayList<>();
        List<String> createEvents = new ArrayList<>();
        List<String> issuesEvents = new ArrayList<>();
        List<String> forkEvents = new ArrayList<>();
        List<String> otherEvents = new ArrayList<>();

        for (JsonNode event : githubActivity) {
            String eventType = event.get("type").asText();
            String repoName = event.get("repo").get("name").asText();

            switch (eventType) {
                case "PushEvent" -> {
                    int commitEventSize = event.get("payload").get("commits").size();
                    pushEvents.add("Pushed " + commitEventSize + " commits to " + repoName);
                }
                case "WatchEvent" -> watchEvents.add("Starred " + repoName);
                case "CreateEvent" -> {
                    String createType = event.get("payload").get("ref_type").asText();
                    String logMessage = "Created " + createType;
                    if (createType.equals("repository")) {
                        createEvents.add(logMessage + " " + repoName);
                    } else {
                        createEvents.add(logMessage + " in " + repoName);
                    }
                }
                case "IssuesEvent" -> {
                    char firstWord = event.get("payload").get("action").asText().toUpperCase().charAt(0);
                    String action = firstWord + event.get("payload").get("action").asText().substring(1);
                    issuesEvents.add(action + " an issue in " + repoName);
                }
                case "ForkEvent" -> forkEvents.add("Forked " + repoName);
                default -> otherEvents.add(eventType + " in " + repoName);
            }
        }
        printEvents(pushEvents, "Commits");
        printEvents(watchEvents, "Stars");
        printEvents(createEvents, "Create");
        printEvents(issuesEvents, "Issues");
        printEvents(forkEvents, "Forks");
        printEvents(otherEvents, "Others");
    }

    private static void printEvents(List<String> listEvents, String title) {
        System.out.println("--------------------------- " + title + " ---------------------------");
        if (listEvents.size() == 0) {
            System.out.println("There are no records to display");
        } else {
            listEvents.forEach(System.out::println);
        }
        System.out.println("---------------------------------------------------------------");
    }
}
