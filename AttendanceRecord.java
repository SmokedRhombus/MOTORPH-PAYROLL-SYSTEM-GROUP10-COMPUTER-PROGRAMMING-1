/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;


public class AttendanceRecord {
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private LocalDate date;
    private LocalTime logIn;
    private LocalTime logOut;
    
    public static final LocalTime STANDARD_LOGIN_TIME = LocalTime.of(8, 0);
    public static final LocalTime GRACE_PERIOD_END = LocalTime.of(8, 10);
    public static final int STANDARD_WORK_HOURS = 8;

    public AttendanceRecord() {
    }

    public AttendanceRecord(int employeeNumber, String lastName, String firstName, 
                           LocalDate date, LocalTime logIn, LocalTime logOut) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
        this.logIn = logIn;
        this.logOut = logOut;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getLogIn() {
        return logIn;
    }

    public void setLogIn(LocalTime logIn) {
        this.logIn = logIn;
    }

    public LocalTime getLogOut() {
        return logOut;
    }

    public void setLogOut(LocalTime logOut) {
        this.logOut = logOut;
    }


    public double getHoursWorked() {
        if (logIn == null || logOut == null) {
            return 0.0;
        }

        Duration duration = Duration.between(logIn, logOut);
        double hoursWorked = duration.toMinutes() / 60.0;

        hoursWorked -= 1.0;

        if (logIn.isAfter(GRACE_PERIOD_END)) {
            Duration lateDuration = Duration.between(STANDARD_LOGIN_TIME, logIn);
            double lateHours = lateDuration.toMinutes() / 60.0;
            hoursWorked -= lateHours;
        }

        return Math.max(0, hoursWorked);
    }


    public boolean isLate() {
        return logIn != null && logIn.isAfter(GRACE_PERIOD_END);
    }


    public long getMinutesLate() {
        if (!isLate()) {
            return 0;
        }
        return Duration.between(GRACE_PERIOD_END, logIn).toMinutes();
    }

    @Override
    public String toString() {
        return String.format("Employee #%d: %s %s - Date: %s, Login: %s, Logout: %s, Hours: %.2f",
            employeeNumber, firstName, lastName, date, logIn, logOut, getHoursWorked());
    }
}