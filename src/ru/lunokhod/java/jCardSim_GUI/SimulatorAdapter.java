package ru.lunokhod.java.jCardSim_GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.licel.jcardsim.base.Simulator;
import javacard.framework.AID;
import javacard.framework.Applet;
import com.licel.jcardsim.utils.AIDUtil;

public class SimulatorAdapter {
	private Simulator simulator;
	SCAppClassLoader classLoader = new SCAppClassLoader();
	
	public SimulatorAdapter() {
		simulator = new Simulator();
	}

	public void installApplet(String aid, File classFile) {
		AID appAid = AIDUtil.create(aid);
		Class<?> appletClass = null;
		
		try {
			byte[] bytes = getByteCode(classFile);
			appletClass = classLoader.loadClass(bytes, "ru.lunokhod.javacard.applet2c.MyApplet");
		}
		catch (Exception e) {
			System.out.println("SimulatorAdapter.installApplet ClassLoader.loadClass Exception: " + e.getMessage());
		}
		
		if (appletClass != null) {
			System.out.println("appletClass loaded");
			//simulator.loadApplet(appAid, appletClass);
		}
		else {
			System.out.println("appletClass == null");
		}
	}
	
	public void installApplet(byte[] aid, File classFile) {
		installApplet(AIDUtil.create(aid).toString(), classFile);
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
				System.out.println("SimulatorAdapter.getByteCode Error while closing stream: " + e);
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
