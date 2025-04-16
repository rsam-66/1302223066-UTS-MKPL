package lib;

public class TaxFunction {

    // Class untuk menyimpan informasi pegawai
    public static class EmployeeIncomeProfile {
        public int monthlySalary;
        public int otherMonthlyIncome;
        public int numberOfMonthWorking;
        public int deductible;
        public boolean isMarried;
        public int numberOfChildren;

        // Constructor untuk menginisialisasi objek
        public EmployeeIncomeProfile(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking,
                                     int deductible, boolean isMarried, int numberOfChildren) {
            this.monthlySalary = monthlySalary;
            this.otherMonthlyIncome = otherMonthlyIncome;
            this.numberOfMonthWorking = numberOfMonthWorking;
            this.deductible = deductible;
            this.isMarried = isMarried;
            this.numberOfChildren = numberOfChildren;
        }
    }

    // Fungsi untuk menghitung pajak
    public static int calculateTax(EmployeeIncomeProfile profile) {

        // Validasi jumlah bulan bekerja
        validateWorkingMonths(profile.numberOfMonthWorking);
        
        // Normalisasi jumlah anak (maks 3)
        int numberOfChildren = normalizeNumberOfChildren(profile.numberOfChildren);

        // Hitung penghasilan tidak kena pajak
        int nonTaxableIncome = calculateNonTaxableIncome(profile.isMarried, numberOfChildren);
        
        // Hitung penghasilan kena pajak
        int taxableIncome = calculateTaxableIncome(
            profile.monthlySalary,
            profile.otherMonthlyIncome,
            profile.numberOfMonthWorking,
            profile.deductible,
            nonTaxableIncome
        );

        // Hitung pajak dari penghasilan kena pajak
        return calculateTaxFromIncome(taxableIncome);
    }

    // Validasi jumlah bulan bekerja
    private static void validateWorkingMonths(int months) {
        if (months > 12) {
            throw new IllegalArgumentException("Number of working months cannot exceed 12.");
        }
    }

    // Normalisasi jumlah anak (maks 3)
    private static int normalizeNumberOfChildren(int children) {
        return Math.min(children, 3);
    }

    // Hitung penghasilan tidak kena pajak
    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int nonTaxableIncome = 54000000;
        if (isMarried) {
            nonTaxableIncome += 4500000;
        }
        nonTaxableIncome += numberOfChildren * 4500000;
        return nonTaxableIncome;
    }

    // Hitung penghasilan kena pajak
    private static int calculateTaxableIncome(int salary, int otherIncome, int monthsWorked,
                                              int deductible, int nonTaxableIncome) {
        int annualIncome = (salary + otherIncome) * monthsWorked;
        return annualIncome - deductible - nonTaxableIncome;
    }

    // Hitung pajak berdasarkan penghasilan kena pajak
    private static int calculateTaxFromIncome(int taxableIncome) {
        if (taxableIncome <= 0) {
            return 0;
        }
        return (int) Math.round(taxableIncome * 0.05);
    }
}
