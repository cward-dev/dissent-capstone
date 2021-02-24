import UserCard from './UserCard.js';

function UserFeed( { users } ) {

  const makeUser = (user) => {
    return (
      <UserCard key={user.userId} user={user} />
    );
  };

  return (
    <div>
      {users.map(user => makeUser(user))}
    </div>
  );
}

export default UserFeed;