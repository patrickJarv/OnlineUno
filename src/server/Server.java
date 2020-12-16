package server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import GameTable.GameTable;
import GameTable.Player;
import GameTable.PlayerQueue;



public class Server {
  public static final int rooms = 3;
  public static final int roomsize = 4;
  public static final int maxPlayers = 20;
  //have port be 1024<=port<=49151 since they can be used by any app
  public static final int port = 1234;   
  	ArrayList<Player> playerList = new ArrayList<Player>(); //saves num players joined
	int startIdx = 0;
	int curIdx = 0;
	Boolean playerJoin = false;
	ArrayList<GameTable> GameTableList = new ArrayList<GameTable>(); //saves game tables
  
	  public Server()
	  {
		try {
			ServerSocket sSocket = new ServerSocket(port);
			//make a server manager thread
			socketThread socketManager = new socketThread(this, sSocket);
			System.out.println("Starting Server");
		
		    while(true)
		    {
		    	playerJoin = socketManager.newPlayerJoined;
//		      Socket s = sSocket.accept();
		      if(playerJoin == true) {
			      Player p = new Player(socketManager.s); //don't handle username up here
			      loginThread l = new loginThread(this,p); //will authenticate user
			      socketManager.newPlayerJoined = false;
		      }

		      if((curIdx-startIdx) >= roomsize){
		    	  
		    	System.out.println("Starting new room");
		        
		        ArrayList<Player> dupPlayerList = new ArrayList<Player>();
		        for(int i = startIdx; i < curIdx; i++)
		        {
		            dupPlayerList.add(playerList.get(i));
		            playerList.get(i).oos.writeObject("start");
		            playerList.get(i).oos.flush();
		        }

		        PlayerQueue pq = new PlayerQueue(dupPlayerList); //!!need deep copy? How to transfer players?
		        
		        GameTable gt = new GameTable(pq);
		        GameTableList.add(gt);
		        
		        //maybe?
		        gt.start();
		        
		        startIdx = curIdx;
		            
		    }
		  }
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	  
	public static void main (String[] args) throws IOException
	{
		new Server();
	}
}