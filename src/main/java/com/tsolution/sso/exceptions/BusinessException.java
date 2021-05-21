package com.tsolution.sso.exceptions;

public class BusinessException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 5071607540508825515L;

	public static final String COMMON_INPUT_INFO_INVALID = "common.input.info.invalid";

	public BusinessException(String message) {
		super(message);
	}
}
