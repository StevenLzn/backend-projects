public class GithubActivityCLI {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("GithubActivityCLI: You must provide an username! e.g: 'GithubActivityCLI <username>'");
            return;
        }
        GithubActivityManager.getUserActivity(args[0]);
    }
}
