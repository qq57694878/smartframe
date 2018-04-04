package com.jldata.smartframe.core.config;

import com.jldata.smartframe.core.jpa.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.jldata.smartframe", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class JPAConfig {

}
