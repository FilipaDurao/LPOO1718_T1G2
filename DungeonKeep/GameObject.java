public class GameObject {
	
	private byte x_pos;
	private byte y_pos;
	
	public GameObject(byte x_pos, byte y_pos) {
		super();
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}

	public byte getX_pos() {
		return x_pos;
	}

	public void setX_pos(byte x_pos) {
		this.x_pos = x_pos;
	}

	public byte getY_pos() {
		return y_pos;
	}

	public void setY_pos(byte y_pos) {
		this.y_pos = y_pos;
	}
	
}
