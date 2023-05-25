package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductsRepositoryJdbcImplTest {

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(1L, "TONON", new BigDecimal("87.25")),
            new Product(2L, "PEGS", new BigDecimal("60.71")),
            new Product(3L, "HILAND", new BigDecimal("88.52")),
            new Product(4L, "CONCHITA", new BigDecimal("71.16")),
            new Product(5L, "HEALEO", new BigDecimal("10.19")),
            new Product(6L, "PIZZETI", new BigDecimal("80.32")),
            new Product(7L, "JONES", new BigDecimal("71.26")),
            new Product(8L, "TRUFFINO", new BigDecimal("99.69")),
            new Product(9L, "KIKI", new BigDecimal("77.59")),
            new Product(10L, "FRAGILE", new BigDecimal("77.50"))));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(6L, "PIZZETI", new BigDecimal("80.32"));
    final Product EXPECTED_UPDATED_PRODUCT = new Product(8L, "NEW_NAME", new BigDecimal("888.88"));
    final Product EXPECTED_SAVED_PRODUCT = new Product(11, "NEW_NAME", new BigDecimal("888.88"));
    DataSource dataSource;
    ProductsRepositoryJdbcImpl pri;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        pri = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    public void drop() {
        if (dataSource != null && dataSource instanceof EmbeddedDatabase) {
            ((EmbeddedDatabase) dataSource).shutdown();
        }
    }

    @Test
    void findAll() {
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, pri.findAll());
    }

    @Test
    void findById() {
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, pri.findById(6L).orElse(null));
    }

    @Test
    void update() {
        pri.update(EXPECTED_UPDATED_PRODUCT);
        assertEquals(EXPECTED_UPDATED_PRODUCT, pri.findById(8L).orElse(null));
    }

    @Test
    void save() {
        pri.save(EXPECTED_SAVED_PRODUCT);
        assertEquals(EXPECTED_SAVED_PRODUCT, pri.findById(11L).orElse(null));
    }

    @Test
    void delete() {

    }
}