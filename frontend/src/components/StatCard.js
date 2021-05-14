import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';

const useStyles = makeStyles({
  root: {
    minWidth: 240,
    backgroundColor: "rgba(255, 255, 255, 0.7)",
  },
  value: {
    fontSize: "2.5rem",
    fontWeight: 600,
    color: "gray", 
  },
  formula: {
    fontSize: "1.5rem",
    fontWeight: 600,
    color:  "darkgray"
  },
  small: {
    position: "relative",
    top: "0.5rem",
    fontSize: "1rem",
  }
});


export default function StatCard({label, value}) {
  const classes = useStyles();

  const Formula = ({children}) => (<span className={classes.formula}>{children}</span>);
  const Small = ({children}) => (<span className={classes.small}>{children}</span>);

  const labelNames = {
    "co": <>CO<Small>2</Small></>,
    "no2": <>NO<Small>2</Small></>,
    "o3": <>O<Small>3</Small></>,
    "so2": <>SO<Small>2</Small></>,
    "pm25": <>PM<Small>2.5</Small></>,
    "pm10": <>PM<Small>10</Small></>,
  }

  const cardStyle = {
    margin: "1rem",
    boxShadow: "0px 2px 4px -1px rgb(0 0 0 / 20%), 0px 4px 5px 0px rgb(0 0 0 / 14%), 0px 1px 10px 0px rgb(0 0 0 / 12%)"
  }

  return (
    <Card className={classes.root} variant="outlined" style={cardStyle}>
      <CardContent className="text-center">
        <div className={classes.value}>
          {value}
        </div>
        <Formula>{labelNames[label]}</Formula>
      </CardContent>
    </Card>
  );
}
