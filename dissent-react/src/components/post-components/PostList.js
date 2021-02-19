import Post from './Post.js'

function PostList ( { posts } ) {

  const makePost = (post) => {
    return (
      <Post post={post} />
    );
  };

  return (
    <div>
      {posts.map(post => makePost(post))}
    </div>
  );
}

export default PostList;