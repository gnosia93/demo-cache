package com.amazon.democache.service;

import com.amazon.democache.entity.Order;
import com.amazon.democache.entity.Product;
import com.amazon.democache.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class OrderService {

    @Autowired OrderRepository orderRepository;
    @Autowired ProductService productService;
    @Autowired MemoryDBService memoryService;
    @Autowired KafkaTemplate kafkaTemplate;

   // @Autowired QueueMessagingTemplate messagingTemplate;
   // final static String SQS_TOPIC   = "OrderQueue";

    final static String KAFKA_TOPIC = "ocktank";


    public Page<Order> findAll(Pageable page) {
        Page<Order> retPage = orderRepository.findAll(page);
      //  retPage.
        return retPage;
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }


    public Order save(Order order) {
        productService.addBuyCount(order.getProductId());
        Order retOrder = doOrder(order);
        return retOrder;

        // doOrder 부터 호출하면 데드락
        // addBuyCount 부터 호출하면 데드락을 피한다. 이해할 수 없다.

    }

    // when database error happens, this method made exception, there is no elasticache update
    // and if cache service is down.. also this is no insert into database.
    //
    public Order eventSave(Order order) {
    //    memoryService.addProductBuyCount(order.getProductId());
        Order retOrder = doOrder(order);
        memoryService.addProductBuyCount(order.getProductId());
        return retOrder;
    }

    private Order doOrder(Order order) {
        Optional<Product> optProduct = productService.findById(order.getProductId());
        Order newOrder = Order.builder()
                .productId(order.getProductId())
                .orderPrice(0)
                .orderPrice(optProduct.get().getPrice())
                .thumImageUrl(optProduct.get().getImageUrl())
                .build();

        Order savedOrder = orderRepository.save(newOrder);

        //kafkaTemplate.send(KAFKA_TOPIC, savedOrder.toOrderMessage());
        //messagingTemplate.convertAndSend(SQS_TOPIC, "this is test..");


        return savedOrder;
    }




}
