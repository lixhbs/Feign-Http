# Feign-Http


没有接触Spring Cloud Feign的时候调用其他公司接口都是用`java.net.URL`或者`org.apache.commons.httpclient`, 现在可以使用Feign，使用起来比较简单。

<!--more-->

关于如何使用`Feign`可以查看[【Spring cloud】第六篇 Declarative REST Client | 声明性客户端 - Feign](https://blog.aprcode.com/sc-f-e-06/)

新建`Spring cloud`基础项目    

### 创建`eureka-server`工程    

可以参考 [【Spring Cloud】第一篇 Service Discovery | 服务发现 - Eureka](https://blog.aprcode.com/sc-f-e-01/)      

### 创建`server-feign`工程    

可以参考 [【Spring cloud】第六篇 Declarative REST Client | 声明性客户端 - Feign](https://blog.aprcode.com/sc-f-e-06/)    

- 添加Feign接口 `com.cyinfotech.serverfeign.services.ApiOpenService.java`

在`@FeignClient` 设置`url`为http 基础地址，在各个方法上添加`@PostMapping`注解为`api`地址
    
```java
package com.cyinfotech.serverfeign.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
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
    String searchPoetry(@RequestParam("name") String name);
}
```

    
- 在启动类添加测试代码    
添加注解`@EnableFeignClients(clients = {ApiOpenService.class})`

```java

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



```    
启动 `EurekaServerApplication` > `ServerFeignApplication`    

- 访问：http://localhost:8085/api/singlePoetry
> {"code":200,"message":"成功!","result":{"author":"苏轼","origin":"临江仙·夜饮东坡醒复醉","category":"古诗文-人生-梦想","content":"长恨此身非我有，何时忘却营营。"}}

- 访问：http://192.168.1.108:8085/api/recommendPoetry     
>{"code":200,"message":"成功!","result":{"title":"送卢孟明还上都","content":"江皋北风至，归客独伤魂。|楚水逢乡雁，平陵忆故园。|征骖嘶别馆，落日隐寒原。|应及秦川望，春华满国门。","authors":"皎然"}}

- 访问：http://192.168.1.108:8085/api/searchPoetry?name=黄鹤楼    
> {"code":200,"message":"成功!","result":[{"title":"黄鹤楼","content":"黄鹤何年去杳冥，高楼千载倚江城。|碧云朝卷四山景，流水夜传三峡声。|柳暗西州供骋望，草芳南浦遍离情。|登临一晌须回首，看却乡心万感生。","authors":"卢郢"},{"title":"黄鹤楼","content":"云锁吕公洞，月明黄鹤楼。|抱关非故卒，谁见羽衣游。","authors":"王十朋"},{"title":"黄鹤楼","content":"手把仙人绿玉枝，吾行忽及早秋期。|苍龙阙角归何晚，黄鹤楼中醉不知。|江汉交流波渺渺，晋唐遗迹草离离。|平生最喜听长笛，裂石穿云何处吹。","authors":"陆游"},{"title":"黄鹤楼","content":"长江巨浪拍天浮，城郭相望万景收。|汉水北吞云梦入，蜀江西带洞庭流。|角声交送千家月，帆影中分两岸秋。|黄鹤楼高人不见，却随鹦鹉过汀洲。","authors":"游似"},{"title":"黄鹤楼","content":"翚飞栋宇据城端，车马尘中得异观。|双眼莫供淮地阔，一江不尽蜀波寒。|老仙横笛月亭午，骚客怀乡日欲残。|独抚遗踪增慨慕，徘徊不忍下层栏。","authors":"罗与之"},{"title":"黄鹤楼","content":"崔颢题诗在上头，登临何必更冥搜。|楼前黄鹤不重见，槛外长江空自流。|万顷烟云连梦泽，一川风景借西州。","authors":"张颙"},{"title":"黄鹤楼","content":"昔人已乘白云去，旧国连天不知处。|思量费子真仙子，从他浮世悲生死。|黄鹤一去不复返，光阴流转忽已晚。","authors":"王得臣"},{"title":"黄鹤楼","content":"登真者谁子，昔有费公祎。|白日玉书下，青天驾鹤飞。|此地少留憩，神标怅依依。|振裾谢尘浊，与尔方远违。|层楼宠陈迹，江山长四围。|登临美商素，雨气薄西晖。|水落州觜嫩，风豪帆背肥。|飘然起遐想，琳馆閟岩扉。|明月识悠阔，白云疑是非。|金桃未遽熟，薤露虞先晞。|高树两华表，长招羽驾归。|灵瓢五色剂，定与遗民挥。|自顾乏仙质，延生犹可希。|刀圭傥不吝，如饱首阳饥。","authors":"贺铸"}]}

---  

欢迎关注我的公众号，跟我留言。   
![](http://paz1myrij.bkt.clouddn.com/qrcode_for_gh_22df58e4959f_258.jpg)    
博客地址：[https://blog.aprcode.com/Feign-Http/](https://blog.aprcode.com/Feign-Http/)   
教程源码Github地址：[Feign-Http](https://github.com/lixhbs/Feign-Http)   
教程源码Gitee地址：[Feign-Http](https://gitee.com/Lixhbs/Feign-Http)





