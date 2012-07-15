package com.justcloud.dynamy.launcher.runner;

import java.util.logging.Logger;

import org.osgi.framework.launch.Framework;

public class SameThreadRunner extends FrameworkRunner {

	@Override
	public void start(FrameworkConfig config) {
		try {
			Framework framework = realStart(config);
			framework.waitForStop(0);
		} catch (Exception ex) {
			Logger.getLogger(SameThreadRunner.class.getName())
					.warning(ex.getMessage());
			System.exit(1);
		} finally {
			System.exit(0);
		}
	}

}
