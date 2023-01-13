import { useState} from "react";
import { useAuthContext } from "./useAuthContext";
import API from "../api";

export const useRegister = () => {
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const { dispatch } = useAuthContext()
    const register = async (username, password, email) => {

        const response = await API.post('users/register', {
            username: username,
            password: password,
            email: email
        })

        if (response.status !== 201){
            setLoading(false)
            setError(response.data)
        }
        if (response.status === 201){
            setLoading(false)
            localStorage.setItem('user', JSON.stringify({username: username, token: response.data}))
            dispatch({type: 'LOGIN', payload: { username: username, token: response.data}})
            setLoading(false)
        }
    }
    return { register, error, loading }
}