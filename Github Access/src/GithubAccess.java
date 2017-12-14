import java.util.Scanner;
import java.io.*;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

//

public class GithubAccess {

	public static void main(String[] args) throws IOException {
		System.out.println("Would you like to display data via login (0) or use another users data (1)? (0/1)");
		Scanner input = new Scanner (System.in);
		int answer = input.nextInt();
		System.out.print("Enter a username:\n");
		String owner = input.next();

		if(answer == 0){
			System.out.println("Enter your password:");
			String password = input.next();
			User user = FindUser(owner, password);
			System.out.println("Information on authenticated user:"
								+ "\nLogin: "+ user.getLogin() + 
								"\nName: " + user.getName() + 
								"\nEmail: " + user.getEmail() + 
								"\nFollowers: "+ user.getFollowers() + 
								"\nFollowing: "+ user.getFollowing() + 
								"\nPublic Repos: "+ user.getPublicRepos());
		}
		else{

			int repos = noOfPublicRepos(owner);
			int followers = noOfFollowers(owner);
			int following = noFollowing(owner);
			System.out.print("Information on external user:" +
					"\nFollowers: " + followers + "\nFollowing: " + 
					following + "\nPublic Repos: " + repos);

			FileWriter csv = new FileWriter("src// visualise.csv", false);
			csv.append("Owner, Followers, Following, Repos\n");
			csv.append(owner+",");
			csv.append(String.valueOf(followers) +",");
			csv.append(String.valueOf(following)+",");
			csv.append(String.valueOf(repos));
			csv.flush();
			csv.close();
		}
		input.close();
	}

	/**
	 * Print a user's repositories
	 * @throws IOException 
	 */
	private static int noOfPublicRepos (String owner) throws IOException {
		//FileWriter csv = new FileWriter("src// visualise.csv", false);
		int count = 1;
		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories(owner)){
			System.out.println(count++ +") Name: " + repo.getName());
			//csv.append(repo.getName());
		}
		return --count;
	}


	private static int noOfFollowers (String username) throws IOException {
		FileWriter csv = new FileWriter("src// visualise.csv", false);

		int count =0;
		UserService uService = new UserService();
		for (User user : uService.getFollowers(username)){
			count++;
		}
		return count;
	}

	private static int noFollowing (String username) throws IOException {
		int count =0;
		UserService uService = new UserService();
		for (User user : uService.getFollowing(username)){
			count++;
		}
		return count;
	}

	private static User FindUser(String gitUser, String authenticate) throws IOException {
		GitHubClient client = new GitHubClient();
		client.setCredentials(gitUser, authenticate);
		UserService userService = new UserService(client);
		return userService.getUser();
	}
}
