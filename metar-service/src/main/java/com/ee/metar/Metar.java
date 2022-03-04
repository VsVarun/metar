package com.ee.metar;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application that continuously loads and stores a subset of METAR data for the subscribed airports
 *
 * @author Varun Chandresekar [ VC ]
 * Date 20/1/2022
 * Project Metar data
 */
/*
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER
)*/
@OpenAPIDefinition(
    info = @Info(
    title = "metar",
    version = "${build.version}",
    description = "Application that continuously loads and stores a subset of METAR data for the subscribed airports"),
    servers = { @Server(url = "https://vc-iot.app/metar"), @Server(url = "http://localhost:13131/metar") }
)
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableJpaRepositories
@SpringBootApplication
public class Metar {

    protected static final Logger LOG = LoggerFactory.getLogger(Metar.class);

    public static void main(String[] args) {
        LOG.info("Initializing Metar Application ...");
        SpringApplication springApplication = new SpringApplication(Metar.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
        LOG.info("Metar Application Started !!!");
    }

}
