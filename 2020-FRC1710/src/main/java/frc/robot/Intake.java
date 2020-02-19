/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class Intake {
    public static TalonSRX intake, indexer_bot, indexer_top;
    public static void Intake(){
        TalonSRX intake = new TalonSRX(10);
        TalonSRX indexer_bot = new TalonSRX(10);
        TalonSRX indexer_top = new TalonSRX(10);
    }
}
