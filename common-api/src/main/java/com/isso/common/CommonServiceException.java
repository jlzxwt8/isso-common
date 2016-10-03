package com.isso.common;

import com.isso.common.BaseException;


public class CommonServiceException extends BaseException {

	private static final long serialVersionUID = 5757333134914661807L;

	public CommonServiceException() {
	}

	public CommonServiceException(String errorKey, String errorMessage) {
		super(errorKey, errorMessage);
	}

	public CommonServiceException(String errorKey, String errorMessage, Throwable rootCause) {
		super(errorKey, errorMessage, rootCause);
	}

	public CommonServiceException(Throwable rootCause) {
		super(rootCause);
	}

}
