package exceptions;

import java.io.IOException;

public class NotFoundException extends IOException{
private static final long serialVersionUID = 1L;
	
	//error for schedule collisions
	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
