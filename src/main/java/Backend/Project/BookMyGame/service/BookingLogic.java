package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.BookingDetails;
import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.repoistory.BookingRepoistory;
import Backend.Project.BookMyGame.repoistory.GameRepoistory;
import Backend.Project.BookMyGame.repoistory.TeamRepoistory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class BookingLogic {
    private BookingRepoistory bookingRepoistory;
    private TeamRepoistory teamRepoistory;
    private GameRepoistory gameRepoistory;
    private GameService gameService;
    private TeamService teamService;

    public BookingLogic(BookingRepoistory bookingRepoistory, TeamRepoistory teamRepoistory, GameRepoistory gameRepoistory, GameService gameService, TeamService teamService) {
        this.bookingRepoistory = bookingRepoistory;
        this.teamRepoistory = teamRepoistory;
        this.gameRepoistory = gameRepoistory;
        this.gameService = gameService;
        this.teamService = teamService;
    }

    public Boolean checkOverLapping(Game game, LocalDateTime startBookingTime, LocalDateTime endBookingTime) {
        List<BookingDetails> overlappingBookings = bookingRepoistory
                .findByGameAndEndTimeGreaterThanAndStartTimeLessThan(
                       game, startBookingTime, endBookingTime);
        if (!overlappingBookings.isEmpty()) {
            return true;
        }
        return false;
    }
    public BookingDetails bookGameConfirmation(int teamId, int gameId,
           LocalDateTime startBookingTime, LocalDateTime endBookingTime) throws Exception{
        BookingDetails newBookingDetails = new BookingDetails();
        newBookingDetails.setTeam(teamService.getById(teamId));
        newBookingDetails.setGame(gameService.getById(gameId));
        newBookingDetails.setStartTime(startBookingTime);
        newBookingDetails.setEndTime(endBookingTime);
        return bookingRepoistory.save(newBookingDetails);
    }

    public BookingDetails bookGame(int teamId, int gameId, long start, long end) throws Exception{
        LocalDateTime startBookingTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(start),
                TimeZone.getDefault().toZoneId());
        LocalDateTime endBookingTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(end),
                TimeZone.getDefault().toZoneId());
        if(start>=end){
            throw new Exception("please give valid start and end time");
        }
        else if ( end - start > 7200) {
            throw new Exception("You are not allowed to book for more than 2 hours");
        }
        else if(checkLastBooking(null,teamId,gameId,start,end)){
            throw new Exception("you have booking in last 7 days!!!");
        }
        else if(checkOverLapping(gameService.getById(gameId),
                startBookingTime,endBookingTime)){
            throw new Exception("New booking overlaps with existing bookings");
        }

        return bookGameConfirmation(teamId,gameId,startBookingTime,endBookingTime);
    }

    private boolean checkLastBooking(UUID bookingId,int teamId, int gameId, long start, long end) {

        Optional<List<BookingDetails>> optionalOldBookingDetails=bookingRepoistory.findAllByTeamAndGame(
                    teamRepoistory.findById(teamId).get(),gameRepoistory.findById(gameId).get()
            );
        List<BookingDetails> oldBookedTimes = optionalOldBookingDetails.get();

        if(optionalOldBookingDetails.get().isEmpty()){
            return false;
        }
        else {
             return oldBookedTimes.stream()
                     .filter(booking -> !booking.getBookingId().equals(bookingId))
                    .mapToLong(booking -> booking.getEndTime().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()/1000)
                    .anyMatch(oldBookedTime -> Math.abs(start - oldBookedTime) < 518400);

        }
    }

    public BookingDetails updateBookedGame(UUID bookingID, long start, long end) throws Exception{
        LocalDateTime startBookingTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(start),
                TimeZone.getDefault().toZoneId());
        LocalDateTime endBookingTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(end),
                TimeZone.getDefault().toZoneId());
        Optional<BookingDetails> oldBookingDetails=bookingRepoistory.findById(bookingID);
        int teamId=oldBookingDetails.get().getTeam().getTeamId();
        int gameId=oldBookingDetails.get().getGame().getGameId();

        if(start>=end){
            throw new Exception("please give valid start and end time");
        }
        else if(checkLastBooking(bookingID,teamId,gameId,start,end)){
            throw new Exception("you have booking in last 7 days!!!");
        }
        else if(checkOverLapping(gameService.getById(
                oldBookingDetails.get().getGame().getGameId()),
                startBookingTime,endBookingTime)){
            throw new Exception("New booking overlaps with existing bookings");
        }
        oldBookingDetails.get().setStartTime(startBookingTime);
        oldBookingDetails.get().setEndTime(endBookingTime);

        return bookingRepoistory.save(oldBookingDetails.get());
    }


}
