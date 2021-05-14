import React, { useRef, useState } from "react";
import { makeStyles } from '@material-ui/core/styles';
import {
  InputGroup,
  FormControl,
  Button
} from "react-bootstrap";

import StatCard from "components/StatCard";
import MainStatCard from "components/MainStatCard";


const useStyles = makeStyles({
  title: {
    fontSize: "2.125rem",
    fontWeight: 600,
    color: "white",
    textShadow: "2px 2px #888",
    marginTop: "2rem"
  }
});



const Home = () => {
  const [city, setCity] = useState(null);
  const [data, setData] = useState(null);

  const cityInput = useRef();
  const latInput = useRef();
  const lonInput = useRef();
  const classes = useStyles();

  const submitCity = async () => {
    const city = cityInput.current.value;

    if (city)
      await fetch(`http://localhost:8080/api/v1/city/${city}`,
        {mode: "cors", headers: {"Access-Control-Allow-Origin": "*"}})
          .then(response => {console.log(response); return response.json();})
          .then(data => {
              console.log(data);
          });

    setData(example["data"][0]);
  }

  const submitCoords = async () => {
    const lat = latInput.current.value;
    const lon = lonInput.current.value;
    
    if (lat && lon)
      await fetch(`http://localhost:8080/api/v1/geo?lat=${lat}&lon=${lon}`,
        {mode: "cors", headers: {"Access-Control-Allow-Origin": "*"}})
          .then(response => {console.log(response); return response.json();})
          .then(data => {
              console.log(data);
          });
  }


  const example = {
    city_name: "Aveiro",
    lat: 2,
    lon: 3,
    data : [{
      "aqi": 3,
      "ts": 3,
      "co": 186.92,
      "no2": 0.24,
      "o3": 117.3,
      "so2": 0.1,
      "pm25": 2.66,
      "pm10": 4.98,
    }]
  }


  return (
    <>
      <div style={{maxWidth: "500px", margin: "auto"}}>
        <h2 className={classes.title}>Search by City</h2>
        <InputGroup className="mb-3">
          <FormControl
            ref={cityInput}
            placeholder="City"
            aria-label="City"
            aria-describedby="basic-addon2"
          />
          <InputGroup.Append>
            <Button variant="outline-secondary" onClick={submitCity}>Search</Button>
          </InputGroup.Append>
        </InputGroup>

        <h2 className={classes.title}>Search by Geo Coordinates</h2>
        <InputGroup className="mb-5">
          <FormControl 
            ref={latInput}
            placeholder="Latitude" 
            type="number"
            maxLength="10"
          />
          <FormControl
            ref={lonInput}
            placeholder="Latitude" 
            type="number"
            maxLength="10"
          />
          <InputGroup.Append>
            <Button variant="outline-secondary" onClick={submitCoords}>Search</Button>
          </InputGroup.Append>
        </InputGroup>
      </div>

      { data && <MainStatCard color={true} label={"aqi"} value={data["aqi"]} />}
      <div style={{ display: "flex", flexWrap: "wrap", flexGrow: 1, justifyContent: "center" }}>
        {
          data && Object.entries(data).map(([key, value], index) => (
            !["aqi", "ts"].includes(key) ? <StatCard key={index} label={key} value={value} /> : null
          ))
        }
      </div>
    </>
  )
}

export default Home;
