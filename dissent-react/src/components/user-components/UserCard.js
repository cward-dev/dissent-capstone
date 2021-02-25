import { useContext, useEffect, useState } from 'react';
import UserFeedbackTagIcon from '../feedback-tag-components/user/UserFeedbackTagIcon'
import AuthContext from '../AuthContext';

function UserCard ({user}) {

  const auth = useContext(AuthContext);

  const [posts, setPosts] = useState([])

  useEffect(() => {
    getData();
  }, []);

  const getData = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/post/user/${user.userId}`);
      const data = await response.json();
      setPosts(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };


  return (
    <div key={user.userId} className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <img src={user.photoUrl} className="user-page-img" />
      </div>
      <div className="col-7 p-3">
        <h4 className="card-title">{user.username}</h4>
        <p className="card-text">{user.country}</p>
        <p className="card-text">{user.bio}</p>
        <p className="card-text">Roles: {Object.values(user.roles).toString()}</p>
      </div>
      <div className="col text-center p-2">
        <UserFeedbackTagIcon user={auth.user} thisUser={user} />
      </div>
      <div className="card-footer w-100 text-white p-2 border-white">Total Posts: {posts.length} </div>
    </div>
  );
}

export default UserCard;