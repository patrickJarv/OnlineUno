package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import GameTable.Player;

public class socketThread extends Thread {
	
	Server server;
	ServerSocket ss;
	Socket s;
	Boolean newPlayerJoined = false;
	
	public socketThread(Server s, ServerSocket servSoc)
	{
		this.server = s;
		this.ss = servSoc;
		
		this.start();
	}

	public void run() {
		
		while(true) {
			
		     try {
				  s = ss.accept();
				  newPlayerJoined = true;

				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
