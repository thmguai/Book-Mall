package com.dao.impl;

import com.dao.ProductDao;
import com.model.Product;
import com.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public long count(String category) throws SQLException {

        long count = 0;
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select count(*) from products where 1=1";
        if (category != null && !"".equals(category)){
            sql += " and category=?";
            count = (long)qr.query(sql, new ScalarHandler(), category);
        }else {
            count = (long)qr.query(sql,new ScalarHandler());
        }
        return count;
    }

    @Override
    public List<Product> findBooks(String category, int page, int pageSize) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        List<Object> param = new ArrayList<>();
        String sql = "select * from products where 1=1";
        if (category != null && !"".equals(category)){
            sql += " and category=?";
            param.add(category);
        }
        sql += " limit ?,?";
        int start = (page - 1) * pageSize;
        int length = pageSize;
        param.add(start);
        param.add(length);
        List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),param.toArray());
        return list;
    }

    @Override
    public Product findBooksById(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from products where id=?";
        Product product = qr.query(sql, new BeanHandler<Product>(Product.class),id);
        return product;
    }

    public static void main(String[] args) throws SQLException {
        ProductDaoImpl dao = new ProductDaoImpl();
        //long count = dao.count("计算机");
        List<Product> books = dao.findBooks("", 2, 3);
        System.out.println(books);
    }
}
