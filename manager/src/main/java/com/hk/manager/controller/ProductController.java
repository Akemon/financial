package com.hk.manager.controller;

import com.hk.entity.Product;
import com.hk.manager.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author 何康
 * @date 2018/9/13 10:34
 */
@RestController
@Slf4j
@RequestMapping("/product")
@Api(tags = "product",description = "产品相关")
public class ProductController {

    @Autowired
    private ProductService productService;

    /***
     * 添加产品
     * @param product
     * @return
     */
    @ApiOperation(value = "创建产品",notes = "根据对应业务规则创建相应的产品")
    @RequestMapping(value = "addProduct",method = RequestMethod.POST)
    public Product addProduct(@RequestBody Product product){
        log.info("创建产品，参数={}",product);
        Product result = productService.addProduct(product);
        log.info("创建产品，结果={}",result);
        return result;
    }
    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }


    @RequestMapping(value = "getProductById",method = RequestMethod.GET)
    public Product getProductById(@RequestParam("id")String id){
        log.info("查询单个产品，id={}",id);
        Product result = productService.findOne(id);
        log.info("查询单个产品，结果={}",result);
        return result;
    }

    @RequestMapping(value="getProducts",method = RequestMethod.GET)
    public Page<Product> query(String ids, BigDecimal minRewardRate,BigDecimal maxRewardRate,String status,
                               @RequestParam(defaultValue = "0") int pageNum,@RequestParam(defaultValue = "10") int pageSize){
        log.info("查询产品，ids={},minRewardRate={},maxRewardRate={},status={},pageNum={},pageSize={}",
                ids,minRewardRate,maxRewardRate,status,pageNum,pageSize);
        List<String> idList = null;
        List<String> stuList = null;
        if(!StringUtils.isEmpty(ids)){
            idList = Arrays.asList(ids.split(","));
        }
        if(!StringUtils.isEmpty(status)){
            stuList = Arrays.asList(status.split(","));
        }
        Pageable pageable = new PageRequest(pageNum,pageSize);
        Page<Product> result = productService.query(idList,minRewardRate,maxRewardRate,stuList,pageable);
        log.info("查询产品，结果={}",result);
        return result;
    }
}
