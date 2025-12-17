package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Shooter;
import java.util.HashMap;
import java.util.Map;

public class RobotConfig {
  String name;
  int driverControllerPort;
  int operatorControllerPort;
  Drivetrain.Config drivetrainConfig;
  Vision.Config visionConfig;
  Shooter.Config shooterConfig;

  static Map<RoboRio, RobotConfig> knownConfigs;

  static {
    knownConfigs = new HashMap<RoboRio, RobotConfig>();
    knownConfigs.put(RoboRio.DELTA, getkitbotConfig());
    knownConfigs.put(RoboRio.EPSILON, getkitbotConfig());
  }

  static RobotConfig lookupConfig(RoboRio rio) {
    if (knownConfigs.containsKey(rio)) {
      return knownConfigs.get(rio);
    } else {
      return getkitbotConfig();
    }
  }

  static RobotConfig getCommonConfig() {
    var common = new RobotConfig();
    common.driverControllerPort = 0;
    common.operatorControllerPort = 1;

    return common;
  }

  static RobotConfig getkitbotConfig() {
    var kitbot = getCommonConfig();
    kitbot.name = "kitbot";



    kitbot.drivetrainConfig = new Drivetrain.Config();
    kitbot.drivetrainConfig.frontLeftModule = Drivetrain.Config.Module.G;
    kitbot.drivetrainConfig.frontRightModule = Drivetrain.Config.Module.B;
    kitbot.drivetrainConfig.backLeftModule = Drivetrain.Config.Module.C;
    kitbot.drivetrainConfig.backRightModule = Drivetrain.Config.Module.A;
    kitbot.drivetrainConfig.sidelength = Inches.of(26);
    kitbot.drivetrainConfig.shouldUsePIDForAlignment = false;
    kitbot.visionConfig = new Vision.Config();
    kitbot.shooterConfig = new Shooter.Config();
    kitbot.shooterConfig.shooterMotorId = 25;

    // kitbot.visionConfig.frontCameraToRobot = new Transform3d(
    //     new Translation3d(Inches.of(6.996), Inches.of(7.250), Inches.of(38.075)),
    //     new Rotation3d(Degrees.of(0), Degrees.of(-30), Degrees.of(0)));

    // These are (theoretically) close to correct
    kitbot.visionConfig.rightCameraToRobot = new Transform3d(
        new Translation3d(Inches.of(11.186), Inches.of(-9.091), Inches.of(9.026)),
        // new Translation3d(Inches.of(0), Inches.of(0), Inches.of(0)),
        new Rotation3d(Degrees.of(0), Degrees.of(-20), Degrees.of(28 + 1)));

    kitbot.visionConfig.leftCameraToRobot = new Transform3d(
        new Translation3d(Inches.of(11.186), Inches.of(9.091), Inches.of(9.026)),
        new Rotation3d(Degrees.of(0), Degrees.of(-20), Degrees.of(-28)));

    return kitbot;
  }
}
