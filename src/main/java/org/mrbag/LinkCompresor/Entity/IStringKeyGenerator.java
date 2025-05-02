package org.mrbag.LinkCompresor.Entity;

public interface IStringKeyGenerator {

	public String next();
	
	public String get();
	
	public boolean isValid();
	
}
