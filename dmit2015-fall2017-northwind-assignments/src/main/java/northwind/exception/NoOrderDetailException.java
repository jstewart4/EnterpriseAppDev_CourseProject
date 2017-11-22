package northwind.exception;

@SuppressWarnings("serial")
public class NoOrderDetailException extends Exception{
	
	public NoOrderDetailException(String message) {
		super(message);
	}

}
