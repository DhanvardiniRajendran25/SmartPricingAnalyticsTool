/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Sales;

import java.util.Map;
import javax.swing.table.DefaultTableModel;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;

/**
 *
 * @author keerthichandrakanth
 */
public class runSimulation extends javax.swing.JPanel {
    
      private Map<String, Order> orders;

    /**
     * Creates new form runSimulation
     */
    public runSimulation(Map<String, Order> orders) {
        this.orders = orders;
        initComponents();
        
         runSimulation();
         
    }
    
     public void runSimulation() {

        DefaultTableModel model = (DefaultTableModel) tblRunSimulation.getModel();
        model.setRowCount(0);
        
         double maxRevenueImpact = Double.MIN_VALUE;
             Object[] bestAdjustmentRow = null; 
                
          for (Order order : orders.values()) {
            for (OrderItem item : order.getOrderitems()) {
            
            // Step 3: Calculate initial revenue and price performance
             double initialRevenue = (item.getActualPrice()) * item.getQuantity();
             double pricePerformanceTotal = item.calculatePricePerformanceforSingleQuantity();
           System.out.println("Initial Revenue: " + initialRevenue);
           System.out.println("Initial Price Performance: " + pricePerformanceTotal);
           
           // Step 4: Adjust target price based on sales performance (10% adjustment)
          double adjustmentPercentage;
           int newTargetPrice;
          
          if (item.getActualPrice() >= item.getSelectedProduct().getTargetPrice()) {
                // Sales above or equal to target -> increase target price
                adjustmentPercentage = (double)(item.getSelectedProduct().getCeilingPrice()  - item.getSelectedProduct().getTargetPrice()) / 
                        (item.getSelectedProduct().getCeilingPrice()  - item.getSelectedProduct().getFloorPrice());
                newTargetPrice = (int) (item.getSelectedProduct().getTargetPrice() + adjustmentPercentage * (item.getSelectedProduct().getCeilingPrice()  - item.getSelectedProduct().getTargetPrice()));
                newTargetPrice = Math.min(newTargetPrice, item.getSelectedProduct().getCeilingPrice() ); // Ensure new target doesn't exceed ceiling
            } else {
                // Sales below target -> lower target price
                adjustmentPercentage = (double)(item.getSelectedProduct().getTargetPrice() - item.getSelectedProduct().getFloorPrice()) / (item.getSelectedProduct().getCeilingPrice()  - item.getSelectedProduct().getFloorPrice());
                newTargetPrice = (int) (item.getSelectedProduct().getTargetPrice() - adjustmentPercentage * (item.getSelectedProduct().getTargetPrice() - item.getSelectedProduct().getFloorPrice()));
                newTargetPrice = Math.max(newTargetPrice, item.getSelectedProduct().getFloorPrice()); // Ensure new target doesn't go below floor
            }
          
           
            double percentageIncrease = ((double)(newTargetPrice - item.getSelectedProduct().getTargetPrice()) / item.getSelectedProduct().getTargetPrice()) * 100;
            System.out.println("Percentage Increase in Target Price: " + percentageIncrease + "%");
          

           
           // Ensure new target price stays within floor and ceiling limits
           // newTargetPrice = Math.max(item.getSelectedProduct().getFloorPrice(), Math.min(newTargetPrice, item.getSelectedProduct().getCeilingPrice()));

            System.out.println("New Target Price: " + newTargetPrice);

              // Step 5: Calculate new revenue based on adjusted target price
              double newRevenue = newTargetPrice * item.getQuantity();
              
              
              // Step 6: Calculate revenue impact
            double revenueImpact = newRevenue - initialRevenue;

            System.out.println("New Revenue: " + newRevenue);
            
           
                Object[] row = new Object[11];                                 
                
                row[0] = item.getSelectedProduct().getName();
                row[1] = item.getSelectedProduct().getFloorPrice();
                row[2] = item.getSelectedProduct().getCeilingPrice();                         
                row[3] = item.getSelectedProduct().getTargetPrice();
                row[4] = item.getQuantity();
                row[5] = item.getActualPrice();
                row[6] = initialRevenue;
                row[7] = newTargetPrice;
                row[8] = String.format("%.2f%%", percentageIncrease);
                row[9] = newRevenue;
                row[10] = revenueImpact;
                                               
                model.addRow(row);

                System.out.println("Revenue Impact: " + revenueImpact);                                                    

           // Add the updated row to the table model
           // Track the best adjustment with highest revenue impact
                if (revenueImpact > maxRevenueImpact) {
                    maxRevenueImpact = revenueImpact;
                    bestAdjustmentRow = row;
                }
            
                
 
        }
    }
            // After all simulations, add the row with the highest revenue impact to the table
           /*if (bestAdjustmentRow != null) {
               // Add a label indicating this row had the highest impact
               model.addRow(new Object[] { "Best Adjustment", "-", "-", "-", "-", "-", "-", "-", "-", maxRevenueImpact });
           }*/
           
           // Update the text field with the highest impact value
                    txtHighestImpact.setText(String.format("Target adjustments yielding the highest impact of:  %.2f ", maxRevenueImpact));

     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblRunSimulation = new javax.swing.JTable();
        txtHighestImpact = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        tblRunSimulation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ProductName", "Floor Price", "Ceiling Price", "Target Price", "Quantity", "Actual Price", "Initial Revenue", "New Target Price", "% after Adjustment", "New Revenue", "Revenue Impact"
            }
        ));
        tblRunSimulation.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tblRunSimulation);

        txtHighestImpact.setEditable(false);
        txtHighestImpact.setFont(new java.awt.Font("Helvetica Neue", 3, 14)); // NOI18N
        txtHighestImpact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHighestImpactActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel1.setText("Assess the impact of adjusted prices on company sales revenue");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(362, 362, 362))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtHighestImpact, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(370, 370, 370))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(txtHighestImpact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtHighestImpactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHighestImpactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHighestImpactActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRunSimulation;
    private javax.swing.JTextField txtHighestImpact;
    // End of variables declaration//GEN-END:variables
}
