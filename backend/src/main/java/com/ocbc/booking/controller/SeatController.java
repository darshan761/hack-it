package com.ocbc.booking.controller;


import com.ocbc.booking.config.SwaggerConfig;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.service.SeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/seats")
@Api(tags = {SwaggerConfig.SEAT_TAG})
public class SeatController {

    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);

    @Autowired
    SeatService seatService;

    @CrossOrigin
    @ApiOperation("Get all the seats")
    @GetMapping("")
    public List<Seat> getAllSeats() {
        logger.info("/seats - get sll seats request received");
        return seatService.getAllSeats();
    }

    @CrossOrigin
    @ApiOperation("Get seat by Id")
    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable int id) {
        logger.info("/seats/{} - get seat by Id request received",id);
        return seatService.getSeatById(id);
    }

    @CrossOrigin
    @ApiOperation("Save seat")
    @PostMapping("")
    public void saveSeat(@RequestBody Seat seat) {
        logger.info("/seats - save seat request received");
        seatService.saveSeat(seat);
    }

    @CrossOrigin
    @ApiOperation("Delete seat by Id")
    @DeleteMapping("/{id}")
    public void deleteSeatById(@PathVariable int id) {
        logger.info("/seats - delete seat request received");
        seatService.deleteSeatById(id);
    }
}
