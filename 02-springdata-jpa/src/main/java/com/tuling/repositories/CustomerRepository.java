package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
//public interface CustomerRepository extends CrudRepository<Customer,Long> {
public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {//实现分页和排序

    // 增删查改
    // SpringdataJpaTest、SpringDataJpaPagingAndSortTest


    //JpqlTest
    /**
     * 业务复杂时采用自定义操作，也就是自己写JPQL
     */
    // 查询单个
//    @Query("FROM Customer where custName=?1 ") //索引(方法参数位置，从1开始)，涉及多个字段时
//    Customer findCustomerByCustName01(String custName);
    @Query("FROM Customer where custName=:custName ")//具名，:=不能有空格！
    Customer findCustomerByCustName01(@Param("custName") String custName);//通过@Param指定参数名字

    //查询多个
    @Query("FROM Customer where custName=:custName ")
    List<Customer> findCustomerByCustName02(@Param("custName") String custName);

    // 修改
    //增删改操作时，需要事务支持！
    @Transactional  //通常会放在业务逻辑曾上面区声明(而不是@Test中)
    @Modifying      // 通知springdatajpa 是增删改的操作
    @Query("UPDATE Customer c set c.custName=:custName where c.custId=:id")
    int updateCustomer(@Param("custName") String custName,@Param("id")Long id);

    //删除
    @Transactional
    @Modifying   // 通知springdatajpa 是增删改的操作
    @Query("DELETE FROM Customer c where c.custId=?1")
    int deleteCustomer(Long id);

    // 新增  JPQL(其实是不支持新增的，但是spring data jpa基于hibernate，有个伪新增)
    @Transactional
    @Modifying   // 通知springdatajpa 是增删改的操作
    @Query("INSERT INTO Customer (custAddress, custName) SELECT c.custAddress, c.custName FROM Customer c where c.custId=?1")//插入从别的地方查到的值
    int insertCustomerBySelect(Long id);

    /**
     * 原生SQL
     */
    @Query(value="select * FROM tb_customer where cust_name=:custName ", nativeQuery = true)//nativeQuery属性值: false->JPSQL，true->原生SQL
    List<Customer> findCustomerByCustNameBySql(@Param("custName") String custName);

}
