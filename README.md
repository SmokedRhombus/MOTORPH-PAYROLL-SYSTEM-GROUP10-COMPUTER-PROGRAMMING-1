# MotorPH Payroll System

A command line Java application for **MotorPH's Payroll Module** made by Group 10 that presents employee information and calculates salaries with government-mandated deductions, built as a single `.java` file with no OOP concepts and no external libraries.


## Members

- **Miguel Dominic E. Roa** 
- **Kreskin Bejoc** 
- **Carlos Louis Acula** 
- **April Joyce Abejo**

---

## Features

- **View Employee Information** — displays all employee details (Employee #, Name, Birthday, Address, Government IDs, Position, Salary, etc.)
- **View Monthly Payroll for an Employee** — generates a full payroll slip for a chosen employee and month
- **View All Employees Payroll Summary** — displays a payroll table for all 34 employees for a chosen month
- **Data coverage: June – December 2024**

---

## Payroll Calculation Rules

### Hours Worked
| Rule | Details |
|------|---------|
| **Grace period** | Login at **8:10 AM or earlier** → treated as **8:00 AM** (not marked late) |
| **Late login** | Login after 8:10 AM → actual login time used as start |
| **End of day cap** | Only hours up to **5:00 PM** are counted; overtime is excluded |
| **Lunch break** | **1 hour** deducted per day (if more than 1 hour worked) |
| **Cutoff split** | 1st cutoff = Days 1–15 · 2nd cutoff = Days 16–31 |

### Gross Pay
```
Gross Pay = Total Hours Worked × Hourly Rate
Hourly Rate = Basic Salary / 21 / 8
```

### Deductions (computed on combined 1st + 2nd cutoff gross pay)

| Deduction | Rule |
|-----------|------|
| **SSS** | Bracket table: below ₱3,250 → ₱135; up to ₱24,750+ → ₱1,125 (+₱22.50 per ₱500 bracket) |
| **Philhealth** | 3% of monthly salary, employee pays 50%; min ₱150, max ₱900 |
| **Pag-ibig** | 1% if salary ≤ ₱1,500; 2% if above; max ₱100 |
| **Withholding Tax** | Applied on taxable income (Gross − SSS − Philhealth − Pag-ibig); see tax table below |

#### Withholding Tax Table
| Taxable Income | Tax Due |
|----------------|---------|
| ₱20,832 and below | ₱0 |
| ₱20,833 – ₱33,332 | 20% of excess over ₱20,833 |
| ₱33,333 – ₱66,666 | ₱2,500 + 25% of excess over ₱33,333 |
| ₱66,667 – ₱166,666 | ₱10,833 + 30% of excess over ₱66,667 |
| ₱166,667 – ₱666,666 | ₱40,833.33 + 32% of excess over ₱166,667 |
| ₱666,667 and above | ₱200,833.33 + 35% of excess over ₱666,667 |

> **No rounding** — all values retain full decimal precision throughout calculations.

---

## Repository Structure

```
MotorPHPayroll/
├── MotorPHPayroll.java       ← Single Java source file (all logic)
├── attendance.csv            ← Required at runtime (not tracked in git — see setup)
└── README.md
```

> Employee data (all 34 employees) is **embedded directly** in `MotorPHPayroll.java`.  
> Attendance data is **read from `attendance.csv`** at runtime.

---

## Setup & How to Run

### Prerequisites
- **Java JDK 11 or higher**
- **VSCode** (recommended) with the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

### Step 1 — Export Attendance CSV

1. Open **`Copy_of_MotorPH_Employee_Data.xlsx`**
2. Click the **`Attendance Record`** sheet tab at the bottom
3. Go to **File → Save As → CSV (Comma delimited) (.csv)**
4. Save the file as **`attendance.csv`**
5. Place `attendance.csv` in the **same folder** as `MotorPHPayroll.java`

> The CSV must have these columns (header row is automatically skipped):
> `EmpNum, LastName, FirstName, Date, LogIn, LogOut`  
> Date format: `M/D/YYYY` · Time format: `H:MM` or `HH:MM`

### Step 2 — Compile

```bash
javac MotorPHPayroll.java
```

### Step 3 — Run

```bash
java MotorPHPayroll
```

### Running in VSCode

1. Open the project folder in VSCode
2. Place `attendance.csv` in the same folder
3. Open `MotorPHPayroll.java`
4. Click the **▷ Run** button (top right) or press `F5`
5. Interact with the menu in the **Terminal** panel

---

## Sample Output

```
=======================================================
              MOTORPH PAYROLL SYSTEM                   
           Phase 1 - Basic Payroll Module              
=======================================================

MAIN MENU
  [1] View Employee Information
  [2] View Monthly Payroll for an Employee
  [3] View All Employees Payroll Summary
  [4] Exit
Enter choice: 2

--- MONTHLY PAYROLL ---
Enter Employee Number: 10001

Select Month (June – December 2024):
  [1] June    [2] July     ...
Enter choice (1-7): 1

==========================================================
  PAYROLL SLIP  —  June 2024
==========================================================
  Employee #          : 10001
  Name                : Manuel III Garcia
  Position            : Chief Executive Officer
  Hourly Rate         : 535.7143
----------------------------------------------------------
  1st Cutoff Hours  (Days  1–15) : 64.8333 hrs
  2nd Cutoff Hours  (Days 16–31) : 66.8167 hrs
  Total Hours Worked             : 131.6500 hrs
----------------------------------------------------------
  1st Cutoff Gross Pay           : 34732.1429
  2nd Cutoff Gross Pay           : 35794.6429
  TOTAL GROSS PAY                : 70526.7857
----------------------------------------------------------
  DEDUCTIONS  (based on total monthly gross pay):
    SSS Contribution             : 1125.0000
    Philhealth                   : 900.0000
    Pag-ibig                     : 100.0000
      Taxable Income             : 68401.7857
    Withholding Tax              : 11353.4357
  TOTAL DEDUCTIONS               : 13478.4357
----------------------------------------------------------
  NET PAY                        : 57048.3500
==========================================================
```

---

## How the Code Works

This section explains every part of the program in detail what each piece does, why it works the way it does, and how all the parts connect together.

---

### Program Structure Overview

The entire program lives in one class (`MotorPHPayroll`) and is organized into logical groups of static methods. There are no objects, no inheritance, and no constructors just a `main` method that calls other methods directly. Data is passed around as plain arrays (`String[][]`).

```
main()
 ├── loadAttendanceCsv()         ← reads attendance.csv into memory
 ├── getEmployees()              ← returns embedded employee data
 └── [menu loop]
      ├── showEmployeeInfo()     ← Menu option 1
      ├── showEmployeePayroll()  ← Menu option 2
      │    └── printPayrollSlip()
      │         ├── calculateHoursWorked()
      │         ├── computeSSS()
      │         ├── computePhilhealth()
      │         ├── computePagibig()
      │         └── computeWithholdingTax()
      └── showAllPayroll()       ← Menu option 3
           └── (same calculation methods as above)
```

---

### 1. `main()` — Main Menu

When the program starts, `main` does two things before showing the menu:

1. **Loads employee data** by calling `getEmployees()`, which returns a hardcoded 2D array of all 34 employees sourced directly from the MotorPH Excel file.
2. **Loads attendance data** by calling `loadAttendanceCsv("attendance.csv")`, which reads and parses the CSV file into a 2D array of strings.

After that, a `while` loop keeps the main menu running until the user chooses Exit. Each menu option calls its corresponding method and passes both data arrays into it.

---

### 2. `getEmployees()` — Employee Data Storage

Because the requirement says all employee data must come from the spreadsheet but also that no external libraries should be used, the employee details from `Copy_of_MotorPH_Employee_Data.xlsx` (Employee Details sheet) are **embedded directly** as a literal 2D `String` array inside the method.

Each row is one employee, and each column index maps to a field:

| Index | Field |
|-------|-------|
| 0 | Employee # |
| 1 | Last Name |
| 2 | First Name |
| 3 | Birthday |
| 4 | Address |
| 5 | Phone Number |
| 6 | SSS # |
| 7 | Philhealth # |
| 8 | TIN # |
| 9 | Pag-ibig # |
| 10 | Status |
| 11 | Position |
| 12 | Immediate Supervisor |
| 13 | Basic Salary |
| 14 | Rice Subsidy |
| 15 | Phone Allowance |
| 16 | Clothing Allowance |
| 17 | Gross Semi-monthly Rate (= Basic Salary ÷ 2) |
| 18 | Hourly Rate (= Basic Salary ÷ 21 ÷ 8) |

The hourly rate formula `BasicSalary / 21 / 8` is based on 21 working days per month and 8 working hours per day, which matches the formula in the original Excel file.

---

### 3. `loadAttendanceCsv()` — Reading Attendance from File

This method opens `attendance.csv` line by line using a `BufferedReader`. The first line (the header row) is skipped automatically.

Each subsequent line is passed to `parseCsvLine()`, which splits it into fields while correctly handling quoted values that may contain commas (e.g., an address field). After splitting, each field is trimmed and any surrounding double-quotes are stripped.

Only rows with at least 6 columns and a non-empty Employee # are kept. The result is a `String[][]` where each row represents one attendance record with columns:

`[0] EmpNum  [1] LastName  [2] FirstName  [3] Date  [4] LogIn  [5] LogOut`

If the file is missing entirely, the program prints clear instructions and exits immediately rather than crashing with a cryptic error.

---

### 4. `calculateHoursWorked()` — The Core Time Calculation

This is the most important calculation method. It takes the full attendance array, an employee ID, a year, a month, and a day range (start and end), and returns the total decimal hours worked in that period.

**Step-by-step logic for each attendance row:**

1. **Filter** — skip any row that doesn't match the employee ID, year, month, and day range.
2. **Parse** — convert the date string (e.g., `"6/3/2024"`) and time strings (e.g., `"8:59"`) into plain integers representing minutes since midnight. This is done by `parseDate()` and `parseTime()`.
3. **Apply grace period** — the effective start time is calculated as:
   - If `loginMinutes ≤ 490` (8:10 AM) → effective start = **480 minutes** (8:00 AM)
   - If `loginMinutes > 490` → effective start = **actual login time** (employee is late)
4. **Cap end time** — the effective end is `Math.min(logoutMinutes, 1020)`. Since 5:00 PM = 17 × 60 = 1020 minutes, any logout time after 5 PM is treated as exactly 5 PM. Overtime is not counted.
5. **Calculate worked minutes** — `effectiveEnd − effectiveStart`. If this is greater than zero and greater than 60 minutes, 60 minutes (1 hour) is subtracted for the lunch break.
6. **Accumulate** — the day's worked minutes are added to a running total.

After looping through all rows, the total minutes are divided by 60 to give hours as a decimal (e.g., 7.5 hours). No rounding is applied at any step.

**Worked example:**

An employee logs in at `8:59` and logs out at `18:31`:
- Login is after 8:10 → effective start = 8:59 = **539 min**
- Logout is after 17:00 → effective end = **1020 min**
- Worked = 1020 − 539 = **481 min**
- Subtract lunch: 481 − 60 = **421 min = 7.0167 hrs**

---

### 5. `printPayrollSlip()` — Full Payroll Calculation

This method assembles the complete payroll for one employee in one month. It calls `calculateHoursWorked()` twice — once for the 1st cutoff (days 1–15) and once for the 2nd cutoff (days 16–31) — then combines those results before computing deductions.

**Why combine before deductions?**
Government contributions (SSS, Philhealth, Pag-ibig) and withholding tax are computed on a **monthly** basis, not per cutoff. Adding the two cutoffs together first gives the true monthly gross, which then feeds into the correct deduction bracket. Computing deductions on each half-month separately would give a different (and incorrect) result.

The full calculation sequence:
```
Hours1        = calculateHoursWorked( days 1–15 )
Hours2        = calculateHoursWorked( days 16–31 )
Gross1        = Hours1 × HourlyRate
Gross2        = Hours2 × HourlyRate
TotalGross    = Gross1 + Gross2

SSS           = computeSSS( TotalGross )
Philhealth    = computePhilhealth( TotalGross )
Pagibig       = computePagibig( TotalGross )
TaxableIncome = TotalGross − SSS − Philhealth − Pagibig
Tax           = computeWithholdingTax( TaxableIncome )

TotalDeductions = SSS + Philhealth + Pagibig + Tax
NetPay          = TotalGross − TotalDeductions
```

---

### 6. `computeSSS()` — SSS Contribution Lookup

The SSS table from `Copy_of_SSS_Contribution.xlsx` works in ₱500-wide salary brackets. Rather than storing the full table as a lookup array, the formula detects which bracket the salary falls in using integer division:

```java
int bracket = (int) ((monthlySalary - 3250) / 500);
return 157.5 + bracket * 22.5;
```

The first bracket starting at ₱3,250 contributes ₱157.50. Each subsequent ₱500 bracket adds ₱22.50. Salaries below ₱3,250 are hardcoded to ₱135.00, and salaries at ₱24,750 or above are capped at ₱1,125.00. This matches all 45 rows of the SSS table exactly without storing them individually.

---

### 7. `computePhilhealth()` — Philhealth Contribution

Philhealth charges 3% of monthly salary as a premium, split equally between employee and employer. The employee therefore pays **1.5%** of their salary. The formula is:

```
Employee Share = (MonthlySalary × 0.03) / 2
```

Per the MotorPH table, the contribution is floored at ₱150 (for salaries ≤ ₱10,000) and capped at ₱900 (for salaries ≥ ₱60,000).

---

### 8. `computePagibig()` — Pag-ibig Contribution

Pag-ibig (HDMF) uses a simple two-tier rate: 1% for monthly salaries at or below ₱1,500, and 2% for salaries above that. The employee contribution is then capped at a maximum of ₱100, regardless of salary size. In practice, any salary above ₱5,000 will hit the ₱100 cap.

---

### 9. `computeWithholdingTax()` — Withholding Tax

Withholding tax is a **progressive** tax, meaning higher income is taxed at higher rates but only on the portion that exceeds each bracket's threshold. The function implements the six-bracket table from `Copy_of_Witholding_Tax.xlsx` using chained `if-else` conditions.

Tax is computed **on taxable income**, which is the gross pay after all three mandatory contributions have been subtracted. This means a higher SSS/Philhealth/Pag-ibig contribution actually reduces the taxable base and therefore the tax owed.

**Worked example** (using Employee 10001, June 2024):
```
Total Gross Pay     =  70,526.7857
  − SSS             =   1,125.0000
  − Philhealth      =     900.0000
  − Pag-ibig        =     100.0000
Taxable Income      =  68,401.7857

Tax bracket: ₱66,667–₱166,666
  = 10,833 + (68,401.7857 − 66,667) × 0.30
  = 10,833 + 1,734.7857 × 0.30
  = 10,833 + 520.4357
  = 11,353.4357
```

---

### 10. `parseDate()` and `parseTime()` — String-to-Integer Parsing

These helpers convert the raw string values from the CSV into numbers the calculation logic can work with.

`parseDate()` handles the `M/D/YYYY` format produced by Excel's CSV export (e.g., `"6/3/2024"` → `{2024, 6, 3}`). It also has a fallback for `YYYY-MM-DD` format in case the CSV was exported differently. It always returns `int[] { year, month, day }` so the caller can compare values directly.

`parseTime()` handles `H:MM` and `HH:MM` formats (e.g., `"8:59"` or `"18:31"`). It splits on `:` and reads only the first two parts (hours and minutes), ignoring seconds if present. It returns `int[] { hour, minute }`.

Both methods return `null` on any parsing failure, and the calling code skips the row silently if either value is null — preventing crashes from malformed CSV lines.

---

### 11. `findEmployee()` — Employee Lookup

This is a simple linear search through the employees array. It compares the cleaned-up employee ID string (with any trailing `.0` stripped) against the search input. Returns the matching row array, or `null` if not found.

---

### 12. `parseCsvLine()` — Robust CSV Parsing

A basic `split(",")` would break on addresses that contain commas. This method handles that by tracking whether the current character is inside a quoted string. While inside quotes, commas are treated as literal characters, not field separators. When a closing quote is found, quote mode is toggled off and normal splitting resumes.

---

## Employee Coverage

The system includes all **34 MotorPH employees** (Employee #10001 – #10034) across departments:
Executive, HR, Accounting, Payroll, Account Management, IT, Sales & Marketing, Supply Chain, and Customer Service.

---

## References
- [MotorPH Company Website](https://sites.google.com/mmdc.mcl.edu.ph/motorph/home)
- Government contribution tables provided by MotorPH (simplified rates for academic purposes)
