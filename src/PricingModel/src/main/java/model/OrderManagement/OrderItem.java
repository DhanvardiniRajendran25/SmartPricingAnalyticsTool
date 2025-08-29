/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.OrderManagement;

import model.ProductManagement.Product;

/**
 *
 * @author kal bugrara
 */
public class OrderItem {

    Product selectedproduct;
    int actualPrice;
    int quantity;

    public OrderItem(Product p, int paidprice, int q) {
        selectedproduct = p;
        p.addOrderItem(this); //make sure product links back to the item
        quantity = q;
        this.actualPrice = paidprice;
    }

    public int getOrderItemTotal() {
        return actualPrice * quantity;
    }

//The following calculates what the price gain would have been if products were sold at target price
    public int getOrderItemTargetTotal() {
        return selectedproduct.getTargetPrice() * quantity;
    }

    //returns positive if seller is making higher margin than target
    //returns negative if seller is making lower margin than target
    //otherwise zero meaning neutral
    public int calculatePricePerformance() {
        return (actualPrice - selectedproduct.getTargetPrice()) * quantity;
    }

     public int calculatePricePerformanceforSingleQuantity() {
        return (actualPrice - selectedproduct.getTargetPrice());
    }
     
   public String calculatePricePerformancePercentage() {
    double targetPrice = selectedproduct.getTargetPrice();
    
    // Check for division by zero
    if (targetPrice == 0) return "0.00%";
    
    // Calculate the performance percentage
    double percentage = ((actualPrice - targetPrice) / targetPrice) * 100;
    
    // Format the result to two decimal places
    return String.format("%.2f%%", percentage);
}
     
    public boolean isActualAboveTarget() {
        if (actualPrice > selectedproduct.getTargetPrice()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isActualBelowTarget() {
        if (actualPrice < selectedproduct.getTargetPrice()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isActualATTarget() {
        if (actualPrice == selectedproduct.getTargetPrice()) {
            return true;
        } else {
            return false;
        }

    }

    public Product getSelectedProduct() {
        return selectedproduct;
    }

    public int getActualPrice() {
        return actualPrice;

    }

    public int getQuantity() {
        return quantity;
    }
    
       @Override
    public String toString() {
        return selectedproduct.getName() ; //To change body of generated methods, choose Tools | Templates.
    }
    
}
