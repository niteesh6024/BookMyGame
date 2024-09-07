import { apiClient } from "./ApiClient"


export const getGames
= (token)=>apiClient.get(`/api/game/all`,
        {
                headers: {
                        Authorization: token
                }
        }
)
