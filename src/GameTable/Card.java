package GameTable;
/**
 * 
 */

/**
 * @author Lydia
 *
 */
public abstract class Card {
		String color;
		
		Card(String col)
		{
			color = col;
		}
		
		public abstract String getColor();
		public abstract int getNumber();
		public abstract String stringout();

		protected abstract void setColor(String newColor);

}
