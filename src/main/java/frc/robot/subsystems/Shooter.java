package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends PVCSparkSystemBase {
    
    private SparkMaxConfig shooterMotorConfig;

    public static class Config {
        public int shooterMotorId;
    }
    
    public Shooter(Config config) {
        primaryMotor = new SparkMax(config.shooterMotorId,MotorType.kBrushless);
        primaryMotor.clearFaults();
        shooterMotorConfig = new SparkMaxConfig();
        shooterMotorConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40,40);
    }

    

   public Command shootingCommand(){
return new StartEndCommand(()-> primaryMotor.set(.3), ()-> primaryMotor.set(0));
    }

    public Command spitBackCommand(){
return new StartEndCommand(()-> primaryMotor.set(-.3), ()->primaryMotor.set(0));        
    }



    @Override
    public void setAllMotorsBrake() {
}



    @Override
    public void setAllMotorsCoast() {}
}