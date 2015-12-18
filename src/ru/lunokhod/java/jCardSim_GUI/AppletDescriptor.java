package ru.lunokhod.java.jCardSim_GUI;

import java.util.ArrayList;
import javacard.framework.AID;

public class AppletDescriptor {
	private AID aid;
	private String className;
	private ArrayList<String> superClassNames;
	private String filePath;
	private int size;
	
	public AppletDescriptor() { 
		className = "";
		superClassNames = new ArrayList<String>();
		filePath = "";
		size = 0;
	}
	
	public AppletDescriptor(AID aid, String className, String filePath, int size) {
		this.aid = aid;
		this.className = className;
		this.filePath = filePath;
		this.size = size;
	}
	
	public void setAid (AID aid) {
		this.aid = aid;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	public void setSuperClassNames(ArrayList<String> superClassNames) {
		this.superClassNames = superClassNames;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public AID getAid () {
		return aid;
	}
	
	public String getClassName() {
		return className;
	}

	public ArrayList<String> getSuperClassNames() {
		return superClassNames;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public int getSize() {
		return size;
	}
}
	
