import React, { useState, useEffect } from 'react';
import { useAuth } from './security/AuthContext';
import { getBookingDetails, deleteBooking } from './api/BookApiSerivce';
import './assets/bookingdetails.css'
import { useNavigate} from 'react-router-dom'

const BookingDetailsComponent = () => {
  const [jsonData, setJsonData] = useState([]);
  const authContext =useAuth();
  const navigate=useNavigate()

  const fetchData = async () => {
    try {
        await getBookingDetails( authContext.teamId, authContext.token)
        .then(response=>{
          setJsonData(response.data)
          console.log(response.data)
        })
        
    } catch (error) {
        console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);


  const updateBooking = (bookingId) => {
    navigate(`/update/${bookingId}`)
  }

  const deleteBookingById = async (bookingId) => {
    await deleteBooking(bookingId, authContext.token)
    await fetchData()
  }

  return (
    <div>
      <h2>My Team Bookings</h2>
      <table className="custom-table">
        <thead>
          <tr>
            <th>Game ID</th>
            <th>Game Name</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>change</th>
            <th>withdraw</th>
          </tr>
        </thead>
        <tbody>
          {jsonData.map((item, index) => (
            <tr key={index}>
              <td>{item.game.gameId}</td>
              <td>{item.game.name}</td>
              <td>{new Date(item.startTime[0], item.startTime[1] - 1, item.startTime[2], item.startTime[3], item.startTime[4]).toLocaleString('en-GB')}</td>
              <td>{new Date(item.endTime[0], item.endTime[1] - 1, item.endTime[2], item.endTime[3], item.endTime[4]).toLocaleString('en-GB') } </td>
              <td><button className="btn btn-warning" onClick={() => updateBooking(item.bookingId)}>Update Booking</button></td>
              <td><button className="btn btn-danger" onClick={() => deleteBookingById(item.bookingId)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookingDetailsComponent;
