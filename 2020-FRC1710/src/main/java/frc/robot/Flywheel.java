/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Add your docs here.
 */
public class Flywheel {
    public static CANSparkMax m_flyOne, m_flyTwo, m_feeder;
    public static CANEncoder e_flyOne, e_flyTwo, e_feeder;
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    public static MiniPID flywheelPID, feederPID;
    public static double flywheelSpeed, feederSpeed;
    public static void initShooter(){
        m_flyOne = new CANSparkMax(8, MotorType.kBrushless);
        m_flyTwo = new CANSparkMax(9, MotorType.kBrushless);
        m_feeder = new CANSparkMax(11, MotorType.kBrushless);
        m_flyOne.restoreFactoryDefaults();
        m_flyTwo.restoreFactoryDefaults();
        m_feeder.restoreFactoryDefaults();
        e_flyOne = m_flyOne.getEncoder();
        e_flyTwo = m_flyTwo.getEncoder();
        e_feeder = m_feeder.getEncoder();
        m_flyOne.setIdleMode(IdleMode.kCoast);
        m_flyTwo.setIdleMode(IdleMode.kCoast);
        m_feeder.setIdleMode(IdleMode.kCoast);
        kP = 5e-5; 
        kI = 7.5e-7;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        maxRPM = 5700;
        flywheelPID = new MiniPID(0,0,0); //values need to be changed
        feederPID = new MiniPID(0,0,0); //values need to be changed
    }
    public static void setShooterSpeeds(double flywheelRPM, double feederRPM, boolean On){
        flywheelSpeed = flywheelPID.getOutput(e_flyOne.getVelocity(), flywheelRPM);
        feederSpeed = feederPID.getOutput(e_feeder.getVelocity(), feederRPM);
        if(On = true){
            m_flyOne.set(flywheelSpeed);
            m_flyTwo.set(-flywheelSpeed);
            m_feeder.set(feederSpeed);
        } else {
            m_flyOne.set(0);
            m_flyTwo.set(0);
            m_feeder.set(0);
        }
    }
    public static double getFlySpeed(){
        return e_flyOne.getVelocity();
    }
    public static double getFeedSpeed(){
        return e_feeder.getVelocity();
    }
}
