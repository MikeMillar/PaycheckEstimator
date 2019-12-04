package com.mikemillar.hondaPaycheckEstimator.payModel;

public class Sales {
    
    private String month;
    private int year;
    private double customerLabor;
    private double warrantyLabor;
    private double internalLabor;
    private double totalLabor;
    private double customerParts;
    private double warrantyParts;
    private double internalParts;
    private double totalParts;
    private double elrValue;
    private double percentage;
    private double personalCSIValue;
    private double departmentCSIValue;
    private double csiPay;
    private double totalGross;
    
    public Sales(String month, int year, double customerLabor, double warrantyLabor,
                 double internalLabor, double customerParts, double warrantyParts,
                 double internalParts, double elrValue, double personalCSIValue,
                 double departmentCSIValue) {
        this.month = month;
        this.year = year;
        this.customerLabor = customerLabor;
        this.warrantyLabor = warrantyLabor;
        this.internalLabor = internalLabor;
        this.totalLabor = getTotalLabor();
        this.customerParts = customerParts;
        this.warrantyParts = warrantyParts;
        this.internalParts = internalParts;
        this.totalParts = getTotalParts();
        this.elrValue = elrValue;
        this.personalCSIValue = personalCSIValue;
        this.departmentCSIValue = departmentCSIValue;
        this.totalGross = calcTotalGross();
        this.csiPay = calcCSIPay();
    }
    
    public double calcCSIPay() {
        double pay = 0;
        if (personalCSIValue >= 92.0) {
            pay += 500;
        } else {
            pay -= 500;
        }
        if (departmentCSIValue >= 92.0) {
            pay += 250;
        } else {
            pay -= 250;
        }
        return pay;
    }
    
    public double calcTotalGross() {
        double laborPay = totalLabor * percentage;
        double partsPay = totalParts * 0.06;
        double salary = 1000.00;
        return laborPay + partsPay + salary + csiPay;
    }
    
    public double getTotalGross() {
        return totalGross;
    }
    
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public double getCustomerLabor() {
        return customerLabor;
    }
    
    public void setCustomerLabor(double customerLabor) {
        this.customerLabor = customerLabor;
    }
    
    public double getWarrantyLabor() {
        return warrantyLabor;
    }
    
    public void setWarrantyLabor(double warrantyLabor) {
        this.warrantyLabor = warrantyLabor;
    }
    
    public double getInternalLabor() {
        return internalLabor;
    }
    
    public void setInternalLabor(double internalLabor) {
        this.internalLabor = internalLabor;
    }
    
    public double getTotalLabor() {
        return customerLabor + warrantyLabor + internalLabor;
    }
    
    public void setTotalLabor(double totalLabor) {
        this.totalLabor = totalLabor;
    }
    
    public double getCustomerParts() {
        return customerParts;
    }
    
    public void setCustomerParts(double customerParts) {
        this.customerParts = customerParts;
    }
    
    public double getWarrantyParts() {
        return warrantyParts;
    }
    
    public void setWarrantyParts(double warrantyParts) {
        this.warrantyParts = warrantyParts;
    }
    
    public double getInternalParts() {
        return internalParts;
    }
    
    public void setInternalParts(double internalParts) {
        this.internalParts = internalParts;
    }
    
    public double getTotalParts() {
        return customerParts + warrantyParts + internalParts;
    }
    
    public void setTotalParts(double totalParts) {
        this.totalParts = totalParts;
    }
    
    public double getElrValue() {
        return elrValue;
    }
    
    public void setElrValue(double elrValue) {
        this.elrValue = elrValue;
    }
    
    public double getPercentage() {
        return percentage;
    }
    
    private void setPercentage() {
        if (elrValue >= 95.0) {
            percentage = 0.09;
        } else if (elrValue >= 90.0) {
            percentage = 0.085;
        } else if (elrValue >= 85.0) {
            percentage = 0.08;
        } else {
            percentage = 0.06;
        }
    }
    
    public double getPersonalCSIValue() {
        return personalCSIValue;
    }
    
    public void setPersonalCSIValue(double personalCSIValue) {
        this.personalCSIValue = personalCSIValue;
    }
    
    public double getDepartmentCSIValue() {
        return departmentCSIValue;
    }
    
    public void setDepartmentCSIValue(double departmentCSIValue) {
        this.departmentCSIValue = departmentCSIValue;
    }
    
    
}
