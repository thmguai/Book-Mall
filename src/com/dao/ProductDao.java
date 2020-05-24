package com.dao;

import com.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    public long count(String category) throws SQLException;
    public List<Product> findBooks(String category, int page, int pageSize) throws SQLException;
    public Product findBooksById(int id) throws SQLException;
}
