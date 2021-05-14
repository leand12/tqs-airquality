import React, { useRef } from "react";
import {
    InputGroup,
    FormControl,
    Button
} from "react-bootstrap";

import StatCard from "components/StatCard";
import AQIStatCard from "components/AQIStatCard";


const Home = () => {
    const searchBar = useRef();

    const handleClick = () => {
        const value = searchBar.current.value;

        window.location.href = `https://www.google.com/search?q=${value}`;
        // fetch(`https://www.google.com/search?q=${value}`)
        //     .then(response => response.json())
        //     .then(data => {
        //         console.log(data);
        //     });
    }

    const data = {
    "aqi":3,
    "ts":3,
    "co":186.92,
    "no2":0.24,
    "o3":117.3,
    "so2":0.1,
    "pm25":2.66,
    "pm10":4.98,
    }
 
    return (
        <>
            <InputGroup className="mb-3">
                <FormControl
                    ref={searchBar}
                    placeholder="Recipient's username"
                    aria-label="Recipient's username"
                    aria-describedby="basic-addon2"
                />
                <InputGroup.Append>
                    <Button variant="outline-secondary" onClick={handleClick}>Button</Button>
                </InputGroup.Append>
            </InputGroup>
            { data["aqi"] && <AQIStatCard value={data["aqi"]} /> }
            <div style={{display: "flex", flexWrap: "wrap", flexGrow: 1, justifyContent: "center"}}>
            {
                Object.entries(data).map(([key, value], index) => (
                    !["aqi","ts"].includes(key) ? <StatCard key={index} label={key} value={value} /> : null
                ))
            }
            </div>
        </>
    )
}

export default Home;
