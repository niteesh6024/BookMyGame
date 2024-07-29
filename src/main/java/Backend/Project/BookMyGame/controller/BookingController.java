package Backend.Project.BookMyGame.controller;

import Backend.Project.BookMyGame.entity.BookingDetails;
import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;
import Backend.Project.BookMyGame.repoistory.BookingRepoistory;
import Backend.Project.BookMyGame.service.BookingService;
import Backend.Project.BookMyGame.service.GameService;
import Backend.Project.BookMyGame.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/bookingDetails")
//@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    private BookingService bookingService;
    private GameService gameService;
    private TeamService teamService;
    private BookingRepoistory bookingRepoistory;
    @Autowired
    public void setBookingRepoistory(BookingRepoistory bookingRepoistory) {
        this.bookingRepoistory = bookingRepoistory;
    }

    public BookingController(BookingService bookingService, GameService gameService, TeamService teamService) {
        this.bookingService = bookingService;
        this.gameService = gameService;
        this.teamService =teamService;
    }

    @GetMapping(value = "/all")
    private ResponseEntity<?> getBookingDetailsByTeamId(@RequestParam(required = false) Integer teamId,@RequestParam(required = false) Integer gameId){
        try{
            if (teamId != null && gameId != null) {
                List<BookingDetails> bookings=bookingService.getAllBookings(teamService.getById(teamId), gameService.getById(gameId));
                return new ResponseEntity<List<BookingDetails>>(bookings,null, HttpStatus.OK);
            } else if (teamId != null) {
                List<BookingDetails> bookings=bookingService.getAllBookings(teamService.getById(teamId));
                return new ResponseEntity<List<BookingDetails>>(bookings,null, HttpStatus.OK);
            } else if (gameId != null) {
                List<BookingDetails> bookings=bookingService.getAllBookings(gameService.getById(gameId));
                return new ResponseEntity<List<BookingDetails>>(bookings,null, HttpStatus.OK);
            }
            else {
                return ResponseEntity.badRequest().body("Please provide either teamId or gameId");
            }
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping()
    private ResponseEntity<?> getBookingDetailsByBookingId(@RequestParam(required = true) UUID bookingId){
        try{
            BookingDetails booking=bookingService.getBookingById(bookingId);
            return new ResponseEntity<BookingDetails>(booking,null, HttpStatus.OK);

        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("day")
    private ResponseEntity<?> getBookingDetailsOnDay(@RequestParam int gameId,
                                                     @RequestParam long start, @RequestParam long end){

        try{
                List<BookingDetails> booking=bookingService.getAllBookingsOnDay(gameId,start,end);
                return new ResponseEntity<List<BookingDetails>>(booking,null, HttpStatus.OK);}
        catch (Exception exception){
            return new ResponseEntity<String>(exception.toString(),null, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping()
    private ResponseEntity<?> saveBookingDetailsTest(
            @RequestParam int teamId,@RequestParam int gameId,
            @RequestParam long start, @RequestParam long end){
        try{
            return new ResponseEntity<BookingDetails>(bookingService.saveBooking(
                    teamId,gameId,start,end
            ),null, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.toString(),null, HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping()
    private ResponseEntity<?> updateBooking(
            @RequestParam String bookingId,
             @RequestParam long start, @RequestParam long end){
        try{
            return new ResponseEntity<BookingDetails>(
                    bookingService.UpdateBooking(UUID.fromString(bookingId),start,end),null, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.toString(),null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping()
    private ResponseEntity<String> deleteBooking(@RequestParam String bookingId){
        try{
            bookingService.deleteBooking(UUID.fromString(bookingId));
            return new ResponseEntity<String>("Successfully deleted",null,HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null,HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/admin/all")
    private ResponseEntity<String> deleteBookings(){
        bookingService.deleteAll();
        return new ResponseEntity<String>("Successfully deleted",null,HttpStatus.OK);
    }

}
