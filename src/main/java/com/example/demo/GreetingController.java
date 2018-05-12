package com.example.demo;

import io.swagger.annotations.*;
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
@CrossOrigin(origins = "http://13.209.42.155:8080/swagger-ui.html")
@RequestMapping("/Greeting")

@Api(value="onlinestore", description="물건 찾기 API")
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @ApiOperation(value = "분실물 목록")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved list"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//    }
//    )

    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "분실물 종류", required = true, dataType = "string", paramType = "query", defaultValue = "핸드폰"),
            @ApiImplicitParam(name = "place", value = "분실 장소", required = true, dataType = "string", paramType = "query", defaultValue = "b1"),
            @ApiImplicitParam(name = "name", value = "분실물 명", dataType = "string", paramType = "query", defaultValue = ""),
    })


    @RequestMapping(value = "/losts", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<JSONObject> restTemplate(HttpServletRequest request) throws Exception {
        String baseUrl = "openapi.seoul.go.kr";
        String svcKey = "72694d7657737769323442486a694d";
        String category = request.getParameter("category");
        String place = request.getParameter("place");
        String name = request.getParameter("name");

        if(name==null) name = "";

        RestTemplate restTpl = new RestTemplate();
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(baseUrl)
                .port("8088")
                .path(svcKey)
                .path("/json/SearchLostArticleService/1/8/")
                .path(category)
                .path("/")
                .path(place)
                .path("/")
                .path(name)
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

        return url;


    }

}

/*
* 해야할일
* 배포 하기
* gson 사용하기
* swagger 연결하기
* */
