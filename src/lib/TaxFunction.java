package lib;

public class TaxFunction {

    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking,
                                   int deductible, boolean isMarried, int numberOfChildren) {

        validateWorkingMonths(numberOfMonthWorking);
        numberOfChildren = normalizeNumberOfChildren(numberOfChildren);

        int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);
        int taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, nonTaxableIncome);

        return calculateTaxFromIncome(taxableIncome);
    }

    private static void validateWorkingMonths(int months) {
        if (months > 12) {
            throw new IllegalArgumentException("Number of working months cannot exceed 12.");
        }
    }

    private static int normalizeNumberOfChildren(int children) {
        return Math.min(children, 3);
    }

    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int nonTaxableIncome = 54000000;

        if (isMarried) {
            nonTaxableIncome += 4500000;
        }

        nonTaxableIncome += numberOfChildren * 4500000;

        return nonTaxableIncome;
    }

    private static int calculateTaxableIncome(int salary, int otherIncome, int monthsWorked,
                                              int deductible, int nonTaxableIncome) {

        int annualIncome = (salary + otherIncome) * monthsWorked;
        return annualIncome - deductible - nonTaxableIncome;
    }

    private static int calculateTaxFromIncome(int taxableIncome) {
        if (taxableIncome <= 0) {
            return 0;
        }
        return (int) Math.round(taxableIncome * 0.05);
    }
}
