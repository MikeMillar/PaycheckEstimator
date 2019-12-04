package com.mikemillar.hondaPaycheckEstimator.payModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    
    public void setFilename(String file) {
        filename = file;
    }
    
    public Sales loadSales(File file) throws IOException {
        String path = file.getPath();
        BufferedReader br = Files.newBufferedReader(Paths.get(path));
        String input;
        
        try {
            while ((input = br.readLine()) != null) {
                String[] salesPiece = input.split("\t");
                String month = salesPiece[0];
                int year = Integer.parseInt(salesPiece[1]);
                double custLabor = Double.parseDouble(salesPiece[2]);
                double warrLabor = Double.parseDouble(salesPiece[3]);
                double intLabor = Double.parseDouble(salesPiece[4]);
                double custParts = Double.parseDouble(salesPiece[5]);
                double warrParts = Double.parseDouble(salesPiece[6]);
                double intParts = Double.parseDouble(salesPiece[7]);
                double elr = Double.parseDouble(salesPiece[8]);
                double personCSI = Double.parseDouble(salesPiece[9]);
                double departCSI = Double.parseDouble(salesPiece[10]);
                
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
    
    public void saveSales(Sales sales, File file) throws IOException {
        String path = file.getPath();
        BufferedWriter bw = Files.newBufferedWriter(Paths.get(path));
        try {
            bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    sales.getMonth(),
                    sales.getYear(),
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
