package org.example.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.LoggerFactory;

public class LogConfig {
    public static void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();


        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(context);
        consoleEncoder.setPattern("%d{HH:mm:ss} %highlight(%-5level) %-5level %logger{36} - %msg%n");
        consoleEncoder.start();

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(consoleEncoder);
        consoleAppender.start();


        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(context);
        fileEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} %highlight(%-5level)  %-5level %logger{36} - %msg%n");
        fileEncoder.start();

        RollingFileAppender<ILoggingEvent> errorFileAppender = new RollingFileAppender<>();
        errorFileAppender.setContext(context);
        errorFileAppender.setFile("logs/error.log");


        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(errorFileAppender);
        rollingPolicy.setFileNamePattern("logs/error.%d{yyyy-MM-dd}.log " );
        rollingPolicy.setMaxHistory(30);
        rollingPolicy.start();

        errorFileAppender.setRollingPolicy(rollingPolicy);
        errorFileAppender.setEncoder(fileEncoder);


        LevelFilter errorFilter = new LevelFilter();
        errorFilter.setLevel(Level.ERROR);
        errorFilter.setOnMatch(FilterReply.ACCEPT);
        errorFilter.setOnMismatch(FilterReply.DENY);
        errorFilter.start();

        errorFileAppender.addFilter(errorFilter);
        errorFileAppender.start();


        ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT");
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(errorFileAppender);
    }
}

