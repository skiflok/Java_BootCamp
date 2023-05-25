package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{

    private final JdbcTemplate jdbcTemplate;

    public ProductsRepositoryJdbcImpl(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from tests.products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "select * from tests.products where id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Product.class))
                .stream().findAny();
    }

    @Override
    public void update(Product product) {
        String sql = "update tests.products set name = ?, price = ? where id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getId());
    }

    @Override
    public void save(Product product) {
        String sql = "insert into tests.products (name, price) values (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }


    @Override
    public void delete(Long id) {

    }
}
