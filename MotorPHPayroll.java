//Made by Group 10 (Miguel Dominic E. Roa)

import java.io.*;
import java.util.*;

public class MotorPHPayroll {

    // =====================================================================
    // COLUMN INDEX CONSTANTS — Employee CSV
    // Matches the column order exported from the "Employee Details" sheet:
    // 0  Employee #
    // 1  Last Name
    // 2  First Name
    // 3  Birthday
    // 4  Address
    // 5  Phone Number
    // 6  SSS #
    // 7  Philhealth #
    // 8  TIN #
    // 9  Pag-ibig #
    // 10 Status
    // 11 Position
    // 12 Immediate Supervisor
    // 13 Basic Salary
    // 14 Rice Subsidy
    // 15 Phone Allowance
    // 16 Clothing Allowance
    // 17 Gross Semi-monthly Rate
    // 18 Hourly Rate
    // =====================================================================
    static final int EMP_ID           = 0;
    static final int EMP_LAST_NAME    = 1;
    static final int EMP_FIRST_NAME   = 2;
    static final int EMP_BIRTHDAY     = 3;
    static final int EMP_ADDRESS      = 4;
    static final int EMP_PHONE        = 5;
    static final int EMP_SSS          = 6;
    static final int EMP_PHILHEALTH   = 7;
    static final int EMP_TIN          = 8;
    static final int EMP_PAGIBIG      = 9;
    static final int EMP_STATUS       = 10;
    static final int EMP_POSITION     = 11;
    static final int EMP_SUPERVISOR   = 12;
    static final int EMP_BASIC_SALARY = 13;
    static final int EMP_RICE         = 14;
    static final int EMP_PHONE_ALLOW  = 15;
    static final int EMP_CLOTHING     = 16;
    static final int EMP_SEMI_MONTHLY = 17;
    static final int EMP_HOURLY_RATE  = 18;

    // =====================================================================
    // COLUMN INDEX CONSTANTS — Attendance CSV
    // Matches the column order exported from the "Attendance Record" sheet:
    // 0  Employee #
    // 1  Last Name
    // 2  First Name
    // 3  Date       (format: M/D/YYYY)
    // 4  Log In     (format: H:MM or HH:MM)
    // 5  Log Out    (format: H:MM or HH:MM)
    // =====================================================================
    static final int ATT_EMP_ID    = 0;
    static final int ATT_LAST_NAME = 1;
    static final int ATT_FIRST_NAME= 2;
    static final int ATT_DATE      = 3;
    static final int ATT_LOGIN     = 4;
    static final int ATT_LOGOUT    = 5;

    // =====================================================================
    // MAIN MENU
    // =====================================================================

    public static void main(String[] args) throws Exception {
        // Load all employee records from the employee CSV file
        String[][] employees = loadEmployeeCsv("employees.csv");

        // Load all attendance records from the attendance CSV file
        String[][] attendance = loadAttendanceCsv("attendance.csv");

        Scanner sc = new Scanner(System.in);

        System.out.println("=======================================================");
        System.out.println("              MOTORPH PAYROLL SYSTEM                   ");
        System.out.println("=======================================================");

        // --- LOGIN ---
        // The system accepts two valid roles: "employee" and "payroll_staff"
        // Both roles share the same password: 12345
        System.out.println();
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        // Check if username is one of the two valid roles
        boolean validUsername = username.equals("employee") || username.equals("payroll_staff");
        // Check if password matches the shared password
        boolean validPassword = password.equals("12345");

        // If either credential is wrong, show error and terminate immediately
        if (!validUsername || !validPassword) {
            System.out.println("Incorrect username and/or password.");
            sc.close();
            return;
        }

        // Route to the correct menu based on which role logged in
        if (username.equals("employee")) {
            runEmployeeMenu(sc, employees);
        } else {
            // username must be "payroll_staff" at this point
            runPayrollStaffMenu(sc, employees, attendance);
        }

        sc.close();
    }

    // =====================================================================
    // EMPLOYEE MENU
    // Shown when the user logs in with the "employee" role.
    // Employees can only look up their own basic details (no payroll data).
    // =====================================================================

