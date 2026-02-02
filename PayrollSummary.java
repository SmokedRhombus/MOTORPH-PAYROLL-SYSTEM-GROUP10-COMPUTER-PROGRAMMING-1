/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/

import java.time.LocalDate;


public class PayrollSummary {
    private Employee employee;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private double hoursWorked;
    private double hourlyRate;
    private double grossWeeklySalary;
    
    private double sssContribution;
    private double philhealthContribution;
    private double pagibigContribution;
    private double withholdingTax;
    private double totalDeductions;
    
    private double netWeeklySalary;

    public PayrollSummary() {
    }

    public PayrollSummary(Employee employee, LocalDate weekStartDate, LocalDate weekEndDate) {
        this.employee = employee;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(LocalDate weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public LocalDate getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(LocalDate weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getGrossWeeklySalary() {
        return grossWeeklySalary;
    }

    public void setGrossWeeklySalary(double grossWeeklySalary) {
        this.grossWeeklySalary = grossWeeklySalary;
    }

    public double getSssContribution() {
        return sssContribution;
    }

    public void setSssContribution(double sssContribution) {
        this.sssContribution = sssContribution;
    }

    public double getPhilhealthContribution() {
        return philhealthContribution;
    }

    public void setPhilhealthContribution(double philhealthContribution) {
        this.philhealthContribution = philhealthContribution;
    }

    public double getPagibigContribution() {
        return pagibigContribution;
    }

    public void setPagibigContribution(double pagibigContribution) {
        this.pagibigContribution = pagibigContribution;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getNetWeeklySalary() {
        return netWeeklySalary;
    }

    public void setNetWeeklySalary(double netWeeklySalary) {
        this.netWeeklySalary = netWeeklySalary;
    }

    public void calculateTotalDeductions() {
        this.totalDeductions = sssContribution + philhealthContribution + 
                              pagibigContribution + withholdingTax;
    }

    public void calculateNetSalary() {
        this.netWeeklySalary = grossWeeklySalary - totalDeductions;
    }

    @Override
    public String toString() {
        return String.format(
            "Payroll Summary for %s (Employee #%d)\n" +
            "Period: %s to %s\n" +
            "Hours Worked: %.2f\n" +
            "Hourly Rate: ₱%.2f\n" +
            "Gross Weekly Salary: ₱%.2f\n" +
            "Deductions:\n" +
            "  SSS: ₱%.2f\n" +
            "  PhilHealth: ₱%.2f\n" +
            "  Pag-IBIG: ₱%.2f\n" +
            "  Withholding Tax: ₱%.2f\n" +
            "Total Deductions: ₱%.2f\n" +
            "Net Weekly Salary: ₱%.2f",
            employee.getFullName(), employee.getEmployeeNumber(),
            weekStartDate, weekEndDate,
            hoursWorked, hourlyRate, grossWeeklySalary,
            sssContribution, philhealthContribution, pagibigContribution, withholdingTax,
            totalDeductions, netWeeklySalary
        );
    }
}