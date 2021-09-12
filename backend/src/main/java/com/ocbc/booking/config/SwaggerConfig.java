package com.ocbc.booking.config;

import com.ocbc.booking.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * For enabling Swagger for API documentation
 * @author darshan
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String BOOKING_TAG = "BookingController";
    public static final String SEAT_TAG = "SeatController";
    public static final String USER_TAG = "UserController";

    /**
     * @return config settings
     */
    @Bean
    public Docket bookingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ocbc.booking"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(BOOKING_TAG, Constants.BOOKING_TAG_DESC),
                        new Tag(SEAT_TAG, Constants.SEAT_TAG_DESC),
                        new Tag(USER_TAG, Constants.USER_TAG_DESC));
    }
}
