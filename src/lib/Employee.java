package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;

    private int yearJoined;
    private int monthJoined;
    private int dayJoined;
    private int monthWorkingInYear;

    private boolean isForeigner;
    private boolean gender; // true = Laki-laki, false = Perempuan

    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;

    // ? Private constructor digunakan oleh Builder
    private Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
                     int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.yearJoined = yearJoined;
        this.monthJoined = monthJoined;
        this.dayJoined = dayJoined;
        this.isForeigner = isForeigner;
        this.gender = gender;

        this.childNames = new LinkedList<>();
        this.childIdNumbers = new LinkedList<>();
    }

    // ? Static Builder Class
    public static class EmployeeBuilder {
        private String employeeId;
        private String firstName;
        private String lastName;
        private String idNumber;
        private String address;
        private int yearJoined;
        private int monthJoined;
        private int dayJoined;
        private boolean isForeigner;
        private boolean gender;

        public EmployeeBuilder setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public EmployeeBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeBuilder setIdNumber(String idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public EmployeeBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public EmployeeBuilder setJoinDate(int year, int month, int day) {
            this.yearJoined = year;
            this.monthJoined = month;
            this.dayJoined = day;
            return this;
        }

        public EmployeeBuilder setIsForeigner(boolean isForeigner) {
            this.isForeigner = isForeigner;
            return this;
        }

        public EmployeeBuilder setGender(boolean gender) {
            this.gender = gender;
            return this;
        }

        public Employee build() {
            return new Employee(employeeId, firstName, lastName, idNumber, address,
                                yearJoined, monthJoined, dayJoined, isForeigner, gender);
        }
    }

    // ==== Sisa method tetap seperti sebelumnya ====

    public void setMonthlySalary(int grade) {
        if (grade == 1) {
            monthlySalary = 3000000;
            if (isForeigner) {
                monthlySalary = (int) (3000000 * 1.5);
            }
        } else if (grade == 2) {
            monthlySalary = 5000000;
            if (isForeigner) {
                monthlySalary = (int) (5000000 * 1.5);
            }
        } else if (grade == 3) {
            monthlySalary = 7000000;
            if (isForeigner) {
                monthlySalary = (int) (7000000 * 1.5);
            }
        }
    }

    public void setAnnualDeductible(int deductible) {
        this.annualDeductible = deductible;
    }

    public void setAdditionalIncome(int income) {
        this.otherMonthlyIncome = income;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouseName = spouseName;
        this.spouseIdNumber = spouseIdNumber;
    }

    public void addChild(String childName, String childIdNumber) {
        childNames.add(childName);
        childIdNumbers.add(childIdNumber);
    }

    private int calculateWorkingMonthsThisYear() {
        LocalDate date = LocalDate.now();
        if (date.getYear() == yearJoined) {
            return date.getMonthValue() - monthJoined;
        }
        return 12;
    }

    public int getAnnualIncomeTax() {
        int monthsWorked = calculateWorkingMonthsThisYear();
        return TaxFunction.calculateTax(
                monthlySalary,
                otherMonthlyIncome,
                monthsWorked,
                annualDeductible,
                spouseIdNumber == null || spouseIdNumber.equals(""),
                childIdNumbers.size()
        );
    }
}
