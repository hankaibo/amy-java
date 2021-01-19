package cn.mypandora.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * SpringbootApplication
 * <p>
 * 注： 1. @MapperScan，此注解为tk的而非mybatis。
 *
 * @author hankaibo
 * @date 2019/6/17
 */

@SpringBootApplication
@ServletComponentScan
@EnableCaching
@MapperScan("cn.mypandora.springboot.modular.system.mapper")
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
