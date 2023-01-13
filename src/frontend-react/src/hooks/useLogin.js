import { useState} from "react";
import { useAuthContext } from "./useAuthContext";
import API from "../api";

export const useLogin = () => {
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const { dispatch } = useAuthContext()
    const login = async (username, password) => {

        const response = await API.post('users/login', {
            username: username,
            password: password
        })

        if (response.status !== 200){
            setLoading(false)
            setError(response.data)
        }
        if (response.status === 200){
            setLoading(false)
            localStorage.setItem('user', username)
            dispatch({type: 'LOGIN', payload: { username: username, token: response.data}})
            setLoading(false)
        }
    }
    return { login, error, loading }
}