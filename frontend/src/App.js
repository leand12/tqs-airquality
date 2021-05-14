import React from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
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
          <Route element={<Navigate to="/app" />} />
        </Route>
        <Route path="*" element={<Navigate to="/app" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
