# spring-cloud-starter-netflix-ribbon
springCloud netflix 的ribbon负载均衡组件demo示例

本demo仅为演示 微服务中的负载均衡组件跟底层网络通信组件的集成使用

Ribbon(LoadBalancerClient)、RestTemplate 组件
    
依赖jar包：

            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <!-- spring cloud netflix ribbon的组件-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                <version>2.2.3.RELEASE</version>
            </dependency>
            
主要组件讲解：

RestTemplate组件：

    对HttpClient，okHttp等的类似功能封装，便于微服务间的网络请求通信。
    使用时注意：
        1.需要手动使用@Bean 将restTemplate加入spring的IOC容器中
        2.使用@AutoWried注解装配该bean
        
Ribbon的LoadBalancerClient负载均衡客户端组件，依赖netflix-ribbon jar

    使用：
    1.使用@AutoWried注解装配该bean
    2.loadBalancerClient.choose("spring-cloud-order-service")，括号中为提供者服务的唯一Id
    3.在application。properties文件中配置 指定的服务提供者id 地址列表。
        例如：spring-cloud-order-service.ribbon.listOfServers =localhost:8081,localhost:8082
        
注意：
上述的spring-cloud-order-service服务，需要自己建一个springboot服务，application.name与这个保持一致，然后启动两个端口（
8081，8082），
即 两个应用。
然后浏览器地址端输入：http://localhost:8080/user/1
查看返回结果：会发现robbin默认的使用的负载策略为轮询，8081/8082循环依次访问
