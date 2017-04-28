package org.chiwooplatform.gen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.chiwooplatform.gen.Generator;
import org.chiwooplatform.gen.PropertyHolder;

@Configuration
@PropertySource(value = "classpath:code-gen.properties")
public class GenConfiguration {

    /**
     * To resolve ${} in &#64;Value
     * @return bean spring-bean
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PropertyHolder propertyHolder() {
        return new PropertyHolder();
    }

    @Bean
    public Generator generator() {
        return new Generator();
    }

    @Bean
    public ValueHolder valueHolder() {
        return new ValueHolder();
    }
}
