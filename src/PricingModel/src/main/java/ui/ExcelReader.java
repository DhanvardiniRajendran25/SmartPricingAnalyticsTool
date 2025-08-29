/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.Order;
import model.Personnel.Person;
import model.ProductManagement.Product;
import model.Supplier.Supplier;


/**
 *
 * @author keerthichandrakanth
 */
public class ExcelReader {
    
    private static FileInputStream getFileInputStream() throws IOException {
        // Adjust the resource path as it is not in the default 'resources' folder
        String relativePath = "src/resource/Pricing_Model_Data.xlsx";
        File file = new File(relativePath);
        if (!file.exists()) {
            throw new IOException("Resource file 'Pricing_Model_Data.xlsx' not found at " + file.getAbsolutePath());
        }
        return new FileInputStream(file);
    }

     public Map<String, Supplier> loadSuppliers() {
        Map<String, Supplier> suppliers = new HashMap<>();
        try (FileInputStream file = getFileInputStream(); Workbook workbook = new XSSFWorkbook(file)) {

            // Print all available sheet names
            System.out.println("Available sheets:");
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                System.out.println(" - " + workbook.getSheetName(i));
            }

            // Attempt to load the "Suppliers" sheet
            Sheet sheet = workbook.getSheet("Supplier");
            
            if (sheet == null) {
                System.err.println("Sheet 'Suppliers' not found. Please check the sheet name.");
                return suppliers;
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                String supplierName = row.getCell(0).getStringCellValue();
                suppliers.put(supplierName, new Supplier(supplierName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
      
   public Map<String, CustomerProfile> loadCustomers() {
        Map<String, CustomerProfile> customers = new HashMap<>();
        try (FileInputStream file = getFileInputStream(); Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("Customers");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String customerId = row.getCell(0).getStringCellValue();
                customers.put(customerId, new CustomerProfile(new Person(customerId)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }
   
    public Map<String, Product> loadProducts(Map<String, Supplier> suppliers) {
        Map<String, Product> products = new HashMap<>();
        try (FileInputStream file = getFileInputStream(); Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("Products");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String supplierName = row.getCell(0).getStringCellValue();
                String productName = row.getCell(1).getStringCellValue();
                int floorPrice = (int) row.getCell(2).getNumericCellValue();
                int ceilingPrice = (int) row.getCell(3).getNumericCellValue();
                int targetPrice = (int) row.getCell(4).getNumericCellValue();
                int targetSales = (int) row.getCell(5).getNumericCellValue();

                Supplier supplier = suppliers.get(supplierName);
                Product product = new Product(productName, floorPrice, ceilingPrice, targetPrice, targetSales);
                product.setTargetSales(targetSales);
                products.put(productName, product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public Map<String, Order> loadOrders(Map<String, CustomerProfile> customers) {
        Map<String, Order> orders = new HashMap<>();
        try (FileInputStream file = getFileInputStream(); Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("Order");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String orderId = row.getCell(0).getStringCellValue();
                String customerId = row.getCell(1).getStringCellValue();
                CustomerProfile customer = customers.get(customerId);

                if (customer != null) {
                    Order order = new Order(customer);
                    order.setOrderID(orderId);
                    order.status = row.getCell(4).getStringCellValue();
                    orders.put(orderId, order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void loadOrderItems(Map<String, Order> orders, Map<String, Product> products) {
        try (FileInputStream file = getFileInputStream(); Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("OrderItem");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String orderId = row.getCell(0).getStringCellValue();
                String productName = row.getCell(1).getStringCellValue();
                int quantity = (int) row.getCell(2).getNumericCellValue();
                int actualPrice = (int) row.getCell(3).getNumericCellValue();

                Order order = orders.get(orderId);
                Product product = products.get(productName);

                if (order != null && product != null) {
                    order.newOrderItem(product, actualPrice, quantity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

