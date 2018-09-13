package com.hk.manager.repository;

import com.hk.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 何康
 * @date 2018/9/13 10:11
 */

/**
 * 产品管理
 */
public interface ProductRepository extends JpaRepository<Product,String>,JpaSpecificationExecutor<Product>{

}
