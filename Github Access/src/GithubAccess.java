import java.util.Scanner;
import java.io.*;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

public class GithubAccess {

	public static void main(String[] args) throws IOException {
		System.out.print("Enter your username:\n");
		Scanner input = new Scanner (System.in);
		String owner = input.next();
		System.out.print("Enter your password:\n");
		String password = input.next();
		input.close();
		
		User user = FindUser(owner, password);
		System.out.println("Information on this user:" +
							"\nLogin: " + user.getLogin() + 
							"\nName: " + user.getEmail() + 
							"\nFollowers: " + user.getFollowers() + 
							"\nFollowing: "+ user.getFollowing() + 
							"\nPublic Repos: " + user.getPublicRepos() + 
							"\nCollaborators: " + user.getCollaborators());
		
		printRepos(owner);
		//String JsonData = toJson(user);
	}

	/**
	 * Print a user's repositories
	 * @throws IOException 
	 */
	private static void printRepos (String owner) throws IOException {
		int count = 1;
		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories(owner)){
			System.out.println(count++ +") Name: " + repo.getName() + " created on, " + repo.getCreatedAt());
		}
	}
	
	private static User FindUser(String username, String password) throws IOException {
		GitHubClient client = new GitHubClient();
		client.setCredentials(username, password);
		UserService userService = new UserService(client);
		return userService.getUser();
	}
}
