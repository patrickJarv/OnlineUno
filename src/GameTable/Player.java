package GameTable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

//import Card;

public class Player extends Thread {
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String username;
	private Integer score;
	private PlayerQueue playerQueue;
	public Socket socket;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	private boolean isPlayerTurn;
	
	//socket to the corresponding client
	
	private GameTable table;
	
	public Player(Socket s) {
	  this.socket = s;
	  
	  try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  //ask for user info?
	}
	
	public void setGameTable(GameTable g)
	{
		table = g;
	}
  
	public String Play() { //TODO: Have this function return the "move" that the player took -> "Drew card" or "Played Yellow 4"
		//read in card name
		Card top = table.discardStk.top();
		Card cur = null;

		//communicate with its socket
		try {
			oos.writeObject("your turn"); //tell socket it's their turn
			oos.flush();
			
			//get card
			String cardInput = (String) ois.readObject();
			
			//just an if statement
			if(cardInput.equals("draw")) //player needs to draw a new card
			{
				if(table.drawStk.size() == 0) //if ran out of cards, reload drawStack
				{
					table.ReShuffle();
				}

			hand.add(table.drawStk.draw()); //add the top of the draw stack to hand
			}
			
			//including wildcard in this one
			else { //if not a wildcard or drawing a card, find regular card in hand
			//process card
			//this is assuming the card is a viable move
				String[] cardPlayedInfo = cardInput.split(" "); //assuming this is in format "blue 3"
				//handle wildcard here
				
				int cardNum = Integer.parseInt(cardPlayedInfo[1]);
				String newColor = cardPlayedInfo[0];
				if(cardNum == -1)
				{
					cardPlayedInfo[0] = "wildcard";
				}
					
				for(int i = 0; i < hand.size(); i++)
				{
					cur = hand.get(i);
					if(cur.getColor().equals(cardPlayedInfo[0]) && cur.getNumber() == cardNum)
					{
						//remove selected card from hand and put on table's discard stack
						table.discardStk.discard(cur); //add to discard stack
						if(cardNum == -1) {
							cur.setColor(newColor);
						}
						hand.remove(i);
						break;
					}
					
				}
			}
			
			//send updated hand back to client
			ArrayList<String> handInfo = new ArrayList<String>();
			for(Card c : hand)
			{
				handInfo.add(c.stringout());
			}
			
			oos.writeObject(handInfo);
			oos.flush();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(cur == null) {
			return (username + " played draw");
		} else {
			return (username + " played " + cur.stringout());
		}
		
		
	}
	
	public void updateClient(String s) //send a string update to client
	{
		try {
			oos.writeObject(s);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public String GetName() {
	  return username;
	}
	
	public void SetName(String s) {
		username = s;
	}
	
	public ArrayList<Card> GetHand() {
	  return hand;
	}
	
	boolean IsHandEmpty() {
		  return hand.isEmpty();
	}
	
	public boolean GetIsPlayerTurn() {
	  return isPlayerTurn;
	}
	
	public void SetIsPlayerTurn(boolean isPlayerTurn) {
	  this.isPlayerTurn = isPlayerTurn;
	}
	
  //   //playedcard
	
  //   @Override
  //   public void run() {
  //     try {
  //       playerQueue.GetSemaphore().acquire();
  //       play();
  //       playerQueue.NextTurn();
  //     }
  //     catch (InterruptedException e) {
  //       e.printStackTrace();
  //     }
  //     finally {
  //       playerQueue.GetSemaphore().release();
  //     }
  //   }
	
  }
