//package fr.sacem.priam.ui;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.actuate.autoconfigure.*;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
//import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
//import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//
///**
// * Created by benmerzoukah on 10/05/2017.
// */
//@Configuration
//@ComponentScan(value = {"fr.sacem.priam.model.dao"})
//@EnableAutoConfiguration(exclude = {
//  ElasticsearchAutoConfiguration.class,
//  ElasticsearchDataAutoConfiguration.class,
//  DataSourceAutoConfiguration.class,
//  HibernateJpaAutoConfiguration.class,
//  AuditAutoConfiguration.class,
//  CrshAutoConfiguration.class,
//  EndpointAutoConfiguration.class,
//  EndpointMBeanExportAutoConfiguration.class,
//  HealthIndicatorAutoConfiguration.class,
//  MetricFilterAutoConfiguration.class,
//  MetricRepositoryAutoConfiguration.class,
//  PublicMetricsAutoConfiguration.class,
//  TraceRepositoryAutoConfiguration.class,
//  TraceWebFilterAutoConfiguration.class,
//  WebMvcAutoConfiguration.class,
//  JacksonAutoConfiguration.class
//})
//public class UiApplication {
//
//    private final Logger logger = LoggerFactory.getLogger(UiApplication.class);
//
//
//    @PostConstruct
//    public void initApplication() {
//        logger.info("Chargement de l'application priam ....");
//    }
//}
