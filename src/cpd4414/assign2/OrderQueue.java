/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cpd4414.assign2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueue  {
    Queue<Order> orderQueue = new ArrayDeque<>();
    
    public void add(Order order) throws Exception {
        if(order.getCustomerId().isEmpty() && order.getCustomerName().isEmpty()){
         throw new Exception("Customer ID or Customer name Doesn't Exits");
            }
        if(order.getListOfPurchases().isEmpty()){
            throw new Exception("New Order arrvies there is no list of purchases");
        }
        orderQueue.add(order);
        order.setTimeReceived(new Date());
    }

    Order nextOrder() {
        
        //To change body of generated methods, choose Tools | Templates.
        if(orderQueue.isEmpty())
        {
            return null;
        }
        else
        {
            return orderQueue.peek();
        }
}

    void processOrder(Order order) throws Exception {
       boolean flag = true;
        if(order.getTimeReceived() != null)
        {
            for(int i = 0; i < order.getListOfPurchases().size(); i++){
                flag = Stock(order.getListOfPurchases().get(i).getQuantity(), order.getListOfPurchases().get(i).getProductId());
            }
            if(flag){
                order.setTimeProcessed(new Date());}
        }
        else{
            throw new Exception("Order must have a time recieved to be processed");
        }
    }

    private boolean Stock(int quantity, int productId) {
     //To change body of generated methods, choose Tools | Templates.
        boolean inStock = true;
        if(quantity > Inventory.getQuantityForId(productId)){
            inStock = false;
        }
        return inStock;
    }

    void fulfilledOrder(Order nextOrder) throws Exception {
         //To change body of generated methods, choose Tools | Templates.
        boolean inStock = true;
        if(nextOrder.getTimeReceived() != null){
            if(nextOrder.getTimeProcessed() != null){
                for(int i = 0; i < nextOrder.getListOfPurchases().size(); i++){
                    inStock = Stock(nextOrder.getListOfPurchases().get(i).getQuantity(), nextOrder.getListOfPurchases().get(i).getProductId());
                }
                if(inStock){
                    nextOrder.setTimeFulfilled(new Date());
                }
            }
            else{
                throw new Exception("order has a time processed and a time received and all of the purchases are in-stock in the inventory table");
            }
        }
        else{
            throw new Exception("when the order does not have a time received, then throw an exception");
        }
    }
  //Incomplete One 
    String orderReport() {
        String report = "";
        return report;
    }
    
}