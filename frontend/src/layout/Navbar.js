import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";

import EqualizerIcon from "@material-ui/icons/Equalizer";
import AppsIcon from "@material-ui/icons/Apps";

import {
  Link,
  Outlet,
} from 'react-router-dom';


const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1
  },
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: {
    display: "none",
    padding: "0 0.8rem 0 0.5rem",
    fontSize: "1.125rem",
    [theme.breakpoints.up("sm")]: {
      display: "block"
    }
  },
  inputRoot: {
    color: "inherit"
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create("width"),
    width: "100%"
  },
  navBar: {
    display: "flex",
    alignItems: "center"
  },
  navLink: {
    display: "flex",
    alignItems: "center",
    margin: "0 0.5rem",
    cursor: "pointer",
    color: "inherit !important",
    textDecoration: "none !important"
  }
}));

export default function Navbar() {
  const classes = useStyles();

  const handleProfileMenuOpen = (event) => {
    //setAnchorEl(event.currentTarget);
  };

  return (
    <div className={classes.grow} >
      <AppBar position="static" style={{backgroundColor: "rgba(255, 255, 255, 0.8)", color: "gray"}}>
        <Toolbar>
          <div className={classes.grow} />
          <div className={classes.navBar}>
            <Link className={classes.navLink} to="/app">
              <AppsIcon />
              <Typography className={classes.title} variant="h6" noWrap>
                AirQuality App
              </Typography>
            </Link>
            <Link className={classes.navLink} to="/cache">
              <EqualizerIcon />
              <Typography className={classes.title} variant="h6" noWrap>
                Cache Statistics
              </Typography>
            </Link>
          </div>
        </Toolbar>
      </AppBar>
      <Outlet />
    </div>
  );
}
