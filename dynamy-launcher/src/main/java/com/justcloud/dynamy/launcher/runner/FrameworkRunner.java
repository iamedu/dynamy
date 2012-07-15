package com.justcloud.dynamy.launcher.runner;

import java.io.IOException;

import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import com.justcloud.dynamy.deployer.AutoDeployService;

public abstract class FrameworkRunner {
		
	public abstract void start(FrameworkConfig config);

	protected Framework realStart(FrameworkConfig config) throws BundleException {
		FrameworkFactory factory = config.getFrameworkFactory();
		Framework framework = factory.newFramework(config.getProperties());
		
		framework.init();
		framework.start();
		
		String autoDeployPath = config.getProperties().get("dynamy.autodeploy.dir");
		
		try {
			new AutoDeployService(framework, autoDeployPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return framework;
	}

}
