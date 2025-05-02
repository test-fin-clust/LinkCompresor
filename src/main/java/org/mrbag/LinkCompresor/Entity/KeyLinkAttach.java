package org.mrbag.LinkCompresor.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyLinkAttach {

	String key; 
	
	Link link;
	
	public boolean isValid() {
		if(link != null && key !=null)
			return true;
		return false;
	}
	
}
