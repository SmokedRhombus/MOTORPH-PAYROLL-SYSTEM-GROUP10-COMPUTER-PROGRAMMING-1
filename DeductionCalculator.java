/** Made by Group 10  
Miguel Dominic E. Roa
Carlos Louis Acula
Kreskin Bejoc
April Joyce Abejo
*/

public class DeductionCalculator {

    public static double calculateSSSContribution(double monthlySalary) {
        if (monthlySalary < 3250) {
            return 135.0;
        } else if (monthlySalary < 3750) {
            return 157.5;
        } else if (monthlySalary < 4250) {
            return 180.0;
        } else if (monthlySalary < 4750) {
            return 202.5;
        } else if (monthlySalary < 5250) {
            return 225.0;
        } else if (monthlySalary < 5750) {
            return 247.5;
        } else if (monthlySalary < 6250) {
            return 270.0;
        } else if (monthlySalary < 6750) {
            return 292.5;
        } else if (monthlySalary < 7250) {
            return 315.0;
        } else if (monthlySalary < 7750) {
            return 337.5;
        } else if (monthlySalary < 8250) {
            return 360.0;
        } else if (monthlySalary < 8750) {
            return 382.5;
        } else if (monthlySalary < 9250) {
            return 405.0;
        } else if (monthlySalary < 9750) {
            return 427.5;
        } else if (monthlySalary < 10250) {
            return 450.0;
        } else if (monthlySalary < 10750) {
            return 472.5;
        } else if (monthlySalary < 11250) {
            return 495.0;
        } else if (monthlySalary < 11750) {
            return 517.5;
        } else if (monthlySalary < 12250) {
            return 540.0;
        } else if (monthlySalary < 12750) {
            return 562.5;
        } else if (monthlySalary < 13250) {
            return 585.0;
        } else if (monthlySalary < 13750) {
            return 607.5;
        } else if (monthlySalary < 14250) {
            return 630.0;
        } else if (monthlySalary < 14750) {
            return 652.5;
        } else if (monthlySalary < 15250) {
            return 675.0;
        } else if (monthlySalary < 15750) {
            return 697.5;
        } else if (monthlySalary < 16250) {
            return 720.0;
        } else if (monthlySalary < 16750) {
            return 742.5;
        } else if (monthlySalary < 17250) {
            return 765.0;
        } else if (monthlySalary < 17750) {
            return 787.5;
        } else if (monthlySalary < 18250) {
            return 810.0;
        } else if (monthlySalary < 18750) {
            return 832.5;
        } else if (monthlySalary < 19250) {
            return 855.0;
        } else if (monthlySalary < 19750) {
            return 877.5;
        } else if (monthlySalary < 20250) {
            return 900.0;
        } else if (monthlySalary < 20750) {
            return 922.5;
        } else if (monthlySalary < 21250) {
            return 945.0;
        } else if (monthlySalary < 21750) {
            return 967.5;
        } else if (monthlySalary < 22250) {
            return 990.0;
        } else if (monthlySalary < 22750) {
            return 1012.5;
        } else if (monthlySalary < 23250) {
            return 1035.0;
        } else if (monthlySalary < 23750) {
            return 1057.5;
        } else if (monthlySalary < 24250) {
            return 1080.0;
        } else if (monthlySalary < 24750) {
            return 1102.5;
        } else {
            return 1125.0;
        }
    }

    public static double calculatePhilHealthContribution(double monthlySalary) {
        double premiumRate = 0.03;
        double employeeShare = 0.50;
        
        double monthlyPremium;
        
        if (monthlySalary <= 10000) {
            monthlyPremium = 300.0;
        } else if (monthlySalary >= 60000) {
            monthlyPremium = 1800.0;
        } else {
            monthlyPremium = monthlySalary * premiumRate;
            monthlyPremium = Math.min(monthlyPremium, 1800.0);
        }
        
        return monthlyPremium * employeeShare;
    }

    public static double calculatePagIbigContribution(double monthlySalary) {
        double contributionRate;
        
        if (monthlySalary <= 1500) {
            contributionRate = 0.01;
        } else {
            contributionRate = 0.02;
        }
        
        double contribution = monthlySalary * contributionRate;
        return Math.min(contribution, 100.0);
    }

    public static double calculateWithholdingTax(double monthlyTaxableIncome) {
        double tax = 0.0;
        
        if (monthlyTaxableIncome <= 20833) {
            tax = 0.0;
        } else if (monthlyTaxableIncome <= 33333) {
            tax = (monthlyTaxableIncome - 20833) * 0.20;
        } else if (monthlyTaxableIncome <= 66667) {
            tax = 2500 + (monthlyTaxableIncome - 33333) * 0.25;
        } else if (monthlyTaxableIncome <= 166667) {
            tax = 10833 + (monthlyTaxableIncome - 66667) * 0.30;
        } else if (monthlyTaxableIncome <= 666667) {
            tax = 40833.33 + (monthlyTaxableIncome - 166667) * 0.32;
        } else {
            tax = 200833.33 + (monthlyTaxableIncome - 666667) * 0.35;
        }
        
        return tax;
    }

    public static double monthlyToWeekly(double monthlyAmount) {
        return monthlyAmount / 4.0;
    }
}