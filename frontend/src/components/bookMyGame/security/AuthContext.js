import { createContext, useContext, useState, useEffect } from "react";
import { basicAuth } from "../api/BookApiSerivce";
export const AuthContext = createContext()
export const useAuth =() => useContext(AuthContext)

export default function AuthProvider({children}){
    const [isAuthenticated,setAuthenticated]=useState(false)
    const [username,setusername]=useState(null)
    const [teamId,setTeamId]=useState(null)
    const [token,setToken]=useState(null)
    const [isAdmin, setIsAdmin]=useState(false)
    // const valueToBeShared= {number,isAuthenticated, setAuthenticated}
    // function login(username, password){
    //     if(username==="niteesh" && password==="niteesh"){
    //         setusername(username)
    //         setAuthenticated(true)
    //         return true
    //     }
    //     else{
    //         logout()
    //         return false
    //     }
    // }

    async function login(username, password){
        const token = 'Basic ' + window.btoa(username+":"+password)
        try{
            const response=await basicAuth(username,token) 
            if(response.status===200){
                setusername(username)
                setAuthenticated(true)
                setToken(token)
                setTeamId(response.data.teamId)

                response.data.roles.length>1 ? setIsAdmin(true) : 
                console.log(response.data.roles.length)
                // apiClient.interceptors.request.use(
                //     (config) => {
                //         console.log('intercepting and adding a token')
                //         config.headers.Authorization = token
                //         return config
                //     }
                // )
                return true
            }
            else{
                await logout();
                return false
            }
        }
        catch(Error){
            await logout();
            return false
        }
    }

    async function logout(){
        setAuthenticated(false)
        setusername(null)
        setToken(null)
        setTeamId(null)
        setIsAdmin(false)
        
    }

    return (
        <AuthContext.Provider value={{isAuthenticated, login,logout, username, teamId, token, isAdmin}}>  
            {children}
        </AuthContext.Provider>
    )
}