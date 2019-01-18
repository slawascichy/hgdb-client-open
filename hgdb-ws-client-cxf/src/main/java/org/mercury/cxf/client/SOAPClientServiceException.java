package org.mercury.cxf.client;

public class SOAPClientServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6525986761120260906L;

	public SOAPClientServiceException() {
		super();
	}

	public SOAPClientServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SOAPClientServiceException(String message) {
		super(message);
	}

	public SOAPClientServiceException(Throwable cause) {
		super(cause);
	}

}
