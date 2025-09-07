

import swiftbot.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ObstacleHandler implements IObstacleHandler {
    private static final double OBSTACLE_DISTANCE_THRESHOLD = 40; // Obstacle detection threshold in cm
    private SwiftBotAPI swiftBot;

    public ObstacleHandler(SwiftBotAPI swiftBot) {
        System.out.println("[ObstacleHandler] Initializing ObstacleHandler...");
        this.swiftBot = swiftBot;
    }

    @Override
    public boolean detectObstacle() throws Exception {
        System.out.println("[ObstacleHandler] Detecting obstacle...");
        double distance = swiftBot.useUltrasound();
        System.out.println("[ObstacleHandler] Distance to obstacle: " + distance + " cm");

        if (distance < OBSTACLE_DISTANCE_THRESHOLD) {
            System.out.println("[ObstacleHandler] Obstacle detected within " + OBSTACLE_DISTANCE_THRESHOLD + " cm.");
            return true;
        } else {
            System.out.println("[ObstacleHandler] No obstacle detected.");
            return false;
        }
    }

    @Override
    public void handleObstacle() throws Exception {
        System.out.println("[ObstacleHandler] Handling obstacle...");
        swiftBot.move(0, 0, 0); // Stop the robot
        System.out.println("[ObstacleHandler] Robot stopped.");

        System.out.println("[ObstacleHandler] Capturing obstacle image...");
        BufferedImage obstacleImage = swiftBot.takeStill(ImageSize.SQUARE_720x720);

        try {
            System.out.println("[ObstacleHandler] Saving obstacle image as 'obstacle.png'...");
            ImageIO.write(obstacleImage, "png", new File("obstacle.png"));
            System.out.println("[ObstacleHandler] Obstacle image saved successfully.");
        } catch (IOException e) {
            System.out.println("[ObstacleHandler] ERROR: Failed to save obstacle image.");
            throw new Exception("Failed to save obstacle image: " + e.getMessage());
        }
    }
}