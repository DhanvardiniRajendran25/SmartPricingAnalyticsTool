/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.Order;
import model.ProductManagement.Product;
import model.Supplier.Supplier;

import java.util.Map;
import javax.swing.SwingUtilities;
import model.OrderManagement.OrderItem;

/**
 *
 * @author kal bugrara
 */
public class PricingModel {

  /**
   * @param args the command line arguments
   */
    
    private ExcelReader excelReader;
    private Map<String, Supplier> suppliers;
    private Map<String, CustomerProfile> customers;
    private Map<String, Product> products;
    private Map<String, Order> orders;

     // Constructor to initialize or load the data (for simplicity, assuming data is loaded)
    public PricingModel() {
        // Initialize and load data (you already have the ExcelReader class for this)
        ExcelReader excelReader = new ExcelReader();
        suppliers = excelReader.loadSuppliers();
        customers = excelReader.loadCustomers();
        products = excelReader.loadProducts(suppliers);
        orders = excelReader.loadOrders(customers);
        excelReader.loadOrderItems(orders, products);
    }
    
     // Getter methods for Swing UI to access data

    public Map<String, Supplier> getAllSuppliers() {
        return suppliers;
    }

    public Map<String, CustomerProfile> getAllCustomers() {
        return customers;
    }

    public Map<String, Product> getAllProducts() {
        return products;
    }

    public Map<String, Order> getAllOrders() {
        return orders;
    }
    
     // Command-line output of the data
    public void displayDataInCommandLine() {
        // Display Suppliers
        for (Order order : orders.values()) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Customer: " + order.customer);
            System.out.println("Status: " + order.status);
            System.out.println("Order Total: " + order.getOrderTotal());

            System.out.println("Order Items:");
            for (OrderItem item : order.orderitems) {
                System.out.println("  Product: " + item.getSelectedProduct().getName());
                System.out.println("  Quantity: " + item.getQuantity());
                System.out.println("  Actual Price: " + item.getActualPrice());
                System.out.println("  Item Total: " + item.getOrderItemTotal());
                System.out.println("  Price Performance: " + item.calculatePricePerformance());
            }
            System.out.println("---------------------------------------------------");
        }
  
    }
    
    // Method to launch the Swing-based GUI
  /*  public void launchSwingGUI() {
        // Create and show the MainJFrame GUI
        SwingUtilities.invokeLater(() -> {
            MainJFrame frame = new MainJFrame(this);
            frame.setVisible(true);
        });
    }*/

    // Main method: Choose between Command Line or GUI
    /*public static void main(String[] args) {
        PricingModel pricingModel = new PricingModel();
        pricingModel.displayDataInCommandLine();
     
      }*/
    public static void main(String[] args) {
   
     java.awt.EventQueue.invokeLater(new Runnable() {
          PricingModel pricingModel = new PricingModel();
            public void run() {
                new MainJFrame(pricingModel);
            }
        });
    
    PricingModel pricingModel = new PricingModel();
        pricingModel.displayDataInCommandLine();
  }

    
  
  }
  
  




























