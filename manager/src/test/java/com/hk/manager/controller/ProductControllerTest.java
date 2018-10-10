package com.hk.manager.controller;

import com.hk.entity.Product;
import com.hk.manager.repository.ProductRepository;
import com.hk.util.RestUtil;
import com.mysql.jdbc.util.ResultSetUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 何康
 * @date 2018/10/3 17:37
 */
@RunWith(SpringRunner.class)
//随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
//使测试用例根据名称的字典顺序去执行（先执行插入操作再执行查询操作）
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ProductRepository productRepository;

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    //正常数据
    private static List<Product> normalList = new ArrayList<>();

    //异常数据
    private static List<Product> exceptList = new ArrayList<>();

    /***
     * 初始化正常数据，在类加载之前执行，只执行一次
     */
    @BeforeClass
    public static void intiNormalList(){
        Product product1 = new Product("f000001","灵活宝1号", BigDecimal.valueOf(1000),BigDecimal.valueOf(1),0,BigDecimal.valueOf(0.2),"AUDITING");
        Product product2 = new Product("f000002","灵活宝2号", BigDecimal.valueOf(1000),BigDecimal.valueOf(1),0,BigDecimal.valueOf(0.2),"AUDITING");
        Product product3 = new Product("f000003","灵活宝3号", BigDecimal.valueOf(1000),BigDecimal.valueOf(1),0,BigDecimal.valueOf(0.2),"AUDITING");
        normalList.add(product1);
        normalList.add(product2);
        normalList.add(product3);
    }

    @BeforeClass
    public static void initExceptList(){
        Product product1 = new Product(null,"订单编号为空", BigDecimal.valueOf(1000),BigDecimal.valueOf(1),0,BigDecimal.valueOf(0.2),"AUDITING");
        Product product2 = new Product("f0000055","投资步长需为整数", BigDecimal.valueOf(1000),BigDecimal.valueOf(1.3),0,BigDecimal.valueOf(0.2),"AUDITING");
        Product product3 = new Product("f0000066","收益率范围错误", BigDecimal.valueOf(1000),BigDecimal.valueOf(1),0,BigDecimal.valueOf(0.5),"AUDITING");
        exceptList.add(product1);
        exceptList.add(product2);
        exceptList.add(product3);
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

            }
        };
        restTemplate.setErrorHandler(errorHandler);
    }

    @Test
    public void addProductNormal() {
        normalList.forEach(product -> {
            Product result = RestUtil.postJSON(restTemplate, baseUrl + "/product/addProduct", product, Product.class);
            Assert.notNull(result,"插入失败");
        });
    }

    @Test
    public void addProductException() {
        exceptList.forEach(product -> {
            HashMap<String,String> result = RestUtil.postJSON(restTemplate, baseUrl + "/product/addProduct", product, HashMap.class);
            //为了将异常不被其它情况干扰，将异常数据的name与异常信息一致，防止因为网络干扰等导致的异常
            Assert.isTrue(result.get("message").equals(product.getName()),"插入失败");
//            log.error("result={}",result);
        });
    }

//    @Test
//    public void hello() throws Exception {
//    }

    @Test
    public void getProductById()  {
        normalList.forEach(product -> {
            Product result = restTemplate.getForObject(baseUrl+"/product/getProductById?id="+product.getId(),Product.class);
            Assert.isTrue(result.getId().equals(product.getId()),"查询失败");
        });
        exceptList.forEach(product -> {
            Product result = restTemplate.getForObject(baseUrl+"/product/getProductById?id="+product.getId(),Product.class);
            Assert.isNull(result,"查询失败");
        });
    }

    @Test
    //该注解只能使用在直接数据库交互的地方，即repository层，不能够用于回滚controller层的操作
    @Transactional
    public void transactionAddProduct(){
        normalList.forEach(product -> {
            productRepository.save(product);
        });
    }

    /**
     * 删除测试数据前面加zzzz是为了在字典排序时排在最后，保证是最后执行,然而这种方法在
     * 遇到无法删除的数据或者是伪删除的数据时依然无效，最好的办法是采用内存数据库，与原来数据库具有一样的
     * 的结构，完美解决了测试的问题
     * @throws Exception
     */
//    @Test
//    public void zzzzzzzzzzzdeleteData(){
//        productRepository.deleteAll(normalList);
//    }

    @Test
    public void query() throws Exception {
    }

}