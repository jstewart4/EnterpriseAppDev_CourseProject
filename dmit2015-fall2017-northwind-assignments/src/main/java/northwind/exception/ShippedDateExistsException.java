package northwind.exception;

@SuppressWarnings("serial")
public class ShippedDateExistsException extends Exception {
	
	public ShippedDateExistsException(String message) {
		super(message);
	}

}
