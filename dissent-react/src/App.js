import React from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import HomePage from './components/HomePage.js';
import TopicPage from './components/TopicPage.js';
import About from './components/About.js';
import UserPage from './components/UserDisplay/UserPage.js';
import NotFound from './components/NotFound.js';
import Navbar from './components/Navbar.js';
import './App.css';

function App() {
  return (      
    <Router>
      <Navbar />
      <div className="container">
        <div className="row">
          <div className="col-8 alert alert-secondary pt-4">
            <Switch>
              <Route path='/about' exact component={About} />
              <Route path='/topic' exact component={TopicPage} />
              <Route path='/user' exact component={UserPage} />
              <Route path='/' exact component={HomePage} />
              <Route path='*' exact component={NotFound} />
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
