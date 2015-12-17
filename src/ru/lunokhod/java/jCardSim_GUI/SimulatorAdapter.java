package ru.lunokhod.java.jCardSim_GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.licel.jcardsim.base.Simulator;
import javacard.framework.AID;
import javacard.framework.Applet;
import javacard.framework.SystemException;
import com.licel.jcardsim.utils.AIDUtil;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.ClassFormatException;

public class SimulatorAdapter {
	private Simulator simulator;
	SCAppClassLoader classLoader = new SCAppClassLoader();
	
	public SimulatorAdapter() {
		simulator = new Simulator();
	}

	public boolean installApplet(String aid, File classFile) {
		AID appAid = AIDUtil.create(aid);
		Class<?> appletClass = null;
		
		try {
			byte[] bytes = getByteCode(classFile);
			String name = getFileClassName(classFile);
			
			System.out.println("ClassName parsed : " + name);
			appletClass = classLoader.loadClass(bytes, name);
		}
		catch (Exception e) {
			System.out.println("SimulatorAdapter.installApplet ClassLoader.loadClass Exception: " + e.getMessage());
		}
		
		if (appletClass != null) {
			try
			{
				simulator.loadApplet(appAid, appletClass);
				System.out.println("appletClass loaded into Simulator");
			}
			catch (SystemException e) {
				System.out.println("SimulatorAdapter.installApplet loadApplet SystemException: " + e.getMessage());
				return false;
			}
			return true;
		}
		else {
			System.out.println("appletClass == null");
			return false;
		}
	}
	
	public void installApplet(byte[] aid, File classFile) {
		installApplet(AIDUtil.create(aid).toString(), classFile);
	}
	
	private String getFileClassName(File appFile) {
		ClassParser parser;
		String name = "";
		JavaClass jclass;
		
		try {
			parser = new ClassParser(appFile.getPath());
			jclass = parser.parse();
			name = jclass.getClassName();
			
			if (!jclass.getSuperclassName().equals("javacard.framework.Applet"))
				throw new ClassFormatException("Incorrect superclass");
		}
		catch (IOException e) {
			System.out.println("SimulatorAdapter.getFileClassName IOException: " + e.getMessage());
		}
		catch (ClassFormatException e) {
			System.out.println("SimulatorAdapter.getFileClassName ClassFormatException: " + e.getMessage());
		}
		
		return name;
	}
	
	private byte[] getByteCode(File appFile) {
		byte[] bytes = null;
		FileInputStream fs = null;
		
		try {
			bytes = new byte[(int)appFile.length()];
			fs = new FileInputStream(appFile);
			fs.read(bytes);
		}
		catch (FileNotFoundException e) {
			System.out.println("SimulatorAdapter.getByteCode FileNotFoundException: " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("SimulatorAdapter.getByteCode IOException: " + e.getMessage());
		}
		finally {
			try {
				if (fs != null) {
					fs.close();
				}
			}
			catch (IOException e) {
				System.out.println("SimulatorAdapter.getByteCode closing stream IOException: " + e);
			}	
		}
		return bytes;
	}
	
	class SCAppClassLoader extends ClassLoader {
		SCAppClassLoader() {
			super();
		}
		
		public Class<?> loadClass(byte[] bytes, String className) {
			try {
				return this.defineClass(className, bytes, 0, bytes.length);
			}
			catch (ClassFormatError e) {
				System.out.println("SimulatorAdapter.loadAppClass ClassFormatError: " + e);
			}
			return null;
		}
	}
}
