import { useState , useEffect } from 'react';
import { Link } from 'react-router-dom';
import FeedbackTagIcon from '../feedback-tag-components/FeedbackTagIcon';
import FeedbackTagForm from '../feedback-tag-components/FeedbackTagForm';
import DeleteArticle from './DeleteArticle';
import './ArticleCard.css';

function ArticleCard ( { article, articleOpen, setAddPost, user } ) {

  const [feedbackTagMenuDisplayed, setFeedbackTagMenuDisplayed] = useState(false);
  const [deleteArticle, setDeleteArticle] = useState(false);

  const { articleId, title, description, author, articleUrl, articleImageUrl, datePublished, datePosted, source, topics, posts, feedbackTags } = article;

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
  
  const timePassed = getTimePassed(datePosted);

  const getDiscussionLength = () => {
    let counter = 0;

    const countChildPosts = (post) => {
      for (let p of post.childPosts) {
        if (p.active) {
          counter++;
        }
        countChildPosts(p);
      }
    };

    for (let p of posts) {
      if (p.active) {
        counter++;
      }
      countChildPosts(p);
    }
    return counter;
  }

  const discussionLength = getDiscussionLength();

  const handleAddPost = () => {
    setAddPost(true);
  }

  const handleDelete = () => {
    setDeleteArticle(true);
  }

  return (
    <>
      <div className="article-card card flex-row flex-wrap text-white bg-dark mb-4">
        <div className="border-0">
          <a href={articleUrl}><img src={articleImageUrl} className="card-img-top" /></a>
        </div>

        <div className="col p-3">
          <a href={articleUrl}><h4 className="card-title">{title}</h4></a>
          <p className="card-text">{description}</p>
          <div className="row">
            <div className="col-8">
              <p className="source-author-line card-text"><a className="source-link" href={source.websiteUrl}>{source.sourceName}</a><br />{author}</p>
            </div>
            <div className="col-4 text-right align-bottom">
              <p className="source-author-line card-text"><br />{timePassed}</p>
            </div>

          </div>
        </div>
        <div className="card-footer w-100 text-muted px-1">
          {deleteArticle ? <DeleteArticle article={article} setDeleteArticle={setDeleteArticle} user={user} /> : null}
          <div className="d-flex flex-row">
            <div className="align-self-start">
              <FeedbackTagIcon feedbackTagMenuDisplayed={feedbackTags} setFeedbackTagMenuDisplayed={setFeedbackTagMenuDisplayed} object={article} user={user} />
            </div>
            <div className="col text-right">
              {user.userRole === "admin" ? <>
                    <button onClick={handleDelete} className="btn btn-secondary mr-2 px-2 py-1">Delete</button>
                  </> : null}
              {articleOpen ? <button className="btn btn-secondary px-2 py-1" onClick={handleAddPost}>Add Post</button> : <Link className="btn btn-secondary px-2 py-1" to={`/article/${articleId}`}>Discussion ({discussionLength})</Link>}
            </div>
          </div>
        </div>
      </div>
      {feedbackTagMenuDisplayed ? <FeedbackTagForm object={article} user={user} /> : null}
    </>
  );
}

export default ArticleCard;