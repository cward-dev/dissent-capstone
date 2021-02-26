import { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import UserFeedbackTagIcon from '../feedback-tag-components/user/UserFeedbackTagIcon'
import AuthContext from '../AuthContext';

function UserCard ( { user } ) {

  const auth = useContext(AuthContext);

  const [posts, setPosts] = useState([])

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/post/user/${user.userId}`);
      const data = await response.json();
      setPosts(data);
    } catch (error) {
      console.log("Something went wrong.");
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div key={user.userId} className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <Link to={`/user/${user.username}`}><img src={user.photoUrl} className="user-page-img" /></Link>
      </div>
      <div className="col-7 p-3">
        <h4 className="card-title"><Link to={`/user/${user.username}`}>{user.username}</Link></h4>
        <p className="card-text">{user.country}</p>
        <p className="card-text">{user.bio}</p>
        <p className="card-text">Roles: {Object.values(user.roles).toString()}</p>
      </div>
      <div className="col text-center p-2 pt-3">
        <UserFeedbackTagIcon user={auth.user} thisUser={user} />
      </div>
      <div className="card-footer w-100 text-white p-2 border-white">Total Posts: {posts.length} </div>
    </div>
  );
}

export default UserCard;