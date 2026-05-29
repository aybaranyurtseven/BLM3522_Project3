package com.project.ecommerce;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Product(null, "Laptop", "High performance laptop", new BigDecimal("1500.00"), "https://via.placeholder.com/150"),
                        new Product(null, "Smartphone", "Latest model smartphone", new BigDecimal("800.00"), "https://via.placeholder.com/150"),
                        new Product(null, "Headphones", "Noise cancelling headphones", new BigDecimal("200.00"), "https://via.placeholder.com/150")
                ));
            }
        };
    }
}
