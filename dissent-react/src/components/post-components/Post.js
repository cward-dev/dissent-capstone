function Post ( { post } ) {
  return (
    <div key={post.postId} className="mb-4">
      <div class="media"><img class="mr-3 rounded-circle" alt="User Picture" src={post.user.photoUrl} />
        <div class="media-body">
            <div class="row">
                <div class="col-7 d-flex">
                    <h5>{post.user.username}</h5>
                </div>
                <div class="col-5 text-right pr-4">
                    <div class="pull-right row">
                      <div className="col">
                        {post.timestamp}
                      </div>
                      <a href="#" className="btn btn-secondary btn-sm">Reply</a>
                    </div>
                </div>
            </div>
            <div>
              {post.content}
            </div>
        </div>
      </div>
    </div>
  );
}

export default Post;