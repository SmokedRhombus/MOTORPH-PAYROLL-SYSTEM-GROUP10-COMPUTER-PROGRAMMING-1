/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/

import java.time.LocalDate;
import java.util.List;

public class PayrollService {

    public static double calculateWeeklyHours(List<AttendanceRecord> weeklyAttendance) {
        double totalHours = 0.0;
        
        for (AttendanceRecord record : weeklyAttendance) {
            totalHours += record.getHoursWorked();
        }
        
        return totalHours;
    }

    public static double calculateGrossWeeklySalary(double hoursWorked, double hourlyRate) {
        return hoursWorked * hourlyRate;
    }

    public static PayrollSummary calculatePayroll(Employee employee, 
                                                   List<AttendanceRecord> weeklyAttendance,
                                                   LocalDate weekStartDate, 
                                                   LocalDate weekEndDate) {
        PayrollSummary payroll = new PayrollSummary(employee, weekStartDate, weekEndDate);
        
        double hoursWorked = calculateWeeklyHours(weeklyAttendance);
        payroll.setHoursWorked(hoursWorked);
        
        double hourlyRate = employee.getHourlyRate();
        payroll.setHourlyRate(hourlyRate);
        
        double grossWeeklySalary = calculateGrossWeeklySalary(hoursWorked, hourlyRate);
        payroll.setGrossWeeklySalary(grossWeeklySalary);
        
        double monthlySalary = grossWeeklySalary * 4;
        
        double monthlySss = DeductionCalculator.calculateSSSContribution(monthlySalary);
        double monthlyPhilhealth = DeductionCalculator.calculatePhilHealthContribution(monthlySalary);
        double monthlyPagibig = DeductionCalculator.calculatePagIbigContribution(monthlySalary);
        
        double monthlyTaxableIncome = monthlySalary - monthlySss - monthlyPhilhealth - monthlyPagibig;
        double monthlyTax = DeductionCalculator.calculateWithholdingTax(monthlyTaxableIncome);
        
        double weeklySss = DeductionCalculator.monthlyToWeekly(monthlySss);
        double weeklyPhilhealth = DeductionCalculator.monthlyToWeekly(monthlyPhilhealth);
        double weeklyPagibig = DeductionCalculator.monthlyToWeekly(monthlyPagibig);
        double weeklyTax = DeductionCalculator.monthlyToWeekly(monthlyTax);
        
        payroll.setSssContribution(weeklySss);
        payroll.setPhilhealthContribution(weeklyPhilhealth);
        payroll.setPagibigContribution(weeklyPagibig);
        payroll.setWithholdingTax(weeklyTax);
        
        payroll.calculateTotalDeductions();
        payroll.calculateNetSalary();
        
        return payroll;
    }
}