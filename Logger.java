

import java.io.FileWriter;
import java.io.IOException;
public class Logger implements ILogger {
    private FileWriter writer;
    private StringBuilder logContent = new StringBuilder();

    // Constructor
    public Logger(String filename) throws Exception {
        System.out.println("[Logger] Initializing Logger...");
        try {
            writer = new FileWriter(filename);
            System.out.println("[Logger] Log file created: " + filename);
        } catch (IOException e) {
            System.out.println("[Logger] ERROR: Failed to create log file.");
            throw new Exception("Failed to create log file.");
        }
    }

    // Log a message with a timestamp
    public void log(String message) {
        System.out.println("[Logger] Logging message: " + message);
        String timestamp = new java.util.Date().toString();
        String logEntry = timestamp + " - " + message;
        logContent.append(logEntry).append("\n");

        try {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            System.out.println("[Logger] ERROR: Failed to write to log file.");
        }
    }

    // Get the full log content
    public String getLogContent() {
        System.out.println("[Logger] Retrieving log content...");
        return logContent.toString();
    }

    // Close the log file
    public void close() {
        System.out.println("[Logger] Closing log file...");
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("[Logger] ERROR: Failed to close log file.");
        }
    }
}