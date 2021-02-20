import { useState, useEffect } from 'react';
import FeedbackTagIcon from "../feedback-tag-components/FeedbackTagIcon";
import AddReplyPost from "./AddReplyPost.js";
import EditPost from "./EditPost.js";
import DeletePost from "./DeletePost.js";
import "./Post.css";

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
            <div className="pr-3">
                <div className="d-flex">
                    <h5>{post.user.username}</h5>
                    <div className="ml-2">
                      {post.dissenting ? <div className="badge badge-dark mr-2">Dissenting {parentPost ? `@${parentPost.user.username}` : null}</div> : <div className="badge badge-light mr-2">Accepting</div>}
                      <span>
                        {timestampDate === Date.now() ? timestampTime : timestampDate} {/* TODO Currently not working */}
                      </span>
                    </div>
                </div>
            </div>
            <div className="mb-2 pr-3">
              {post.active ? <div>{post.content}</div> : <div className="alert alert-dark font-italic mt-3 py-2">{post.content}</div>}
            </div>
            <div className="pr-3 mb-3">
              {post.active ? <>
                <button onClick={handleAddReplyPost} className="btn btn-link btn-sm p-0">Reply</button>
                <button onClick={handleEditPost} className="btn btn-link btn-sm ml-2 p-0">Edit</button>
                <button onClick={handleDeletePost} className="btn btn-link btn-sm ml-2 p-0">Delete</button>
              </> : null}
              <button className="btn btn-link btn-sm ml-2 p-0" type="button" data-toggle="collapse" data-target={`.${post.postId}-children`} 
                aria-expanded="true" aria-controls={`${post.postId}-children1 ${post.postId}-children2`}>Collapse</button>
            </div>
            {currentOption === 1 ? <EditPost originalPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} /> : null}
            {currentOption === 2 ? <DeletePost originalPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} /> : null}
            {currentOption === 3 ? <AddReplyPost parentPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} /> : null}
            <hr></hr>
            <div className={`collapser ${post.postId}-children`} id={`${post.postId}-children1`}>
              {postLevel < 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} user={user} />) : null}
            </div>
        </div>
      </div>
      <div className={`collapser ${post.postId}-children`} id={`${post.postId}-children2`}>
        {postLevel >= 3 ? post.childPosts.map(childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} user={user} parentPost={post} />) : null}
      </div>
    </div>
  );
}

export default Post;