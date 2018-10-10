package com.hk.manager.service;

/**
 * @author 何康
 * @date 2018/9/13 10:15
 */

import com.hk.entity.Product;
import com.hk.entity.enums.ProductStatus;
import com.hk.manager.error.ErrorEnum;
import com.hk.manager.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品服务类
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /***
     * 添加产品
     * @param product
     * @return
     */
    public Product addProduct(Product product){
        log.debug("创建产品，参数：{}",product);
        //数据校验
        checkProduct(product);
        //设置默认值
        setDefaultValue(product);
        Product result = productRepository.save(product);
        log.debug("创建产品，结果：{}",result);
        return result;
    }

    /***
     * 根据id查询单个产品
     * @param id
     * @return
     */
    public Product findOne(String id){
        Assert.notNull(id,"需要产品编号参数");
        log.debug("查询单个产品，id={}",id);
        Product result = productRepository.findById(id).orElse(null);
        log.debug("查询单个产品，结果：{}",result);
        return result;
    }

    /***
     * 分页查询产品
     * @param idList
     * @param minRewardRate
     * @param maxRewardRate
     * @param statusList
     * @param pageable
     * @return
     */
    public Page<Product> query(List<String> idList, BigDecimal minRewardRate, BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable){
            log.debug("查询产品，idList={},minRewardRate={},maxRewardRata={},statusList={},pageable={}",
                    idList,minRewardRate,maxRewardRate,statusList,pageable);
            Specification<Product>  specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                List<Predicate> predicates = new ArrayList<>();
                if(idList!=null&&idList.size()>0){
                    predicates.add(idCol.in(idList));
                }
                if(minRewardRate!=null && BigDecimal.ZERO.compareTo(minRewardRate)<0){
                    predicates.add(criteriaBuilder.ge(rewardRateCol,minRewardRate));
                }
                if(maxRewardRate!=null && BigDecimal.ZERO.compareTo(maxRewardRate)<0){
                    predicates.add(criteriaBuilder.le(rewardRateCol,maxRewardRate));
                }
                if(statusList!=null&&statusList.size()>0){
                    predicates.add(statusCol.in(statusList));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };
       Page<Product> products = productRepository.findAll(specification,pageable);
       log.debug("查询产品，结果：{}",products);
       return products;
    }


    /***
     * 设置默认值
     * 创建时间，更新时间，投资步长，锁定期，状态
     * @param product
     */
    private void setDefaultValue(Product product) {
        if(product.getCreateTime()==null) product.setCreateTime(new Date());
        if(product.getUpdateTime()==null) product.setUpdateTime(new Date());
        if(product.getStepAmount()==null) product.setStepAmount(BigDecimal.ZERO);
        if(product.getLockTerm()==null) product.setLockTerm(0);
        if(product.getStatus()==null) product.setStatus(ProductStatus.AUDITING.name());
    }

    /***
     * 数据校验
     * 1、非空数据
     * 2、收益率0-30%
     * 3、投资步长需为整数
     * @param product
     */
    private void checkProduct(Product product) {
        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0
                &&BigDecimal.valueOf(0.3).compareTo(product.getRewardRate())>=0,ErrorEnum.REWARD_ERROR.getCode());
        //取整与原来的数进行比较
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue()).compareTo(product.getStepAmount())==0,ErrorEnum.STEPAMOUNT_ERROR.getCode());
    }

}
