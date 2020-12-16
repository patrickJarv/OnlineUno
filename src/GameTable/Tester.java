package GameTable;

import java.util.ArrayList;

public class Tester {
	
	public static void main(String [] args) 
	{
		//DECK TEST
		Deck d = new Deck();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " ");
		}
		System.out.println();
		d.shuffle();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " | ");
		}
		
		
		//PLAYER TEST
		System.out.println();
		ArrayList<Player> playerArr = new ArrayList<>();
		String[] userNms = {"me1", "another1", "3rdGuy", "lastPlayer"};
		for(int i = 0; i < userNms.length; i++)
		{
			Player p = new Player(null);
			p.SetName(userNms[i]);
			playerArr.add(p);
		}
		PlayerQueue pq = new PlayerQueue(playerArr);
		
		System.out.println("top player: "+pq.Top().getId());
		pq.RemovePlayer(pq.Top());
		
		System.out.println("new top player: "+pq.Top().getId());
		
		pq.NextTurn();
		System.out.println("next turn player: "+pq.Top().getId());
		
		for(int i = 0; i < 3; i++)
		{
			pq.NextTurn();
			System.out.println(pq.Top().getId() + " , then ");
		}
		
		
		//GAMETABLE TEST
		try {
			System.out.println("PLAYER HANDS");
			GameTable gt = new GameTable(pq);
			
			System.out.println(gt.drawStk.top().stringout());
			
			PlayerQueue pl = gt.getPlayers();
			for(Player p : pl.q)
			{
				System.out.print("PLAYER "+p.getId()+ " : ");
				for(int i = 0; i<p.GetHand().size(); i++)
				{
					System.out.print(p.GetHand().get(i).stringout() + " | ");
				}
				System.out.println();
			}
		} catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}

}
