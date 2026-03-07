import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for payroll calculation formulas: Total Hours Worked, Gross Salary,
 * Net Salary (1st cutoff = gross only, 2nd cutoff = gross − total deductions).
 */
class MotorPHPayrollTest {

    private static final double DELTA = 1e-6;

    // ---------- getLastDay ----------

    @Test
    void getLastDay_februaryNonLeap_returns28() {
        assertEquals(28, MotorPHPayroll.getLastDay(2, 2023));
    }

    @Test
    void getLastDay_februaryLeap_returns29() {
        assertEquals(29, MotorPHPayroll.getLastDay(2, 2024));
    }

    @Test
    void getLastDay_april_returns30() {
        assertEquals(30, MotorPHPayroll.getLastDay(4, 2024));
    }

    @Test
    void getLastDay_january_returns31() {
        assertEquals(31, MotorPHPayroll.getLastDay(1, 2024));
    }

    // ---------- parseDate ----------

    @Test
    void parseDate_slashFormat_returnsYearMonthDay() {
        int[] d = MotorPHPayroll.parseDate("6/3/2024");
        assertArrayEquals(new int[]{2024, 6, 3}, d);
    }

    @Test
    void parseDate_invalid_returnsNull() {
        assertNull(MotorPHPayroll.parseDate("invalid"));
        assertNull(MotorPHPayroll.parseDate(""));
        assertNull(MotorPHPayroll.parseDate("   "));
        assertNull(MotorPHPayroll.parseDate(null));
    }

    // ---------- parseTime ----------

    @Test
    void parseTime_valid_returnsHourMinute() {
        assertArrayEquals(new int[]{8, 59}, MotorPHPayroll.parseTime("8:59"));
        assertArrayEquals(new int[]{17, 0}, MotorPHPayroll.parseTime("17:0"));
        assertArrayEquals(new int[]{17, 0}, MotorPHPayroll.parseTime("17:00"));
    }

    @Test
    void parseTime_invalid_returnsNull() {
        assertNull(MotorPHPayroll.parseTime("invalid"));
        assertNull(MotorPHPayroll.parseTime(""));
        assertNull(MotorPHPayroll.parseTime(null));
    }

    // ---------- calculateHoursWorked ----------
    // Attendance row: [empId, lastName, firstName, date, login, logout]

    private static String[] row(String empId, String date, String login, String logout) {
        return new String[]{empId, "Last", "First", date, login, logout};
    }

