package com.pickpick.server.apiPayload.exception;

public abstract class BaseException extends RuntimeException{
	public abstract BaseExceptionType getExceptionType();
}