    static void runEmployeeMenu(Scanner sc, String[][] employees) {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("EMPLOYEE MENU");
            System.out.println("  [1] Enter your employee number");
            System.out.println("  [2] Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                // Ask for the employee number and look it up
                System.out.print("Enter Employee Number: ");
                String empId = sc.nextLine().trim();
                String[] emp = findEmployee(employees, empId);

                if (emp == null) {
                    // No employee found with that number
                    System.out.println("Employee number does not exist.");
                } else {
                    // Display basic employee info only — no salary data
                    System.out.println();
                    System.out.println("======================================");
                    System.out.println("          EMPLOYEE DETAILS");
                    System.out.println("======================================");
                    System.out.printf("  Employee #   : %s%n", emp[EMP_ID]);
                    System.out.printf("  Employee Name: %s %s%n", emp[EMP_FIRST_NAME], emp[EMP_LAST_NAME]);
                    System.out.printf("  Birthday     : %s%n", emp[EMP_BIRTHDAY]);
                    System.out.println("======================================");
                }
            } else if (choice.equals("2")) {
                System.out.println("Exiting. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }

    // =====================================================================
    // PAYROLL STAFF MENU
    // Shown when the user logs in with the "payroll_staff" role.
    // Payroll staff can process payroll for one or all employees.
    // =====================================================================

    static void runPayrollStaffMenu(Scanner sc, String[][] employees, String[][] attendance) {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("PAYROLL STAFF MENU");
            System.out.println("  [1] Process Payroll");
            System.out.println("  [2] Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                runProcessPayrollMenu(sc, employees, attendance);
            } else if (choice.equals("2")) {
                System.out.println("Exiting. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }

    // Sub-menu under Process Payroll — choose one employee or all employees
    static void runProcessPayrollMenu(Scanner sc, String[][] employees, String[][] attendance) {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("PROCESS PAYROLL");
            System.out.println("  [1] One Employee");
            System.out.println("  [2] All Employees");
            System.out.println("  [3] Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                processOneEmployee(sc, employees, attendance);
            } else if (choice.equals("2")) {
                processAllEmployees(employees, attendance);
            } else if (choice.equals("3")) {
                running = false;
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    // =====================================================================
    // PROCESS ONE EMPLOYEE
    // Prompts for an employee number and prints the full payroll report
    // for that employee covering all months from June to December 2024.
    // =====================================================================

    static void processOneEmployee(Scanner sc, String[][] employees, String[][] attendance) {
        System.out.print("Enter Employee Number: ");
        String empId = sc.nextLine().trim();
        String[] emp = findEmployee(employees, empId);

        if (emp == null) {
            System.out.println("Employee number does not exist.");
            return;
        }

        // Print the full payroll report for this employee
        printFullPayrollForEmployee(emp, attendance);
    }

    // =====================================================================
    // PROCESS ALL EMPLOYEES
    // Loops through every employee and prints the full payroll report
    // for each one.
    // =====================================================================

    static void processAllEmployees(String[][] employees, String[][] attendance) {
        for (String[] emp : employees) {
            printFullPayrollForEmployee(emp, attendance);
        }
    }

    // =====================================================================
    // PRINT FULL PAYROLL FOR ONE EMPLOYEE — ALL MONTHS (JUNE–DECEMBER 2024)
    //
    // For each month, payroll is split into two cutoff periods:
    //   1st Cutoff: Days 1–15  → shows Gross and Net only (no deductions yet)
    //   2nd Cutoff: Days 16–end → shows Gross, all deductions, and Net
    //
    // IMPORTANT: Government deductions (SSS, PhilHealth, Pag-IBIG, Tax) are
    // computed on the COMBINED monthly gross (1st + 2nd cutoff added together)
    // before applying any bracket or rate. This matches how Philippine payroll
    // law works — deductions are monthly, not per-cutoff.
    // =====================================================================

    static void printFullPayrollForEmployee(String[] emp, String[][] attendance) {
        // Read the year directly from this employee's attendance records — no hardcoding
        int year = getYearFromAttendance(attendance, emp[EMP_ID]);

        // All seven months covered by the system requirements
        int[]    months = {6, 7, 8, 9, 10, 11, 12};
        String[] mNames = {"June", "July", "August", "September", "October", "November", "December"};

        // Hourly rate is stored in the employee record (Basic Salary / 21 days / 8 hrs)
        double hourlyRate = Double.parseDouble(emp[EMP_HOURLY_RATE]);

        for (int m = 0; m < months.length; m++) {
            int    month     = months[m];
            String monthName = mNames[m];

            // Find the last day of this month (e.g., June = 30, July = 31)
            int lastDay = getLastDay(month, year);

            // --- 1ST CUTOFF: days 1 to 15 ---
            double hours1 = calculateHoursWorked(attendance, emp[EMP_ID], year, month, 1, 15);
            double gross1 = hours1 * hourlyRate;
            // No deductions on the 1st cutoff — net equals gross
            double netSalary1 = gross1;

            // --- 2ND CUTOFF: days 16 to last day of month ---
            double hours2 = calculateHoursWorked(attendance, emp[EMP_ID], year, month, 16, lastDay);
            double gross2 = hours2 * hourlyRate;

            // --- DEDUCTIONS: computed on combined monthly gross ---
            // Step 1: Add both cutoff grosses to get the full monthly gross
            double totalGross = gross1 + gross2;

            // Step 2: Compute each government deduction using the monthly gross
            double sss        = computeSSS(totalGross);
            double philhealth = computePhilhealth(totalGross);
            double pagibig    = computePagibig(totalGross);

            // Step 3: Taxable income = monthly gross minus the three mandatory contributions
            double taxable = totalGross - sss - philhealth - pagibig;

            // Step 4: Compute withholding tax using the progressive bracket table
            double tax = computeWithholdingTax(taxable);

            // Step 5: Sum all deductions
            double totalDeductions = sss + philhealth + pagibig + tax;

            // 2nd cutoff net = gross2 minus all deductions (deductions collected here)
            double netSalary2 = gross2 - totalDeductions;

            // --- PRINT OUTPUT ---
            // Values are printed using String concatenation with the raw double variable.
            // This preserves full decimal precision — no rounding is applied at any point.
            System.out.println();
            System.out.println("==========================================================");
            System.out.println("  PAYROLL — " + monthName + " " + year);
            System.out.println("==========================================================");
            System.out.println("  Employee #   : " + emp[EMP_ID]);
            System.out.println("  Employee Name: " + emp[EMP_FIRST_NAME] + " " + emp[EMP_LAST_NAME]);
            System.out.println("  Birthday     : " + emp[EMP_BIRTHDAY]);
            System.out.println("----------------------------------------------------------");

            // 1st Cutoff block — no deductions shown
            System.out.println("  Cutoff Date  : " + monthName + " 1 to " + monthName + " 15");
            System.out.println("  Total Hours Worked : " + hours1 + " hrs");
            System.out.println("  Gross Salary       : " + gross1);
            System.out.println("  Net Salary         : " + netSalary1);
            System.out.println("----------------------------------------------------------");

            // 2nd Cutoff block — all deductions itemized here
            System.out.println("  Cutoff Date  : " + monthName + " 16 to " + monthName + " " + lastDay
                    + "  (Second payout includes all deductions)");
            System.out.println("  Total Hours Worked : " + hours2 + " hrs");
            System.out.println("  Gross Salary       : " + gross2);
            System.out.println("  Each Deduction (based on combined monthly gross):");
            System.out.println("    SSS              : " + sss);
            System.out.println("    PhilHealth       : " + philhealth);
            System.out.println("    Pag-IBIG         : " + pagibig);
            System.out.println("    Tax              : " + tax);
            System.out.println("  Total Deductions   : " + totalDeductions);
            System.out.println("  Net Salary         : " + netSalary2);
            System.out.println("==========================================================");
        }
    }

    // =====================================================================
    // GET YEAR FROM ATTENDANCE
    // Reads the year directly from the first attendance record belonging
    // to the given employee. The date column uses M/D/YYYY format, so
    // parseDate() returns [year, month, day] and we take index 0.
    // Returns -1 if no matching record is found.
    // =====================================================================

    static int getYearFromAttendance(String[][] attendance, String empId) {
        for (String[] row : attendance) {
            if (!row[ATT_EMP_ID].equals(empId)) continue;
            int[] date = parseDate(row[ATT_DATE]);
            if (date != null) return date[0]; // date[0] = year
        }
        return -1;
    }

    // =====================================================================
    // LAST DAY OF MONTH
    // Returns the last calendar day of the given month and year.
    // Accounts for leap years when checking February.
    // =====================================================================

    static int getLastDay(int month, int year) {
        // February: 29 days in a leap year, 28 otherwise
        if (month == 2) {
            boolean isLeap = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
            return isLeap ? 29 : 28;
        }
        // April, June, September, November have 30 days
        if (month == 4 || month == 6 || month == 9 || month == 11) return 30;
        // All other months have 31 days
        return 31;
    }

    // =====================================================================
    // HOURS WORKED CALCULATION
    //
    // For each attendance row that belongs to the specified employee,
    // month, and day range, this method calculates how many hours the
    // employee actually worked, applying these rules:
    //
    //   Grace period : Login at or before 8:10 AM is treated as 8:00 AM
    //   End cap      : Only time up to 5:00 PM is counted (no overtime)
    //   Lunch break  : 1 hour is deducted if the employee worked more than
    //                  1 hour (i.e., worked > 60 minutes before lunch deduction)
    // =====================================================================

    static double calculateHoursWorked(String[][] attendance, String empId,
                                        int year, int month, int dayStart, int dayEnd) {
        double totalMinutes = 0;

        for (String[] row : attendance) {
            // Skip rows that don't belong to the target employee
            if (!row[ATT_EMP_ID].equals(empId)) continue;

            // Parse the date from the attendance record
            int[] date = parseDate(row[ATT_DATE]);
            if (date == null) continue;

            // Skip if the record is outside the target year or month
            if (date[0] != year || date[1] != month) continue;

            // Skip if the day is outside the cutoff range (e.g., 1–15 or 16–31)
            if (date[2] < dayStart || date[2] > dayEnd) continue;

            // Parse login and logout times
            int[] login  = parseTime(row[ATT_LOGIN]);
            int[] logout = parseTime(row[ATT_LOGOUT]);
            if (login == null || logout == null) continue;

            // Convert login and logout to total minutes since midnight
            int loginMin  = login[0]  * 60 + login[1];
            int logoutMin = logout[0] * 60 + logout[1];

            // Apply grace period: any login at or before 8:10 AM counts as exactly 8:00 AM
            // 8:00 AM = 480 min, 8:10 AM = 490 min
            int effectiveStart = (loginMin <= 490) ? 480 : loginMin;

            // Cap the end time at 5:00 PM (1020 minutes) — no overtime counted
            int effectiveEnd = Math.min(logoutMin, 1020);

            // Calculate total time worked in minutes for this day
            int worked = effectiveEnd - effectiveStart;

            if (worked > 0) {
                // Deduct 1 hour (60 min) for lunch break, but only if worked > 1 hour
                if (worked > 60) worked -= 60;
                totalMinutes += worked;
            }
        }

        // Convert total minutes to hours and return
        return totalMinutes / 60.0;
    }

    // =====================================================================
    // GOVERNMENT DEDUCTION COMPUTATIONS
    // All deductions below are based on the MONTHLY gross salary.
    // =====================================================================

    // SSS (Social Security System) contribution
    // Uses a bracket table: below ₱3,250 → ₱135 flat; ₱24,750+ → ₱1,125 max
    // Between those bounds: starts at ₱157.50 and rises ₱22.50 per ₱500 bracket
    static double computeSSS(double monthlySalary) {
        if (monthlySalary < 3250)  return 135.0;
        if (monthlySalary >= 24750) return 1125.0;
        // Determine which ₱500 bracket the salary falls into
        int bracket = (int) ((monthlySalary - 3250) / 500);
        return 157.5 + bracket * 22.5;
    }

    // PhilHealth contribution (Philippine Health Insurance Corporation)
    // Employee share = (monthly salary × 3%) / 2
    // Minimum: ₱150 (for salaries ≤ ₱10,000)
    // Maximum: ₱900 (for salaries ≥ ₱60,000)
    static double computePhilhealth(double monthlySalary) {
        if (monthlySalary <= 10000) return 150.0;
        if (monthlySalary >= 60000) return 900.0;
        return (monthlySalary * 0.03) / 2.0;
    }

    // Pag-IBIG (HDMF) contribution
    // 1% of salary if salary ≤ ₱1,500; 2% otherwise
    // Contribution is capped at ₱100 maximum
    static double computePagibig(double monthlySalary) {
        double rate = (monthlySalary <= 1500) ? 0.01 : 0.02;
        double contribution = monthlySalary * rate;
        // Cap at ₱100
        return (contribution > 100) ? 100.0 : contribution;
    }

    // Withholding Tax — progressive bracket table (monthly taxable income)
    // Taxable income = Monthly Gross − SSS − PhilHealth − Pag-IBIG
    // Brackets (monthly):
    //   ≤ ₱20,832              → ₱0
    //   ₱20,833 – ₱33,332     → 20% of excess over ₱20,833
    //   ₱33,333 – ₱66,666     → ₱2,500 + 25% of excess over ₱33,333
    //   ₱66,667 – ₱166,666    → ₱10,833 + 30% of excess over ₱66,667
    //   ₱166,667 – ₱666,666   → ₱40,833.33 + 32% of excess over ₱166,667
    //   > ₱666,667             → ₱200,833.33 + 35% of excess over ₱666,667
    static double computeWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832) {
            return 0;
        } else if (taxableIncome <= 33332) {
            return (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 66666) {
            return 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166666) {
            return 10833 + (taxableIncome - 66667) * 0.30;
        } else if (taxableIncome <= 666666) {
            return 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            return 200833.33 + (taxableIncome - 666667) * 0.35;
        }
    }

    // =====================================================================
    // DATA LOADING — EMPLOYEE CSV
    //
    // Reads employee records from a CSV file exported from the
    // "Employee Details" sheet in Copy_of_MotorPH_Employee_Data.xlsx.
    //
    // Expected CSV column order (header row is skipped automatically):
    //   Employee #, Last Name, First Name, Birthday, Address, Phone Number,
    //   SSS #, Philhealth #, TIN #, Pag-ibig #, Status, Position,
    //   Immediate Supervisor, Basic Salary, Rice Subsidy, Phone Allowance,
    //   Clothing Allowance, Gross Semi-monthly Rate, Hourly Rate
    // =====================================================================

    static String[][] loadEmployeeCsv(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println();
            System.out.println("ERROR: '" + filename + "' was not found in the current directory.");
            System.out.println();
            System.out.println("To generate employees.csv:");
            System.out.println("  1. Open  Copy_of_MotorPH_Employee_Data.xlsx");
            System.out.println("  2. Click the  'Employee Details'  sheet tab");
            System.out.println("  3. File > Save As > CSV (Comma delimited) (.csv)");
            System.out.println("  4. Save the file as  employees.csv");
            System.out.println("  5. Move employees.csv to the same folder as MotorPHPayroll.java");
            System.out.println();
            System.exit(1);
        }

        List<String[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        boolean skipHeader = true;

        while ((line = br.readLine()) != null) {
            // Skip the first line (column headers)
            if (skipHeader) { skipHeader = false; continue; }

            String[] cols = parseCsvLine(line);

            // Only add rows that have at least 19 columns and a non-empty employee ID
            if (cols.length >= 19 && !cols[EMP_ID].trim().isEmpty()) {
                for (int i = 0; i < cols.length; i++) {
                    // Strip surrounding quotes and extra whitespace from each field
                    cols[i] = cols[i].trim().replaceAll("^\"|\"$", "").trim();
                }
                // Normalize employee ID: remove trailing ".0" if present (Excel float export)
                cols[EMP_ID] = cols[EMP_ID].replaceAll("\\.0*$", "");
                rows.add(cols);
            }
        }
        br.close();

        System.out.println("[INFO] Loaded " + rows.size() + " employee records from '" + filename + "'.");
        return rows.toArray(new String[0][]);
    }

    // =====================================================================
    // DATA LOADING — ATTENDANCE CSV
    //
    // Reads attendance records from a CSV file exported from the
    // "Attendance Record" sheet in Copy_of_MotorPH_Employee_Data.xlsx.
    //
    // Expected CSV column order (header row is skipped automatically):
    //   Employee #, Last Name, First Name, Date, Log In, Log Out
    // =====================================================================

    static String[][] loadAttendanceCsv(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println();
            System.out.println("ERROR: '" + filename + "' was not found in the current directory.");
            System.out.println();
            System.out.println("To generate attendance.csv:");
            System.out.println("  1. Open  Copy_of_MotorPH_Employee_Data.xlsx");
            System.out.println("  2. Click the  'Attendance Record'  sheet tab");
            System.out.println("  3. File > Save As > CSV (Comma delimited) (.csv)");
            System.out.println("  4. Save the file as  attendance.csv");
            System.out.println("  5. Move attendance.csv to the same folder as MotorPHPayroll.java");
            System.out.println();
            System.exit(1);
        }

        List<String[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        boolean skipHeader = true;

        while ((line = br.readLine()) != null) {
            // Skip the first line (column headers)
            if (skipHeader) { skipHeader = false; continue; }

            String[] cols = parseCsvLine(line);

            // Only add rows that have at least 6 columns and a non-empty employee ID
            if (cols.length >= 6 && !cols[ATT_EMP_ID].trim().isEmpty()) {
                for (int i = 0; i < cols.length; i++) {
                    // Strip surrounding quotes and extra whitespace from each field
                    cols[i] = cols[i].trim().replaceAll("^\"|\"$", "").trim();
                }
                rows.add(cols);
            }
        }
        br.close();

        System.out.println("[INFO] Loaded " + rows.size() + " attendance records from '" + filename + "'.");
        return rows.toArray(new String[0][]);
    }

    // =====================================================================
    // CSV LINE PARSER
    // Splits one CSV line into an array of fields, correctly handling fields
    // that are wrapped in double quotes (which may contain commas inside them).
    // =====================================================================

    static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                // Toggle whether we are currently inside a quoted field
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                // Comma outside quotes = field separator; save the current field
                fields.add(sb.toString());
                sb.setLength(0);
            } else {
                // Regular character — append to current field
                sb.append(c);
            }
        }

        // Add the last field (no trailing comma after it)
        fields.add(sb.toString());
        return fields.toArray(new String[0]);
    }

    // =====================================================================
    // UTILITY / HELPER METHODS
    // =====================================================================

    // Find an employee in the employee array by their employee number.
    // Returns the matching row array, or null if not found.
    static String[] findEmployee(String[][] employees, String empId) {
        // Normalize the search ID: trim whitespace and remove trailing ".0" (Excel artifact)
        String id = empId.trim().replaceAll("\\.0$", "");
        for (String[] emp : employees) {
            if (emp[EMP_ID].trim().replaceAll("\\.0$", "").equals(id)) return emp;
        }
        return null;
    }

    // Parse a date string into an int array: [year, month, day]
    // Supports M/D/YYYY format (from CSV) and YYYY-MM-DD format (fallback)
    static int[] parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            // Take only the date part (ignore any time component after a space)
            String s = dateStr.trim().split(" ")[0];

            // Try M/D/YYYY format first (standard CSV export from Excel)
            String[] p = s.split("/");
            if (p.length == 3) {
                return new int[]{
                    Integer.parseInt(p[2].trim()), // year
                    Integer.parseInt(p[0].trim()), // month
                    Integer.parseInt(p[1].trim())  // day
                };
            }

            // Fallback: try YYYY-MM-DD format
            p = s.split("-");
            if (p.length == 3) {
                return new int[]{
                    Integer.parseInt(p[0].trim()), // year
                    Integer.parseInt(p[1].trim()), // month
                    Integer.parseInt(p[2].trim())  // day
                };
            }
        } catch (Exception ignored) {}
        return null;
    }

    // Parse a time string "H:MM" or "HH:MM" into an int array: [hour, minute]
    static int[] parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        try {
            String[] p = timeStr.trim().split(":");
            if (p.length >= 2) {
                return new int[]{
                    Integer.parseInt(p[0].trim()), // hour
                    Integer.parseInt(p[1].trim())  // minute
                };
            }
        } catch (Exception ignored) {}
        return null;
    }

    // Return the full name of a month number (1 = January, 12 = December)
    static String getMonthName(int month) {
        String[] names = {
            "", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return (month >= 1 && month <= 12) ? names[month] : "Unknown";
    }
}
