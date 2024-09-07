import './BookApp.css'
import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import BookingDetailsComponent from './BookingDetailsComponent'
import UpdateBookingComponent from './UpdateBooking'
import WelcomeComponent from './WelcomeComponent'
import LoginComponent from './LoginComponent'
import SaveBookingComponent from './SaveBooking'
import AuthProvider, { useAuth } from './security/AuthContext'
// import TodoComponent from './TodoComponent'

function AuthenticatedRoute({children}){
    const authContext=useAuth()
    if(authContext.isAuthenticated)
    return(
            children
        )
    else{
        return <Navigate to="/"/>
    }
}

export default function BookApp(){
    return(
        <div className="BookApp">
            <AuthProvider>
            <BrowserRouter>
                
                <HeaderComponent/>
                <Routes>
                    <Route path='/' element={<LoginComponent/>}/>
                    <Route path='/login' element={<LoginComponent/>}/>
                    <Route path='/welcome' element={
                        <AuthenticatedRoute>
                            <WelcomeComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path='/mybookings' element={
                        <AuthenticatedRoute>
                            <BookingDetailsComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path='/update/:bookingId' element={
                        <AuthenticatedRoute>
                            <UpdateBookingComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path='/save/:gameId' element={
                        <AuthenticatedRoute>
                            <SaveBookingComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path='/logout' element={
                        <AuthenticatedRoute>
                            <LogoutComponent/>
                        </AuthenticatedRoute>
                    }></Route>
                    
                    {/* <Route path='*' element={<ErrorComponent/>}></Route> */}
                </Routes>
            
                </BrowserRouter>
            </AuthProvider>
        </div>
    )
}

