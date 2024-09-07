import { apiClient } from "./ApiClient"
export  const retriveTodosUsername 
= (username)=>apiClient.get(`/users/${username}/todos`)


export  const basicAuth 
= (teamName,token)=>apiClient.get(`/login/basic/${teamName}`,
        {
                headers: {
                        Authorization: token
                }
        }
)

export const getTeam
= (teamID,token)=>apiClient.get(`/api/team/${teamID}`,
        {
                headers: {
                        Authorization: token
                }
        }
)

export const getBookingDetails
= (teamID,token)=>apiClient.get(`/api/bookingDetails/all?teamId=${teamID}`,
        
        {
                headers: {
                        Authorization: token,
                        'Content-Type': 'application/json'
                }
        }
)

export const getBookingDetailsById
= (bookingID,token)=>apiClient.get(`/api/bookingDetails?bookingId=${bookingID}`,
        
        {
                headers: {
                        Authorization: token
                }
        }
)


export const getBookingDetailsOnDay
= (gameID,start,end,token)=>apiClient.get(`/api/bookingDetails/day?gameId=${gameID}&start=${start}&end=${end}`,
        {
                headers: {
                        Authorization: token
                }
        }
)


export const updateBookingDetails
= (bookingId,start,end,token)=>apiClient.put(`/api/bookingDetails?bookingId=${bookingId}&start=${start}&end=${end}`,
        {},
        {
                headers: {
                        Authorization: token
                }
        }
)

export const saveBooking
= (teamId,gameId,start,end,token)=>apiClient.post(`/api/bookingDetails?teamId=${teamId}&gameId=${gameId}&start=${start}&end=${end}`,
        {},
        {
                headers: {
                        Authorization: token
                }
        }
)

export const deleteBooking
= (bookingId,token)=>apiClient.delete(`/api/bookingDetails?bookingId=${bookingId}`,
        {
                headers: {
                        Authorization: token
                }
        }
)

