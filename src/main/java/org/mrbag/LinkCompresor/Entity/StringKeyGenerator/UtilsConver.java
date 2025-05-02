package org.mrbag.LinkCompresor.Entity.StringKeyGenerator;

final class UtilsConver {

	private final static int SIZE_STR = 5;
	
	protected final static char[] conf = ("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890").toCharArray(); 
	
	static String IntToString(final int in) {
		String str = "";
		int buf = Math.abs(in); 
		for(int i = 1; i <= SIZE_STR; i++) {
			str += conf[buf % conf.length];
			buf >>= 4 * i;
		}
		
		return str;
	}
	
}
