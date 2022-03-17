package org.sid.security;

public interface SecurityParams {
	
	public static final String HEADER_NAME = "Authorization";
	public static final String SECRET = "azerty";
	public static final long EXPIRATION = 10*24*3600*1000; //en ms
	public static final String HEADER_PREFIX = "Bearer ";

}
