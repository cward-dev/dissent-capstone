import UserCard from '../user-components/UserCard.js';
import Post from '../post-components/Post.js';
import './UserPage.css';

function UserDisplay ( { user, posts } ) {

  const makePost = (post) => {
    <Post post={post} />
  }

  return (
    <div>
      <UserCard user={user}/>
      {posts ? posts.map(post => makePost(post)) : null}
    </div>
  );
}

export default UserDisplay;