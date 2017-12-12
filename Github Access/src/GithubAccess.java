import java.util.Scanner;
import java.io.*;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.CommitService;
//import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

public class GithubAccess {

	public static void main(String[] args) throws IOException {
		System.out.print("Enter a username:\n");
		Scanner input = new Scanner (System.in);
		String owner = input.next();
		input.close();

		int repos = noOfPublicRepos(owner);
		int followers = noOfFollowers(owner);
		int following = noFollowing(owner);
		System.out.print("Followers: " + followers + "\nFollowing: " + following + "\nPublic Repos: " + repos);
		
		FileWriter csv = new FileWriter("src// visualise.csv", false);
		csv.append("Owner, Followers, Following, No of Repos\n");
		csv.append(owner+",");
		csv.append(String.valueOf(followers) +",");
		csv.append(String.valueOf(following)+",");
		csv.append(String.valueOf(repos));
		csv.flush();
		csv.close();
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
}
