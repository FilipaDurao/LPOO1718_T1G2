package DungeonKeep.logic;

public interface IStatus {
	
	public enum Status { RUNNING , DEFEAT , VICTORY , LEVELCHANGED }	/**< The possible Statuses that an object implementing this Interface can have */
	
	/**
	 * Returns the object's current status
	 * 
	 * @return the Status of the Object
	 */
	public Status getStatus();
	
}
