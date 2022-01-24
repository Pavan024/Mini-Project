package com.ecommerce.customerservice.validation;

/**
 * This is used when the existing email id used for new registration it will
 * throws an exception
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

	public EmailExistsException(final String message) {
		super(message);
	}

}
