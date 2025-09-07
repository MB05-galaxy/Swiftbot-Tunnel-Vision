

import swiftbot.*;
public class CalibrationManager {
    private SwiftBotAPI swiftBot;

    public CalibrationManager(SwiftBotAPI swiftBot) {
        System.out.println("[CalibrationManager] Initializing CalibrationManager...");
        this.swiftBot = swiftBot;
    }

    public double calibrate(int speed, int time) throws Exception {
        System.out.println("[CalibrationManager] Starting calibration...");
        if (speed < 0 || speed > 100) {
            System.out.println("[CalibrationManager] ERROR: Speed must be between 0 and 100.");
            throw new IllegalArgumentException("Speed must be between 0 and 100.");
        }
        if (time <= 0) {
            System.out.println("[CalibrationManager] ERROR: Time must be greater than 0.");
            throw new IllegalArgumentException("Time must be greater than 0.");
        }

        System.out.println("[CalibrationManager] Moving robot at speed " + speed + " for " + time + " seconds...");
        swiftBot.move(speed, speed, time * 1000); // Move for 'time' seconds
        double distance = swiftBot.useUltrasound(); // Measure distance traveled
        System.out.println("[CalibrationManager] Distance traveled: " + distance + " cm");
        return distance;
    }
}