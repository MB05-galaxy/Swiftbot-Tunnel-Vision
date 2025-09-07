
import swiftbot.*;
public class RobotController {
    private ITunnelDetector tunnelDetector;
    private IObstacleHandler obstacleHandler;
    private ILogger logger;
    private UserInterface userInterface;
    private SwiftBotAPI swiftBot;
    private boolean isRunning;

    // Constructor
    public RobotController(SwiftBotAPI swiftBot, ITunnelDetector tunnelDetector, IObstacleHandler obstacleHandler, ILogger logger) {
        System.out.println("[RobotController] Initializing RobotController...");
        this.swiftBot = swiftBot;
        this.tunnelDetector = tunnelDetector;
        this.obstacleHandler = obstacleHandler;
        this.logger = logger;
        this.userInterface = new UserInterface();
        this.isRunning = true;

        // Enable the 'X' button to terminate the program
        swiftBot.enableButton(Button.X, () -> terminateProgram());
        System.out.println("[RobotController] Initialization complete.");
    }

    // Main method to start the program
    public void start() {
        System.out.println("[RobotController] Starting program...");
        try {
            while (isRunning) {
                userInterface.displayMenu();
                int choice = userInterface.getUserInput();

                switch (choice) {
                    case 1:
                        System.out.println("[RobotController] Starting robot movement...");
                        startMovement();
                        break;
                    case 2:
                        System.out.println("[RobotController] Displaying log...");
                        userInterface.displayLog(logger.getLogContent());
                        break;
                    case 3:
                        System.out.println("[RobotController] Terminating program...");
                        terminateProgram();
                        break;
                    default:
                        System.out.println("[RobotController] Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("[RobotController] ERROR: " + e.getMessage());
            logger.log("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (userInterface.promptToDisplayLog()) {
                System.out.println("[RobotController] Prompting user to display log...");
                userInterface.displayLog(logger.getLogContent());
            }
            logger.close();
            userInterface.close();
            System.out.println("[RobotController] Program ended.");
        }
    }

    // Start robot movement and tunnel detection
    private void startMovement() throws Exception {
        System.out.println("[RobotController] Setting underlights to red...");
        swiftBot.fillUnderlights(new int[]{255, 0, 0}); // Set underlights to red
        System.out.println("[RobotController] Starting movement at 40% speed...");
        swiftBot.move(40, 40, 0); // Move forward at 40% speed

        while (isRunning) {
            detectTunnels();
            if (obstacleHandler.detectObstacle()) {
                System.out.println("[RobotController] Obstacle detected. Handling obstacle...");
                obstacleHandler.handleObstacle();
                terminateProgram();
                break;
            }
        }
    }

    // Detect tunnels using light intensity
    private void detectTunnels() throws Exception {
        System.out.println("[RobotController] Detecting tunnels...");
        double lightIntensity = tunnelDetector.captureAndProcessImage();
        if (tunnelDetector.isInsideTunnel(lightIntensity)) {
            System.out.println("[RobotController] Tunnel detected. Light intensity: " + lightIntensity);
            logger.log("Entered tunnel. Light intensity: " + lightIntensity);
        }
    }

    // Terminate the program
    private void terminateProgram() {
        System.out.println("[RobotController] Terminating program...");
        isRunning = false;
        swiftBot.move(0, 0, 0); // Stop the robot
        logger.log("Program terminated by user.");
        System.out.println("Program terminated. Log saved to execution_log.txt.");
    }

    // Main method to initialize and start the program
    public static void main(String[] args) {
        System.out.println("[RobotController] Initializing program...");
        try {
        	System.out.println("[RobotController] Initializing SwiftBot API...");
            // Initialize SwiftBot and dependencies
            SwiftBotAPI swiftBot = new SwiftBotAPI();
           // System.out.println("[RobotController] SwiftBot API initialized successfully.");
            //System.out.println("[RobotController] Testing I2C communication...");
            //swiftBot.testI2C();
            System.out.println("[RobotController] I2C communication test successful.");
            ITunnelDetector tunnelDetector = new TunnelDetector(swiftBot);
            IObstacleHandler obstacleHandler = new ObstacleHandler(swiftBot);
            ILogger logger = new Logger("execution_log.txt");

            // Create and start the robot controller
            RobotController controller = new RobotController(swiftBot, tunnelDetector, obstacleHandler, logger);
            controller.start();
        } catch (Exception e) {
            System.out.println("[RobotController] ERROR: Failed to initialize the program.");
            e.printStackTrace();
        }
    }
}

    // Main method to initialize and start the program
   