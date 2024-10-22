import { useState , useEffect } from 'react';
import { Link } from 'react-router-dom';
import ArticleFeedbackTagIcon from '../feedback-tag-components/article/ArticleFeedbackTagIcon';
import EditArticleTopics from '../article-topic-components/EditArticleTopics';
import DeleteArticle from './DeleteArticle';
import Errors from '../Errors.js';
import placeholderImage from '../images/D-logo.png';
import './ArticleCard.css';

const PLACEHOLDER_ARTICLE = {
  "title": "Loading",
  "description": "Description",
  "sourceId": "sourceid",
  "author": "Author",
  "articleUrl": "https://www.google.com",
  "articleImageUrl": placeholderImage,
  "datePublished": "2021-2-10",
  "datePosted": "2021-2-10",
  "source": {
    "sourceId": "",
    "sourceName": "",
    "websiteUrl": "",
    "description": ""
  },
  "posts": [],
  "topics": []
};

function ArticleCard ( { articleId, articleOpen, setAddPost, updateArticleDelete, setUpdateArticleDelete, user } ) {

  const [article, setArticle] = useState(PLACEHOLDER_ARTICLE);
  const [topics, setTopics] = useState([]);
  const [timePassed, setTimePassed] = useState("");

  const [update, setUpdate] = useState(false);
  const [editTopics, setEditTopics] = useState(false);
  const [deleteArticle, setDeleteArticle] = useState(false);
  const [errors, setErrors] = useState([]);

  const { title, description, author, articleUrl, articleImageUrl, datePublished, datePosted, source, posts, feedbackTags } = article;

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

  useEffect(()=> {
    const getData = async () => {
      let mounted = true;

      try {
        const response = await fetch(`http://localhost:8080/api/article/${articleId}`);

        if (mounted) {
          if (response.status === 200) {
            const data = await response.json();
            setArticle(data);
            setTopics(data.topics);
            setTimePassed(getTimePassed(data.datePublished));
          } else if (response.status === 404) {
            setErrors(["Something went wrong with our database, sorry!"]);
          }
        }
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }

      return () => mounted = false;
    };
    getData();
  }, [update]);
  
  const handleAddPost = () => {
    setAddPost(true);
    setEditTopics(false);
  };

  const handleEditTopics = () => {
    if (editTopics) {
      setEditTopics(false);
      update === true ? setUpdate(false) : setUpdate(true);
    } else {
      setEditTopics(true);
      setDeleteArticle(false);
    }
  };

  const handleDelete = () => {
    setDeleteArticle(true);
    setEditTopics(false);
  };

  return (
    <>
      <Errors errors={errors} />
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
          {!editTopics && topics.length > 0 ? 
            <div className="d-flex flex-row flex-wrap badge badge-secondary p-1 px-2 mx-2 mb-3 text-left">-
              {topics.sort((a, b) => (a.topicName > b.topicName) ? 1 : -1).map(topic => 
                <div key={topic.topicId}>
                  <span>{"\u00a0"}</span>
                  <Link to={`/t/${topic.topicName}`}>{topic.topicName}</Link>
                  <span>{"\u00a0"}-</span>
                </div>)}
              </div> : null}
          {editTopics ? <EditArticleTopics article={article} topics={topics} setTopics={setTopics} update={update} setUpdate={setUpdate} user={user} /> : null}
          {deleteArticle ? <DeleteArticle article={article} setDeleteArticle={setDeleteArticle} updateArticleDelete={updateArticleDelete} setUpdateArticleDelete={setUpdateArticleDelete} user={user} /> : null}
          <div className="d-flex flex-row">
            <div className="align-self-start">
              <ArticleFeedbackTagIcon setErrors={setErrors} article={article} user={user} />
            </div>
            <div className="col text-right">
              {(user != null && user.hasRole("ROLE_ADMIN")) ? <>
                <button onClick={handleEditTopics} className="btn btn-secondary mr-2 px-2 py-1">Edit Topics</button>
                <button onClick={handleDelete} className="btn btn-secondary mr-2 px-2 py-1">Delete</button>
                  </> : null}
              {articleOpen && user ? <button className="btn btn-secondary px-2 py-1" onClick={handleAddPost}>Add Post</button> : null}
              {!articleOpen ? <Link className="btn btn-secondary px-2 py-1" to={`/article/${articleId}`}>Discussion ({article.discussionLength})</Link> : null}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default ArticleCard;