package GameTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Deck {
  
  List<Card> cards;
  
  public Deck() { //fill deck with 25 red, 25 blue, 25 green, 25 yellow cards (normal), 8 wild
    
    cards = new ArrayList<Card>(); //make an arrayList of cards
    String[] colors = {"red","blue","green","yellow"}; //make the list of colors
    
    for(String c : colors)
    {
      for(int i = 0; i < 25; i++) //can adjust this number lower to accommodate more special cards
      {
        cards.add(new Normal_Card(c, i%9)); //create a normal card
      }
    }
    
    for(int i = 0; i < 10; i++) //add wildcards to the deck
    {
      cards.add(new Wild_Card("wildcard"));
    }
    
  }
  
  public void shuffle() { //ref: https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/
    Collections.shuffle(cards);

  }
  
  public Queue<Card> reShuffle(Queue<Card> q)
  {
	  Collections.shuffle((List<?>) q);
	  
	  return q;
  }
  
  public int size() { //return size of current deck
	  
	  return(cards.size());
  }
  
  public Card DealOut() { //deal out will return the front card of the deck and remove it
	  if(cards.size() > 0)
	  {
		  Card c = cards.get(0);
		  cards.remove(0);
		  
		  return c;
	  }
	  else return null;
  }

}
