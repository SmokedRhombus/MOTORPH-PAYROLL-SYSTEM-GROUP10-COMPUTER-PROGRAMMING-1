/*Made by MO-IT101 Group 10 
Miguel Dominic E. Roa
Kreskin Bejoc
Carlos Louis Acula
April Joyce Abejo */

import java.io.*;
import java.util.*;
public class MotorPHPayroll {

    // ===================== MAIN MENU =====================
    public static void main(String[] args) throws Exception {
        String[][] employees = getEmployees();
        String[][] attendance = loadAttendanceCsv("attendance.csv");

        Scanner sc = new Scanner(System.in);

        System.out.println("=======================================================");
        System.out.println("              MOTORPH PAYROLL SYSTEM                   ");
        System.out.println("=======================================================");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("MAIN MENU");
            System.out.println("  [1] View Employee Information");
            System.out.println("  [2] View Monthly Payroll for an Employee");
            System.out.println("  [3] View All Employees Payroll Summary");
            System.out.println("  [4] Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                showEmployeeInfo(sc, employees);
            } else if (choice.equals("2")) {
                showEmployeePayroll(sc, employees, attendance);
            } else if (choice.equals("3")) {
                showAllPayroll(sc, employees, attendance);
            } else if (choice.equals("4")) {
                System.out.println("Exiting MotorPH Payroll System. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
            }
        }
        sc.close();
    }

    // ===================== MENU 1: VIEW EMPLOYEE INFO =====================

