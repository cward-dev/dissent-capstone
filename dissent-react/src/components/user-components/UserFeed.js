import { useState, useEffect } from 'react';
import UserCard from './UserCard.js';

function UserFeed() {

  const [users, setUsers] = useState([]);

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
  }, []);

  const makeUser = (user) => {
    return (
      <UserCard key={user.userId} user={user} />
    );
  };

  return (
    <div>
      {users.sort((a, b) => (a.username > b.username ? 1 : -1)).map(user => makeUser(user))}
    </div>
  );
}

export default UserFeed;