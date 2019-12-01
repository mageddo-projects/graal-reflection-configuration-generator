package com.mageddo.aptools.log;

import javax.annotation.processing.Messager;

public class LoggerFactory {

	private LoggerFactory() {
	}

	public static Logger getLogger(){
		return (Logger) System.getProperties().get("aptools.logger");
	}

	public static Logger bindLogger(Messager messager) {
		Logger logger = new ApLogger(messager);
		System.getProperties().put("aptools.logger", logger);
		return logger;
	}
}
