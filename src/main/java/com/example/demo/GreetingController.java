package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();
 @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Object restTemplate() throws Exception{
        String baseUrl = "openapi.seoul.go.kr";
        String svcKey  = "72694d7657737769323442486a694d";

        RestTemplate restTpl = new RestTemplate();
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .port("8088")
                .path(svcKey)
                .path("/json/SearchLostArticleService/1/5/핸드폰/b1/")
                .build()
                .encode()
                .toUri();

        Object responseStr = restTpl.getForObject(uri, Object.class);
//        Losts lost = (Losts) responseStr;
//        System.out.print(lost);

        return responseStr;
    }

}
