package com.justcloud.dynamy.launcher.runner;

import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public abstract class FrameworkRunner {

	public abstract void start(FrameworkConfig config);

	protected Framework realStart(FrameworkConfig config) throws BundleException {
		FrameworkFactory factory = config.getFrameworkFactory();
		Framework framework = factory.newFramework(config.getProperties());
		
		framework.init();
		framework.start();
		
		return framework;
	}

}
