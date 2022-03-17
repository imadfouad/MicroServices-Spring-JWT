package org.sid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.sid.dao.CategoryRepository;
import org.sid.dao.ProductRepository;
import org.sid.entities.Category;
import org.sid.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CatalogueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogueServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository) {
		return args ->{
			categoryRepository.deleteAll();
			Stream.of("Ordinateurs","Imprimantes").forEach(c->{
				categoryRepository.save(new Category(null,c,new ArrayList<>() ));
			});
			categoryRepository.findAll().forEach(s->{
				System.out.println(s);
			});
			
			productRepository.deleteAll();
			Category c1 = categoryRepository.findAll().get(0);
			Stream.of("p1","p2","p3").forEach(p->{
				Product product = new Product(null,p,Math.random()*1000,c1);
				productRepository.save(product);
				c1.getProducts().add(product);
				categoryRepository.save(c1);
				
			});
			productRepository.findAll().forEach(p -> {
				System.out.println(p);
			});
		};
	}
	
}
