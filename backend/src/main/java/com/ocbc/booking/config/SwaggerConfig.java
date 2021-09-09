package com.ocbc.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String BOOKING_TAG = "BookingController";
    public static final String SEAT_TAG = "SeatController";
    public static final String USER_TAG = "UserController";

    @Bean
    public Docket bookingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(BOOKING_TAG, "REST APIs for booking seats"),
                        new Tag(SEAT_TAG, "REST APIs for seats"),
                        new Tag(USER_TAG, "REST APIs for users"));
    }
}
