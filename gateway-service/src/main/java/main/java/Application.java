/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient 		// It acts as a eureka client
@EnableZuulProxy		// Enable Zuul

/**
 *
 * @author frederikhelth
 */
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
