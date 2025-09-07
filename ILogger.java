

public interface ILogger {
	void log(String message);
    void close();
    String getLogContent();
}
