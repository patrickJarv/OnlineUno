package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import Database.db_connect;

public class ClientLogin {
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Scanner sc;
	
	public boolean checkUser(ArrayList<String> currPlayers, String user)
	{
		//checks to see if a user is already in the game
		for(int i = 0; i < currPlayers.size(); ++i)
		{
			if(currPlayers.get(i).equals(user))
			{
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("finally")
	public String getLoginCred(ObjectOutputStream outputStr, ObjectInputStream inputStr, Scanner scan)
	{
		oos = outputStr;
		ois = inputStr;
		sc = scan;
		String username = null;
		
		try {
			
			ArrayList<String> currPlayers = (ArrayList<String>) ois.readObject();
			while(true)
			{
				//menu opens, user chooses an option
				System.out.println("Please enter one of the following options:");
				System.out.println("1 if you want to play as a guest");
				System.out.println("2 if you want to login");
				System.out.println("3 if you want to create a new user");
				String response = sc.nextLine();
				
				if(response.equals("1"))
				{
					// generate guest username
					//generates new numbers until we find one that hasn't been used yet
					while(true)
					{
						username = "Player"+(int)(Math.random() * (5000) +1);
						if(checkUser(currPlayers, username)) break;
					}
					System.out.println("Your guest username is: " + username);
					break;
				}
				else if(response.equals("2")||response.equals("3"))
				{
					boolean isAuthenticated = false;
					String passResponse;
					
					//while loop allows user to try again, goes until authentication or user chooses to return to menu
					while(true)
					{
						//gets username and pass
						System.out.print("Username: ");
		            	username = sc.nextLine();
		            	System.out.print("Password: ");
		            	passResponse = sc.nextLine();
		            	boolean success = true;
		            	
		            	//checks to see if user is already in game
		            	if(!checkUser(currPlayers, username))
		            	{
		            		System.out.println("User already playing.\n");
		            		success = false;
		            	}
		            	
		            	//checks to see if either username or password is invalid format
		            	if(!db_connect.checkAlphaNum(username) || !db_connect.checkAlphaNum(passResponse)) 
		            	{
		            		System.out.println("Invalid username or password contains non-alphanumeric characters.\n");
		            		success = false;
		            	}
		            	
		            	//if no issues yet, program continues
		            	//2 for log on, 3 for new user
		            	if(success && response.equals("2"))
		            	{
		            		//checks for existing user and correct password
			            	try {
			            		boolean exists = db_connect.userExists(username);
			            		if(exists)
			            		{
			            			boolean correct = db_connect.validateLogin(username, passResponse);
			            			
			            			if (correct) {
			            				System.out.println("Succesfully logged in.\n");
			            				isAuthenticated = true;
			            				break;
			            			}
			            			else {
			            				System.out.println("The password is incorrect.\n");
			            				success = false;
			            			}
			            			
			            		}
			            		else
			            		{
			            			System.out.println("This user does not exist.\n");
			            			success = false;
			            		}
			            	} catch (Exception ex) {
			            		System.out.println("Error: can not connect to the database");
			            	}
		            	}
		            	else if(success && response.equals("3"))
		            	{
		            		//checks to see if username is already taken
		            		try {
			            		boolean exists = db_connect.userExists(username);
			            		if(!exists)
			            		{
			            			db_connect.addUser(username, passResponse);
			            			isAuthenticated = true;
			            			break;
			            		}
			            		else
			            		{
			            			System.out.println("This user already exists.\n");
			            			success = false;
			            		}
			            	} catch (Exception ex) {
			            		System.out.println("Error: can not connect to the database");
			            	}
		            	}
		            	
		            	//if there was an error at any point, success will be false and error menu will print
		            	if(!success){
		            		System.out.println("There was an issue logging in:");
		            		System.out.println("1 to try again");
		            		System.out.println("2 to return to the main menu");
		            		String choice = sc.nextLine();
		            		if(choice.equals("2"))
		            		{
		            			System.out.println("Returning to menu...");
		            			break;
		            		}
		            	}
		            	else
		            	{
		            		isAuthenticated = true;
		            		break;
		            	}
					}
					
					//break if successfully logged on
					if(isAuthenticated)
					{
						break;
					}
				}
				
				else
				{
					System.out.println(response + " is not a valid option\n");
				}
			}
			
			String username2 = username;
            double x = 1;
            try {
                if(db_connect.userExists(username))
                {
                    double wins = db_connect.getWins(username);
                    double losses = db_connect.getLosses(username);
                    if(losses!=0) x = wins / losses;
                    else x = wins;
                    x = ((int)(x*100))/100.0;
                    username = username + "(" + x + "W/L)";
                }

            }
            catch(Exception e) { }
            
            // send username back to loginThread
            oos.writeObject(username);
            oos.flush();

            // output current number of players
            Integer num = (Integer) ois.readObject();

            System.out.println("Welcome " + username2 + " to Online Uno! There are "+num+" players on the server");

		} 
		
		finally {
			return username;
		}
	}
}
