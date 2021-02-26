import UserCard from '../user-components/UserCard.js';
import PostFeedUserPage from '../post-components/PostFeedUserPage.js';
import './UserPage.css';
import { useContext, useState, useEffect } from 'react';
import React from "react";
import Edit from "../user-components/Edit"
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams
} from "react-router-dom";
import AuthContext from '../AuthContext';

function UserPage () {
  const [updated, setUpdated] = useState(false);
  const auth = useContext(AuthContext);

  const [user, setUser] = useState(null);
  const [currentOption, setCurrentOption] = useState(0);


  let { username } = useParams();

  const getData = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/user/username/${username}`);
      const data = await response.json();
      setUser(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };


  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/api/user/username/${username}`);
        const data = await response.json();
        setUser(data);
      } catch (error) {
        console.log("Something went wrong.")
      }
    };
    getData();
  }, []);

  const handleEditUser = () => {
    setCurrentOption(1);
  };

  const handleUserEdited = () => {
    setCurrentOption(0);
  };


  return (
    <div>
      <div>
        {(user != null) ?
            <div>
              <UserCard user={user}/>
              {auth.user != null && (user.userId === auth.user.userId) ? <button onClick={handleEditUser} className="btn btn-dark">Edit Profile</button> : null}
              {currentOption === 1 ? <Edit updated={updated} originalUser={user} user={user} setCurrentOption={setCurrentOption} handleUserEdited={handleUserEdited} /> : null}
            </div>
          : null
        }
      </div>
      <div>
        <PostFeedUserPage thisUser={user} user={user} />
      </div>
    </div>
  );
}

export default UserPage;