# MOTORPH-PAYROLL-SYSTEM-GROUP10-COMPUTER-PROGRAMMING-1
Phase 1: Employee payroll system with automated salary calculations including government mandated deductions. Java based console application for weekly payroll processing.



# MotorPH Payroll System - Phase 1
A Java based payroll system that automates employee salary calculations with government mandated deductions.

## Project Overview
This is Phase 1 of the MotorPH Payroll System, designed to manage employee details and automatically calculate weekly salaries with Philippine government mandated deductions (SSS, PhilHealth, Pag-IBIG, and Withholding Tax).

## Features

- **Employee Management**: Display and manage employee information
- **Attendance Tracking**: Record login/logout times with 10-minute grace period
- **Hours Calculation**: Automatic calculation of weekly hours worked
- **Gross Salary**: Compute weekly salary based on hours and hourly rate
- **Deductions**: Calculate SSS, PhilHealth, Pag-IBIG, and withholding tax
- **Net Salary**: Final take-home pay after all deductions

## Quick Start

### Prerequisites
- Java JDK 8 or higher

### Running the Program

1. Clone this repository:
```bash
git clone https://github.com/SmokedRhombus/MotorPH-Payroll-System.git
cd MotorPH-Payroll-System
```

2. Compile and run:
```bash
javac *.java
java MotorPHPayrollSystem
```

Or simply run directly:
```bash
java MotorPHPayrollSystem.java
```

## Project Structure
```
MotorPH-Payroll-System/
├── MotorPHPayrollSystem.java    # Main application
├── Employee.java                 # Employee data model
├── AttendanceRecord.java         # Attendance tracking
├── PayrollSummary.java          # Payroll results
├── DeductionCalculator.java     # Government deductions
├── PayrollService.java          # Payroll calculations
├── DataLoader.java              # Sample data
└── PayrollDisplay.java          # Output formatting
```

## Technical Details

### Calculation Methods

**Hours Worked:**
- Standard work hours: 8 hours/day
- Grace period: 10 minutes (8:00 AM - 8:10 AM)
- Automatic lunch break deduction: 1 hour
- Late deduction applied after grace period

**Deductions:**
- **SSS**: Based on monthly salary bracket table
- **PhilHealth**: 3% premium rate, 50% employee share
- **Pag-IBIG**: 1-2% based on salary, capped at ₱100/month
- **Withholding Tax**: BIR tax table based on taxable income

All deductions are calculated monthly then converted to weekly (÷ 4).

## License

This project is licensed under the MIT License.

## Group Members
- Miguel Dominic Roa
- Kreskin Bejoc
- Carlos Louis Acula
- April Joyce Abejo

---

**Project Status:** Phase 1 Complete ✅
