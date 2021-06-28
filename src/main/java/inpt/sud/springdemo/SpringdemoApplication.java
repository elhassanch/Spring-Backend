package inpt.sud.springdemo;

import inpt.sud.springdemo.dao.CategoryRepository;
import inpt.sud.springdemo.dao.ProductRepository;
import inpt.sud.springdemo.model.Category;
import inpt.sud.springdemo.model.Product;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Random;

@SpringBootApplication
public class SpringdemoApplication implements CommandLineRunner {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);

        categoryRepository.save(new Category(1,"category 1",null,null ));
        categoryRepository.save(new Category(2,"category 2",null,null ));
        categoryRepository.save(new Category(3,"category 3",null,null ));

        Random rand = new Random();

        categoryRepository.findAll().forEach(category -> {
            for(int i=0; i<10; i++) {
                Product product = new Product();
                product.setName(RandomString.make(10));
                product.setCurrentPrice(rand.nextInt(10000));
                product.setAvailable(rand.nextBoolean());
                product.setPromotion(rand.nextBoolean());
                product.setSelected(rand.nextBoolean());
                product.setCategory(category);
                product.setPhotoName("404.png");
                productRepository.save(product);
            }
        });
    }
}
