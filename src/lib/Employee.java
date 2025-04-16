package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId;
    private Name name;
    private String idNumber;
    private String address;
    private JoinDate joinDate;
    private boolean isForeigner;
    private Gender gender;

    private int monthWorkingInYear;
    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;

    private Employee(String employeeId, Name name, String idNumber, String address,
                     JoinDate joinDate, boolean isForeigner, Gender gender) {
        this.employeeId = employeeId;
        this.name = name;
        this.idNumber = idNumber;
        this.address = address;
        this.joinDate = joinDate;
        this.isForeigner = isForeigner;
        this.gender = gender;
        this.childNames = new LinkedList<>();
        this.childIdNumbers = new LinkedList<>();
    }

    public static class EmployeeBuilder {
        private String employeeId;
        private Name name;
        private String idNumber;
        private String address;
        private JoinDate joinDate;
        private boolean isForeigner;
        private Gender gender;

        public EmployeeBuilder setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public EmployeeBuilder setName(Name name) {
            this.name = name;
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

        public EmployeeBuilder setJoinDate(JoinDate joinDate) {
            this.joinDate = joinDate;
            return this;
        }

        public EmployeeBuilder setIsForeigner(boolean isForeigner) {
            this.isForeigner = isForeigner;
            return this;
        }

        public EmployeeBuilder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Employee build() {
            return new Employee(employeeId, name, idNumber, address, joinDate, isForeigner, gender);
        }
    }

    public void setMonthlySalary(int grade) {
        if (grade == 1) {
            monthlySalary = 3000000;
        } else if (grade == 2) {
            monthlySalary = 5000000;
        } else if (grade == 3) {
            monthlySalary = 7000000;
        }

        if (isForeigner) {
            monthlySalary = (int) (monthlySalary * 1.5);
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
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getYear() == joinDate.getYear()) {
            return currentDate.getMonthValue() - joinDate.getMonth();
        }
        return 12;
    }

    public int getAnnualIncomeTax() {
        int monthsWorked = calculateWorkingMonthsThisYear();
        boolean hasSpouse = spouseIdNumber != null && !spouseIdNumber.isEmpty();

        return TaxFunction.calculateTax(
            monthlySalary,
            otherMonthlyIncome,
            monthsWorked,
            annualDeductible,
            !hasSpouse,
            childIdNumbers.size()
        );
    }

    // Inner class: Name
    public static class Name {
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    // Inner class: JoinDate
    public static class JoinDate {
        private int year;
        private int month;
        private int day;

        public JoinDate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public LocalDate toLocalDate() {
            return LocalDate.of(year, month, day);
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }

    // Inner enum: Gender
    public enum Gender {
        MALE,
        FEMALE
    }
}
