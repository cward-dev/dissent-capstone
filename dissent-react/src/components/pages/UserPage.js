import UserCard from '../user-components/UserCard.js';
import Post from '../post-components/Post.js';
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
  const auth = useContext(AuthContext);
  const [user, setUser] = useState();
  const [currentOption, setCurrentOption] = useState(0);

  let { username } = useParams();

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/username/${username}`);
      const data = await response.json();
      setUser(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };

  useEffect(() => {
    getData();
  }, []);

  const makePost = (post) => {
    <Post post={post} />
  }

  const handleEditUser = () => {
    setCurrentOption(1);
  };


  return (
    <div>
      <div>
        {(user != null) ?
            <div>
              <UserCard user={user}/>
              {auth.user != null && (user.userId === auth.user.userId) ? <button onClick={handleEditUser} className="btn btn-dark">Edit Profile</button> : null}
              {currentOption === 1 ? <Edit originalUser={user} user={user} setCurrentOption={setCurrentOption} /> : null}
            </div>
          : null
        }
      </div>
      <div>
      </div>
    </div>
  );
}

export default UserPage;