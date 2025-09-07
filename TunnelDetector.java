
import swiftbot.*;
//import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class TunnelDetector implements ITunnelDetector {
    private static final double TUNNEL_THRESHOLD = 50; // Light intensity threshold for tunnel detection
    private SwiftBotAPI swiftBot;

    public TunnelDetector(SwiftBotAPI swiftBot) {
        System.out.println("[TunnelDetector] Initializing TunnelDetector...");
        this.swiftBot = swiftBot;
    }

    @Override
    public double captureAndProcessImage() throws Exception {
        System.out.println("[TunnelDetector] Capturing and processing image...");
        try {
            BufferedImage image = swiftBot.takeGrayscaleStill(ImageSize.SQUARE_720x720);
            double lightIntensity = computeLightIntensity(image);
            System.out.println("[TunnelDetector] Light intensity calculated: " + lightIntensity);
            return lightIntensity;
        } catch (Exception e) {
            System.out.println("[TunnelDetector] ERROR: Failed to capture or process image.");
            throw new Exception("Failed to capture or process image: " + e.getMessage());
        }
    }

    private double computeLightIntensity(BufferedImage image) {
        System.out.println("[TunnelDetector] Computing light intensity...");
        int width = image.getWidth();
        int height = image.getHeight();
        double sum = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sum += getPixelIntensity(image.getRGB(x, y));
            }
        }
        return sum / (width * height);
    }

    private double getPixelIntensity(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = pixel & 0xff;
        return (red + green + blue) / 3.0; // Average of RGB
    }

    @Override
    public boolean isInsideTunnel(double lightIntensity) {
        System.out.println("[TunnelDetector] Checking if inside tunnel...");
        return lightIntensity < TUNNEL_THRESHOLD;
    }
}