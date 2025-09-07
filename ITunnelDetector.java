

public interface ITunnelDetector {
	double captureAndProcessImage() throws Exception;
    boolean isInsideTunnel(double lightIntensity);
}
