package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.BookingDetails;
import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;
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
public class BookingServiceImp implements BookingService {
    private BookingRepoistory bookingRepoistory;
    private TeamRepoistory teamRepoistory;
    private GameRepoistory gameRepoistory;
    private GameService gameService;
    private TeamService teamService;
    private BookingLogic bookGame;

    public BookingServiceImp(BookingRepoistory bookingRepoistory, TeamRepoistory teamRepoistory,
                             GameRepoistory gameRepoistory, GameService gameService,
                             TeamService teamService, BookingLogic bookGame) {
        this.bookingRepoistory = bookingRepoistory;
        this.teamRepoistory = teamRepoistory;
        this.gameRepoistory = gameRepoistory;
        this.gameService = gameService;
        this.teamService = teamService;
        this.bookGame = bookGame;
    }

    @Override
    public BookingDetails saveBooking(int teamId,int gameId, long start,long end) throws Exception{
//        Optional<List<BookingDetails>> optionalOldBookingDetails=bookingRepoistory.findAllByTeamAndGame(
//                teamRepoistory.findById(teamId).get(),gameRepoistory.findById(gameId).get()
//        );
//
//        if(optionalOldBookingDetails.get().isEmpty()){
//            return bookGame.bookGame(teamId,gameId,start,end);
//        }
//        else {
//            long oldBookedTime=
//                    optionalOldBookingDetails.get().get(0).getEndTime().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()/1000;
//
//            System.out.println(oldBookedTime);
//            if( Math.abs(start - oldBookedTime) < 518400 ){
//                throw new Exception("you have booking in last 7 days!!!");
//            }
//            return bookGame.bookGame(teamId,gameId,start,end);
//        }
        return bookGame.bookGame(teamId,gameId,start,end);
    }
    @Override
    public BookingDetails getBookingById(UUID bookingId) throws Exception{
        Optional<BookingDetails> optionalBookingDetails=bookingRepoistory.findById(bookingId);
        if(optionalBookingDetails.isEmpty()){
            throw new Exception("booking not found");
        }
        return optionalBookingDetails.get();
    }
    @Override
    public BookingDetails UpdateBooking(UUID bookingId,  long start,long end) throws Exception{
        return bookGame.updateBookedGame(bookingId,start,end);
    }

    @Override
    public List<BookingDetails> getAllBookings(Team team, Game game) throws Exception {
        return bookingRepoistory.findAllByTeamAndGame(team,game).get();
    }

    @Override
    public List<BookingDetails> getAllBookings(Team team) throws Exception{
        return bookingRepoistory.findAllByTeam(team);
//        return null;
    }
    @Override
    public List<BookingDetails> getAllBookingsOnDay(int gameId, long start,long end) throws Exception {
           Optional<Game> game=gameRepoistory.findById(gameId);
        if(game.isEmpty()){
            throw new Exception("Game not found");
        }
        LocalDateTime startBookingTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(start),
                TimeZone.getDefault().toZoneId());
        LocalDateTime endBookingTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(end),
                TimeZone.getDefault().toZoneId());
        return  bookingRepoistory.findByGameAndStartTimeGreaterThanAndEndTimeLessThan(
                game.get(),startBookingTime,endBookingTime);
    }
    @Override
    public List<BookingDetails> getAllBookings(Game game) throws Exception {
        return bookingRepoistory.findAllByGame(game);
    }
    @Override
    public void deleteBooking(UUID bookingId) throws Exception{
        bookingRepoistory.deleteById(bookingId);
    }
    @Override
    public void deleteAll(){
        bookingRepoistory.deleteAll();
    }

}
