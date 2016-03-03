package ru.lunokhod.java.jCardSim_GUI;

import java.util.ArrayList;

import org.apache.bcel.classfile.JavaClass;

import javacard.framework.AID;

public class AppletDescriptor {
	private AID aid;
	private JavaClass javaClass;
	private ArrayList<String> superClassNames;
	private String filePath;
	private int size;
	
	public AppletDescriptor() { 
		javaClass = null;
		superClassNames = new ArrayList<String>();
		filePath = "";
		size = 0;
	}
	
	public AppletDescriptor(AID aid, JavaClass javaClass, String filePath, int size) {
		this.aid = aid;
		this.javaClass = javaClass;
		this.filePath = filePath;
		this.size = size;
	}
	
	public void setAid (AID aid) {
		this.aid = aid;
	}
	
	public void setJavaClass(JavaClass javaClass) {
		this.javaClass = javaClass;
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
	
	public JavaClass getJavaClass() {
		return javaClass;
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
	
	public String getClassName() {
		return javaClass.getClassName();
	}
}
	
