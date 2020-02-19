/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
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

/**
 * Add your docs here.
 */

public class Limelight {

    //shooting anywhere on field
    public static double yShooter, validShooter;
    public NetworkTable shooterTable, intakeTable;
    public static DigitalInput in;
    public static LidarLitePWM lidar;
    public static void initShooter() {
        //flywheel.initialize();
        in = new DigitalInput(0);
        lidar = new LidarLitePWM(in);
    }

    public static void limelight_periodic() {
        //shooting
        NetworkTable shooterTable = NetworkTableInstance.getDefault().getTable("limelight-shooter");
        NetworkTableEntry txShooter = shooterTable.getEntry("tx");
        NetworkTableEntry tyShooter = shooterTable.getEntry("ty");
        NetworkTableEntry taShooter = shooterTable.getEntry("ta");
        NetworkTableEntry tvShooter = shooterTable.getEntry("tv");
      
        //read values periodically
        double xShooter = txShooter.getDouble(0.0);
        double yShooter = tyShooter.getDouble(0.0);
        double areaShooter = taShooter.getDouble(0.0);
        double validShooter = tvShooter.getDouble(0.0);
      
        //post to smart dashboard periodically
        SmartDashboard.putNumber("ShooterLimelightX", xShooter);
        SmartDashboard.putNumber("ShooterLimelightY", yShooter);
        SmartDashboard.putNumber("ShooterLimelightArea", areaShooter);
        SmartDashboard.putNumber("ShooterifTarget", validShooter);
        
        //driving to loading station
        NetworkTable intakeTable = NetworkTableInstance.getDefault().getTable("limelight-intake");
        NetworkTableEntry txIntake = intakeTable.getEntry("tx");
        NetworkTableEntry tyIntake = intakeTable.getEntry("ty");
        NetworkTableEntry taIntake = intakeTable.getEntry("ta");
        NetworkTableEntry tvIntake = intakeTable.getEntry("tv");
      
        //read values periodically
        double xIntake = txIntake.getDouble(0.0);
        double yIntake = tyIntake.getDouble(0.0);
        double areaIntake = taIntake.getDouble(0.0);
        double validIntake = tvIntake.getDouble(0.0);
      
        //post to smart dashboard periodically
        SmartDashboard.putNumber("IntakeLimelightX", xIntake);
        SmartDashboard.putNumber("IntakeLimelightY", yIntake);
        SmartDashboard.putNumber("IntakeLimelightArea", areaIntake);
        SmartDashboard.putNumber("IntakeifTarget", validIntake);
    }
}

