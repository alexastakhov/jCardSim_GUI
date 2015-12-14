package ru.lunokhod.java.jCardSim_GUI;

import java.io.File;
import java.io.IOException;
import com.licel.jcardsim.base.Simulator;
import javacard.framework.AID;
import javacard.framework.Applet;
import com.licel.jcardsim.utils.AIDUtil;
import java.net.URLClassLoader;
import java.net.URL;


public class SimulatorAdapter {
	private Simulator simulator;
	SCardAppClassLoader classLoader = new SCardAppClassLoader(new URL[]{});
	
	public SimulatorAdapter() {
		simulator = new Simulator();
	}

	public void installApplet(String aid, File classFile) {
		AID appAid = AIDUtil.create(aid);
		Class appletClass = null;
		
		try {
			classLoader.addAppletFile(classFile);
			appletClass = classLoader.loadClass(classFile.getName());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (appletClass != null) {
			simulator.loadApplet(appAid, appletClass);
		}
		else {
			System.out.println("appletClass == null");
		}
	}
	
	public void installApplet(byte[] aid, File classFile) {
		installApplet(AIDUtil.create(aid).toString(), classFile);
	}
	
	class SCardAppClassLoader extends URLClassLoader {
		SCardAppClassLoader(URL[] urls) {
			super(urls, SimulatorAdapter.class.getClassLoader());
		}
		
		void addAppletFile(File appFile) throws IOException {
			this.addURL(appFile.toURI().toURL());
		}
	}
}
