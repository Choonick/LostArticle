package com.example.demo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {
    @RequestMapping("/ping")
    public String home() {
        return "hello Spring Boot!";
    }

    @RequestMapping("/ping2")
    public String home2() {
        return "hello Spring Boot!";
    }


    @ApiOperation(value = "헬로우 테스트", httpMethod = "GET", notes = "헬로우 테스트입니다")

    @ApiResponses(value = {

            @ApiResponse(code = 400, message = "Invalid input:…"),

            @ApiResponse(code = 200, message = "Ok" )

    })

    @RequestMapping(value="/hello", method= RequestMethod.POST )

    public String hello(@ApiParam(value = "키값", required = true, defaultValue = "기본값") String value) {

        return "Blind Interview Server Running\n";

    }
}
