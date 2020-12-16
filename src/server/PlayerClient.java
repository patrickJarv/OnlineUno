//package project.server;
package server;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

import GameTable.GameTableState;
import GameTable.Player;
//import project.GameTable.GameTableState;
//import project.GameTable.Player;
//import PlayerGUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

//PLAYER CLIENT CLASS
public class PlayerClient{
  
  Player p;
  private static Socket s;
  Boolean turn = false;
  private PlayerGUI gui;
  String cardResult;

  
  
  @SuppressWarnings({ "unchecked", "unused" })
  
  public static void main(String[] args)
  {
	  PlayerClient pc = new PlayerClient();
  }
  
  
public PlayerClient() {
	  Scanner sc = new Scanner(System.in);
	  ArrayList<String> hand; // color #
	  String username = null;
       while (true) {
              String ans = null;
              try {
                  System.out.print("Enter the server hostname: ");
                  String hostname = sc.nextLine();
                  System.out.print("Enter the server port number: ");
                  ans = sc.nextLine();
                  int port = Integer.parseInt(ans);
                  s = new Socket(hostname, port);
                  System.out.println();
                  break;
              } catch (NumberFormatException nfe) {
                  System.out.println("The given input " + ans + " is not a number.\n");
              } catch (UnknownHostException uhe) {
                  System.out.println("The given host is unknown.\n");
              } catch (IOException ioe) {
                  ioe.printStackTrace();
              }
          }

    
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            
            //get login credentials
            ClientLogin cl = new ClientLogin();
            username = cl.getLoginCred(oos, ois, sc); //pass in output and input stream with scanner
            
            //start game sequence
            while (true) {
             //signal game starts
             String s = (String) ois.readObject();
             if(s.equals("start"))
             {
            	System.out.println("Starting the game");
                //pass player object
            	 
                 //receive gamestateobj from server
     			GameTableState gs_init = (GameTableState) ois.readObject();

             
     			//wait for a card array/Player object p 
            	 hand = (ArrayList<String>) ois.readObject();
            	 
            	 ArrayList<String> temp = gs_init.GetUsernames();
            	 gui = new PlayerGUI(hand, username, gs_init.GetTopCard());
            	 int idx = 0;
        	   	 boolean start = false;
        	   	 String[] temp2 = new String[temp.size()-1];
        	   	 for(int i = 0; i < temp.size(); i++) {
        	   		 if (idx == temp2.length) {
        	   			 break;
        	   		 }
        	   		 if(username.equals(temp.get(i))) {
        	   			 start = true;
        	   		 }
        	   		 else {
        	   			 if(start)
        	   				 temp2[idx++] = temp.get(i);
        	   		 }
        	   		 if(i == temp.size() -1) {
        	   			 i = -1;
        	   		 }
        	   	 }
            	 gui.setUsername(temp2);
            	 break;
            	 
             }
            }
                   
            
          while(true){

            //read in strings from server side

			 String str = (String) ois.readObject();
			  if(str.equals("your turn"))
			  {
				    gui.setMove();	
				    String card = gui.getMove();
				    gui.yourTurn();
				    while(card.equals("None")){ 
				    	card = gui.getMove(); 
				    	TimeUnit.MILLISECONDS.sleep(40);
				    }
				    oos.writeObject(card); //signal end turn
				    oos.flush(); 
				  
				    //update yourself, display hand
				    hand = (ArrayList<String>) ois.readObject();
				    gui.setHand(hand);
				    gui.setMove();			
			  }
			  else if(str.equals("end")) {
				  String finalMessage = (String) ois.readObject();
				  gui.displayEnd(finalMessage);
				  break;
			  }

			//get game state object 
			GameTableState gs = (GameTableState) ois.readObject();

			//need the user who played it and the card played or if they drew
			gui.playMove(gs.GetLastMove());            
        }  
 
        } catch (SocketException se) {
            System.out.println(" Server dropped connection");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
  }
}
