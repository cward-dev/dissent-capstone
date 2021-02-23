import { useState, useEffect } from 'react';
import PostFeedbackTagIcon from "../feedback-tag-components/post/PostFeedbackTagIcon.js";
import FeedbackTagForm from "../feedback-tag-components/FeedbackTagForm.js";
import AddReplyPost from "./AddReplyPost.js";
import EditPost from "./EditPost.js";
import DeletePost from "./DeletePost.js";
import "./Post.css";

function Post ( { post, postLevel, user, parentPost, startsCollapsed, handlePostAdded } ) {

  const [feedbackTagMenuDisplayed, setFeedbackTagMenuDisplayed] = useState(false);
  const [currentOption, setCurrentOption] = useState(0);
  const [collapsed, setCollapsed] = useState(startsCollapsed);

  const getTimePassed = (date) => {
    const timestampDate = new Date(date);
    const currentDate = new Date(Date.now());

    const timeSincePosted = Math.abs((timestampDate.getTime() - currentDate.getTime()) / 1000);
    if (timeSincePosted < 60) return "just now";
    if (timeSincePosted < 120) return "1 minute ago";
    if (timeSincePosted < 3600) return Math.trunc(timeSincePosted / 60) + " minutes ago";
    if (timeSincePosted < 7200) return "1 hour ago";
    if (timeSincePosted < 86400) return Math.trunc(timeSincePosted / 3600) + " hours ago";

    const daysSincePosted = Math.abs((timestampDate.getDate() - currentDate.getDate()));

    if (daysSincePosted === 1) return "1 day ago";
    if (daysSincePosted < 7) return daysSincePosted + " days ago";
    if (daysSincePosted < 14) return "1 week ago";
    if (daysSincePosted < 365) return Math.trunc(daysSincePosted / 7) + " weeks ago";

    if (daysSincePosted < 730) return "1 year ago";

    return Math.trunc(daysSincePosted / 365) + " years ago"
  };

  const timePassed = getTimePassed(post.timestamp);

  const handleEditPost = () => {
    setCurrentOption(1);
  };

  const handleDeletePost = () => {
    setCurrentOption(2);
  };

  const handleAddReplyPost = () => {
    setCurrentOption(3);
  };

  const handleCollapse = () => {
    if (collapsed) {
      setCollapsed(false);
    } else {
      setCollapsed(true);
    }
  };

  return (
    <div>
      {feedbackTagMenuDisplayed ? <FeedbackTagForm object={post} user={user} /> : null}
      <div className="media">
        <PostFeedbackTagIcon post={post} feedbackTagMenuDisplayed={feedbackTagMenuDisplayed} setFeedbackTagMenuDisplayed={setFeedbackTagMenuDisplayed} user={user} />
        <div className="media-body">
          <div className="pr-3">
              <div className="d-flex">
                  <h5>{post.user.username}</h5>
                  <div className="ml-2">
                    {post.dissenting ? <div className="badge badge-dark mr-2">Dissenting {parentPost ? `@${parentPost.user.username}` : null}</div> : <div className="badge badge-light mr-2">Accepting</div>}
                    <span className="timestamp">
                      {timePassed}
                    </span>
                  </div>
              </div>
          </div>
          <div className="mb-2 pr-3">
            {post.active ? <div>{post.content}</div> : <div className="alert alert-dark font-italic my-1 py-2">{post.content}</div>}
          </div>
          <div className="pr-3 mb-3">
            <button onClick={handleAddReplyPost} className="btn btn-link btn-sm mr-2 p-0">Reply</button>
            {post.active && (post.user.userId === user.userId || user.userRole === "admin") ? <>
              <button onClick={handleEditPost} className="btn btn-link btn-sm mr-2 p-0">Edit</button>
              <button onClick={handleDeletePost} className="btn btn-link btn-sm mr-2 p-0">Delete</button>
            </> : null}
            <button onClick={handleCollapse} className="btn btn-link btn-sm p-0">{collapsed ? `Expand (${post.childPostCount})` : `Collapse (${post.childPostCount})`}</button>
          </div>
          {currentOption === 1 ? <EditPost originalPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} handlePostAdded={handlePostAdded} /> : null}
          {currentOption === 2 ? <DeletePost originalPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} handlePostAdded={handlePostAdded} /> : null}
          {currentOption === 3 ? <AddReplyPost parentPost={post} articleId={post.articleId} setCurrentOption={setCurrentOption} user={user} handlePostAdded={handlePostAdded} /> : null}
          <hr></hr>
          {postLevel < 3 && !collapsed ? post.childPosts.sort((a, b) => (new Date(a.timestamp) < new Date(b.timestamp)) ? 1 : -1).map(
            childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} user={user} startsCollapsed={postLevel === 2 ? true : false} handlePostAdded={handlePostAdded} />) : null}
        </div>
      </div>
        {postLevel >= 3 && !collapsed ? post.childPosts.sort((a, b) => (new Date(a.timestamp) < new Date(b.timestamp)) ? 1 : -1).map(
          childPost => <Post key={childPost.postId} post={childPost} postLevel={postLevel + 1} user={user} parentPost={post} startsCollapsed={true} handlePostAdded={handlePostAdded} />) : null}
    </div>
  );
}

export default Post;