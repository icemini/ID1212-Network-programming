package server.model;

import java.io.File;
import java.io.Serializable;

//holds a file object with attributes
public class FileObject implements Serializable{
	
	private String name;
	private int size;
	private String owner;
	private String access;
	private String permission;
	private String notification;
	
	public FileObject(String name, int size, String owner, String access, String permission, String notification) {
		
		this.name = name;
		this.size = size;
		this.owner = owner;
		this.access = access;
		this.permission = permission;
		this.notification = notification;
	}
	
	public String getName() {
		return this.name;
	}
	public int getSize() {
		return this.size;
	}
	public String getOwner() {
		return this.owner;
	}
	public String getAccess() {
		return this.access;
	}
	public String getPermission() {
		return this.permission;
	}
	public String getNotification() {
		return this.notification;
	}
	
	public String toString() {
		return "File name: " + name + "\nFile size: " + size + "\nFile owner: " + owner + "\nFile access: " + access + "\nFile permission: " + permission + "\nFile notification: " + notification;
	}

}
