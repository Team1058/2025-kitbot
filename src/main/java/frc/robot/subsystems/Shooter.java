package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    SparkMax shooterMotor;
    private SparkMaxConfig shooterMotorConfig;

    public static class Config {
        public int shooterMotorId;
    }
    
    public Shooter(Config config) {
        shooterMotor=new SparkMax(config.shooterMotorId,MotorType.kBrushless);
        shooterMotorConfig = new SparkMaxConfig();
        shooterMotorConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40,40);
    }

    

   public Command shootingCommand(){
return new StartEndCommand(()-> shooterMotor.set(.3), ()->shooterMotor.set(0)).withName("shooting command");
    }

    public Command spitBackCommand(){
return new StartEndCommand(()-> shooterMotor.set(-.3), ()->shooterMotor.set(0));        
    }
}