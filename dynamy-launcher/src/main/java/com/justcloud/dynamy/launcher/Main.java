package com.justcloud.dynamy.launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ServiceLoader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.osgi.framework.launch.FrameworkFactory;

import com.justcloud.dynamy.launcher.runner.FrameworkConfig;
import com.justcloud.dynamy.launcher.runner.FrameworkConfig.FrameworkConfigFactory;
import com.justcloud.dynamy.launcher.runner.FrameworkRunner;
import com.justcloud.dynamy.launcher.runner.SameThreadRunner;

public class Main {

	public static void main(String[] args) {
		new Main().run(args);
	}

	public void run(String[] args) {
		FrameworkRunner runner;
		CommandLineParser parser = new PosixParser();

		String frameworkProperties = "config/framework.properties";

		Options options = new Options();
		options.addOption(new Option("h", "help", false, "Print this help"));
		options.addOption(new Option("c", "config", true,
				"Framework properties file"));

		try {
			CommandLine line = parser.parse(options, args);
			FrameworkConfigFactory configFactory = FrameworkConfig.getFactory();

			if (line.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("dynamy", options);
				System.exit(0);
			}

			if (line.hasOption("config")) {
				frameworkProperties = line.getOptionValue("config");
			}

			runner = new SameThreadRunner();

			FrameworkConfig config = configFactory
					.properties(loadFrameworkProperties(frameworkProperties))
					.frameworkFactory(buildFactory()).build();

			runner.start(config);

		} catch (ParseException ex) {
			System.out.println(ex.getMessage());
		}

	}

	private Map<String, String> loadFrameworkProperties(String path) {
		Properties properties = new Properties();
		Map<String, String> propertiesMap = new HashMap<>();
		try {
			properties.load(new FileInputStream(path));
			for (Entry<Object, Object> entry : properties.entrySet()) {
				propertiesMap.put((String) entry.getKey(),
						(String) entry.getValue());
			}
			return propertiesMap;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private FrameworkFactory buildFactory() {
		try {
			return ServiceLoader.load(FrameworkFactory.class).iterator().next();
		} catch (NoSuchElementException ex) {
			throw new RuntimeException(
					"FrameworkFactory not found in classpath");
		}
	}

}
