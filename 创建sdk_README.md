## 如何创建一个自己的sdk

实现一个sdk给别人调用，就是将自己写的代码打成jar包，给别的工程依赖。

### 需求描述：

​	本demo用springboot工程搭建框架，只实现一个功能。调用百度的url转码，将长的url转成短码的形式。

比如：<https://coolshell.cn/articles/4990.html>这个网址，转后 https://dwz.cn/HUI3wppy 。

sdk将请求过程封装了，调用这个只需要注入sdk中的MeowClient并组装参数传入就好，至于怎么请求百度就是sdk去做的事情。

```java
 	@Autowired
    private MeowClient meowClient;

    @GetMapping("callSdk")
    public String call(){
        MeowRequest request = new MeowRequest("http://www.baidu.com","long-term");
        Map responseMap = meowClient.transform(request);

        System.out.println(JSON.toJSONString(responseMap));

        return "ok";
    }
```

### 怎么做？

https://github.com/meowworld/ConfigurableJobDemo

1.搭建一个基础的springboot工程

![Image text](https://github.com/meowworld/ConfigurableJobDemo/blob/8570a175a8/picture/A工程pom引入.jpg)

本工程的 resources 目录删除了，也没什么用。下面是pom.xml依赖，待会sdk要用到自动装配和http请求所以添加了下面依赖。

```xml
<dependencies>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-autoconfigure</artifactId>
   </dependency>

   <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>5.0.4.RELEASE</version>
      <scope>compile</scope>
   </dependency>

   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
   </dependency>

   <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>1.2.49</version>
   </dependency>
</dependencies>
```

2.写自己的代码	

<!--代码说明 ConfigProperties ：比如A工程 有application.yml文件中，有下面的配置，这个类是读取 application.yml配置文件中的值-->：

```
meow:
  token: 2a8f885a7afca6324b9d263f82b7dc7d
  dwzHost: https://dwz.cn/admin/v2/create
```

```java
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "meow")
public class ConfigProperties {

    private String token;
    private String dwzHost;
}
```

<!--这个类是在A工程容器中注入  MeowClient 并初始化一些值。-->

```java
@EnableConfigurationProperties(ConfigProperties.class)
@Configuration
public class AutoConfiguration {
    private ConfigProperties properties;

    public AutoConfiguration(ConfigProperties properties){
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MeowClient meowClient(){
        MeowClient meowClient = new MeowClient();
        meowClient.setToken(this.properties.getToken());
        meowClient.setDwzHost(this.properties.getDwzHost());
        return meowClient;
    }
}
```

<!--这个是传入的参数对象：-->

```java
import lombok.Data;

@Data
public class MeowRequest {

    private String Url;
    private String TermOfValidity;

    public MeowRequest(String Url ,String TermOfValidity){
        this.Url = Url;
        this.TermOfValidity = TermOfValidity;
    }
}
```

<!--这个类就是请求百度的方法-->

```java
import com.alibaba.fastjson.JSON;
import com.test.sdk.domain.MeowRequest;
import lombok.Data;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/*封装一个sdk:封装百度短网址 */

@Data
public class MeowClient {

    private String token;
    private String dwzHost;

    //构造方法:构造的基本参数传递进来
    public MeowClient(){}

    /**
     * @param request @see {@link MeowRequest}
     * @param  :Url  eg："http://www.baidu.com"
     * @param  :TermOfValidity string 非必须    
     * 		    短网址有效期，目前支持：（1）永久："long-term" （2）一年："1-year"  eg: "long-term"
     * @return shortUrl
     */
    public Map transform(MeowRequest request){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Token", this.token);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> httpEntity =new HttpEntity<>(JSON.toJSONString(request),headers);
        ResponseEntity<Map> responseMap = restTemplate.postForEntity(this.dwzHost, httpEntity, Map.class);
        if (!responseMap.getStatusCode().equals(HttpStatus.OK)){
            throw new RuntimeException("调用百度短网址转换失败，请重新调用");
        }
        Map body = responseMap.getBody();
        return body;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
```

​	<!--代码部分完成-->

3.将写好的代码做成sdk

​	1）: 在idea中 File --> ProjectStructure 

![将代码打包](C:\Users\meow\Desktop\tu\将代码打包.png)

2): Build --> Build Artifacts 

![1565791698565](C:\Users\meow\Desktop\tu\build.png)

<!--这样sdk就OK啦-->

![ok](C:\Users\meow\Desktop\tu\ok.png)

4.别的工程怎么使用

启动类上添加包扫描注解 @ComponentScan(basePackages = "com.test.sdk")

![引用](C:\Users\meow\Desktop\tu\引用.png)



![A工程pom引入](C:\Users\meow\Desktop\tu\A工程pom引入.jpg)

<!--代码中使用sdk-->

```java
@RestController
@RequestMapping("/sdk")
public class SdkTestController {
    @Autowired
    private MeowClient meowClient;

    @GetMapping("callSdk")
    public String call(){
        MeowRequest request = new MeowRequest("http://www.baidu.com","long-term");
        Map responseMap = meowClient.transform(request);
        System.out.println(JSON.toJSONString(responseMap));
        return "ok";
    }
}
```

<!--Ending-->
