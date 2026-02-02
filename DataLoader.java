/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DataLoader {
    
    private static Map<Integer, Employee> employees = new HashMap<>();
    private static List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    

    public static void loadData() {
        loadEmployees();
        loadAttendanceRecords();
    }
    

    private static void loadEmployees() {
        Employee emp1 = new Employee();
        emp1.setEmployeeNumber(10001);
        emp1.setLastName("Garcia");
        emp1.setFirstName("Manuel III");
        emp1.setBirthday(LocalDate.of(1983, 3, 2));
        emp1.setAddress("Bagong Ilog, Pasig City");
        emp1.setPhoneNumber("966-860-270");
        emp1.setSssNumber("44-4506057-3");
        emp1.setPhilhealthNumber("450605734440");
        emp1.setTinNumber("442-605-657-000");
        emp1.setPagibigNumber("691601266222");
        emp1.setStatus("Regular");
        emp1.setPosition("Chief Executive Officer");
        emp1.setBasicSalary(90000.00);
        emp1.setRiceSubsidy(1500.00);
        emp1.setPhoneAllowance(2000.00);
        emp1.setClothingAllowance(1500.00);
        emp1.setGrossSemiMonthlyRate(45000.00);
        emp1.setHourlyRate(535.714286);
        employees.put(emp1.getEmployeeNumber(), emp1);
        

        Employee emp2 = new Employee();
        emp2.setEmployeeNumber(10002);
        emp2.setLastName("Lim");
        emp2.setFirstName("Antonio");
        emp2.setBirthday(LocalDate.of(1988, 9, 27));
        emp2.setAddress("Barangka Drive, Mandaluyong City");
        emp2.setPhoneNumber("171-867-411");
        emp2.setSssNumber("52-5506057-0");
        emp2.setPhilhealthNumber("150605733550");
        emp2.setTinNumber("552-605-657-000");
        emp2.setPagibigNumber("691601233222");
        emp2.setStatus("Regular");
        emp2.setPosition("Chief Operating Officer");
        emp2.setBasicSalary(60000.00);
        emp2.setRiceSubsidy(1500.00);
        emp2.setPhoneAllowance(2000.00);
        emp2.setClothingAllowance(1500.00);
        emp2.setGrossSemiMonthlyRate(30000.00);
        emp2.setHourlyRate(357.142857);
        employees.put(emp2.getEmployeeNumber(), emp2);
        

        Employee emp3 = new Employee();
        emp3.setEmployeeNumber(10003);
        emp3.setLastName("Aquino");
        emp3.setFirstName("Bianca Sofia");
        emp3.setBirthday(LocalDate.of(1997, 5, 11));
        emp3.setAddress("Camella Homes Northpoint, Novaliches, Quezon City");
        emp3.setPhoneNumber("966-889-370");
        emp3.setSssNumber("29-2052487-5");
        emp3.setPhilhealthNumber("920524873290");
        emp3.setTinNumber("292-052-487-000");
        emp3.setPagibigNumber("691601222782");
        emp3.setStatus("Regular");
        emp3.setPosition("Chief Finance Officer");
        emp3.setBasicSalary(60000.00);
        emp3.setRiceSubsidy(1500.00);
        emp3.setPhoneAllowance(2000.00);
        emp3.setClothingAllowance(1500.00);
        emp3.setGrossSemiMonthlyRate(30000.00);
        emp3.setHourlyRate(357.142857);
        employees.put(emp3.getEmployeeNumber(), emp3);
        

        Employee emp4 = new Employee();
        emp4.setEmployeeNumber(10004);
        emp4.setLastName("Reyes");
        emp4.setFirstName("Isabella");
        emp4.setBirthday(LocalDate.of(1993, 12, 3));
        emp4.setAddress("Poblacion, Mandaluyong City");
        emp4.setPhoneNumber("527-675-270");
        emp4.setSssNumber("65-7605536-9");
        emp4.setPhilhealthNumber("576053369657");
        emp4.setTinNumber("657-605-536-000");
        emp4.setPagibigNumber("691601265222");
        emp4.setStatus("Regular");
        emp4.setPosition("Chief Marketing Officer");
        emp4.setBasicSalary(60000.00);
        emp4.setRiceSubsidy(1500.00);
        emp4.setPhoneAllowance(2000.00);
        emp4.setClothingAllowance(1500.00);
        emp4.setGrossSemiMonthlyRate(30000.00);
        emp4.setHourlyRate(357.142857);
        employees.put(emp4.getEmployeeNumber(), emp4);
        

        Employee emp5 = new Employee();
        emp5.setEmployeeNumber(10005);
        emp5.setLastName("Hernandez");
        emp5.setFirstName("Eduard");
        emp5.setBirthday(LocalDate.of(1995, 8, 25));
        emp5.setAddress("Addition Hills, Mandaluyong City");
        emp5.setPhoneNumber("919-600-590");
        emp5.setSssNumber("40-2234900-6");
        emp5.setPhilhealthNumber("023490064402");
        emp5.setTinNumber("402-234-900-000");
        emp5.setPagibigNumber("691601278222");
        emp5.setStatus("Regular");
        emp5.setPosition("IT Operations and Systems");
        emp5.setBasicSalary(52000.00);
        emp5.setRiceSubsidy(1500.00);
        emp5.setPhoneAllowance(2000.00);
        emp5.setClothingAllowance(835.00);
        emp5.setGrossSemiMonthlyRate(26335.00);
        emp5.setHourlyRate(313.511905);
        employees.put(emp5.getEmployeeNumber(), emp5);
    }
    

    private static void loadAttendanceRecords() {
        attendanceRecords.add(createAttendance(10001, "Garcia", "Manuel III", 
            "2024-06-03", "08:59:00", "18:31:00"));
        attendanceRecords.add(createAttendance(10001, "Garcia", "Manuel III", 
            "2024-06-04", "08:02:00", "17:05:00"));
        attendanceRecords.add(createAttendance(10001, "Garcia", "Manuel III", 
            "2024-06-05", "08:00:00", "17:00:00"));
        attendanceRecords.add(createAttendance(10001, "Garcia", "Manuel III", 
            "2024-06-06", "08:05:00", "17:10:00"));
        attendanceRecords.add(createAttendance(10001, "Garcia", "Manuel III", 
            "2024-06-07", "08:01:00", "17:03:00"));
        

        attendanceRecords.add(createAttendance(10002, "Lim", "Antonio", 
            "2024-06-03", "10:35:00", "19:44:00"));
        attendanceRecords.add(createAttendance(10002, "Lim", "Antonio", 
            "2024-06-04", "08:15:00", "17:20:00"));
        attendanceRecords.add(createAttendance(10002, "Lim", "Antonio", 
            "2024-06-05", "08:03:00", "17:05:00"));
        attendanceRecords.add(createAttendance(10002, "Lim", "Antonio", 
            "2024-06-06", "08:08:00", "17:12:00"));
        attendanceRecords.add(createAttendance(10002, "Lim", "Antonio", 
            "2024-06-07", "08:00:00", "17:00:00"));
        

        attendanceRecords.add(createAttendance(10003, "Aquino", "Bianca Sofia", 
            "2024-06-03", "10:23:00", "18:32:00"));
        attendanceRecords.add(createAttendance(10003, "Aquino", "Bianca Sofia", 
            "2024-06-04", "08:01:00", "17:02:00"));
        attendanceRecords.add(createAttendance(10003, "Aquino", "Bianca Sofia", 
            "2024-06-05", "08:00:00", "17:01:00"));
        attendanceRecords.add(createAttendance(10003, "Aquino", "Bianca Sofia", 
            "2024-06-06", "08:05:00", "17:08:00"));
        attendanceRecords.add(createAttendance(10003, "Aquino", "Bianca Sofia", 
            "2024-06-07", "08:02:00", "17:00:00"));
    }
    
    private static AttendanceRecord createAttendance(int empNum, String lastName, String firstName,
                                                     String dateStr, String loginStr, String logoutStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        AttendanceRecord record = new AttendanceRecord();
        record.setEmployeeNumber(empNum);
        record.setLastName(lastName);
        record.setFirstName(firstName);
        record.setDate(LocalDate.parse(dateStr, dateFormatter));
        record.setLogIn(LocalTime.parse(loginStr, timeFormatter));
        record.setLogOut(LocalTime.parse(logoutStr, timeFormatter));
        
        return record;
    }
    

    public static Employee getEmployee(int employeeNumber) {
        return employees.get(employeeNumber);
    }
    

    public static Collection<Employee> getAllEmployees() {
        return employees.values();
    }
    

    public static List<AttendanceRecord> getAttendanceRecords(int employeeNumber, 
                                                              LocalDate startDate, 
                                                              LocalDate endDate) {
        List<AttendanceRecord> filtered = new ArrayList<>();
        
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeNumber() == employeeNumber &&
                !record.getDate().isBefore(startDate) &&
                !record.getDate().isAfter(endDate)) {
                filtered.add(record);
            }
        }
        
        return filtered;
    }
    

    public static List<AttendanceRecord> getAttendanceRecords(int employeeNumber) {
        List<AttendanceRecord> filtered = new ArrayList<>();
        
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeNumber() == employeeNumber) {
                filtered.add(record);
            }
        }
        
        return filtered;
    }
}