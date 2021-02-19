import FeedbackTagIcon from "../feedback-tag-components/FeedbackTagIcon";

function Post ( { post, postLevel } ) {

  const timestampDate = (new Date(post.timestamp)).toISOString().split('T')[0];
  const timestampTime = (new Date(post.timestamp)).toISOString().split('T')[1];
  
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
                        {timestampDate === Date.now() ? timestampTime : timestampDate}
                      </div>
                      <a href="#" className="btn btn-secondary btn-sm">Reply</a>
                    </div>
                </div>
            </div>
            <div className="mb-4">
              {post.content}
            </div>
            <hr></hr>
            {postLevel < 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} />) : null}
        </div>
      </div>
      {postLevel >= 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} />) : null}
    </div>
  );
}

export default Post;