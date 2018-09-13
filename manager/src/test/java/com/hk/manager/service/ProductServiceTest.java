package com.hk.manager.service;

import com.hk.entity.Product;
import com.hk.manager.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author 何康
 * @date 2018/9/13 11:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;
    @Test
    public void findOne() throws Exception {
        Product product = productRepository.findById("123").orElse(null);
        log.info("结果：product={}",product);
    }

    @Test
    public void addProduct() {
        Product product = new Product();
        product.setStatus("AUDITING");
        product.setId("154545");
        product.setLockTerm(0);
        product.setStepAmount(BigDecimal.valueOf(1));
        product.setThresholdMount(BigDecimal.valueOf(1000));
        product.setRewardRate(BigDecimal.valueOf(3.45));
        product.setName("平安保险");
        Product result = productService.addProduct(product);
        log.info("结果：result={}",result);
    }

}