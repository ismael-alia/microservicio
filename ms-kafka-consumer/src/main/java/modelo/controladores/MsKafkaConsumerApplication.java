package modelo.controladores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories("modelo.persistencia")
@ComponentScan(basePackages= {"modelo", "config"})
public class MsKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsKafkaConsumerApplication.class, args);
	}
}
