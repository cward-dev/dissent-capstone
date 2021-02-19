import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import HomePage from './components/pages/HomePage.js';
import TopicPage from './components/pages/TopicPage.js';
import About from './components/pages/About.js';
import UserPage from './components/pages/UserPage.js';
import ArticlePage from './components/article-components/ArticlePage.js';
import NotFound from './components/pages/NotFound.js';
import Navbar from './components/Navbar.js';
import './App.css';

const DEFAULT_USER = {
  "userId": "dffec086-b1e9-455a-aab4-ff6c6611fef0",
  "userLoginId": "103a7d9b-f72b-4469-b1a3-bdba2f6356b4",
  "username": "dissenter101",
  "user_role": "user",
  "photo_url": "https://www.birdnote.org/sites/default/files/Daffy_Duck-2-warner-bros-625.jpg", 
  "country": "United States", 
  "bio": "The truth is out there."
}

function App() {
  const [user, setUser] = useState(DEFAULT_USER);

  const handleSetUser = (user) => {
    setUser(user);
  }

  const handleLogout = () => {
    setUser(null);
  }

  return (      
    <Router>
      <Navbar user={user} />
      <div className="container">
        <div className="row">
          <div className="col-8 alert alert-secondary pt-4">
            <Switch>
              <Route path={'/article/:articleId'}>
                <ArticlePage user={user} />
              </Route>
              <Route path='/about' exact>
                <About user={user} />
              </Route>
              <Route path='/topic' exact>
                <TopicPage user={user} />
              </Route>
              <Route path='/user' exact>
                <UserPage user={user} />
              </Route>
              <Route path='/' exact>
                <HomePage user={user} />
              </Route>
              <Route path='*' exact>
                <NotFound user={user} />
              </Route>
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
