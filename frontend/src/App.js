import React from "react";
import {
  BrowserRouter,
  Routes,
  Route,
} from 'react-router-dom';

import Navbar from 'layout/Navbar';
import Home from 'pages/Home';
import Cache from 'pages/Cache';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar />}>
          <Route path="/app" element={<Home />} />
          <Route path="/cache" element={<Cache />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
