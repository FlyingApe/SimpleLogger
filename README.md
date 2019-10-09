# SimpleLogger
A basic Java logger

SimpleLogger.jar can be found in out/artifacts/ and can be added as a library to any project.

Static function `SimpleLogger.setLogDirectory(String path)` will allow you to set a directory to store .txt logfiles. If none is set SimpleLogger will create a log directory in the project root. It will be set internally through `System.getProperty("user.dir") + "\\log"`. If the given directory doesn't exist, it will be created.

Static function `SimpleLogger.write(String logfile, String whoDunnit, String logText)` will allow you to write **logText** to **logfile**. **whodunnit** can be used to log where te message came from. **logfile** has to be a .txt file, if it doesn't exist, it will be created

