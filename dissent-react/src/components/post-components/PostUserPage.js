import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import PostFeedbackTagIcon from "../feedback-tag-components/post/PostFeedbackTagIcon.js";
import AddReplyPost from "./AddReplyPost.js";
import EditPost from "./EditPost.js";
import DeletePost from "./DeletePost.js";
import Errors from "../Errors.js";
import "./Post.css";

function Post ( { post, user } ) {

  const [feedbackTagMenuDisplayed, setFeedbackTagMenuDisplayed] = useState(false);
  const [currentOption, setCurrentOption] = useState(0);
  const [errors, setErrors] = useState([]);

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

  return (
    <div>
      <Errors errors={errors} />
      <div className="media">
        <PostFeedbackTagIcon post={post} feedbackTagMenuDisplayed={feedbackTagMenuDisplayed} setFeedbackTagMenuDisplayed={setFeedbackTagMenuDisplayed} setErrors={setErrors} user={user} />
        <div className="media-body">
          <div className="pr-3">
              <div className="d-flex">
                  <h5><Link to={`/user/${post.user.username}`} className="usernameLink">{post.user.username}</Link></h5>
                  <div className="ml-2">
                    {post.dissenting ? <div className="badge badge-dark mr-2">Dissenting</div> : <div className="badge badge-light mr-2">Accepting</div>}
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
            <button className="btn btn-link btn-sm p-0" disabled>{`Discussion (${post.childPostCount})`}</button>
          </div>
          <hr></hr>
        </div>
      </div>
    </div>
  );
}

export default Post;