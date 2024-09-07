import { useState } from 'react'
import { useNavigate} from 'react-router-dom'
import { useAuth } from './security/AuthContext'

export default function LoginComponent(){
    const [userName, setUserName]=useState('')
    const [password, setpassword]=useState('')
    const [showfail, setshowfail]=useState(false)
    const navigate=useNavigate()
    const authContext =useAuth()
    // function SuccessMessage(){
    //     if(showSuccess){
    //        return <div className="successMessage" >Authentication successfull</div>
    //     }
    //     return null
    // }
    // function FailureMessage(){
    //     if(showfail){
    //        return <div className="errorMessage">Authentication failed. check credentials</div>
    //     }
    //     return null
    // }
    function handleUserNameCHange(event){
        setUserName(event.target.value)
        
    }
    function handlePasswordCHange(event){
        setpassword(event.target.value)
    }
    async function handleSubmit(){
        if(await authContext.login(userName, password)){
            navigate(`/welcome`)
            
        }
        else{
            setshowfail(true)
        }

    }

    return(
        <div className="Login">
            <h1>Time to Login</h1>
            {showfail && <div className="errorMessage">Authentication failed. check credentials</div>}
            <div className="LoginForm">
                
                <div>
                    <label>User Name</label>
                    <input type="text" name="username" value={userName} onChange={handleUserNameCHange}/>
                </div>
                <div>
                    <label>Password</label>
                    <input type="password" name="password" value={password} onChange={handlePasswordCHange}/>
                </div>
                <div>
                    <button type="button" name="login" onClick={handleSubmit}>Login</button>
                </div>
            </div>
        </div>
    )
}
