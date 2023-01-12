import { useState} from "react";
import { useAuthContext } from "./useAuthContext";

export const useLogin = () => {
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
}