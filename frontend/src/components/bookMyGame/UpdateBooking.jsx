import { useNavigate, useParams } from "react-router-dom"
import { useState, useEffect } from "react"
import { useAuth } from "./security/AuthContext"
import { getBookingDetailsById, getBookingDetailsOnDay, updateBookingDetails } from "./api/BookApiSerivce"

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Slider from '@mui/material/Slider';
import './assets/UpdateBookingComponent.css'; 
import Container from '@mui/material/Container';

export default function UpdateBookingComponent(){
    
    const {bookingId}=useParams()
    const navigate=useNavigate()
    const [game,setGame]=useState(-1)
    const [startTimeValue, setStartTimeValue] = useState(11 * 60); 
    const [endTimeValue, setEndTimeValue] = useState(11 * 60);
    const [bookings, setBookings] = useState([]);
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [isToday,serIsToday]=useState(false)
    const [errorFlag, setErrorFlag]=useState(false)
    const [errorMessage,setErrorMessage]=useState('')
    const authContext=useAuth()

    useEffect(()=> {bookingDetailsById()
                    },[bookingId])
    useEffect(() => {
            bookingsOnday()}, [game,selectedDate]);

    const bookingDetailsById =  () => {
        getBookingDetailsById(bookingId,authContext.token)
        .then((response => {
            setGame(response.data.game.gameId)
            setSelectedDate(getDate(response.data.startTime))
            setStartTimeValue(response.data.startTime[3] * 60 + response.data.startTime[4])
            setEndTimeValue(response.data.startTime[3] * 60 + response.data.startTime[4]+15)
            setTime(response.data.startTime)
        }
        ))        
    }

    const setTime=(dateTime)=>{
        const date=new Date(getDate(dateTime))
        currentDate.setHours(0, 0, 0, 0); 
      if (date >= currentDate) {
        console.log("hi",date)
    }

    }

    const bookingsOnday =  () => {
        const unixStart=parseInt((new Date(selectedDate).getTime() / 1000).toFixed(0)) - 330 * 60 

        if(game!==-1){
            getBookingDetailsOnDay(game,unixStart,unixStart+86399,authContext.token)
            .then((response=>{
                var allBookedTimings=[]
                response.data.map((value, index)=>{
                    allBookedTimings.push(value.startTime[3].toString().padStart(2, '0') + ":" + value.startTime[4].toString().padStart(2, '0') + " - "+
                                         value.endTime[3].toString().padStart(2, '0') + ":" + value.endTime[4].toString().padStart(2, '0')
                                            )
                })
                setBookings(allBookedTimings)
                console.log(bookings)
            }))
        }
    }

    const updateBooking =()=>{
        const unixStart=parseInt((new Date(selectedDate).getTime() / 1000).toFixed(0)) - 330 * 60 
        const startTime=unixStart+startTimeValue*60
        const endTime=unixStart+endTimeValue*60
        updateBookingDetails(bookingId,startTime,endTime,authContext.token)
        .then(()=>navigate('/mybookings'))
        .catch(error => {
            setErrorFlag(true)
            setErrorMessage(error.response.data.split(": ")[1])
        })
    }


    const getDate= (dateTime) =>{
        if (dateTime && dateTime.length > 0) {
            const [year, month, day, hours, minutes, seconds] = dateTime;
            return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`
        }

        return new Date();
    }

    const handleStartTimeChange = (event, newValue) => {
      if (typeof newValue === 'number') {
        setErrorFlag(false)
        setStartTimeValue(newValue);
        setEndTimeValue(newValue+15)
      }
    };
    const handleEndTimeChange = (event, newValue) => {
        if (typeof newValue === 'number') {
            setErrorFlag(false)
            setEndTimeValue(newValue)
        }
      };

    function valueLabelFormat(value) {
        const hours = Math.floor(value / 60);
        const minutes = value % 60;
        const minutesStr = (Math.round(minutes / 5) * 5).toString().padStart(2, '0'); // Round minutes to nearest multiple of 5
        
        return `${hours.toString().padStart(2, '0')}:${minutesStr}`;
    }

    const handleDateChange = date => {
      const currentDate = new Date();
      currentDate.setHours(0, 0, 0, 0); 
      if (date >= currentDate) {
        setSelectedDate(date);
        setErrorFlag(false)
      }
    };

    const handleMaxDate = ()=> {
        const date = new Date();
        const newDate = new Date();

        if(date.getMonth()>=10){
            newDate.setFullYear(date.getFullYear()+1)
            newDate.setMonth(date.getMonth()%10)
            return newDate;
        }
        newDate.setMonth(date.getMonth()%11+2);
        return newDate;
    }

    return(
        <div className="update-container">            
            <Box sx={{ mb: 2 }}>
            <Box sx={{ display: 'flex', alignItems: 'center'}}>
                <Box sx={{ width: 300, mr: 2 }}>
                    <DatePicker
                        selected={selectedDate}
                        onChange={handleDateChange}
                        minDate={new Date()} 
                        maxDate={handleMaxDate()}
                        dateFormat="dd/MM/yyyy"
                    />

                </Box>
                {startTimeValue !== null && 
                    <Box sx={{ width: 300, mr: 2, marginLeft: '50px' }}>
                        <Typography id="time-slider" gutterBottom>
                            Start Time: {valueLabelFormat(startTimeValue)}
                        </Typography>
                        <Slider
                            value={startTimeValue}
                            min={9 * 60} 
                            step={15} 
                            max={18 * 60}
                            valueLabelFormat={valueLabelFormat}
                            onChange={handleStartTimeChange}
                            valueLabelDisplay="auto"
                            aria-labelledby="time-slider"
                        />
                    </Box>
                }
            
                <Box sx={{ width: 300, mr: 2, marginLeft: '50px' }}>
                    <Typography id="end-time-slider" gutterBottom>
                        End Time: {valueLabelFormat(endTimeValue)}
                    </Typography>
                    <Slider
                        value={endTimeValue}
                        min={startTimeValue} 
                        step={15}
                        max={startTimeValue + 120} // Let's assume max duration is 2 hours
                        valueLabelFormat={valueLabelFormat}
                        onChange={handleEndTimeChange}
                        valueLabelDisplay="auto"
                        aria-labelledby="end-time-slider"
                    />
                </Box>
            </Box>
            <Box sx={{ mt: 5 }}>
                {errorFlag&&<Box sx={{ ml: 43,mb:2,display: 'flex', alignItems: 'center'}}>
                    <Alert severity="warning" onClose={() => {setErrorFlag(false)}}>
                        {errorMessage}
                    </Alert>
                </Box>}
                <button className="btn btn-primary" onClick={() => updateBooking()}>Save Booking</button>
            </Box>
            <Box sx={{ mt: 12 }}>
                <Container maxWidth="sm">
                    <h4>Today bookings:</h4>
                    {bookings.map((value, index) => (
                    <p key={index}>{value}</p>
                    ))}
                </Container>
            </Box>
            </Box>
        </div>
    )
}


  