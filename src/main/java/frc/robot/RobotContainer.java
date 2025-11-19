package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentricFacingAngle;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.CommandUtil;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.*;
import java.util.Set;

public class RobotContainer {

  RobotConfig robotConfig;
  int driverPort = 0;
  Controllers controllers;
  Drivetrain drivetrain;
  Vision vision;
  Leds leds;

  Shooter shooter;
  CommandXboxController operatorController;
  CommandXboxController driveController;

  FieldMap fieldMap;
  SwerveRequest.FieldCentric drive;
  SwerveRequest.FieldCentricFacingAngle reefLock;
  SwerveRequest.SwerveDriveBrake brake;

  Pneumatics pneumatics;
  SendableChooser<Command> autoChooser;
  private ShuffleboardTab autosTab;


  public RobotContainer() {
    RoboRio roboRio = RoboRio.lookupBySerialNumber(System.getenv("serialnum"));
    robotConfig = RobotConfig.lookupConfig(roboRio);
    controllers = new Controllers(robotConfig.driverControllerPort, robotConfig.operatorControllerPort);
    shooter = new Shooter(robotConfig.shooterConfig);
    leds = new Leds();
    pneumatics = new Pneumatics(null);
    driveController = controllers.driverController;
    operatorController = controllers.operatorController;

    fieldMap = new FieldMap(Alliance.Red);
    initDrivetrain(robotConfig.drivetrainConfig);
    initVision(robotConfig.visionConfig);
    configureDriverBindings(robotConfig.drivetrainConfig);
    initAuto();
  }

  private void configureDriverBindings(Drivetrain.Config config) {
    // driveController
    //     .back()
    //     .debounce(.25)
    //     .and(driveController.start().negate())
    //     .onTrue(cameraCalibrator.runCameraCalibration());
    driveController
        .start()
        .debounce(.25)
        .and(driveController.back().negate())
        .onTrue(drivetrain.resetPerspective());

    driveController
        .rightTrigger(.5)
        .whileTrue(drivetrain.applyRequest(
            () -> drive
                .withVelocityX(Drivetrain.MAX_LINEAR_SPEED.times(-controllers.getDriverLeftY()
                    * 0.125)) // Drive forward with negative Y (forward)
                .withVelocityY(Drivetrain.MAX_LINEAR_SPEED.times(
                    -controllers.getDriverLeftX() * 0.125)) // Drive left with negative X (left)
                .withRotationalRate(drivetrain
                    .getMaxAngularVelocity()
                    .times(-controllers.getDriverRightX()
                        * 0.125)) // Drive counterclockwise with negative X (left)
            ));

            driveController.x().whileTrue(shooter.shootingCommand());

            driveController.a().whileTrue(shooter.spitBackCommand());

            driveController.y().onTrue(pneumatics.setSolenoidCommand(true));

            driveController.b().onTrue(pneumatics.setSolenoidCommand(false));

            operatorController.x().whileTrue(shooter.shootingCommand());

            operatorController.a().whileTrue(shooter.spitBackCommand());

            operatorController.y().onTrue(pneumatics.setSolenoidCommand(true));

            operatorController.b().onTrue(pneumatics.setSolenoidCommand(false));
  }

  

    private void initDrivetrain(Drivetrain.Config config) {
        drivetrain = Drivetrain.makeDrivetrain(config);
    
        if (drivetrain != null) {
            drive = new SwerveRequest.FieldCentric()
                .withDriveRequestType(
                    DriveRequestType.Velocity); // Use open-loop control for drive motors
    
            brake = new SwerveRequest.SwerveDriveBrake();
        }

        
    drivetrain.setDefaultCommand(
      // Drivetrain will execute this command periodically
      drivetrain.applyRequest(
          () -> drive
              .withVelocityX(Drivetrain.MAX_LINEAR_SPEED.times(
                  -controllers.getDriverLeftY())) // Drive forward with negative Y (forward)
              .withVelocityY(Drivetrain.MAX_LINEAR_SPEED.times(
                  -controllers.getDriverLeftX())) // Drive left with negative X (left)
              .withRotationalRate(drivetrain
                  .getMaxAngularVelocity()
                  .times(
                      -controllers
                          .getDriverRightX())) // Drive counterclockwise with negative X (left)
          ));
    }


  private void initVision(Vision.Config config) {
    if (config == null) {
      return;
    }

    vision = new Vision(config);
    if (drivetrain != null) {
      vision.onPoseUpdate((stampedPose) -> {
        drivetrain.addVisionMeasurement(stampedPose.pose(), stampedPose.timestamp());
        fieldMap.updateCurrentReefAngle(stampedPose.pose());
      });
    }
  }

  public void updateAlliance(Alliance alliance) {
    fieldMap = new FieldMap(alliance);
    vision.updateAlliance(alliance);
  }

  public void initAuto() {
    autoChooser = AutoBuilder.buildAutoChooser();
    autoChooser.setDefaultOption("None", Commands.none());
    autosTab = Shuffleboard.getTab("Autos");
    autosTab.add("Auto Chooser", autoChooser).withWidget(BuiltInWidgets.kComboBoxChooser);
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
  public void ledSetPatternsLogic(){

    if (leds != null) {
      if(drivetrain.getIsDrivetrainMoving()){
        leds.ledStripPattern = leds.rainbowBarf;
      }
      else{
        leds.ledStripPattern = leds.redBreathe;
      }
    }
  }

} 

