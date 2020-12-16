package GameTable;
public class Normal_Card extends Card{
	
		int number;

	Normal_Card(String col, int num) {
		super(col);
		number = num;
	}



	@Override
	public String stringout() {
		String s = "";
		s = s.concat(super.color + " " + Integer.toString(number));
		
		return s;
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
		return number;
	}

}
