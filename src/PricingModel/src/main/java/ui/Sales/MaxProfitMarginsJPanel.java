/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Sales;

import java.util.Map;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;

/**
 *
 * @author rdhan
 */
public class MaxProfitMarginsJPanel extends javax.swing.JPanel {
    
    private Map<String, Order> orders;
    private static final double ADJUSTMENT_STEP = 0.01;
    private static final int MAX_ITERATIONS = 100;
    private static final double CONVERGENCE_THRESHOLD = 0.001;

    /**
     * Creates new form MaxProfitMarginsJPanel
     */
    public MaxProfitMarginsJPanel(Map<String, Order> orders) {
        this.orders = orders;
        initComponents();
        Map<String, Double> optimizedPrices = optimizeProfitMargins();
        updateTableWithOptimizedPrices(optimizedPrices);
    }
    
    public Map<String, Double> optimizeProfitMargins() {
        Map<String, Double> optimizedPrices = new HashMap<>();
        double previousTotalRevenue = calculateTotalRevenue();
        
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            boolean improved = false;
            
            for (Order order : orders.values()) {
                for (OrderItem item : order.getOrderitems()) {
                    double currentPrice = item.getActualPrice();
                    double optimalPrice = findOptimalPrice(item, currentPrice);
                    
                    if (optimalPrice != currentPrice) {
                        optimizedPrices.put(item.getSelectedProduct().getName(), optimalPrice);
                        improved = true;
                    } else {
                        optimizedPrices.put(item.getSelectedProduct().getName(), currentPrice);
                    }
                }
            }
            
            double newTotalRevenue = calculateTotalRevenueWithOptimizedPrices(optimizedPrices);
            double improvement = (newTotalRevenue - previousTotalRevenue) / previousTotalRevenue;
            
            if (!improved || improvement < CONVERGENCE_THRESHOLD) {
                break;
            }
            
            previousTotalRevenue = newTotalRevenue;
        }
        
        return optimizedPrices;
    }

    private void updateTableWithOptimizedPrices(Map<String, Double> optimizedPrices) {
        DefaultTableModel model = (DefaultTableModel) tblMaxProfitMargins.getModel();
        model.setRowCount(0); // Clear existing rows
        
        double totalRevenueImpact = 0;

        for (Order order : orders.values()) {
            for (OrderItem item : order.getOrderitems()) {
                String productName = item.getSelectedProduct().getName();
                double originalPrice = item.getActualPrice();
                double optimizedPrice = optimizedPrices.get(productName);
                int quantity = item.getQuantity();
                
                double initialRevenue = originalPrice * quantity;
                double newRevenue = optimizedPrice * quantity;
                double revenueImpact = newRevenue - initialRevenue;
                double percentageAdjustment = ((optimizedPrice - originalPrice) / originalPrice) * 100;

                totalRevenueImpact += revenueImpact;

                Object[] row = {
                    productName,
                    item.getSelectedProduct().getFloorPrice(),
                    item.getSelectedProduct().getCeilingPrice(),
                    item.getSelectedProduct().getTargetPrice(),
                    quantity,
                    originalPrice,
                    initialRevenue,
                    optimizedPrice,
                    String.format("%.2f%%", percentageAdjustment),
                    newRevenue,
                    revenueImpact
                };
                model.addRow(row);
            }
        }
    }

    private double findOptimalPrice(OrderItem item, double currentPrice) {
        double bestPrice = currentPrice;
        double bestRevenue = calculateRevenue(item, currentPrice);
        
        for (double price = currentPrice + ADJUSTMENT_STEP; 
             price <= item.getSelectedProduct().getCeilingPrice(); 
             price += ADJUSTMENT_STEP) {
            double revenue = calculateRevenue(item, price);
            if (revenue > bestRevenue) {
                bestRevenue = revenue;
                bestPrice = price;
            } else {
                break;
            }
        }
        
        if (bestPrice == currentPrice) {
            for (double price = currentPrice - ADJUSTMENT_STEP; 
                 price >= item.getSelectedProduct().getFloorPrice(); 
                 price -= ADJUSTMENT_STEP) {
                double revenue = calculateRevenue(item, price);
                if (revenue > bestRevenue) {
                    bestRevenue = revenue;
                    bestPrice = price;
                } else {
                    break;
                }
            }
        }
        
        return bestPrice;
    }

    private double calculateRevenue(OrderItem item, double price) {
        return price * item.getQuantity();
    }

    private double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Order order : orders.values()) {
            for (OrderItem item : order.getOrderitems()) {
                totalRevenue += calculateRevenue(item, item.getActualPrice());
            }
        }
        return totalRevenue;
    }

    private double calculateTotalRevenueWithOptimizedPrices(Map<String, Double> optimizedPrices) {
        double totalRevenue = 0;
        for (Order order : orders.values()) {
            for (OrderItem item : order.getOrderitems()) {
                String productName = item.getSelectedProduct().getName();
                double price = optimizedPrices.getOrDefault(productName, (double)item.getActualPrice());
                totalRevenue += price * item.getQuantity();
            }
        }
        return totalRevenue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMaxProfitMargins = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Maximum Profit Margin");

        tblMaxProfitMargins.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ProductName", "Floor Price", "Ceiling Price", "Target Price", "Quantity", "Original Price", "Initial Revenue", "Optimized Price", "Percentage Adjustment", "New Revenue", "Revenue Impact"
            }
        ));
        tblMaxProfitMargins.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMaxProfitMargins.setPreferredSize(new java.awt.Dimension(1500, 500));
        jScrollPane2.setViewportView(tblMaxProfitMargins);

        jScrollPane3.setViewportView(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 89, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblMaxProfitMargins;
    // End of variables declaration//GEN-END:variables
}
