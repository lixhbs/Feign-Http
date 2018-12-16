package com.cyinfotech.serverfeign;

import com.cyinfotech.serverfeign.services.ApiOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

/**
 * @author 煮酒泛舟
 * @date 2018年12月16日11:49:06
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableFeignClients(clients = {ApiOpenService.class})
@RequestMapping("/api")
public class ServerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerFeignApplication.class, args);
    }

    @Autowired
    ApiOpenService apiOpenService;

    @RequestMapping("/singlePoetry")
    public String searchSinglePoetry() {

        return apiOpenService.singlePoetry();
    }

    @RequestMapping("/recommendPoetry")
    public String searchRecommendPoetry() {

        return apiOpenService.recommendPoetry();
    }

    @GetMapping("/searchPoetry")
    public String searchPoetry(@RequestParam("name") String name) {

        return apiOpenService.searchPoetry(name);
    }

}

