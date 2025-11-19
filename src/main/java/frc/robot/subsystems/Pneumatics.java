package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {

    private Compressor m_compressor;
    public final Solenoid m_solenoid;

    // return m_compressor.getCurrent();

    // return m_compressor.isEnabled();

    // return m_compressor.getPressureSwitchValue();

    // m_compressor.disable(){

    // }

    public static class Config {
        public int compressorId;
        public int m_solenoid;
    }

public Pneumatics (Config config) {
    m_compressor = new Compressor(PneumaticsModuleType.REVPH);
    m_compressor.enableAnalog(70, 120);   
    m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 0);
} 

public double getCurrent() {
    return m_compressor.getCurrent();
}

public boolean getPressureSwitchValue(){
    return m_compressor.getPressureSwitchValue();
}

public double getPressure(){
    return m_compressor.getPressure();   
}

public Command setSolenoidCommand(boolean open) {
    return Commands.runOnce(()-> m_solenoid.set(open), this);
}

public void toggleSolenoid() {
    m_solenoid.toggle();
}



}