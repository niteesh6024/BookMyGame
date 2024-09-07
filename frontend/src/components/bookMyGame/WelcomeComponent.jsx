import { useAuth } from "./security/AuthContext"
import { useState, useEffect } from 'react';
import { getGames } from "./api/GameApiSerivce";
import './assets/game.css'; 
import { useNavigate} from 'react-router-dom'

export default function WelcomeComponent(){
    // hooks such as useAuth() should always be called at the top level of a functional component,
    // and they should not be called conditionally or within nested functions
    const authcontext=useAuth();
    const [Games, setGames] = useState([]);
    const navigate=useNavigate()

    function getAllGames(){
        getGames( authcontext.token)
        .then(response=>{
            setGames(response.data)
          console.log(response.data)
        })
    }

    const saveBooking = (gameId) => {
        navigate(`/save/${gameId}`)
    }


    useEffect(() => {
        getAllGames();
      }, []);

    return (
        <div className="container">
            <div className="game-list">

            {Games.map((item, index) => (
                // <h1>{item.name}</h1>
                
                <button key={index} className="game-box" onClick={() => saveBooking(item.gameId)}>
                    {item.name}
                </button>
            ))}
            {authcontext.isAdmin && 
                <button  className="game-box" onClick={() => saveBooking(-1)}>
                add new game
            </button>
            }
            </div>
        </div>
    )
}