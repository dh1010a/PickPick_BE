package com.pickpick.server.global.file.exception;

import com.pickpick.server.apiPayload.exception.BaseException;
import com.pickpick.server.apiPayload.exception.BaseExceptionType;

public class FileException extends BaseException {
	private BaseExceptionType exceptionType;


	public FileException(BaseExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return exceptionType;
	}
}
