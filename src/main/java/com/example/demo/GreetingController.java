package com.example.demo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<JSONObject> restTemplate(HttpServletRequest request) throws Exception {
        String baseUrl = "openapi.seoul.go.kr";
        String svcKey = "72694d7657737769323442486a694d";
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String place = request.getParameter("place");

        System.out.println(name+category+place);

        RestTemplate restTpl = new RestTemplate();
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .port("8088")
                .path(svcKey)
                .path("/json/SearchLostArticleService/1/8/핸드폰/b1")
//                .path(category)
//                .path(place)
//                .path(name)
                .build()
                .encode()
                .toUri();

        /* 이게 맞는 걸까?
        * RestTemplate로 데이터를 받아옴 (json)
        * String으로 변환
        * String to JSON
        * JSON로 Instance 생성
        *
        * loging과 System.out.println의 차이점
        */

        String responseStr = restTpl.getForObject(uri, String.class);

        JSONObject json = (JSONObject) new JSONParser().parse(responseStr);
        JSONObject body = (JSONObject) json.get("SearchLostArticleService");

        List<JSONObject> rows = (List<JSONObject>) body.get("row");
        List<JSONObject> results = new ArrayList<JSONObject>();
        JSONObject losts = new JSONObject();

        for (int i = 0; i < rows.size(); i++) {
            String temp = getImages(rows.get(i).get("ID"));
            rows.get(i).put("IMAGE_URL", temp);
            results.add(rows.get(i));
        }

        return results;
    }

    public String getImages(Object number) throws Exception {
        String baseUrl = "openapi.seoul.go.kr";
        String svcKey = "72694d7657737769323442486a694d";

        RestTemplate restTpl = new RestTemplate();
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .port("8088")
                .path(svcKey)
                .path("/json/SearchLostArticleImageService/1/5/")
                .path(number.toString())
                .build()
                .encode()
                .toUri();

        String responseStr = restTpl.getForObject(uri, String.class);
        JSONObject json = (JSONObject) new JSONParser().parse(responseStr);
        JSONObject body = (JSONObject) json.get("SearchLostArticleImageService");
        JSONArray rows = (JSONArray) body.get("row");
        JSONObject row = (JSONObject) rows.get(0);

        String url = (String) row.get("IMAGE_URL");


        System.out.println(url);

        return url;


    }

}

/*
* 해야할일
* gson 사용하기
* */
