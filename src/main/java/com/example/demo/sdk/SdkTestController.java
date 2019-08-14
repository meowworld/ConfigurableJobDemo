package com.example.demo.sdk;

import com.alibaba.fastjson.JSON;
import com.test.sdk.MeowClient;
import com.test.sdk.domain.MeowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
