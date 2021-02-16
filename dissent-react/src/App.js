import React from 'react';
import HomePage from './components/HomePage.js';
import './App.css';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import Home from './components/HomePage.js';
import About from './components/About.js';
import LogoTextOnly from './components/images/logo-text-only.png';

function App() {
  return (      
    <Router>
      <nav className="navbar navbar-expand-lg navbar-light bg-light mb-4">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/"><img src={LogoTextOnly} id="navbar-logo" /></Link>
          <div className="row">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link className="nav-link active" to="/about" aria-current="page">About</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link active" to="/user/my-profile">Profile</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link active" to="/user/log-out">Log Out</Link>
              </li>
            </ul>
          </div> 
        </div>
      </nav>
      <div className="container">
        <div className="row">
          <div className="col-8 alert alert-secondary">
            <Switch>
              <Route path='/about' exact component={About} />
              <Route path='/' exact component={HomePage} />
            </Switch>
          </div>
          <div className="col">
            <div className="container mb-4">
              <form className="d-flex">
                <input className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
                <button className="btn btn-secondary ml-2 px-2" type="submit">Search</button>
              </form>
            </div>
            <div className="container">
              <div className="col alert alert-secondary"></div>
            </div>
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;
