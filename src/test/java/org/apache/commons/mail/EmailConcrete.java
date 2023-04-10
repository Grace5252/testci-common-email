//Grace Cappella, 3/20/23, CIS-376-001
//EmailConcrete given in tutorial video
package org.apache.commons.mail;

import java.util.Map;

public class EmailConcrete extends Email {
	
	@Override
	public Email setMsg(String arg) throws EmailException{
		return null;
	}
	
	public Map<String, String> getHeaders(){
		return this.headers;
	}
	
	public String getContentType() {
		return this.contentType;
	}
}
