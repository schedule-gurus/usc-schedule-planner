package exceptions;

public class CollisionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	//error for schedule collisions
	public CollisionException(String errorMessage) {
		super(errorMessage);
	}
}
