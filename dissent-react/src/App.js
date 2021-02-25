import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import HomePage from './components/article-components/HomePage.js';
import TopicPage from './components/topic-components/TopicPage.js';
import TopicsAdmin from './components/topic-components/topic-admin/TopicsAdmin.js';
import FeedbackTagsAdmin from './components/feedback-tag-components/feedback-tag-admin/FeedbackTagsAdmin.js';
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
import UserCard from './components/user-components/UserCard'
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
  const [topicsUpdated, setTopicsUpdated] = useState(false);

  // can useState(DEFAULT_USER) for development purposes. Or login with U: "admin" P: "admin"
  const [user, setUser] = useState(); 

  // stores token in browser cache so user does not get logged out on refresh.
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      login(token)
    }
  }, []);

  const login = async (token) => {
    const { userId, sub: username, authorities } = jwt_decode(token);
    const roles = authorities.split(',');
    const response = await fetch(`${process.env.REACT_APP_API_URL}/api/user/username/${username}`);
    const { email, photoUrl, country, bio } = await response.json();
    const user = {
      userId,
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      },
      email,
      photoUrl,
      country,
      bio
    }
    setUser(user);
    localStorage.setItem("token", token)
  }

  const authenticate = async (username, password) => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/authenticate`, {
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
  };

  const handleTopicsUpdated = () => {
    if (topicsUpdated) {
      setTopicsUpdated(false);
    } else {
      setTopicsUpdated(true);
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.clear();
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
        {(user != null && user.hasRole("ROLE_ADMIN")) &&
          <AdminBar user={user} />
        }
        <div className="container">
          <div className="row">
            <div className="col-8 alert alert-secondary pt-4">
              <Switch>
                <Route path={'/article/add'} exact>
                  <AddArticlesPage user={user} />
                </Route>
                <Route path={'/admin/feedback-tags'} exact>
                  <FeedbackTagsAdmin user={user} />
                </Route>
                <Route path={'/admin/topics'} exact>
                  <TopicsAdmin handleTopicsUpdated={handleTopicsUpdated} user={user} />
                </Route>
                <Route path={'/admin/article/add'} exact>
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
                <Route path={'/user/:username'} children={<UserPage />} exact>
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
              <TopicSidebar topicsUpdated={topicsUpdated} user={user} />
            </div>
          </div>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
