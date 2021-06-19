package kr.co.doglove.doglove;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = DogloveApplication.class)
public class DogloveApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogloveApplication.class, args);
    }

}
