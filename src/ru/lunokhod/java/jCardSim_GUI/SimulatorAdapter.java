package ru.lunokhod.java.jCardSim_GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	private SCAppClassLoader classLoader = new SCAppClassLoader();
	private ArrayList<AppletDescriptor> applets = new ArrayList<AppletDescriptor>();
	private AppletDescriptor selectedApplet = null;
	
	public SimulatorAdapter() {
		simulator = new Simulator();
	}

	public boolean installApplet(String aid, File classFile) {
		AID appAid = AIDUtil.create(aid);
		Class<?> appletClass = null;
		byte[] bytes = getByteCode(classFile);
		JavaClass jclass = getFileClassDescriptor(classFile);
		String className = jclass.getClassName();
			
		try {	
			if (jclass != null) {
				System.out.println("ClassName parsed : " + className);
				System.out.println("SuperclassName parsed : " + jclass.getSuperclassName());
				
				appletClass = classLoader.loadClass(bytes, className);
				
				if (!checkAppletSuperclass(appletClass))
				{
					System.out.println("SimulatorAdapter.installApplet invalid superclass: " + jclass.getSuperclassName());
					return false;
				}
			}
		}
		catch (Exception e) {
			System.out.println("SimulatorAdapter.installApplet ClassLoader.loadClass Exception: " + e.getMessage());
		}
		
		if (appletClass != null) {
			try
			{
				simulator.installApplet(appAid, appletClass);
				applets.add(new AppletDescriptor(appAid, className, classFile.getPath(), bytes.length));
				
				System.out.println("appletClass loaded into Simulator");
				
				byte[] s = simulator.selectAppletWithResult(appAid);
				if (s != null)
					System.out.println("selectApplet = " + s.toString());
				else
					System.out.println("selectApplet = null");
				
				return true;
			}
			catch (SystemException e) {
				System.out.println("SimulatorAdapter.installApplet loadApplet SystemException: " + e.getMessage());
				return false;
			}
		}
		else {
			System.out.println("appletClass == null");
			return false;
		}
	}
	
	public void installApplet(byte[] aid, File classFile) {
		installApplet(AIDUtil.create(aid).toString(), classFile);
	}
	
	public ArrayList<AppletDescriptor> getInstalledApplets() {
		return applets;
	}
	
	public boolean isAppletInstalled(String aid) {
		for (AppletDescriptor ad : applets) {
			if (AIDUtil.toString(ad.getAid()).equals(aid)) return true;
		}
		return false;
	}
	
	public boolean selectApplet(String aid) {
		if (simulator.selectApplet(getAppletDescriptor(aid).getAid())) {
			selectedApplet = getAppletDescriptor(aid);
			return true;
		}
		return false;
	}
	
	public byte[] selectAppletWithResult(String aid) {
		return simulator.selectAppletWithResult(AIDUtil.create(aid));
	}
	
	public void reset() {
		simulator.reset();
		applets.clear();
	}
	
	public void resetRuntime() {
		simulator.resetRuntime();
		applets.clear();
	}
	
	public AppletDescriptor getAppletDescriptor(String aid) {
		return getAppletDescriptor(AIDUtil.create(aid));
	}
	
	public AppletDescriptor getAppletDescriptor(AID aid) {
		for (AppletDescriptor ad : applets) {
			if (ad.getAid().equals(aid));
				return ad;
		}
		return null;
	}
	
	private JavaClass getFileClassDescriptor(File appFile) {
		ClassParser parser;
		
		try {
			parser = new ClassParser(appFile.getPath());
			return parser.parse();
		}
		catch (IOException e) {
			System.out.println("SimulatorAdapter.getFileClassName IOException: " + e.getMessage());
		}
		catch (ClassFormatException e) {
			System.out.println("SimulatorAdapter.getFileClassName ClassFormatException: " + e.getMessage());
		}
		return null;
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
	
    private boolean checkAppletSuperclass(Class<?> appletClass) {
        Class<?> parent = appletClass;
        while (parent != Object.class) {
            if (parent == Applet.class) {
                return true;
            }
            parent = parent.getSuperclass();
        }
        return false;
    }
	
	class SCAppClassLoader extends ClassLoader {
		SCAppClassLoader() {
			super();
		}
		
		Class<?> loadClass(byte[] bytes, String className) {
			try {
				Class<?> jclass = this.findLoadedClass(className);
				if (jclass != null)
					return jclass;
				else
					return this.defineClass(className, bytes, 0, bytes.length);
			}
			catch (ClassFormatError e) {
				System.out.println("SimulatorAdapter.loadAppClass ClassFormatError: " + e);
			}
			catch (Exception e) {
				System.out.println("SimulatorAdapter.loadAppClass Exception: " + e);
			}
			return null;
		}
	}
}
