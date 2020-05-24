package com.service.impl;

import com.dao.ProductDao;
import com.dao.impl.ProductDaoImpl;
import com.model.PageResult;
import com.model.Product;
import com.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductDao dao = new ProductDaoImpl();
    @Override
    public PageResult<Product> findBooks(String category, int page) {
        try {
            //创建模型
            PageResult<Product> pr = new PageResult<Product>();
            List<Product> books = dao.findBooks(category, page, pr.getPageSize());
            pr.setList(books);
            long count = dao.count(category);
            pr.setTotalCount(count);
            int totalPage = (int) Math.ceil(count * 1.0 / pr.getPageSize());
            pr.setTotalPage(totalPage);
            pr.setCurrentPage(page);
            return pr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product findProductById(String id) {
        try {
            int ID = Integer.parseInt(id);
            return dao.findBooksById(ID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