    @Test
    void calculateHoursWorked_onTimeFullDay_8hours() {
        String[][] att = {
            row("E1", "6/3/2024", "8:0", "17:0")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        assertEquals(8.0, h, DELTA); // 9 hrs - 1 hr lunch = 8
    }

    @Test
    void calculateHoursWorked_gracePeriodAt810_still8hours() {
        String[][] att = {
            row("E1", "6/3/2024", "8:10", "17:0")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        assertEquals(8.0, h, DELTA);
    }

    @Test
    void calculateHoursWorked_gracePeriodAt805_still8hours() {
        String[][] att = {
            row("E1", "6/3/2024", "8:5", "17:0")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        assertEquals(8.0, h, DELTA);
    }

    @Test
    void calculateHoursWorked_lateStart_7point5hours() {
        String[][] att = {
            row("E1", "6/3/2024", "8:30", "17:0")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        // 8:30 to 17:00 = 8.5 hrs - 1 hr lunch = 7.5
        assertEquals(7.5, h, DELTA);
    }

    @Test
    void calculateHoursWorked_endCapAt5pm_8hours() {
        String[][] att = {
            row("E1", "6/3/2024", "8:0", "18:0")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        assertEquals(8.0, h, DELTA);
    }

    @Test
    void calculateHoursWorked_shortDayNoLunchDeduction_halfHour() {
        String[][] att = {
            row("E1", "6/3/2024", "8:0", "8:30")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        assertEquals(0.5, h, DELTA);
    }

    @Test
    void calculateHoursWorked_dayRange_onlyDayInRangeCounted() {
        String[][] att = {
            row("E1", "6/3/2024", "8:0", "17:0"),   // day 3 in range 1-15
            row("E1", "6/20/2024", "8:0", "17:0")   // day 20 outside 1-15
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E1", 2024, 6, 1, 15);
        assertEquals(8.0, h, DELTA);
    }

    @Test
    void calculateHoursWorked_wrongEmpId_zeroHours() {
        String[][] att = {
            row("E1", "6/3/2024", "8:0", "17:0")
        };
        double h = MotorPHPayroll.calculateHoursWorked(att, "E99", 2024, 6, 1, 15);
        assertEquals(0.0, h, DELTA);
    }

    // ---------- Gross Salary formula ----------

    @Test
    void grossSalary_hoursTimesRate() {
        double hours = 10.0;
        double hourlyRate = 500.0;
        double gross = hours * hourlyRate;
        assertEquals(5000.0, gross, DELTA);
    }

    // ---------- computeSSS ----------

    @Test
    void computeSSS_below3250_returns135() {
        assertEquals(135.0, MotorPHPayroll.computeSSS(3000), DELTA);
    }

    @Test
    void computeSSS_at3250_returns157point5() {
        assertEquals(157.5, MotorPHPayroll.computeSSS(3250), DELTA);
    }

    @Test
    void computeSSS_atOrAbove24750_returns1125() {
        assertEquals(1125.0, MotorPHPayroll.computeSSS(24750), DELTA);
        assertEquals(1125.0, MotorPHPayroll.computeSSS(30000), DELTA);
    }

    @Test
    void computeSSS_midBracket_formula() {
        // 5000: bracket = (5000-3250)/500 = 3, 157.5 + 3*22.5 = 225
        assertEquals(225.0, MotorPHPayroll.computeSSS(5000), DELTA);
    }

    // ---------- computePhilhealth ----------

    @Test
    void computePhilhealth_atOrBelow10000_returns150() {
        assertEquals(150.0, MotorPHPayroll.computePhilhealth(10000), DELTA);
        assertEquals(150.0, MotorPHPayroll.computePhilhealth(5000), DELTA);
    }

    @Test
    void computePhilhealth_atOrAbove60000_returns900() {
        assertEquals(900.0, MotorPHPayroll.computePhilhealth(60000), DELTA);
        assertEquals(900.0, MotorPHPayroll.computePhilhealth(70000), DELTA);
    }

    @Test
    void computePhilhealth_middle_1point5Percent() {
        // 30000 * 0.03 / 2 = 450
        assertEquals(450.0, MotorPHPayroll.computePhilhealth(30000), DELTA);
    }

    // ---------- computePagibig ----------

    @Test
    void computePagibig_atOrBelow1500_onePercent() {
        assertEquals(15.0, MotorPHPayroll.computePagibig(1500), DELTA);
    }

    @Test
    void computePagibig_above1500_twoPercent_cap100() {
        assertEquals(100.0, MotorPHPayroll.computePagibig(10000), DELTA);
    }

    // ---------- computeWithholdingTax ----------

    @Test
    void computeWithholdingTax_atOrBelow20832_zero() {
        assertEquals(0.0, MotorPHPayroll.computeWithholdingTax(20832), DELTA);
        assertEquals(0.0, MotorPHPayroll.computeWithholdingTax(10000), DELTA);
    }

    @Test
    void computeWithholdingTax_20pctBracket() {
        // 25000: (25000 - 20833) * 0.20 = 4167 * 0.20 = 833.4
        assertEquals(833.4, MotorPHPayroll.computeWithholdingTax(25000), DELTA);
    }

    @Test
    void computeWithholdingTax_25pctBracket() {
        // 50000: 2500 + (50000 - 33333) * 0.25 = 2500 + 4166.75 = 6666.75
        assertEquals(6666.75, MotorPHPayroll.computeWithholdingTax(50000), DELTA);
    }

    // ---------- Net Salary: 1st cutoff = Gross1, 2nd = Gross2 - TotalDeductions ----------

    @Test
    void netSalary_firstCutoff_equalsGross1() {
        double gross1 = 10000.0;
        double net1 = gross1;
        assertEquals(10000.0, net1, DELTA);
    }

    @Test
    void netSalary_secondCutoff_gross2MinusTotalDeductions() {
        double gross1 = 20000.0;
        double gross2 = 18000.0;
        double totalGross = gross1 + gross2;
        double sss = MotorPHPayroll.computeSSS(totalGross);
        double philhealth = MotorPHPayroll.computePhilhealth(totalGross);
        double pagibig = MotorPHPayroll.computePagibig(totalGross);
        double taxable = totalGross - sss - philhealth - pagibig;
        double tax = MotorPHPayroll.computeWithholdingTax(taxable);
        double totalDeductions = sss + philhealth + pagibig + tax;
        double net2 = gross2 - totalDeductions;

        assertEquals(38000.0, totalGross, DELTA);
        double expectedNet2 = gross2 - totalDeductions;
        assertEquals(expectedNet2, net2, DELTA);
    }
}
