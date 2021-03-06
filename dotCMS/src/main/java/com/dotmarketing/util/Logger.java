/*
 *  UtilMethods.java
 *
 *  Created on March 4, 2002, 2:56 PM
 */
package com.dotmarketing.util;

import com.dotmarketing.loggers.Log4jUtil;
import java.io.File;
import java.util.WeakHashMap;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.velocity.servlet.VelocityServlet;
import org.apache.velocity.tools.view.tools.ViewTool;

/**
 *@author     David Torres
 */
public class Logger{

	static {
		Log4jUtil.configureDefaultSystemProperties();
	}
	private static WeakHashMap<Class, org.apache.logging.log4j.Logger> map = new WeakHashMap<>();

	public static void clearLoggers(){
		map.clear();
	}

    public static org.apache.logging.log4j.Logger clearLogger ( Class clazz ) {
        return map.remove( clazz );
	}
	

	/**
	 * This class is syncrozned.  It shouldn't be called. It is exposed so that 
	 * @param cl
	 * @return
	 */
	private synchronized static org.apache.logging.log4j.Logger loadLogger(Class cl){
		if(map.get(cl) == null){
			org.apache.logging.log4j.Logger logger = LogManager.getLogger(cl);
			map.put(cl, logger);
		}
		return map.get(cl);
	}

	public static void info(Class clazz, final Supplier<String> message) {
		if (isInfoEnabled(clazz)) {
			info(clazz, message.get());
		}
	}

	public static void info(final Object ob, final Supplier<String> message) {
		if (isInfoEnabled(ob.getClass())) {
			info(ob.getClass(), message.get());
		}
	}

    public static void info(Object ob, String message) {
        info(ob.getClass(), message);
    }

