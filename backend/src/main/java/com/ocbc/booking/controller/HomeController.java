package com.ocbc.booking.controller;

import com.ocbc.booking.config.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ocbc.booking.util.Constants.WELCOME_MESSAGE;

/**
 * For default root mapping
 * @author darshan
 */
@RestController
@RequestMapping(path = "/api/v1")
@Api(tags = {SwaggerConfig.HOME_TAG})
public class HomeController {

    @GetMapping("")
    @ApiOperation("display default message")
    public String getDefaultMessage() {
        return WELCOME_MESSAGE;
    }
}
