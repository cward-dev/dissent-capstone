import UserCard from '../user-components/UserCard.js';
import Post from '../post-components/Post.js';
import './UserPage.css';
import { useContext, useState, useEffect } from 'react';
import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams
} from "react-router-dom";
import AuthContext from '../AuthContext';

function UserPage ( ) {
  const [user, setUser] = useState([]);

  let { username } = useParams();

  useEffect(() => {
    getData();
  }, []);

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/username/${username}`);
      const data = await response.json();
      setUser(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };

  const makePost = (post) => {
    <Post post={post} />
  }

  return (
    <div>
      <div key={user.userId} className="card flex-row flex-wrap text-white bg-dark mb-4">
        <div className="border-0">
          <img src={user.photoUrl} className="user-page-img" />
        </div>
        <div className="col-7 p-3">
          <h4 className="card-title">{user.username}</h4>
          <p className="card-text">{user.country}</p>
          <p className="card-text">{user.bio}</p>
        </div>
        <div className="col text-center p-2">
          <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart-user-profile" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
        </div>
        <div className="card-footer w-100 text-white p-2 border-white">Total Posts: </div>
      </div>
      {/* </div>
        <UserCard user={user}/>
        {posts ? posts.map(post => makePost(post)) : null} */}
    </div>
  );
}

export default UserPage;