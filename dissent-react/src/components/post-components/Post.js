import { useState, useEffect } from 'react';
import FeedbackTagIcon from "../feedback-tag-components/FeedbackTagIcon";
import AddReplyPost from "./AddReplyPost.js";
import EditPost from "./EditPost.js";
import DeletePost from "./DeletePost.js";

function Post ( { post, postLevel, user, parentPost } ) {

  const [currentOption, setCurrentOption] = useState(0);

  const timestampDate = (new Date(post.timestamp)).toISOString().split('T')[0];
  const timestampTime = (new Date(post.timestamp)).toISOString().split('T')[1];

  const handleEditPost = () => {
    setCurrentOption(1);
  };

  const handleDeletePost = () => {
    setCurrentOption(2);
  };

  const handleAddReplyPost = () => {
    setCurrentOption(3);
  };

  return (
    <div>
      <div className="media">
        <FeedbackTagIcon />
        <div className="media-body">
            <div className="row pr-3">
                <div className="col-6 d-flex">
                    <h5>{post.user.username}</h5>
                    <div className="container">
                      {post.dissenting ? <div className="badge badge-dark">Dissenting {parentPost ? `@${parentPost.user.username}` : null}</div> : <div className="badge badge-light">Accepting</div>}
                    </div>
                </div>
                <div className="col-6 text-right">
                    <div className="pull-right row">
                      <div className="col">
                        {timestampDate === Date.now() ? timestampTime : timestampDate} {/* Currently not working */}
                      </div>
                      <button onClick={handleEditPost} className="btn btn-light btn-sm mr-2">Edit</button>
                      <button onClick={handleDeletePost} className="btn btn-light btn-sm mr-2">Delete</button>
                      <button onClick={handleAddReplyPost} className="btn btn-dark btn-sm">Reply</button>
                    </div>
                </div>
            </div>
            <div className="mb-4">
              {post.content}
            </div>
            {currentOption === 1 ? <EditPost originalPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} /> : null}
            {currentOption === 2 ? <DeletePost originalPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} /> : null}
            {currentOption === 3 ? <AddReplyPost parentPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} /> : null}
            <hr></hr>
            {postLevel < 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} user={user} />) : null}
        </div>
      </div>
      {postLevel >= 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} user={user} parentPost={post} />) : null}
    </div>
  );
}

export default Post;