    static void showEmployeeInfo(Scanner sc, String[][] employees) {
        System.out.println();
        System.out.println("--- EMPLOYEE INFORMATION ---");
        System.out.println("Enter an Employee Number or type 'all' to list all employees.");
        System.out.print("Employee #: ");
        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("all")) {
            System.out.println();
            System.out.printf("%-8s  %-30s  %-12s  %14s%n", "EMP #", "FULL NAME", "BIRTHDAY", "HOURLY RATE");
            System.out.println("-".repeat(70));
            for (String[] emp : employees) {
                String fullName = emp[2] + " " + emp[1];
                double hourlyRate = Double.parseDouble(emp[18]);
                System.out.printf("%-8s  %-30s  %-12s  %14.4f%n",
                        emp[0], fullName, emp[3], hourlyRate);
            }
        } else {
            String[] emp = findEmployee(employees, input);
            if (emp == null) {
                System.out.println("Employee #" + input + " not found. Please check the number.");
                return;
            }
            System.out.println();
            System.out.println("======================================");
            System.out.println("          EMPLOYEE DETAILS");
            System.out.println("======================================");
            System.out.printf("  Employee #          : %s%n", emp[0]);
            System.out.printf("  Last Name           : %s%n", emp[1]);
            System.out.printf("  First Name          : %s%n", emp[2]);
            System.out.printf("  Birthday            : %s%n", emp[3]);
            System.out.printf("  Address             : %s%n", emp[4]);
            System.out.printf("  Phone Number        : %s%n", emp[5]);
            System.out.printf("  SSS #               : %s%n", emp[6]);
            System.out.printf("  Philhealth #        : %s%n", emp[7]);
            System.out.printf("  TIN #               : %s%n", emp[8]);
            System.out.printf("  Pag-ibig #          : %s%n", emp[9]);
            System.out.printf("  Status              : %s%n", emp[10]);
            System.out.printf("  Position            : %s%n", emp[11]);
            System.out.printf("  Immediate Supervisor: %s%n", emp[12]);
            System.out.printf("  Basic Salary        : %.4f%n", Double.parseDouble(emp[13]));
            System.out.printf("  Rice Subsidy        : %.4f%n", Double.parseDouble(emp[14]));
            System.out.printf("  Phone Allowance     : %.4f%n", Double.parseDouble(emp[15]));
            System.out.printf("  Clothing Allowance  : %.4f%n", Double.parseDouble(emp[16]));
            System.out.printf("  Gross Semi-monthly  : %.4f%n", Double.parseDouble(emp[17]));
            System.out.printf("  Hourly Rate         : %.4f%n", Double.parseDouble(emp[18]));
            System.out.println("======================================");
        }
    }

    // ===================== MENU 2: EMPLOYEE PAYROLL =====================

    static void showEmployeePayroll(Scanner sc, String[][] employees, String[][] attendance) {
        System.out.println();
        System.out.println("--- MONTHLY PAYROLL ---");
        System.out.print("Enter Employee Number: ");
        String empInput = sc.nextLine().trim();

        String[] emp = findEmployee(employees, empInput);
        if (emp == null) {
            System.out.println("Employee #" + empInput + " not found.");
            return;
        }

        int month = promptMonth(sc);
        if (month == -1) return;

        printPayrollSlip(emp, attendance, month, 2024, getMonthName(month));
    }

    static void printPayrollSlip(String[] emp, String[][] attendance,
                                  int month, int year, String monthName) {
        double hourlyRate = Double.parseDouble(emp[18]);

        // 1st cutoff: days 1-15, 2nd cutoff: days 16-end
        double hours1 = calculateHoursWorked(attendance, emp[0], year, month, 1, 15);
        double hours2 = calculateHoursWorked(attendance, emp[0], year, month, 16, 31);
        double totalHours = hours1 + hours2;

        double gross1     = hours1 * hourlyRate;
        double gross2     = hours2 * hourlyRate;
        double totalGross = gross1 + gross2;

        // Deductions on combined monthly gross
        double sss         = computeSSS(totalGross);
        double philhealth  = computePhilhealth(totalGross);
        double pagibig     = computePagibig(totalGross);
        double taxable     = totalGross - sss - philhealth - pagibig;
        double tax         = computeWithholdingTax(taxable);
        double totalDeductions = sss + philhealth + pagibig + tax;
        double netPay      = totalGross - totalDeductions;

        System.out.println();
        System.out.println("==========================================================");
        System.out.printf ("  PAYROLL SLIP  —  %s %d%n", monthName, year);
        System.out.println("==========================================================");
        System.out.printf("  Employee #          : %s%n", emp[0]);
        System.out.printf("  Name                : %s %s%n", emp[2], emp[1]);
        System.out.printf("  Position            : %s%n", emp[11]);
        System.out.printf("  Hourly Rate         : %.4f%n", hourlyRate);
        System.out.println("----------------------------------------------------------");
        System.out.printf("  1st Cutoff Hours  (Days  1–15) : %.4f hrs%n", hours1);
        System.out.printf("  2nd Cutoff Hours  (Days 16–31) : %.4f hrs%n", hours2);
        System.out.printf("  Total Hours Worked             : %.4f hrs%n", totalHours);
        System.out.println("----------------------------------------------------------");
        System.out.printf("  1st Cutoff Gross Pay           : %.4f%n", gross1);
        System.out.printf("  2nd Cutoff Gross Pay           : %.4f%n", gross2);
        System.out.printf("  TOTAL GROSS PAY                : %.4f%n", totalGross);
        System.out.println("----------------------------------------------------------");
        System.out.println("  DEDUCTIONS  (based on total monthly gross pay):");
        System.out.printf("    SSS Contribution             : %.4f%n", sss);
        System.out.printf("    Philhealth                   : %.4f%n", philhealth);
        System.out.printf("    Pag-ibig                     : %.4f%n", pagibig);
        System.out.printf("      Taxable Income             : %.4f%n", taxable);
        System.out.printf("    Withholding Tax              : %.4f%n", tax);
        System.out.printf("  TOTAL DEDUCTIONS               : %.4f%n", totalDeductions);
        System.out.println("----------------------------------------------------------");
        System.out.printf("  NET PAY                        : %.4f%n", netPay);
        System.out.println("==========================================================");
    }

    // ===================== MENU 3: ALL EMPLOYEES =====================

    static void showAllPayroll(Scanner sc, String[][] employees, String[][] attendance) {
        System.out.println();
        System.out.println("--- ALL EMPLOYEES PAYROLL SUMMARY ---");

        int month = promptMonth(sc);
        if (month == -1) return;

        String monthName = getMonthName(month);
        int year = 2024;

        System.out.println();
        System.out.println("=".repeat(120));
        System.out.printf("  PAYROLL SUMMARY — ALL EMPLOYEES — %s %d%n", monthName, year);
        System.out.println("=".repeat(120));
        System.out.printf("%-8s  %-30s  %10s  %14s  %10s  %10s  %10s  %12s  %14s%n",
                "EMP #", "NAME", "HOURS", "GROSS PAY",
                "SSS", "PHILHEALTH", "PAG-IBIG", "W/TAX", "NET PAY");
        System.out.println("-".repeat(120));

        for (String[] emp : employees) {
            double hourlyRate = Double.parseDouble(emp[18]);
            double hours1     = calculateHoursWorked(attendance, emp[0], year, month, 1, 15);
            double hours2     = calculateHoursWorked(attendance, emp[0], year, month, 16, 31);
            double totalHours = hours1 + hours2;
            double totalGross = totalHours * hourlyRate;

            double sss        = computeSSS(totalGross);
            double philhealth = computePhilhealth(totalGross);
            double pagibig    = computePagibig(totalGross);
            double taxable    = totalGross - sss - philhealth - pagibig;
            double tax        = computeWithholdingTax(taxable);
            double netPay     = totalGross - sss - philhealth - pagibig - tax;

            String name = emp[2] + " " + emp[1];
            if (name.length() > 30) name = name.substring(0, 30);

            System.out.printf("%-8s  %-30s  %10.2f  %14.2f  %10.2f  %10.2f  %10.2f  %12.2f  %14.2f%n",
                    emp[0], name, totalHours, totalGross,
                    sss, philhealth, pagibig, tax, netPay);
        }
        System.out.println("=".repeat(120));
    }

    // ===================== HOURS CALCULATION =====================

    static double calculateHoursWorked(String[][] attendance, String empId,
                                        int year, int month, int dayStart, int dayEnd) {
        double totalMinutes = 0;

        for (String[] row : attendance) {
            if (!row[0].equals(empId)) continue;

            int[] date = parseDate(row[3]);
            if (date == null) continue;
            if (date[0] != year || date[1] != month) continue;
            if (date[2] < dayStart || date[2] > dayEnd) continue;

            int[] login  = parseTime(row[4]);
            int[] logout = parseTime(row[5]);
            if (login == null || logout == null) continue;

            int loginMin  = login[0]  * 60 + login[1];
            int logoutMin = logout[0] * 60 + logout[1];

            // Grace period: login at or before 8:10 AM counts as 8:00 AM
            int effectiveStart = (loginMin <= 8 * 60 + 10) ? 8 * 60 : loginMin;

            // Cap end time at 5:00 PM
            int effectiveEnd = Math.min(logoutMin, 17 * 60);

            int worked = effectiveEnd - effectiveStart;
            if (worked > 0) {
                if (worked > 60) worked -= 60; // Deduct 1-hour lunch break
                totalMinutes += worked;
            }
        }

        return totalMinutes / 60.0;
    }

    // ===================== GOVERNMENT DEDUCTION COMPUTATIONS =====================

    static double computeSSS(double monthlySalary) {
        if (monthlySalary < 3250) return 135.0;
        if (monthlySalary >= 24750) return 1125.0;
        // Each bracket is 500 wide, starting at 3,250
        int bracket = (int) ((monthlySalary - 3250) / 500);
        return 157.5 + bracket * 22.5;
    }

    static double computePhilhealth(double monthlySalary) {
        if (monthlySalary <= 10000) return 150.0;
        if (monthlySalary >= 60000) return 900.0;
        return (monthlySalary * 0.03) / 2.0;
    }

    static double computePagibig(double monthlySalary) {
        double rate = (monthlySalary <= 1500) ? 0.01 : 0.02;
        double contribution = monthlySalary * rate;
        return (contribution > 100) ? 100.0 : contribution;
    }

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

    // ===================== DATA LOADING =====================

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
            if (skipHeader) { skipHeader = false; continue; }
            String[] cols = parseCsvLine(line);
            if (cols.length >= 6 && !cols[0].trim().isEmpty()) {
                for (int i = 0; i < cols.length; i++) {
                    cols[i] = cols[i].trim().replaceAll("^\"|\"$", "").trim();
                }
                rows.add(cols);
            }
        }
        br.close();

        System.out.println("[INFO] Loaded " + rows.size() + " attendance records from '" + filename + "'.");
        return rows.toArray(new String[0][]);
    }

    static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString());
        return fields.toArray(new String[0]);
    }

    // ===================== UTILITY / HELPER METHODS =====================

    static String[] findEmployee(String[][] employees, String empId) {
        String id = empId.trim().replaceAll("\\.0$", "");
        for (String[] emp : employees) {
            if (emp[0].trim().replaceAll("\\.0$", "").equals(id)) return emp;
        }
        return null;
    }

    static int[] parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            String s = dateStr.trim().split(" ")[0];
            String[] p = s.split("/");
            if (p.length == 3) {
                return new int[]{
                    Integer.parseInt(p[2].trim()),
                    Integer.parseInt(p[0].trim()),
                    Integer.parseInt(p[1].trim())
                };
            }
            // Fallback: YYYY-MM-DD
            p = s.split("-");
            if (p.length == 3) {
                return new int[]{
                    Integer.parseInt(p[0].trim()),
                    Integer.parseInt(p[1].trim()),
                    Integer.parseInt(p[2].trim())
                };
            }
        } catch (Exception ignored) {}
        return null;
    }

    static int[] parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        try {
            String[] p = timeStr.trim().split(":");
            if (p.length >= 2) {
                return new int[]{
                    Integer.parseInt(p[0].trim()),
                    Integer.parseInt(p[1].trim())
                };
            }
        } catch (Exception ignored) {}
        return null;
    }

    static int promptMonth(Scanner sc) {
        System.out.println();
        System.out.println("Select Month (June – December 2024):");
        System.out.println("  [1] June    [2] July     [3] August    [4] September");
        System.out.println("  [5] October [6] November [7] December");
        System.out.print("Enter choice (1-7): ");
        String s = sc.nextLine().trim();
        int[] map = {6, 7, 8, 9, 10, 11, 12};
        try {
            int idx = Integer.parseInt(s) - 1;
            if (idx >= 0 && idx < map.length) return map[idx];
        } catch (Exception ignored) {}
        System.out.println("Invalid month selection.");
        return -1;
    }

    static String getMonthName(int month) {
        String[] names = {
            "", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return (month >= 1 && month <= 12) ? names[month] : "Unknown";
    }

    // ===================== EMBEDDED EMPLOYEE DATA =====================

    static String[][] getEmployees() {
        return new String[][]{
            {"10001","Garcia","Manuel III","10/11/1983",
             "Valero Carpark Building Valero Street 1227, Makati City",
             "966-860-270","44-4506057-3","820126853951","442-605-657-000","691295330870",
             "Regular","Chief Executive Officer","N/A",
             "90000","1500","2000","1000","45000","535.714285714286"},

            {"10002","Lim","Antonio","06/19/1988",
             "San Antonio De Padua 2, Block 1 Lot 8 and 2, Dasmarinas, Cavite",
             "171-867-411","52-2061274-9","331735646338","683-102-776-000","663904995411",
             "Regular","Chief Operating Officer","Garcia, Manuel III",
             "60000","1500","2000","1000","30000","357.142857142857"},

            {"10003","Aquino","Bianca Sofia","08/04/1989",
             "Rm. 402 4/F Jiao Building Timog Avenue Cor. Quezon Avenue 1100, Quezon City",
             "966-889-370","30-8870406-2","177451189665","971-711-280-000","171519773969",
             "Regular","Chief Finance Officer","Garcia, Manuel III",
             "60000","1500","2000","1000","30000","357.142857142857"},

            {"10004","Reyes","Isabella","06/16/1994",
             "460 Solanda Street Intramuros 1000, Manila",
             "786-868-477","40-2511815-0","341911411254","876-809-437-000","416946776041",
             "Regular","Chief Marketing Officer","Garcia, Manuel III",
             "60000","1500","2000","1000","30000","357.142857142857"},

            {"10005","Hernandez","Eduard","09/23/1989",
             "National Highway, Gingoog, Misamis Occidental",
             "088-861-012","50-5577638-1","395830376622","321-448-955-000","952347222457",
             "Regular","IT Operations and Systems","Lim, Antonio",
             "52670","1500","1000","1000","26335","313.511904761905"},

            {"10006","Villanueva","Andrea Mae","02/14/1988",
             "Paradise Valley, Toro Hills, Lahug, Cebu City, Cebu",
             "478-355-684","49-1632020-8","577006200281","101-476-066-000","441093369646",
             "Regular","HR Manager","Lim, Antonio",
             "52670","1500","1000","1000","26335","313.511904761905"},

            {"10007","San Jose","Brad","03/15/1996",
             "98 Cerrini St Marisol, Carmona Cavite",
             "817-388-437","40-2400714-1","563169578400","702-643-185-000","210850209947",
             "Regular","HR Team Leader","Villanueva, Andrea Mae",
             "42975","1500","800","800","21487.5","255.803571428571"},

            {"10008","Romualdez","Alice","05/14/1992",
             "93-B Agap St. Guadalupe Viejo, Makati City",
             "915-245-578","55-4476527-2","905679783527","336-676-445-000","211385398985",
             "Regular","HR Rank and File","San Jose, Brad",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10009","Atienza","Rosie","09/24/1948",
             "420 Chabacano Road, Guadalupe Nuevo, Makati City",
             "707-981-798","41-0644692-3","900955622521","295-816-491-000","260907016889",
             "Regular","HR Rank and File","San Jose, Brad",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10010","Alvaro","Roderick","03/30/1988",
             "922 Marmol Street Guadalupe Viejo, Makati City",
             "642-709-893","64-7605054-4","631399526719","154-445-MCA-000","550459524612",
             "Regular","Accounting Head","Garcia, Manuel III",
             "52670","1500","1000","1000","26335","313.511904761905"},

            {"10011","Salcedo","Anthony","09/14/1993",
             "22 Jaboneros Street Quiapo, Manila",
             "210-835-851","26-9647608-3","223057882840","425-863-519-000","360028104289",
             "Regular","Payroll Manager","Alvaro, Roderick",
             "50825","1500","1000","1000","25412.5","302.529761904762"},

            {"10012","Lopez","Josie","01/14/1987",
             "Rm 202 San Francisco Condominium 3 San Francisco Del Monte Quezon City",
             "584-084-807","44-8563448-6","631837422295","118-560-896-000","117607687580",
             "Regular","Payroll Team Leader","Salcedo, Anthony",
             "38475","1500","800","800","19237.5","229.017857142857"},

            {"10013","Farala","Martha","01/11/1942",
             "1206 Cityland Condo 10 Tower 2 HV Dela Costa Makati City",
             "775-431-892","45-5656375-0","101205445886","222-047-448-000","168257483398",
             "Regular","Payroll Rank and File","Lopez, Josie",
             "24000","1500","500","500","12000","142.857142857143"},

            {"10014","Martinez","Leila","07/11/1970",
             "564 Poblacion, Makati City",
             "876-309-929","27-2090996-4","416322257110","631-559-498-000","113148678868",
             "Regular","Payroll Rank and File","Lopez, Josie",
             "24000","1500","500","500","12000","142.857142857143"},

            {"10015","Romualdez","Fredrick","03/10/1985",
             "10 Resthaven St. Kapitolyo, Pasig",
             "526-054-560","26-8768374-1","212443190509","719-774-055-000","583768294099",
             "Regular","Account Manager","Reyes, Isabella",
             "53500","1500","1000","1000","26750","318.452380952381"},

            {"10016","Mata","Christian","10/21/1987",
             "10 Magnolia Street, Quezon City",
             "070-337-844","49-2959312-6","515674866459","114-112-394-000","426671756908",
             "Regular","Account Team Leader","Romualdez, Fredrick",
             "42975","1500","800","800","21487.5","255.803571428571"},

            {"10017","De Leon","Selena","02/20/1975",
             "2014 Mapagmahal St. Quezon City",
             "975-432-099","27-2090996-4","210505007095","116-472-103-000","342390989037",
             "Regular","Account Team Leader","Romualdez, Fredrick",
             "41850","1500","800","800","20925","248.809523809524"},

            {"10018","San Jose","Allison","06/24/1986",
             "Rm. 402 Jiao Building Timog, Quezon City",
             "860-426-075","45-3251383-0","210505007095","115-210-717-000","277127684755",
             "Regular","Account Rank and File","Mata, Christian",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10019","Rosario","Cydney","10/06/1996",
             "420 Chabacano, Makati City",
             "740-721-558","49-1632020-8","207560482976","304-440-790-000","473490027319",
             "Regular","Account Rank and File","Mata, Christian",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10020","Bautista","Mark","02/12/1991",
             "City Heights, Antipolo City",
             "793-412-882","49-1795910-5","276477342802","598-202-860-000","404920708257",
             "Regular","Account Rank and File","De Leon, Selena",
             "23250","1500","500","500","11625","138.392857142857"},

            {"10021","Lazaro","Darlene","11/25/1985",
             "12 Calla Lily Street, Antipolo City",
             "052-375-484","45-5617168-2","107305033697","376-339-550-000","548411607695",
             "Regular","Account Rank and File","De Leon, Selena",
             "23250","1500","500","500","11625","138.392857142857"},

            {"10022","Delos Santos","Kolby","02/26/1980",
             "12 Calla Lily Street, Antipolo City",
             "642-323-232","52-0905289-1","113142027985","908-279-640-000","434306427517",
             "Regular","Account Rank and File","De Leon, Selena",
             "24000","1500","500","500","12000","142.857142857143"},

            {"10023","Santos","Vella","12/31/1983",
             "22 Jaboneros Street Quiapo, Manila",
             "990-153-134","52-9883524-3","115142028435","357-210-786-000","441426427519",
             "Regular","Account Rank and File","De Leon, Selena",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10024","Del Rosario","Tomas","12/18/1978",
             "1714 Kalayaan St. New Manila Quezon City",
             "882-191-606","45-5617168-2","117432023495","548-679-875-000","532097932760",
             "Regular","Account Rank and File","De Leon, Selena",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10025","Tolentino","Jacklyn","05/19/1984",
             "Block 7 Lot 39 San Miguel Village, Bicutan, Paranaque",
             "740-401-584","47-1692793-0","218456714986","953-954-070-000","422507777386",
             "Regular","Account Rank and File","De Leon, Selena",
             "24000","1500","500","500","12000","142.857142857143"},

            {"10026","Gutierrez","Percival","12/18/1970",
             "Block 4 Lot 17 Marigold, Camella Homes Molino, Bacoor, Cavite",
             "880-448-893","37-9147095-6","124231640348","183-348-910-000","153569185567",
             "Regular","Account Rank and File","De Leon, Selena",
             "24750","1500","500","500","12375","147.321428571429"},

            {"10027","Manalaysay","Garfield","08/28/1986",
             "Purok 3 Hda. Iscot Heights Bacoor, Cavite",
             "763-765-573","45-5617168-2","223057882840","436-586-829-000","222859505756",
             "Regular","Account Rank and File","De Leon, Selena",
             "24750","1500","500","500","12375","147.321428571429"},

            {"10028","Villegas","Lizeth","12/12/1981",
             "59 Marber Street, West Crame, San Juan, Metro Manila",
             "525-048-979","40-2400714-1","119127823494","565-906-240-000","286773638935",
             "Regular","Account Rank and File","De Leon, Selena",
             "24000","1500","500","500","12000","142.857142857143"},

            {"10029","Ramos","Carol","08/20/1978",
             "420 Chabacano Road Guadalupe Nuevo, Makati City",
             "707-981-798","60-1152206-4","437655652172","416-786-034-000","476389458568",
             "Regular","Account Rank and File","De Leon, Selena",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10030","Maceda","Emelia","04/14/1973",
             "59 Marber Street, West Crame, San Juan, Metro Manila",
             "955-271-450","54-1331005-0","199540985547","952-343-524-000","384207232573",
             "Regular","Account Rank and File","De Leon, Selena",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10031","Aguilar","Delia","01/27/1989",
             "Block 2 Lot 8 Sunnyside Heights, Munoz QC",
             "513-342-359","52-1859253-1","212285956922","707-086-055-000","234875312636",
             "Regular","Account Rank and File","De Leon, Selena",
             "22500","1500","500","500","11250","133.928571428571"},

            {"10032","Castro","John Rafael","02/09/1992",
             "11 Batangas Road, Batangas City",
             "797-488-070","26-7145133-4","524911132416","113-948-119-000","234104197612",
             "Regular","Sales & Marketing","Hernandez, Eduard",
             "52670","1500","1000","1000","26335","313.511904761905"},

            {"10033","Martinez","Carlos Ian","11/16/1990",
             "Rm. 401 Peping Condominium Escolta Manila",
             "990-153-984","26-8768649-4","399313568448","606-048-430-000","231804676563",
             "Regular","Supply Chain and Logistics","Hernandez, Eduard",
             "52670","1500","1000","1000","26335","313.511904761905"},

            {"10034","Santos","Beatriz","08/07/1990",
             "27 Pandan Road, Dasmarinas Village, Makati",
             "935-940-557","44-8563448-6","445625213307","410-378-885-000","217104936202",
             "Regular","Customer Service and Relations","Hernandez, Eduard",
             "52670","1500","1000","1000","26335","313.511904761905"}
        };
    }
}
