/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class MotorPHPayrollSystem {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        DataLoader.loadData();
        
        PayrollDisplay.displayHeader();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    displayEmployeeDetails();
                    break;
                case 2:
                    calculateHoursWorked();
                    break;
                case 3:
                    calculateGrossWage();
                    break;
                case 4:
                    calculateNetWage();
                    break;
                case 5:
                    displayCompletePayrollSummary();
                    break;
                case 6:
                    demonstrateAllFeatures();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using MotorPH Payroll System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    

    private static void displayMenu() {
        System.out.println("MAIN MENU");
        System.out.println("1. Display Employee Details");
        System.out.println("2. Calculate Hours Worked");
        System.out.println("3. Calculate Gross Weekly Salary");
        System.out.println("4. Calculate Net Weekly Salary");
        System.out.println("5. Display Complete Payroll Summary");
        System.out.println("6. Demonstrate All Features");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private static void displayEmployeeDetails() {
        System.out.print("\nEnter Employee Number: ");
        int empNum = scanner.nextInt();
        
        Employee employee = DataLoader.getEmployee(empNum);
        
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        PayrollDisplay.displayDetailedEmployeeInfo(employee);
    }
    
    private static void calculateHoursWorked() {
        System.out.print("\nEnter Employee Number: ");
        int empNum = scanner.nextInt();
        
        Employee employee = DataLoader.getEmployee(empNum);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        LocalDate weekStart = LocalDate.of(2024, 6, 3);
        LocalDate weekEnd = LocalDate.of(2024, 6, 7);
        
        List<AttendanceRecord> weeklyAttendance = 
            DataLoader.getAttendanceRecords(empNum, weekStart, weekEnd);
        
        if (weeklyAttendance.isEmpty()) {
            System.out.println("No attendance records found!");
            return;
        }
        
        PayrollDisplay.displayEmployeeInfo(employee);
        PayrollDisplay.displayAttendanceRecords(weeklyAttendance);
        
        double totalHours = PayrollService.calculateWeeklyHours(weeklyAttendance);
        PayrollDisplay.displayHoursCalculation(weeklyAttendance, totalHours);
    }
    
    private static void calculateGrossWage() {
        System.out.print("\nEnter Employee Number: ");
        int empNum = scanner.nextInt();
        
        Employee employee = DataLoader.getEmployee(empNum);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        LocalDate weekStart = LocalDate.of(2024, 6, 3);
        LocalDate weekEnd = LocalDate.of(2024, 6, 7);
        
        List<AttendanceRecord> weeklyAttendance = 
            DataLoader.getAttendanceRecords(empNum, weekStart, weekEnd);
        
        if (weeklyAttendance.isEmpty()) {
            System.out.println("No attendance records found!");
            return;
        }
        
        PayrollDisplay.displayEmployeeInfo(employee);
        
        double totalHours = PayrollService.calculateWeeklyHours(weeklyAttendance);
        double hourlyRate = employee.getHourlyRate();
        double grossSalary = PayrollService.calculateGrossWeeklySalary(totalHours, hourlyRate);
        
        PayrollDisplay.displayGrossSalaryCalculation(totalHours, hourlyRate, grossSalary);
    }
    
    private static void calculateNetWage() {
        System.out.print("\nEnter Employee Number: ");
        int empNum = scanner.nextInt();
        
        Employee employee = DataLoader.getEmployee(empNum);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        LocalDate weekStart = LocalDate.of(2024, 6, 3);
        LocalDate weekEnd = LocalDate.of(2024, 6, 7);
        
        List<AttendanceRecord> weeklyAttendance = 
            DataLoader.getAttendanceRecords(empNum, weekStart, weekEnd);
        
        if (weeklyAttendance.isEmpty()) {
            System.out.println("No attendance records found!");
            return;
        }
        
        PayrollDisplay.displayEmployeeInfo(employee);
        
        PayrollSummary payroll = PayrollService.calculatePayroll(
            employee, weeklyAttendance, weekStart, weekEnd);
        
        PayrollDisplay.displayNetSalaryCalculation(payroll);
    }
    
    private static void displayCompletePayrollSummary() {
        System.out.print("\nEnter Employee Number: ");
        int empNum = scanner.nextInt();
        
        Employee employee = DataLoader.getEmployee(empNum);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        LocalDate weekStart = LocalDate.of(2024, 6, 3);
        LocalDate weekEnd = LocalDate.of(2024, 6, 7);
        
        List<AttendanceRecord> weeklyAttendance = 
            DataLoader.getAttendanceRecords(empNum, weekStart, weekEnd);
        
        if (weeklyAttendance.isEmpty()) {
            System.out.println("No attendance records found!");
            return;
        }
        
        PayrollSummary payroll = PayrollService.calculatePayroll(
            employee, weeklyAttendance, weekStart, weekEnd);
        
        PayrollDisplay.displayPayrollSummary(payroll);
    }
    

    private static void demonstrateAllFeatures() {
        System.out.println("\n=== DEMONSTRATING ALL PHASE 1 FEATURES ===\n");
        
        int[] employeeNumbers = {10001, 10002, 10003};
        LocalDate weekStart = LocalDate.of(2024, 6, 3);
        LocalDate weekEnd = LocalDate.of(2024, 6, 7);
        
        for (int empNum : employeeNumbers) {
            Employee employee = DataLoader.getEmployee(empNum);
            if (employee == null) continue;
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("PROCESSING EMPLOYEE: " + employee.getFullName());
            System.out.println("=".repeat(80));
            
            PayrollDisplay.displayEmployeeInfo(employee);
            
            List<AttendanceRecord> weeklyAttendance = 
                DataLoader.getAttendanceRecords(empNum, weekStart, weekEnd);
            
            if (!weeklyAttendance.isEmpty()) {
                PayrollDisplay.displayAttendanceRecords(weeklyAttendance);
                double totalHours = PayrollService.calculateWeeklyHours(weeklyAttendance);
                PayrollDisplay.displayHoursCalculation(weeklyAttendance, totalHours);
                
                double grossSalary = PayrollService.calculateGrossWeeklySalary(
                    totalHours, employee.getHourlyRate());
                PayrollDisplay.displayGrossSalaryCalculation(
                    totalHours, employee.getHourlyRate(), grossSalary);
                
                PayrollSummary payroll = PayrollService.calculatePayroll(
                    employee, weeklyAttendance, weekStart, weekEnd);
                PayrollDisplay.displayNetSalaryCalculation(payroll);
                
                PayrollDisplay.displayPayrollSummary(payroll);
            }
        }
        
        System.out.println("\n=== DEMONSTRATION COMPLETE ===\n");
    }
}