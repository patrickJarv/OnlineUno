package GameTable;

public class Wild_Card extends Card {
	String c;

	Wild_Card(String col) {
		super(col);
		
	}


	@Override
	public String stringout() {
		return (super.color);
	}


	@Override
	public String getColor() {
		return super.color;
	}
	
	public void setColor(String newCol) {
		super.color = newCol;
	}


	@Override
	public int getNumber() {
		return -1;
	}

}
