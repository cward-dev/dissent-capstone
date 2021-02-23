import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import HomePage from './components/article-components/HomePage.js';
import TopicPage from './components/topic-components/TopicPage.js';
import About from './components/pages/About.js';
import UserPage from './components/pages/UserPage.js';
import ArticlePage from './components/article-components/ArticlePage.js';
import AddArticlesPage from './components/article-components/AddArticlesPage.js';
import TopicSidebar from './components/topic-components/TopicSidebar.js';
import NotFound from './components/pages/NotFound.js';
import Navbar from './components/Navbar.js';
import AdminBar from './components/AdminBar.js';
import Login from './components/user-components/Login';
import Register from './components/user-components/Register';
import jwt_decode from 'jwt-decode'; 
import AuthContext from './components/AuthContext'
import './App.css';

const DEFAULT_USER = {
  "userId": "dffec086-b1e9-455a-aab4-ff6c6611fef0",
  "userLoginId": "103a7d9b-f72b-4469-b1a3-bdba2f6356b4",
  "username": "dissenter101",
  "userRole": "admin",
  "photoUrl": "https://www.birdnote.org/sites/default/files/Daffy_Duck-2-warner-bros-625.jpg", 
  "country": "United States", 
  "bio": "The truth is out there."
}

function App() {
  const [user, setUser] = useState(null); // replace with default user for dev

  const login = (token) => {
    const { userId, sub: username, authorities } = jwt_decode(token);
    const roles = authorities.split(',');
    const user = {
      userId,
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    }
    setUser(user);
  }

  const authenticate = async (username, password) => {
    const response = await fetch('http://localhost:8080/authenticate', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        username,
        password
      })
    });

    if (response.status === 200) {
      const { jwt_token } = await response.json(); 
      login(jwt_token);
    } else if (response.status === 403) {
      throw new Error('Bad username or password')
    } else {
      throw new Error('There was a problem logging in...')
    }
  }

  const logout = () => {
    setUser(null);
  }

  const auth = {
    user,
    login, 
    authenticate,
    logout
  }


  return (
    <AuthContext.Provider value = {auth}>
      <Router>
        <Navbar />
        {(user != null && Object.values(user.roles).join(',').includes("ADMIN")) &&
          <AdminBar user={user} />
        }
        <div className="container">
          <div className="row">
            <div className="col-8 alert alert-secondary pt-4">
              <Switch>
                <Route path={'/article/add'} exact>
                  <AddArticlesPage user={user} />
                </Route>
                <Route path={'/article/:articleId'} exact>
                  <ArticlePage user={user} />
                </Route>
                <Route path={'/t/:topicName'} exact>
                  <TopicPage user={user} />
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
                <Route path='/login'>
                  <Login />
                </Route>
                <Route path='/register'>
                  <Register />
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
              <TopicSidebar user={user} />
            </div>
          </div>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
