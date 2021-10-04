package com.jdbc;

public class IdInvalidException extends RuntimeException {
	public String getMessage()
	{
		return "INVALID ID";
	}
}
