/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/

import java.util.List;


public class PayrollDisplay {
    
    private static final String LINE = "=".repeat(80);
    private static final String DASH = "-".repeat(80);
    

    public static void displayEmployeeInfo(Employee employee) {
        System.out.println(LINE);
        System.out.println("EMPLOYEE INFORMATION");
        System.out.println(LINE);
        System.out.printf("Employee Number: %d%n", employee.getEmployeeNumber());
        System.out.printf("Employee Name: %s %s%n", employee.getFirstName(), employee.getLastName());
        System.out.printf("Birthday: %s%n", employee.getBirthdayFormatted());
        System.out.printf("Position: %s%n", employee.getPosition());
        System.out.printf("Status: %s%n", employee.getStatus());
        System.out.println(LINE);
        System.out.println();
    }
    
    public static void displayDetailedEmployeeInfo(Employee employee) {
        System.out.println(LINE);
        System.out.println("DETAILED EMPLOYEE INFORMATION");
        System.out.println(LINE);
        System.out.printf("Employee Number: %d%n", employee.getEmployeeNumber());
        System.out.printf("Name: %s %s%n", employee.getFirstName(), employee.getLastName());
        System.out.printf("Birthday: %s%n", employee.getBirthdayFormatted());
        System.out.printf("Address: %s%n", employee.getAddress());
        System.out.printf("Phone Number: %s%n", employee.getPhoneNumber());
        System.out.println(DASH);
        System.out.printf("SSS Number: %s%n", employee.getSssNumber());
        System.out.printf("PhilHealth Number: %s%n", employee.getPhilhealthNumber());
        System.out.printf("TIN: %s%n", employee.getTinNumber());
        System.out.printf("Pag-IBIG Number: %s%n", employee.getPagibigNumber());
        System.out.println(DASH);
        System.out.printf("Position: %s%n", employee.getPosition());
        System.out.printf("Status: %s%n", employee.getStatus());
        System.out.println(DASH);
        System.out.printf("Basic Salary: ₱%.2f%n", employee.getBasicSalary());
        System.out.printf("Gross Semi-monthly Rate: ₱%.2f%n", employee.getGrossSemiMonthlyRate());
        System.out.printf("Hourly Rate: ₱%.2f%n", employee.getHourlyRate());
        System.out.println(LINE);
        System.out.println();
    }
    
    public static void displayAttendanceRecords(List<AttendanceRecord> records) {
        System.out.println(LINE);
        System.out.println("ATTENDANCE RECORDS");
        System.out.println(LINE);
        System.out.printf("%-12s %-12s %-10s %-10s %-10s %s%n", 
            "Date", "Login", "Logout", "Hours", "Late?", "Minutes Late");
        System.out.println(DASH);
        
        for (AttendanceRecord record : records) {
            System.out.printf("%-12s %-12s %-10s %-10.2f %-10s %d%n",
                record.getDate(),
                record.getLogIn(),
                record.getLogOut(),
                record.getHoursWorked(),
                record.isLate() ? "Yes" : "No",
                record.getMinutesLate());
        }
        System.out.println(LINE);
        System.out.println();
    }
    
    public static void displayHoursCalculation(List<AttendanceRecord> records, double totalHours) {
        System.out.println(LINE);
        System.out.println("HOURS WORKED CALCULATION");
        System.out.println(LINE);
        
        for (AttendanceRecord record : records) {
            System.out.printf("%s: %.2f hours%n", record.getDate(), record.getHoursWorked());
        }
        
        System.out.println(DASH);
        System.out.printf("Total Weekly Hours: %.2f hours%n", totalHours);
        System.out.println(LINE);
        System.out.println();
    }
    
    public static void displayGrossSalaryCalculation(double hoursWorked, double hourlyRate, 
                                                     double grossSalary) {
        System.out.println(LINE);
        System.out.println("GROSS WEEKLY SALARY CALCULATION");
        System.out.println(LINE);
        System.out.printf("Hours Worked: %.2f hours%n", hoursWorked);
        System.out.printf("Hourly Rate: ₱%.2f%n", hourlyRate);
        System.out.println(DASH);
        System.out.printf("Gross Weekly Salary: ₱%.2f%n", grossSalary);
        System.out.println(LINE);
        System.out.println();
    }  

    public static void displayNetSalaryCalculation(PayrollSummary payroll) {
        System.out.println(LINE);
        System.out.println("NET WEEKLY SALARY CALCULATION");
        System.out.println(LINE);
        System.out.printf("Gross Weekly Salary: ₱%.2f%n", payroll.getGrossWeeklySalary());
        System.out.println(DASH);
        System.out.println("Deductions:");
        System.out.printf("  SSS Contribution: ₱%.2f%n", payroll.getSssContribution());
        System.out.printf("  PhilHealth Contribution: ₱%.2f%n", payroll.getPhilhealthContribution());
        System.out.printf("  Pag-IBIG Contribution: ₱%.2f%n", payroll.getPagibigContribution());
        System.out.printf("  Withholding Tax: ₱%.2f%n", payroll.getWithholdingTax());
        System.out.println(DASH);
        System.out.printf("Total Deductions: ₱%.2f%n", payroll.getTotalDeductions());
        System.out.println(DASH);
        System.out.printf("Net Weekly Salary: ₱%.2f%n", payroll.getNetWeeklySalary());
        System.out.println(LINE);
        System.out.println();
    }
    
    public static void displayPayrollSummary(PayrollSummary payroll) {
        System.out.println(LINE);
        System.out.println("PAYROLL SUMMARY");
        System.out.println(LINE);
        System.out.printf("Employee: %s (Employee #%d)%n", 
            payroll.getEmployee().getFullName(), 
            payroll.getEmployee().getEmployeeNumber());
        System.out.printf("Period: %s to %s%n", 
            payroll.getWeekStartDate(), 
            payroll.getWeekEndDate());
        System.out.println(DASH);
        System.out.printf("Hours Worked: %.2f hours%n", payroll.getHoursWorked());
        System.out.printf("Hourly Rate: ₱%.2f%n", payroll.getHourlyRate());
        System.out.printf("Gross Weekly Salary: ₱%.2f%n", payroll.getGrossWeeklySalary());
        System.out.println(DASH);
        System.out.println("Deductions:");
        System.out.printf("  SSS: ₱%.2f%n", payroll.getSssContribution());
        System.out.printf("  PhilHealth: ₱%.2f%n", payroll.getPhilhealthContribution());
        System.out.printf("  Pag-IBIG: ₱%.2f%n", payroll.getPagibigContribution());
        System.out.printf("  Withholding Tax: ₱%.2f%n", payroll.getWithholdingTax());
        System.out.printf("Total Deductions: ₱%.2f%n", payroll.getTotalDeductions());
        System.out.println(DASH);
        System.out.printf("NET WEEKLY SALARY: ₱%.2f%n", payroll.getNetWeeklySalary());
        System.out.println(LINE);
        System.out.println();
    }
    
    public static void displayHeader() {
        System.out.println();
        System.out.println(LINE);
        System.out.println("    MOTORPH PAYROLL SYSTEM - PHASE 1");
        System.out.println("    Employee Details and Salary Calculation");
        System.out.println(LINE);
        System.out.println();
    }
}