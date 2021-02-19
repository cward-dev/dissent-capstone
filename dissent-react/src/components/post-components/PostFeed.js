import Post from './Post.js';
import './PostFeed.css';

function PostFeed ( { posts, user } ) {

  const makePost = (post) => {
    return (
      <Post key={post.postId} post={post} postLevel={1} user={user} />
    );
  };

  return (
    <div>
      {posts.sort((a, b) => (new Date(a.timestamp) < new Date(b.timestamp) ? 1 : -1)).map(post => makePost(post))}
    </div>
  );
}

export default PostFeed;