    public static void info(Class cl, String message) {
    	if(isVelocityMessage(cl)){
    		velocityInfo(cl, message);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.info(message);
    }

	public static void debug(final Object ob, final Supplier<String> message) {
		if (isDebugEnabled(ob.getClass())) {
			debug(ob.getClass(), message.get());
		}
	}

    public static void debug(Object ob, String message) {
    	debug(ob.getClass(), message);
    }

    public static void debug(Object ob, String message, Throwable ex) {
    	debug(ob.getClass(), message,ex);
    }

    public static void debug(Class cl, String message) {
    	if(isVelocityMessage(cl)){
    		velocityDebug(cl, message);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.debug(message);
    }

    public static void debug(Class cl, String message, Throwable ex) {
    	if(isVelocityMessage(cl)){
    		velocityDebug(cl, message, ex);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.debug(message, ex);
    }

    public static void error(Object ob, String message) {
    	error(ob.getClass(), message);
    }

    public static void error(Object ob, String message, Throwable ex) {
    	error(ob.getClass(), message, ex);
    }

    public static void error(Class cl, String message) {
    	if(isVelocityMessage(cl)){
    		velocityError(cl, message);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}

        logger.error(message);
    }

    public static void error(Class cl, String message, Throwable ex) {
    	if(isVelocityMessage(cl)){
    		velocityError(cl, message, ex);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
    	try{
    	    logger.error(message, ex);
    	}
    	catch(java.lang.IllegalStateException e){
    	    ex.printStackTrace();
    	}
    }
    /**
     * this method will print the message if at the WARN level
     * and print the message + whole stack trace if at the DEGUG LEVEL
     * @param cl
     * @param message
     * @param ex
     */
    public static void warnAndDebug(Class cl, String message, Throwable ex) {
        org.apache.logging.log4j.Logger logger = map.get(cl);
        if(logger == null){
            logger = loadLogger(cl);    
        }
        try{
            logger.warn(message);
            logger.debug(message, ex);
        }
        catch(java.lang.IllegalStateException e){
            ex.printStackTrace();
        }
    }

    /**
     * this method will print the message if at the WARN level
     * and print the message + whole stack trace if at the DEGUG LEVEL
     * @param cl
     * @param message
     * @param ex
     */
    public static void warnAndDebug(Class cl, Throwable ex) {
        warnAndDebug(cl, ex.getMessage(), ex);
    }

    
    public static void fatal(Object ob, String message) {
    	fatal(ob.getClass(), message);
    }

    public static void fatal(Object ob, String message, Throwable ex) {
    	fatal(ob.getClass(), message, ex);
    }

    public static void fatal(Class cl, String message) {
    	if(isVelocityMessage(cl)){
    		velocityFatal(cl, message);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.fatal(message);
    }

    public static void fatal(Class cl, String message, Throwable ex) {
    	if(isVelocityMessage(cl)){
    		velocityFatal(cl, message, ex);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.fatal(message, ex);
    }

	public static void warn(final Object ob, final Supplier<String> message) {
		if (isWarnEnabled(ob.getClass())) {
			warn(ob.getClass(), message.get());
		}
	}

	public static void warn(Class clazz, final Supplier<String> message) {
		if (isWarnEnabled(clazz)) {
			warn(clazz, message.get());
		}
	}

    public static void warn(Object ob, String message) {
    	warn(ob.getClass(), message);
    }

    public static void warn(Object ob, String message, Throwable ex) {
    	warn(ob.getClass(), message, ex);
    }

    public static void warn(Class cl, String message) {
    	if(isVelocityMessage(cl)){
    		velocityWarn(cl, message);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}

        logger.warn(message);
    }

    public static void warn(Class cl, String message, Throwable ex) {
    	if(isVelocityMessage(cl)){
    		velocityWarn(cl, message, ex);
    		return;
    	}
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}

        logger.warn(message, ex);
    }
    public static boolean isDebugEnabled(Class cl) {
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isDebugEnabled();

    }

    public static boolean isInfoEnabled(Class cl) {
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isInfoEnabled();

    }
    public static boolean isWarnEnabled(Class cl) {
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isWarnEnabled();

    }
    public static boolean isErrorEnabled(Class cl) {
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isErrorEnabled();

    }
    
    public static org.apache.logging.log4j.Logger getLogger(Class cl) {
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger;
    }
    
    
    
    public static void velocityError(Class cl, String message, Throwable thr){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.error(message + " @ " +  Thread.currentThread().getName(), thr);
    }
    
    public static void velocityWarn(Class cl, String message, Throwable thr){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.warn(message + " @ " +  Thread.currentThread().getName(), thr);
    }
    
    public static void velocityInfo(Class cl, String message, Throwable thr){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.info(message + " @ " +  Thread.currentThread().getName(), thr);
    }
    public static void velocityFatal(Class cl, String message, Throwable thr){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.fatal(message + " @ " +  Thread.currentThread().getName(), thr);
	}
    public static void velocityDebug(Class cl, String message, Throwable thr){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.debug(message + " @ " +   Thread.currentThread().getName(), thr);
	}

    public static void velocityError(Class cl, String message){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.error(message + " @ " +  Thread.currentThread().getName());
	}
	
	public static void velocityWarn(Class cl, String message){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.warn(message + " @ " +  Thread.currentThread().getName());
	}
	
	public static void velocityInfo(Class cl, String message){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.info(message + " @ " +  Thread.currentThread().getName());
	}
	public static void velocityFatal(Class cl, String message){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.fatal(message + " @ " +  Thread.currentThread().getName());
	}
	public static void velocityDebug(Class cl, String message){
    	org.apache.logging.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
		logger.debug(message + " @ " +  Thread.currentThread().getName());
	}
    
    
    
    private static String normalizeTemplate(Object t){
    	if(t ==null){
    		return null;
    	}
		String x = t.toString();
		x = x.replace(File.separatorChar, '/');
		x = (x.indexOf("assets") > -1) ? x.substring(x.lastIndexOf("assets"), x.length()) : x;
		x = (x.startsWith("/")) ?  x : "/" + x;

		return x;
    }
    
    
    private static boolean isVelocityMessage(Object obj){
    	if(obj==null)return false;
    	return isVelocityMessage(obj.getClass());
    }
    
    private static boolean isVelocityMessage(Class clazz){
    	boolean ret = false;
    	if(clazz!=null && clazz.getName()!=null){
        	String name = clazz.getName().toLowerCase();
        	ret= name.contains("velocity") || name.contains("viewtool");

        	if(!ret){
					ret= ViewTool.class.isAssignableFrom(clazz);
        	}
        	if(!ret){
					ret= VelocityServlet.class.isAssignableFrom(clazz);
        	}
    	}
    	return ret;

    }
}
