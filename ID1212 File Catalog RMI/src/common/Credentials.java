package common;

import java.io.Serializable;

public class Credentials implements Serializable{
	
	private String username;
	private String password;
	
    public Credentials(String username, String password) {
        
    	this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    
	public String toString() {
		return "username: " + username + "\npassword: " + password;
	}
}
