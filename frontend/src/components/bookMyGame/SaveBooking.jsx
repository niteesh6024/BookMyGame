import { useNavigate, useParams } from "react-router-dom"
import { useState, useEffect } from "react"
import { useAuth } from "./security/AuthContext"
import { getBookingDetailsById, getBookingDetailsOnDay, updateBookingDetails,saveBooking } from "./api/BookApiSerivce"
import TextField from '@mui/material/TextField';
import Alert from '@mui/material/Alert';

import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Slider from '@mui/material/Slider';
import './assets/UpdateBookingComponent.css'; 
import Container from '@mui/material/Container';

export default function SaveBookingComponent(){
    
    const {gameId}=useParams()
    const navigate=useNavigate()
    // const {bookingdDetails, setBookingDetails} = useState({})
    const [game,setGame]=useState(-1)
    const [startTimeValue, setStartTimeValue] = useState(-1); 
    const [endTimeValue, setEndTimeValue] = useState(-1);
    const [bookings, setBookings] = useState([]);
    const [dateValue, setDateValue] = useState(new Date());
    const [errorFlag, setErrorFlag]=useState(false)
    const [errorMessage,setErrorMessage]=useState('')
    const authContext=useAuth()
    useEffect(()=> {setDefaultVaules()
                    },[gameId])

    useEffect(() => {
            bookingsOnday()}, [game,dateValue]);

    const setDefaultVaules =  () => {
        setGame(gameId)
        setDateValue(getDate())     
    }

    const getDate= () =>{
        let currentDate = new Date();

        let year = currentDate.getFullYear()
        let month = ('0' + (currentDate.getMonth() + 1)).slice(-2)
        let day 
        if(getTime()>16*60 ){
            day = ('0' + (currentDate.getDate()+1)).slice(-2)
            setStartTimeValue(9*60)
            setEndTimeValue(9*60+15)
        }
        else{
            day = ('0' + currentDate.getDate()).slice(-2)
            setStartTimeValue(getTime())
            setEndTimeValue(getTime()+15)
        }
        
        let formattedDate = year.toString() + '-' + month + '-' + day;

        return formattedDate
    }

    const getTime= () =>{
        let currentDate = new Date();

        let hours = currentDate.getHours();
        let minutes = currentDate.getMinutes();
        let totalMinutes = hours * 60 + minutes;

        return totalMinutes
    }

    const bookingsOnday =  () => {
        const unixStart=parseInt((new Date(dateValue).getTime() / 1000).toFixed(0)) - 330 * 60 

        if(game!==-1){
            getBookingDetailsOnDay(game,unixStart,unixStart+86399,authContext.token)
            .then((response=>{
                var allBookedTimings=[]
                response.data.map((value, index)=>{
                    allBookedTimings.push(value.startTime[3].toString() + ":" + value.startTime[4].toString() + " - "+
                                         value.endTime[3].toString() + ":" + value.endTime[4].toString()
                                            )
                })
                setBookings(allBookedTimings)
            }))
        }
    }

    const updateBooking =()=>{
        const unixStart=parseInt((new Date(dateValue).getTime() / 1000).toFixed(0)) - 330 * 60 
        const startTime=unixStart+startTimeValue*60
        const endTime=unixStart+endTimeValue*60
        saveBooking(authContext.teamId,gameId,startTime,endTime,authContext.token)
        .then(()=>navigate('/mybookings'))
        .catch(error => {
            setErrorFlag(true)
            setErrorMessage(error.response.data.split(": ")[1])
        })
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
    const handleDateChange = (event) => {
        setErrorFlag(false)
        setDateValue(event.target.value);
      };

    function valueLabelFormat(value) {
        const hours = Math.floor(value / 60);
        const minutes = value % 60;
        const minutesStr = (Math.round(minutes / 5) * 5).toString().padStart(2, '0'); // Round minutes to nearest multiple of 5
        
        return `${hours.toString().padStart(2, '0')}:${minutesStr}`;
    }



    return(
        <div className="update-container">
            
            <Box sx={{ mb: 2 }}>
            <Box sx={{ display: 'flex', alignItems: 'center'}}>
                <Box sx={{ width: 300, mr: 2 }}>
                    <TextField
                        id="date-picker"
                        label="Select Date"
                        type="date"
                        value={dateValue}
                        onChange={handleDateChange}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </Box>
                {startTimeValue !== null && // Conditionally render Slider only when startTime is available
                    <Box sx={{ width: 300, mr: 2, marginLeft: '50px' }}>
                        <Typography id="time-slider" gutterBottom>
                            Start Time: {valueLabelFormat(startTimeValue)}
                        </Typography>
                        <Slider
                            value={startTimeValue}
                            min={9 * 60} // 9:00 AM in minutes
                            step={15} // Interval of 15 minutes
                            max={18 * 60} // 6:00 PM in minutes
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
                        {/* {flag && valueLabelFormat(endTimeValue+15) || valueLabelFormat(endTimeValue)} */}
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


  