package com.ocbc.booking.service.impl;

import com.ocbc.booking.constant.MailConstants;
import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.enums.SeatStatus;
import com.ocbc.booking.model.Mail;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.repository.SeatRepository;
import com.ocbc.booking.repository.UserRepository;
import com.ocbc.booking.service.BookingService;
import com.ocbc.booking.service.MailService;
import com.ocbc.booking.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MailService mailService;

    @Override
    public void bookSeats(BookingDTO bookingDTO) {
        User existingUser = userRepository.findUserByEmail(bookingDTO.getUser().getEmail());
        if (existingUser != null) {
            logger.info("User {} already exists...updating details", existingUser.getName());
            bookingDTO.getUser().setUserId(existingUser.getUserId());
            bookingDTO.getSeats().addAll(existingUser.getSeats());
            bookingDTO.getUser().setSeats(bookingDTO.getSeats());
            logger.info("Saving user in the database...{}", bookingDTO.getUser());
            userRepository.save(bookingDTO.getUser());
        } else {
            logger.info("Saving the new user in the database...{}", bookingDTO.getUser());
            User savedUser = userRepository.save(bookingDTO.getUser());
            savedUser.setSeats(bookingDTO.getSeats());
            logger.info("Saving seat mapping for the user in the database...");
            userRepository.save(savedUser);
        }
        bookingDTO.getSeats().forEach(seat -> {
            logger.info("Updating seat {}{} status to BOOKED", seat.getRowName(), seat.getNumber());
            seat.setStatus(SeatStatus.BOOKED.toString());
            seatRepository.save(seat);
        });
        logger.info("Sending email to user @ {}", bookingDTO.getUser().getEmail());
        sendEmail(bookingDTO);
    }

    private void sendEmail(BookingDTO bookingDTO) {
        Mail mail = new Mail(bookingDTO.getUser().getEmail(), MailConstants.SUBJECT, Utils.createEmailMessage(bookingDTO));
        mailService.sendMail(mail);
    }

    @Override
    public void deleteBookingForAllUsers() {
        logger.info("Removing seat mapping from users");
        userRepository.findAll().forEach(user -> {
            user.setSeats(null);
            userRepository.save(user);
        });
        logger.info("Updating seats status to available");
        seatRepository.findAll().forEach(seat -> {
            seat.setStatus(SeatStatus.AVAILABLE.toString());
            seatRepository.save(seat);
        });
    }
}
