package com.mikemillar.hondaPaycheckEstimator.payModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SalesData {
    
    private static SalesData instance = new SalesData();
    private static String filename;
    
    private SalesData() {}
    
    public static SalesData getInstance() {
        return instance;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filen) {
        filename = filen;
    }
    
    public Sales loadSales(Path path) throws IOException {
        BufferedReader br = Files.newBufferedReader(path);
        String input;
        
        try {
            while ((input = br.readLine()) != null) {
                String[] salesPiece = input.split("\n");
                String month = salesPiece[0];
                int year = Integer.parseInt(salesPiece[1]);
                double custLabor = Double.parseDouble(salesPiece[2]);
                double warrLabor = Double.parseDouble(salesPiece[3]);
                double intLabor = Double.parseDouble(salesPiece[4]);
                double custParts = Double.parseDouble(salesPiece[6]);
                double warrParts = Double.parseDouble(salesPiece[7]);
                double intParts = Double.parseDouble(salesPiece[8]);
                double elr = Double.parseDouble(salesPiece[10]);
                double personCSI = Double.parseDouble(salesPiece[12]);
                double departCSI = Double.parseDouble(salesPiece[13]);
                
                Sales sales = new Sales(month, year, custLabor, warrLabor,
                        intLabor, custParts, warrParts, intParts, elr, personCSI, departCSI);
                return sales;
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return null;
    }
    
    public void saveSales(Sales sales, Path path) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            bw.write(String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",
                    sales.getMonth(),
                    sales.getCustomerLabor(),
                    sales.getWarrantyLabor(),
                    sales.getInternalLabor(),
                    sales.getCustomerParts(),
                    sales.getWarrantyParts(),
                    sales.getInternalParts(),
                    sales.getElrValue(),
                    sales.getPersonalCSIValue(),
                    sales.getDepartmentCSIValue()));
            bw.newLine();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
    
}
