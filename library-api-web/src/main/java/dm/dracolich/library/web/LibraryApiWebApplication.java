package dm.dracolich.library.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = {"dm.dracolich.library.web.repository"})
public class LibraryApiWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApiWebApplication.class, args);
    }
}
