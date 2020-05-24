package com.service;

import com.model.PageResult;
import com.model.Product;

import java.sql.SQLException;

public interface ProductService {
    public PageResult<Product> findBooks(String category, int page);
    public Product findProductById(String id) throws SQLException;
}
