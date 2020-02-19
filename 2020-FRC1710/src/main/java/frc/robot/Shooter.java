/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class Shooter {
        public static double limelightD = (98.25 - 36) / Math.tan(Limelight.yShooter);
        public static double lidarD = (Robot.DistanceLidar.getDistance() / 2.54); //cm to in
        public static double difference = Math.abs(lidarD - limelightD);
        public static double normalState = 2293.055449; //make constant rpm for match
        public static double rpmShooter = normalState; 
        public static double rpmFeeder = 1/2 * rpmShooter;
        public static double pi = Math.PI;
        public static double v;

    public static void SmartDashboard(){
        SmartDashboard.putNumber("LimelightDistance", limelightD);
        SmartDashboard.putNumber("LidarDistance", lidarD);
        SmartDashboard.putNumber("Difference", difference);
    }
    public static void ShooterCalc(){
        if (/*(Limelight.validShooter == 1 && difference <= 20 && Robot.driverController.getAButton()) ||*/ Robot.driverController.getAButton()){
        //make this condition just the A button?
        //figuring out what rpm for flywheel
            if(lidarD <= 20){
                v = 223;
                rpmShooter = v * 60 / (pi * 4); //c of wheel (in)
                //short distance hood
                //Drive.Shooter1.set(Value.kReverse);
                //Drive.Shooter1.set(Value.kReverse);
            } else if (lidarD >= 134.69 && lidarD <= 194.69) {
                v = 0.0383 * lidarD * lidarD - 14.291 * lidarD + 1773.8;
                rpmShooter = v * 60 / (pi * 4); //c of wheel (in)
                //long distance hood
                //Drive.Shooter1.set(Value.kForward);
                //Drive.Shooter1.set(Value.kForward);
            } else if (lidarD >= 204.69 && lidarD <= 514.69){
                v = 0.0004 * lidarD * lidarD + 0.0641 * lidarD + 404.04;
                rpmShooter = v * 60 / (pi * 4); //c of wheel (in)
                //long distance hood
                //Drive.Shooter1.set(Value.kForward);
                //Drive.Shooter1.set(Value.kForward);
            } 
        } else {
            rpmShooter = normalState;
        }
        if(rpmShooter > 5700){
            rpmShooter = 5700;
        }
            Flywheel.setShooterSpeeds(rpmShooter, rpmFeeder, true);
            SmartDashboard.putNumber("FeederRPM", rpmFeeder);
            SmartDashboard.putNumber("ShooterRPM", rpmShooter);
    }
}
