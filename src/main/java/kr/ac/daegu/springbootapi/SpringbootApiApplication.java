package kr.ac.daegu.springbootapi;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan(basePackages="kr.ac.daegu.springbootapi")
public class SpringbootApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiApplication.class, args);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        Resource[] res =
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mappers/*Mapper.xml");
        sessionFactory.setMapperLocations(res);

        return sessionFactory.getObject();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
