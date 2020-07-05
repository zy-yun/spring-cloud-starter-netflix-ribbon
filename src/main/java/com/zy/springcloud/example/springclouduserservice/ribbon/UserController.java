package com.zy.springcloud.example.springclouduserservice.ribbon;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * spring cloud ribbon负载均衡组件 及 restTemplate网络请求组件的 使用demo
 * @Author zy
 * @Date 2020/07/05 14:10
 */
@RestController
public class UserController {

    /**
     * http请求客户端封装组件，适应于微服务间的网络通信请求
     */
    @Autowired
    RestTemplate restTemplate;

    /**
     * ribbon的负载均衡客户端组件
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;


    /**
     * 向容器中注入该组件bean
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    /**
     * web请求
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public Object findById(@PathVariable("id") int id){

        /**
         * 负载均衡客户端，根据指定的服务id，默认轮询的策略进行负载
         */
        ServiceInstance choose = loadBalancerClient.choose("spring-cloud-order-service");

        String url = String.format("http://%s:%s/orders",choose.getHost(),choose.getPort(),id);

        //网络请求，相比httpClient等简化 http请求步骤，已封装在内
        return restTemplate.getForObject(url,String.class);
    }
}
