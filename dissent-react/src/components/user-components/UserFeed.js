import { useState, useEffect } from 'react';
import User from './User.js';
import Errors from '../Errors';
import DeleteUser from './DeleteUser';

function UserFeed() {

  const [users, setUsers] = useState([]);
  const [user, setUser] = useState([]);
  const [update, setUpdate] = useState(false);
  const [userToDelete, setUserToDelete] = useState(null);
  const [errors, setErrors] = useState([]);

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/`);
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };

  

  useEffect(() => {
    getData();
  }, [update]);

  const makeUser = (user) => {
    return (
      <User key={user.userId} user={user} update={update} setUpdate={setUpdate} setUserToDelete={setUserToDelete}/>
    );
  };

  return (
    <div>
      <Errors errors={errors} />
      <hr className="mt-4"></hr>
      <h1 className="d-flex flex-row justify-content-center mb-4">User Admin</h1>
      {userToDelete ? <DeleteUser user={userToDelete} setUserToDelete={setUserToDelete} update={update} setUpdate={setUpdate} /> : null}
      <hr className="mb-4"></hr>
      <h2>Active Users</h2>
      <table className="table table-light table-striped mt-3">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Username</th>
            <th scope="col">Posts</th>
            <th scope="col">Feedback</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {users.filter(u => u.active).sort((a, b) => (a.username > b.username) ? 1 : -1).map(u => makeUser(u))}
        </tbody>
      </table>
    </div>
  );
}

export default UserFeed;