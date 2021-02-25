
import { useContext, useEffect, useState } from 'react';
import { Link, Redirect } from 'react-router-dom';
import TableFeedbackTagIcon from '../feedback-tag-components/user/TableFeedbackTagIcon'
import AuthContext from '../AuthContext';

function User ( { user, setUserToDelete } ) {

  const auth = useContext(AuthContext);
  const [posts, setPosts] = useState([])
  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/post/user/${user.userId}`);
      const data = await response.json();
      setPosts(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };

  const handleDeleteClick = async () => {
    // try {
    //   const response = await fetch(`http://localhost:8080/api/user/${user.userId}`, { method: "DELETE" } );
    //   if (response.status === 204) {
    //     if (update) {
    //       setUpdate(false)
    //     } else {
    //       setUpdate(true)
    //     }

    //   } else {
    //     throw new Error(["Something unexpected went wrong, sorry!"]);
    //   }
    // } catch (error) {
    // }
    setUserToDelete(user);
  }

  useEffect(() => {
    getData();
  }, []);

  return (
    <tr>
      <td scope="row"><Link to={`/user/${user.username}`} className="usernameLink">{user.username}</Link></td>
      <td className="text-left d-flex flex-row">{posts.length}</td>
      <td className="align-left"><TableFeedbackTagIcon user={auth.user} thisUser={user}/></td>
      {user.username == "admin" 
      ? <td></td>
      : <td className="text-right"><button className="btn btn-danger btn-sm " onClick={handleDeleteClick}>Delete</button></td>
      }
    </tr>
  );
}

export default User;