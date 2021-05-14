import React from "react";
import MainStatCard from "components/MainStatCard";


const Cache = () => {

  const data = {
    numRequests: 32,
    numHits: 22,
    numMisses: 12,
  }

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
