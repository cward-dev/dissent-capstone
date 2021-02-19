import Post from './Post.js';
import './PostList.css';

function PostList ( { posts } ) {

  const makePost = (post) => {
    return (
      <Post key={post.postId} post={post} postLevel={1} />
    );
  };

  return (
    <div>
      {posts.map(post => makePost(post))}
    </div>
  );
}

export default PostList;