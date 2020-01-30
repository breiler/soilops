package com.tietoevry.soilops.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Test", produces = "application/json")
public class ThisWillActuallyRun {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Simple hello world", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "We successfully fetched the 'hello world'", response = String.class),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public String home() {
        return "{ test: \"Hello World!\" }";
    }
}