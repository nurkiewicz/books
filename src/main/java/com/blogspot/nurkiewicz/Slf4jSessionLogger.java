package com.blogspot.nurkiewicz;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on: https://gist.github.com/msosvi/1325764
 */
public class Slf4jSessionLogger extends AbstractSessionLog {

	public static final String ECLIPSELINK_NAMESPACE = "org.eclipse.persistence.logging";
	public static final String DEFAULT_CATEGORY = "default";

	public static final String DEFAULT_ECLIPSELINK_NAMESPACE = ECLIPSELINK_NAMESPACE
			+ "." + DEFAULT_CATEGORY;

	private Map<String, Logger> categoryLoggers = new HashMap<>();

	public Slf4jSessionLogger() {
		for (String category : SessionLog.loggerCatagories) {
			addLogger(category, ECLIPSELINK_NAMESPACE + "." + category);
		}
		addLogger(DEFAULT_CATEGORY, DEFAULT_ECLIPSELINK_NAMESPACE);
	}

	@Override
	public void log(SessionLogEntry entry) {
		if (shouldLog(entry.getLevel(), entry.getNameSpace())) {
			final Logger logger = getLogger(entry.getNameSpace());
			final LogLevel logLevel = toLogLevel(entry.getLevel());
			final String message = getSupplementDetailString(entry) + formatMessage(entry);
			switch (logLevel) {
				case TRACE:
					logger.trace(message);
					break;
				case DEBUG:
					logger.debug(message);
					break;
				case INFO:
					logger.info(message);
					break;
				case WARN:
					logger.warn(message);
					break;
				case ERROR:
					logger.error(message);
					break;
			}
		}
	}

	@Override
	public boolean shouldLog(int level, String category) {
		Logger logger = getLogger(category);
		switch (toLogLevel(level)) {
			case TRACE:
				return logger.isTraceEnabled();
			case DEBUG:
				return logger.isDebugEnabled();
			case INFO:
				return logger.isInfoEnabled();
			case WARN:
				return logger.isWarnEnabled();
			case ERROR:
				return logger.isErrorEnabled();
			default:
				return false;
		}
	}

	@Override
	public boolean shouldLog(int level) {
		return shouldLog(level, "default");
	}

	@Override
	public boolean shouldDisplayData() {
		return this.shouldDisplayData != null && shouldDisplayData;
	}

	private void addLogger(String loggerCategory, String loggerNameSpace) {
		categoryLoggers.put(loggerCategory,
				LoggerFactory.getLogger(loggerNameSpace));
	}

	private Logger getLogger(String category) {

		if (!StringUtils.hasText(category)
				|| !this.categoryLoggers.containsKey(category)) {
			category = DEFAULT_CATEGORY;
		}
		return categoryLoggers.get(category);
	}

	private LogLevel toLogLevel(int level) {
		switch (level) {
			case SessionLog.ALL:
				return LogLevel.TRACE;
			case SessionLog.FINEST:
				return LogLevel.TRACE;
			case SessionLog.FINER:
				return LogLevel.TRACE;
			case SessionLog.FINE:
				return LogLevel.DEBUG;
			case SessionLog.CONFIG:
				return LogLevel.DEBUG;
			case SessionLog.INFO:
				return LogLevel.INFO;
			case SessionLog.WARNING:
				return LogLevel.WARN;
			case SessionLog.SEVERE:
				return LogLevel.ERROR;
			default:
				return LogLevel.OFF;
		}
	}

	private enum LogLevel {
		TRACE, DEBUG, INFO, WARN, ERROR, OFF
	}

}