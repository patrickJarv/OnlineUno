package server;
import java.io.IOException;
import java.util.ArrayList;

import GameTable.Player;

public class loginThread extends Thread {
	
	Server server;
	Player player;
	Boolean loggedIn = false;
	//login gui? Login page?
	
	public loginThread(Server s, Player p)
	{
		this.server = s;
		this.player = p;
		
		this.start();
		
	}
	
	public void run() {
		
		try { //isguest bool in player
			
			ArrayList<String> currPlayers = new ArrayList<String>();
			
			for(int i = 0; i < server.playerList.size(); ++i)
			{
				currPlayers.add(server.playerList.get(i).GetName());
			}
			
			player.oos.writeObject(currPlayers);
			player.oos.flush();
			
			String username = (String) player.ois.readObject();
            player.SetName(username);
            player.oos.flush();
            
			//after authenticated, add to server's player list and update num players on server
			server.playerList.add(player);
		    System.out.println("New player added");
		    player.oos.writeObject(server.playerList.size());
		    player.oos.flush();
		    server.curIdx++;
		    System.out.println("current server size: " + server.playerList.size());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
