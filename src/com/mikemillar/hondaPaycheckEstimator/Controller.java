package com.mikemillar.hondaPaycheckEstimator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class Controller {
    
    @FXML private TextField cpLaborField;
    @FXML private TextField wLaborField;
    @FXML private TextField iLaborField;
    @FXML private Label totalLaborLabel;
    @FXML private TextField cpPartsField;
    @FXML private TextField wPartsField;
    @FXML private TextField iPartsField;
    @FXML private Label totalPartsLabel;
    @FXML private TextField elrField;
    @FXML private TextField pCSIField;
    @FXML private TextField dCSIField;
    @FXML private Label laborPercentLabel;
    @FXML private Label cCSIPayField;
    @FXML private Label dCSIPayField;
    @FXML private Label totalGrossLabel;
    private double laborPercentage = 0.06;
    
    public void initialize() {
    
    }
    
    @FXML
    public void updateLaborTotal() {
        double totalLabor = getTotalLabor();
        totalLaborLabel.setText("$" + totalLabor);
        updateGross();
    }
    
    @FXML
    public void updatePartsTotal() {
        double totalParts = getTotalParts();
        totalPartsLabel.setText("$" + totalParts);
        updateGross();
    }
    
    public double getTotalLabor() {
        double cpLabor = toNumber(cpLaborField.getText());
        double wLabor = toNumber(wLaborField.getText());
        double iLabor = toNumber(iLaborField.getText());
        return  cpLabor + wLabor + iLabor;
    }
    
    public double getTotalParts() {
        double cpParts = toNumber(cpPartsField.getText());
        double wParts = toNumber(wPartsField.getText());
        double iParts = toNumber(iPartsField.getText());
        return cpParts + wParts + iParts;
    }
    
    @FXML
    public void updatePercentageLabel() {
        // parse elr value and compare values.
        // Set label value accordingly
        double ELR = toNumber(elrField.getText());
        String percentage;
        if (ELR >= 95.0) {
            laborPercentage = 0.09;
            percentage = "9%";
        } else if (ELR >= 93.0) {
            laborPercentage = 0.085;
            percentage = "8.5%";
        } else if (ELR >= 90.0) {
            laborPercentage = 0.08;
            percentage = "8%";
        } else {
            laborPercentage = 0.06;
            percentage = "6%";
        }
        laborPercentLabel.setText(percentage);
        updateGross();
    }
    
    @FXML
    public void updatePersonalCSI() {
        // parse elr value and compare values
        // update label value accordingly
        double score = toNumber(pCSIField.getText());
        String pay;
        if (score >= 92.0) {
            pay = "500";
        } else {
            pay = "-500";
        }
        cCSIPayField.setText(pay);
        updateGross();
    }
    
    @FXML
    public void updateDepartmentCSI() {
        // parse elr value and compare values
        // update label value accordingly
        double score = toNumber(pCSIField.getText());
        String pay;
        if (score >= 92.0) {
            pay = "250";
        } else {
            pay = "-250";
        }
        dCSIPayField.setText(pay);
        updateGross();
    }
    
    @FXML
    public void updateGross() {
        double labor = getTotalLabor();
        double parts = getTotalParts();
        double laborPay = labor * laborPercentage;
        double partsPay = parts * 0.06;
        double salary = 1000.00;
        double csi = toNumber(cCSIPayField.getText()) + toNumber(dCSIPayField.getText());
        double totalGross = laborPay + partsPay + salary + csi;
        String s = ""+totalGross;
        totalGrossLabel.setText(s);
    }
    
    public double toNumber(String s) {
        // parse string and pull out double
        if (s.isEmpty()) {
            return 0;
        } else {
            return Double.parseDouble(s);
        }
    }
    
    @FXML
    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            // run all functions
            Object source = keyEvent.getSource();
            if (source == cpLaborField || source == wLaborField || source == iLaborField) {
                updateLaborTotal();
            } else if (source == cpPartsField || source == wPartsField || source == iPartsField) {
                updatePartsTotal();
            } else if (source == elrField) {
                updatePercentageLabel();
            } else if (source == pCSIField) {
                updatePersonalCSI();
            } else if (source == dCSIField) {
                updateDepartmentCSI();
            }
        }
    }
    
    @FXML
    public void handleExit() {
        Platform.exit();
    }
}
