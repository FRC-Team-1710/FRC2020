/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static CANSparkMax climber;
  public static XboxController driverController;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static XboxController DriveStick, MechStick;
  public static LidarLitePWM DistanceLidar;
  public static AHRS Navx;
  public static DigitalInput LidarPWMSlot;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    AHRS Navx = new AHRS();
    Drive.Driveinit();
    LEDs.initLEDs();
    DriveStick = new XboxController(0);
    MechStick = new XboxController(1);
    LidarPWMSlot = new DigitalInput(0);
    DistanceLidar = new LidarLitePWM(LidarPWMSlot);
    LEDs.setIncramentBall();
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double xAxisDemand = DriveStick.getX(Hand.kRight);
    double yAxisDemand = DriveStick.getY(Hand.kLeft) * -1;
    boolean xAxsisButton = DriveStick.getStickButton(Hand.kRight);
    SmartDashboard.putBoolean("is shifting?", xAxsisButton);
    Drive.arcadeDrive(yAxisDemand, xAxisDemand * .5, xAxsisButton);
    SmartDashboard.putNumber("lidar distance in inches",DistanceLidar.getDistance()/2.54); //the /2.54 is the conversion factor for cm to inches
    String gameData; 
      gameData = DriverStation.getInstance().getGameSpecificMessage();
      if(gameData.length()>0){
        switch (gameData.charAt(0)){
          case 'B':
          break;
          case 'G':
          break;
          case 'R':
          break;
          case 'Y':
          break;
          default:
          break;
        }
      }
      Limelight.limelight_periodic();
      Shooter.ShooterCalc();
      Shooter.SmartDashboard();
    if(driverController.getBumperPressed(Hand.kRight)) {
      Shooter.rpmShooter += 100;//might need to increase for faster adjustments
    }else if(driverController.getBumperPressed(Hand.kLeft)){
      Shooter.rpmShooter -= 100;//might need to decrease for faster adjustments
    }else if(driverController.getBButtonPressed()){//change to different button
      Shooter.rpmShooter = 0;
    }else if(driverController.getStartButton()){
      Shooter.rpmShooter = Shooter.normalState;
    }

    if(driverController.getTriggerAxis(Hand.kLeft) != 0){
      Intake.intake.set(ControlMode.PercentOutput, driverController.getTriggerAxis(Hand.kLeft));
      Intake.indexer_bot.follow(Intake.intake);
      Intake.indexer_top.follow(Intake.intake);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
