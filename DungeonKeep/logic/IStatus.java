package DungeonKeep.logic;

public interface IStatus {
	
	public enum Status { RUNNING , DEFEAT , VICTORY , LEVELCHANGED }
	
	public Status getStatus();
	
}
