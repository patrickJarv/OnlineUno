package GameTable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameTableState implements Serializable {
	  private String lastMove;
	  private HashMap<String, Integer> playersHandSize; //key: username, value: hand size
	  private String topCard;
	  private ArrayList<String> usernames;
	  private boolean isGameComplete;
	  
	  public GameTableState() {
		  isGameComplete = false;
	  }
	  
	  public boolean GetIsGameComplete() {
		  return isGameComplete;
	  }
	  
	  public void SetIsGameComplete(boolean isGameComplete) {
		  this.isGameComplete = isGameComplete;
	  }
	  
	  public ArrayList<String> GetUsernames() {
		  return usernames;
	  }
	  
	  public void SetUsernames(ArrayList<String> usernames) {
		  this.usernames = usernames;
	  }
	  
	  public String GetLastMove() {
	    return lastMove;
	  }
	  
	  public HashMap<String, Integer> GetPlayersHandSize() {
	    return playersHandSize; 
	  }
	  
	  public String GetTopCard() {
	    return topCard;
	  }
	  
	  public void SetLastMove(String lastMove) {
	    this.lastMove = lastMove;
	  }
	  
	  public void SetPlayersHandSize(HashMap<String, Integer> playersHandSize) {
	    this.playersHandSize = playersHandSize;
	  }
	  
	  public void SetTopCard(String topCard) {
	    this.topCard = topCard;
	  }
}
	       