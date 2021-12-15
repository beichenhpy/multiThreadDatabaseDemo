package cn.beichenhpy.multiThreadDatabaseDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.beichenhpy.multiThreadDatabaseDemo.mapper")
public class MultiThreadDatabaseDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadDatabaseDemoApplication.class, args);
	}

}
