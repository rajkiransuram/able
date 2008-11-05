package able.util;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:plightbo@gmail.com">Patrick Lightbody</a>
 */
public class Log {
    static {
        // tell commons-logging to use the JDK logging (otherwise it would default to log4j
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

        /* If the user didn't specify a log configuration, log to the console. */
        /*
          if (null == System.getProperty("java.util.logging.config.file")) {
              try {
                  Handler[] handlers = java.util.logging.Logger.getLogger("").getHandlers();
                  boolean foundConsoleHandler = false;

                  ConsoleHandler consoleHandler = null;
                  for (Handler handler : handlers) {
                      // since we have a handler, lets go with whatever level it already has
                      if (handler instanceof ConsoleHandler) {
                          consoleHandler = (ConsoleHandler) handler;
                          handler.setFormatter(new TerseFormatter());
                          foundConsoleHandler = true;
                      }
                  }
                  if (!foundConsoleHandler) {
                      // no console handler found
                      System.err.println("No consoleHandler found, adding one.");
                      consoleHandler = new ConsoleHandler();
                      //by default, we only log warnings and up
                      consoleHandler.setLevel(Level.FINE);
                      consoleHandler.setFormatter(new TerseFormatter());
                      java.util.logging.Logger.getLogger("").addHandler(consoleHandler);
                  } else {
                      consoleHandler.setLevel(Level.FINE);
                  }
              }
              catch (Throwable t) {
                  System.err.println("Unexpected Error setting up logging");
                  //noinspection CallToPrintStackTrace
                  t.printStackTrace();
              }
          }*/
    }

    protected Logger logger;
    private String className;

    public Log() {
        Exception e = new Exception();
        className = e.getStackTrace()[1].getClassName();
        logger = Logger.getLogger(className);
    }

    public Log(Class clazz) {
        className = clazz.getName();
        logger = Logger.getLogger(className);
    }

    public void severe(String msg, Throwable e) {
        log(Level.SEVERE, msg, e);
    }

    public void severe(String msg, Object... args) {
        log(Level.SEVERE, msg, args);
    }

    public void severe(String msg, Throwable e, Object... args) {
        log(Level.SEVERE, msg, e, args);
    }

    public RuntimeException severeAndRethrow(String msg, Throwable e, Object... args) {
        log(Level.SEVERE, msg, e, args);

        //noinspection ThrowableInstanceNeverThrown
        return new RuntimeException(new java.util.Formatter().format(msg, args).toString());
    }

    public void warn(String msg, Throwable e) {
        log(Level.WARNING, msg, e);
    }

    public void warn(String msg, Object... args) {
        log(Level.WARNING, msg, args);
    }

    public void warn(String msg, Throwable e, Object... args) {
        log(Level.WARNING, msg, e, args);
    }

    public void info(String msg, Throwable e) {
        log(Level.INFO, msg, e);
    }

    public void info(String msg, Object... args) {
        log(Level.INFO, msg, args);
    }

    public void info(String msg, Throwable e, Object... args) {
        log(Level.INFO, msg, e, args);
    }

    public void fine(String msg, Throwable e) {
        log(Level.FINE, msg, e);
    }

    public void fine(String msg, Object... args) {
        log(Level.FINE, msg, args);
    }

    public void fine(String msg, Throwable e, Object... args) {
        log(Level.FINE, msg, e, args);
    }

    private void log(Level level, String msg, Throwable e) {
        logger.log(level, msg, e);
    }

    private void log(Level level, String msg, Object... args) {
        logger.log(level, msg, args);
    }

    private void log(Level level, String msg, Throwable e, Object... args) {
        LogRecord lr = new LogRecord(level, msg);
        lr.setThrown(e);
        lr.setParameters(args);
        lr.setSourceMethodName("");
        lr.setSourceClassName(className);
        logger.log(lr);
    }
}