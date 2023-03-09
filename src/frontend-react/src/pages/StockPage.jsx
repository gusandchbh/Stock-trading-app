import React, {useEffect, useState} from "react";
import {API} from "../api";

const StockPage = () => {
    const [result, setResult] = useState([]);

    const retrieveStocks = async () => {
        try {
            const response = await API.get("stocks/all");
            setResult(response.data)
        } catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        retrieveStocks()
    }, [])

    return (
        <div style={{display: "flex"}}>
            <div style={{alignItems: "center"}}>
                <h1> Welcome to Stock Tradify! </h1>
            </div>

            <div>
                {result.map((item, index) => {
                    return (
                        <li style={{flexDirection: "column"}} key={index}> {item.name} Price: {item.price} USD </li>
                    )
                })}
            </div>
        </div>
    );
};

export default StockPage;