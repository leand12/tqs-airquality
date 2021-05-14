import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';

const useStyles = makeStyles({
  root: {
    minWidth: 240,
    maxWidth: 300,
    backgroundColor: "rgba(255, 255, 255, 0.7)",
  },
  value: {
    fontSize: "3rem",
    fontWeight: 600,
  },
  label: {
    fontSize: "2rem",
    fontWeight: 600,
  }
});


export default function AQIStatCard({value}) {
  const classes = useStyles();

  const getColor = () => {
      if (value <= 50)
        return "#00e033";
      else if (value <= 100)
        return "#fff93e";
      else if (value <= 150)
        return "#ff7921";
      else if (value <= 200)
        return "#ff0012";
      else if (value <= 300)
        return "#934595";
  }

  const cardStyle = {
    margin: "1rem auto",
    boxShadow: "0px 2px 4px -1px rgb(0 0 0 / 20%), 0px 4px 5px 0px rgb(0 0 0 / 14%), 0px 1px 10px 0px rgb(0 0 0 / 12%)"
  }

  return (
    <Card className={classes.root} variant="outlined" style={cardStyle}>
      <CardContent className="text-center" style={{color: getColor()}}>
        <div className={classes.value}>
          {value}
        </div>
        <div className={classes.label}>
          AQI
        </div>
      </CardContent>
    </Card>
  );
}
