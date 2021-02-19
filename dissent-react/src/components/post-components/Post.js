import FeedbackTagIcon from "../feedback-tag-components/FeedbackTagIcon";

function Post ( { post, postLevel } ) {

  return (
    <div>
      <div className="media">
        <FeedbackTagIcon />
        <div className="media-body">
            <div className="row pr-3">
                <div className="col-6 d-flex">
                    <h5>{post.user.username}</h5>
                </div>
                <div className="col-6 text-right">
                    <div className="pull-right row">
                      <div className="col">
                        {post.timestamp}
                      </div>
                      <a href="#" className="btn btn-secondary btn-sm">Reply</a>
                    </div>
                </div>
            </div>
            <div className="mb-4">
              {post.content}
            </div>
            {postLevel < 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} />) : null}
        </div>
      </div>
      {postLevel >= 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} />) : null}
    </div>
  );
}

export default Post;