import React from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import HomePage from './components/pages/HomePage.js';
import TopicPage from './components/pages/TopicPage.js';
import About from './components/pages/About.js';
import UserPage from './components/pages/UserPage.js';
import NotFound from './components/pages/NotFound.js';
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
          <div className="col container alert alert-secondary ml-4">
            This will be a side bar
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;
