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

import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.Order;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.matchers.StringContains;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {
    
    public OrderQueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        orderQueue.add(order);
        
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    @Test
    public void testWhenNeitherCustomerExitsNorCustomerNameExists() {
        OrderQueue orderQueue = new OrderQueue();
     try
     {
        Order order = new Order("", "");
        orderQueue.add(order);
        fail("Exception Customer ID or Customer name Doesn't Exits");
    }
     catch(Exception e) {
      assertThat(e.getMessage(),StringContains.containsString("Customer ID or Customer name Doesn't Exits"));
     }
    }
    @Test
    public void testOrderArrivesNoPurchases(){
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        try{
            orderQueue.add(order);
            fail("Exception New Order arrvies there is no list of purchases");
        }
        catch(Exception e) {
            assertThat(e.getMessage(),StringContains.containsString("New Order arrvies there is no list of purchases"));
            
        }
    } 
    @Test
    public void testNextOrderWhenOrdersInSystemReturnOrderEarliestTimeReceivedNotHaveProcessed() throws Exception{
        OrderQueue orderQueue = new OrderQueue();
        
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        orderQueue.add(order);
        
        
        Order order1 = new Order ("CUST00002", "BCD Construction");
        order1.addPurchase(new Purchase(04, 450));
        order1.addPurchase(new Purchase(06, 250));
        orderQueue.add(order1);
        
        Order expResult = order;
        Order result = orderQueue.nextOrder();
       assertEquals(expResult,result);  
        }
    @Test
    public void  testNextOrderNoOdersInTheSystemReturnNull()
    {
        OrderQueue orderQueue = new OrderQueue();
        Order expResult = null;
        Order result = orderQueue.nextOrder();
        assertEquals(expResult,result); 
        
        
    }
    @Test
    public void testProcessOrderWhenTheOrderHasTimeReceivedSetTheTimeProcessedNow() throws Exception
    {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        orderQueue.add(order);
        
        orderQueue.processOrder(orderQueue.nextOrder());
        
        long expResult = new Date().getTime();
        long result = order.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    @Test
    public void testWhenOrderDoesNotHaveTimeReceivedThrowException()
    {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        boolean flag = false;
        
        try {
            orderQueue.processOrder(order);
        } catch(Exception e) {
          assertThat(e.getMessage(),StringContains.containsString("Order must have a time recieved to be processed"));
            flag = true;
        }
        
        assertTrue(flag);
    }
    @Test
    public void testWhenOrderTimeProcessedAndTimeReceivedTimeFulfilledNow() throws Exception
    {
        boolean flag = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        orderQueue.add(order);
        try{
        orderQueue.fulfilledOrder(orderQueue.nextOrder());
        }
      catch(Exception e) {
      //assertThat(e.getMessage(),StringContains.containsString("order has a time processed and a time received and all of the purchases are in-stock in the inventory table"));
      flag = true;
        }
        assertTrue(flag);
    }
    @Test
        public void testWhenfulfillOrderWhenTheOrderDoesNotTimeProcessed(){
        boolean flag = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        try{
            orderQueue.processOrder(order);
            orderQueue.fulfilledOrder(orderQueue.nextOrder());
        }
        catch(Exception err) {
         //assertThat(e.getMessage(),StringContains.containsString(" "));
         flag = true;
         }
        assertTrue(flag);
        }
    @Test
      public void testReportWhenNoOrdersInTheSystemThenReturnEmptyString(){
        OrderQueue orderQueue = new OrderQueue();
        String expResult = "";
        String result = orderQueue.orderReport();
        assertEquals(expResult, result);
     
      }
      
      @Test
      public void testOrdersInTheSystemToJSONFormat() throws Exception
      {
        OrderQueue orderQueue = new OrderQueue();
        
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(04, 450));
        order.addPurchase(new Purchase(06, 250));
        orderQueue.add(order);
        
        
        Order order1 = new Order ("CUST00002", "BCD Construction");
        order1.addPurchase(new Purchase(04, 450));
        order1.addPurchase(new Purchase(06, 250));
        orderQueue.add(order1);
        
        Date currentDate = new Date();
        
        String expResult = "{ \"orders\" : [\n" +
                                " { \"customerId\" : \"CUST00001\",\n" +
                                " \"customerName\" : \"ABC Construction\",\n" +
                                " \"timeReceived\" : \"" + currentDate + "\",\n" +
                                " \"timeProcessed\" : \"" + currentDate + "\",\n" +
                                " \"timeFulfilled\" : \"" + currentDate + "\",\n" +
                                " \"purchases\" : [\n" +
                                " { \"productId\" : 04, \"quantity\" : 450 }\n" +
                                " ],\n" +
                                " \"notes\" : \"\"\n" +
                                " },\n" +
                                " { \"customerId\" : \"CUST00002\",\n" +
                                " \"customerName\" : \"ABD Construction\",\n" +
                                " \"timeReceived\" : \"" + currentDate + "\",\n" +
                                " \"timeProcessed\" : \"" + currentDate + "\",\n" +
                                " \"timeFulfilled\" : \"" + currentDate + "\",\n" +
                                " \"purchases\" : [\n" +
                                " { \"productId\" : 06, \"quantity\" : 250 },\n" +
                                " ],\n" +
                                " \"notes\" : \"\"\n" +
                                " }\n" +
                            "] }";
        
        String result = orderQueue.orderReport();
        assertEquals(expResult,result);
          
      }
        
    }
    
        
    

