package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.BookingDetails;
import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingDetails saveBooking(int teamId,int gameId, long start,long end) throws Exception;
    List<BookingDetails> getAllBookings(Team team, Game game) throws Exception;
    List<BookingDetails> getAllBookings(Team team) throws Exception;
    List<BookingDetails> getAllBookings(Game game) throws Exception;
    BookingDetails getBookingById(UUID bookingId) throws Exception;
    BookingDetails UpdateBooking(UUID bookingId, long start,long end) throws Exception;
    List<BookingDetails> getAllBookingsOnDay(int gameId, long start,long end) throws Exception;
    void deleteBooking(UUID bookingId) throws Exception;
    void deleteAll();

}
