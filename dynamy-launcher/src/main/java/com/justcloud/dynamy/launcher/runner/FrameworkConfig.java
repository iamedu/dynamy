package com.justcloud.dynamy.launcher.runner;

import java.util.Map;

import org.osgi.framework.launch.FrameworkFactory;

public class FrameworkConfig {

	private Map<String, String> properties;
	private FrameworkFactory frameworkFactory;

	private FrameworkConfig() {
	}

	public static class FrameworkConfigFactory {
		private Map<String, String> properties;
		private FrameworkFactory frameworkFactory;

		private FrameworkConfigFactory() {
			properties = null;
			frameworkFactory = null;
		}

		public FrameworkConfigFactory frameworkFactory(
				FrameworkFactory frameworkFactory) {
			this.frameworkFactory = frameworkFactory;
			return this;
		}

		public FrameworkConfigFactory properties(Map<String, String> properties) {
			this.properties = properties;
			return this;
		}

		public FrameworkConfig build() {
			if (frameworkFactory == null) {
				throw new RuntimeException("Framework factory not defined");
			}
			FrameworkConfig config = new FrameworkConfig();
			config.setProperties(properties);
			config.setFrameworkFactory(frameworkFactory);
			return config;
		}

	}

	private void setFrameworkFactory(FrameworkFactory frameworkFactory) {
		this.frameworkFactory = frameworkFactory;
	}

	public FrameworkFactory getFrameworkFactory() {
		return frameworkFactory;
	}

	private void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public static FrameworkConfigFactory getFactory() {
		return new FrameworkConfigFactory();
	}

}
