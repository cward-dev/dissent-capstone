function Post ( { post } ) {
  return (
    <div key={post.postId}>
      {post.content}
    </div>
  );
}

export default Post;