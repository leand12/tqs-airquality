import React, { useRef, useState } from "react";
import { makeStyles } from '@material-ui/core/styles';
import {
  InputGroup,
  FormControl,
  Button,
  Row,
  Col
} from "react-bootstrap";

import StatCard from "components/StatCard";
import MainStatCard from "components/MainStatCard";


const useStyles = makeStyles({
  title: {
    fontSize: "2.125rem",
    fontWeight: 600,
    color: "white",
    textShadow: "2px 2px #888",
    marginTop: "2rem",
  },
  value: {
    fontSize: "2.125rem",
    fontWeight: 600,
    color: "gray", 
    paddingLeft: '1rem',
    margin: "1rem auto",
  },
});



const Home = () => {
  const [data, setData] = useState(false);
  const [loading, setLoading] = useState(false);
  const cityInput = useRef();
  const latInput = useRef();
  const lonInput = useRef();
  const classes = useStyles();

  const submitCity = async () => {
    const city = cityInput.current.value;

    if (city) {
      setLoading(true);
      await fetch(`http://localhost:8080/api/v1/city/${city}`,
        {mode: "cors", headers: {"Access-Control-Allow-Origin": "*"}})
          .then((res) => {
            if (res.status === 200)
              return res.json();
          })
          .then(data => {
              console.log(data);
              setData(data);
              setLoading(false);
          })
          .catch(() => setLoading(false));
    }
  }

  const submitCoords = async () => {
    const lat = latInput.current.value;
    const lon = lonInput.current.value;
    
    if (lat && lon) {
      setLoading(true);
      await fetch(`http://localhost:8080/api/v1/geo?lat=${lat}&lon=${lon}`,
        {mode: "cors", headers: {"Access-Control-Allow-Origin": "*"}})
          .then((res) => {
            console.log("ok:", res)
            if (res.status === 200)
              return res.json();
          })
          .then(data => {
              console.log(data);
              setData(data);
              setLoading(false);
          })
          .catch(() => setLoading(false));
    }
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
      <Row>
        <Col md={12} lg={6}>
        <h2 className={classes.title}>Search by City</h2>
        <InputGroup className="mb-3">
          <FormControl
            ref={cityInput}
            placeholder="City"
            aria-label="City"
            aria-describedby="basic-addon2"
          />
          <InputGroup.Append>
            <Button id="city" variant="outline-secondary" onClick={submitCity}>Search</Button>
          </InputGroup.Append>
        </InputGroup>
        </Col>

        <Col md={12} lg={6}>
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
            placeholder="Longitude" 
            type="number"
            maxLength="10"
          />
          <InputGroup.Append>
            <Button id="geo" variant="outline-secondary" onClick={submitCoords}>Search</Button>
          </InputGroup.Append>
        </InputGroup>
        </Col>
      </Row>

      { 
        loading ? (
          <Row>
            <span className={classes.value}>Loading...</span>
          </Row>
        ) : 
        data === false ? null : data ? (
          <>
            <Row>
              <span id="response" className={classes.value}>{`${data.city_name}, (${data.lat.toFixed(6)}, ${data.lon.toFixed(6)})`}</span>
            </Row>

            <MainStatCard color={true} label={"aqi"} value={data["data"][0]["aqi"]} />
            <div style={{ display: "flex", flexWrap: "wrap", flexGrow: 1, justifyContent: "center" }}>
              {
                Object.entries(data["data"][0]).map(([key, value], index) => (
                  !["aqi", "ts"].includes(key) ? <StatCard key={index} label={key} value={value} /> : null
                ))
              }
            </div>
          </>
      ) : (
          <Row>
            <span id="response" className={classes.value}>Not Found</span>
          </Row>
      )}
    </>
  )
}

export default Home;
