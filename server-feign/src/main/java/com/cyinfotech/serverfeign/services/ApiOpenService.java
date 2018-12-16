package com.cyinfotech.serverfeign.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 煮酒泛舟.
 * @title ApiOpen
 * @program feign-http
 * @description ApiOpen
 * @createtime 2018-12-16 11:43
 */
@FeignClient(url = "https://api.apiopen.top", value = "ApiOpenService")
public interface ApiOpenService {

    /**
     * 随机单句诗词推荐
     * @return
     */
    @PostMapping("/singlePoetry")
    String singlePoetry();

    /**
     * 随机一首诗词推荐
     * @return
     */
    @PostMapping("/recommendPoetry")
    String recommendPoetry();

    /**
     * 搜索古诗词
     * @param name
     * @return
     */
    @GetMapping("/searchPoetry")
    String searchPoetry(@RequestParam String name);
}
