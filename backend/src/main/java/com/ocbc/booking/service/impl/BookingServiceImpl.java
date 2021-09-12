package com.ocbc.booking.service.impl;

import com.ocbc.booking.exception.SeatAlreadyBookedException;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.util.Constants;
import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.enums.SeatStatus;
import com.ocbc.booking.model.Mail;
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

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Booking seats for the users.
 *
 * @author darshan
 */
@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MailService mailService;

    /**
     * Book seats for user handling concurrency and covering below cases.
     * case 1. User is already present - updating its details.
     * case 2. User is new - saving to database.
     * case 3. Seat is already booked - throwing message to user.
     * @param bookingDTO
     * @throws SeatAlreadyBookedException
     */
    @Override
    public void bookSeats(BookingDTO bookingDTO) throws SeatAlreadyBookedException {
        // to prevent race condition for multiple requests
        synchronized (this) {
            // checking if the seats are AVAILABLE
            if (!isSeatAvailable(bookingDTO.getSeats())) {
                logger.info("Seat already got booked!");
                throw new SeatAlreadyBookedException("Seat already got booked by other user!");
            } else {
                // checking for existing user
                User existingUser = userRepository.findUserByEmail(bookingDTO.getUser().getEmail());
                if (existingUser != null) {
                    logger.info("User {} already exists...updating details", existingUser.getName());
                    // updating the user details for the existing userId
                    bookingDTO.getUser().setUserId(existingUser.getUserId());
                    bookingDTO.getSeats().addAll(existingUser.getSeats()); // updating previous selected seats for the user else will be overriden when saved
                    bookingDTO.getUser().setSeats(bookingDTO.getSeats());
                    logger.info("Saving user in the database...{}", bookingDTO.getUser());
                    userRepository.save(bookingDTO.getUser());
                    bookingDTO.getSeats().removeAll(existingUser.getSeats()); // removing previous selected seats to avoid repeated info in the mail
                } else {
                    logger.info("Saving the new user in the database...{}", bookingDTO.getUser());
                    User savedUser = userRepository.save(bookingDTO.getUser());
                    savedUser.setSeats(bookingDTO.getSeats());
                    logger.info("Saving seat mapping for the user in the database...");
                    userRepository.save(savedUser); // directly saving the user with seats causes the seats to be inserted instead of update due to JPA CASCADE.ALL property
                }
                bookingDTO.getSeats().forEach(seat -> {
                    logger.info("Updating seat {}{} status to BOOKED", seat.getRowName(), seat.getNumber());
                    seat.setStatus(SeatStatus.BOOKED.toString());
                    seatRepository.save(seat);
                });
            }
        }
        logger.info("Sending email to user @ {}", bookingDTO.getUser().getEmail());
        sendEmail(bookingDTO);
    }

    private boolean isSeatAvailable(List<Seat> seats) {
        AtomicBoolean availability = new AtomicBoolean(true);
        seats.stream().forEach(seat -> {
            if (seatRepository.findById(seat.getSeatId()).get().getStatus().equalsIgnoreCase(SeatStatus.BOOKED.toString())) {
                availability.set(false);
                return;
            }
        });
        logger.info("seats availability - {}", availability.get());
        return availability.get();
    }

    private void sendEmail(BookingDTO bookingDTO) {
        Mail mail = new Mail(bookingDTO.getUser().getEmail(), Constants.SUBJECT, Utils.createEmailMessage(bookingDTO));
        mailService.sendMail(mail);
    }

    /**
     * Reset booking for all users
     * by removing mapping and updating seat status.
     */
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
