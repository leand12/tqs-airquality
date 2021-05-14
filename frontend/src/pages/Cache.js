import React, {useEffect} from "react";
import MainStatCard from "components/MainStatCard";


const Cache = () => {

  const data = {
    numRequests: 32,
    numHits: 22,
    numMisses: 12,
  }

  const getCache = async () => {
    await fetch(`http://localhost:8080/api/v1/cache`,
      {mode: "cors", headers: {"Access-Control-Allow-Origin": "*"}})
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
  }

  useEffect(async () => {
    await getCache();
  },[]);

  return (
    <>
      <h1 style={{fontSize: "3rem",
        fontWeight: 600,
        color: "white",
        textShadow: "2px 2px #888",
        margin: "3rem 0 2rem 0"}}
      >Cache Statistics</h1>
      <div style={{ display: "flex", flexWrap: "wrap", flexGrow: 1, justifyContent: "center" }}>
        {
          Object.entries(data).map(([key, value], index) => (
            <MainStatCard key={index} label={key} value={value} />
          ))
        }
      </div>
    </>
  )
}

export default Cache;
