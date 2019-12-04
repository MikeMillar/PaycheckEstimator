package com.mikemillar.hondaPaycheckEstimator;

import com.mikemillar.hondaPaycheckEstimator.payModel.Sales;
import com.mikemillar.hondaPaycheckEstimator.payModel.SalesData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class Controller {
    
    private Sales sales;
    @FXML private BorderPane mainBorderPane;
    @FXML private TextField monthField;
    @FXML private TextField yearField;
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
    public void newEstimateSheet() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Open new?");
        alert.setHeaderText("Opening new estimate will cause any un-saved data to be lost.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            clearInputs();
        }
    }
    
    public void clearInputs() {
        monthField.clear();
        yearField.clear();
        cpLaborField.clear();
        wLaborField.clear();
        iLaborField.clear();
        totalLaborLabel.setText("$0.00");
        cpPartsField.clear();
        wPartsField.clear();
        iPartsField.clear();
        totalPartsLabel.setText("$0.00");
        elrField.clear();
        laborPercentLabel.setText("6%");
        pCSIField.clear();
        cCSIPayField.setText("$0.00");
        dCSIField.clear();
        dCSIPayField.setText("$0.00");
        totalGrossLabel.setText("$0.00");
    }
    
    public void loadSales() throws IOException {
        // implement method to load pre-existing sales
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File to Load...");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"));
        File file = fc.showOpenDialog(mainBorderPane.getScene().getWindow());
        if (file != null) {
            sales = SalesData.getInstance().loadSales(file);
            populateFields();
        } else {
            System.out.println("File chooser cancelled.");
        }
    }
    
    public void saveSales() throws IOException {
        // implement method to save current sales
        sales = new Sales(monthField.getText(), Integer.parseInt(yearField.getText()),
                Double.parseDouble(cpLaborField.getText()), Double.parseDouble(wLaborField.getText()),
                Double.parseDouble(iLaborField.getText()), Double.parseDouble(cpPartsField.getText()),
                Double.parseDouble(wPartsField.getText()), Double.parseDouble(iPartsField.getText()),
                Double.parseDouble(elrField.getText()), Double.parseDouble(pCSIField.getText()),
                Double.parseDouble(dCSIField.getText()));
        FileChooser fc = new FileChooser();
        fc.setTitle("Save File...");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"));
        File file = fc.showSaveDialog(mainBorderPane.getScene().getWindow());
        if (file != null) {
            SalesData.getInstance().saveSales(sales, file);
        } else {
            System.out.println("File chooser cancelled.");
        }
    }
    
    @FXML
    public void populateFields() {
        // Sets all UI fields = to loaded values;
        monthField.setText(sales.getMonth());
        yearField.setText(""+sales.getYear());
        cpLaborField.setText(""+sales.getCustomerLabor());
        wLaborField.setText(""+sales.getWarrantyLabor());
        iLaborField.setText(""+sales.getInternalLabor());
        updateLaborTotal();
        cpPartsField.setText(""+sales.getCustomerParts());
        wPartsField.setText(""+sales.getWarrantyParts());
        iPartsField.setText(""+sales.getInternalParts());
        updatePartsTotal();
        elrField.setText(""+sales.getElrValue());
        updatePercentageLabel();
        pCSIField.setText(""+sales.getPersonalCSIValue());
        updatePersonalCSI();
        dCSIField.setText(""+sales.getDepartmentCSIValue());
        updateDepartmentCSI();
        updateGross();
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
        } else if (ELR >= 90.0) {
            laborPercentage = 0.085;
            percentage = "8.5%";
        } else if (ELR >= 85.0) {
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
