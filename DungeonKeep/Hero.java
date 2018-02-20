
public class Hero extends GameObject {
	
	private final char idSymbol = 'H';

	public Hero(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}
	
	public void move_up() {
		setY_pos(getY_pos() + 1);
	}
	
	public void move_down() {
		setY_pos(getY_pos() - 1);
	}
	
	public void move_right() {
		setX_pos(getX_pos() + 1);
	}
	
	public void move_left() {
		setX_pos(getX_pos() - 1);
	}

}
