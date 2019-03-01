package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Error: Bad config file format. Check that the file is accessible and that the file is formatted properly.");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
}
