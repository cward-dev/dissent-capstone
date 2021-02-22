import { useState , useEffect } from 'react';
import { Link } from 'react-router-dom';
import FeedbackTagIcon from '../feedback-tag-components/FeedbackTagIcon';
import FeedbackTagForm from '../feedback-tag-components/FeedbackTagForm';
import DeleteArticle from './DeleteArticle';
import Errors from '../Errors';
import './RawArticleCard.css';
import placeholderImage from '../images/D-logo.png';

function RawArticleCard ( { article, user, handleSetArticles } ) {

  const [added, setAdded] = useState(false);
  const [errors, setErrors] = useState([]);

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
  
  const timePassed = getTimePassed(datePublished);

  const handleAddClick = async () => {

    const articleToAdd = {
      "articleId": article.articleId,
      "title": (article.title && article.title.trim().length > 0 ? (article.title.length > 255 ? article.title.substring(0,255) : article.title) : "Unknown"),
      "description": (article.description && article.description.trim().length > 0 ? (article.description.length > 255 ? article.description.substring(0,255) : article.description) : "Unknown"),
      "author": (article.author && article.author.trim().length > 0 ? (article.author.length > 255 ? article.author.substring(0,255) : article.author) : "Unknown"),
      "articleUrl": article.articleUrl,
      "articleImageUrl": article.articleImageUrl ? article.articleImageUrl : placeholderImage,
      "datePublished": article.datePublished,
      "datePosted": article.datePosted,
      "source": article.source,
      "active": true,
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(articleToAdd)
    };

    try {
      const response = await fetch("http://localhost:8080/api/article", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.articleId) {
          setAdded(true);
          setErrors([]);
        } else {
          setErrors(data);
        }
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  return (
    <>
      <div className="article-card card flex-row flex-wrap text-white bg-dark mb-4">
        <div className="border-0">
          <a href={articleUrl}><img src={ articleImageUrl ? articleImageUrl : placeholderImage } className="card-img-top" /></a>
        </div>

        <div className="col p-3">
          <a href={articleUrl}><h4 className="card-title">{title}</h4></a>
          <p className="card-text">{description}</p>
          <div className="row">
            <div className="col-8">
              <p className="source-author-line card-text"><a className="source-link" href={source.websiteUrl}>{source.sourceName}</a><br />{author ? author : "Unknown"}</p>
            </div>
            <div className="col-4 text-right align-bottom">
              <p className="source-author-line card-text"><br />{timePassed}</p>
            </div>

          </div>
        </div>
        <div className="card-footer w-100 text-muted px-1">
          <div className="d-flex flex-row justify-content-end">
            <Errors errors={errors} />
            {user.userRole === "admin" ? <>
                  {added ? <button className="btn btn-success mr-2 px-2 py-1">Article Added</button> : <button onClick={handleAddClick} className="btn btn-secondary mr-2 px-2 py-1">Add Article</button>}
                </> : null}
          </div>
        </div>
      </div>
    </>
  );
}

export default RawArticleCard;