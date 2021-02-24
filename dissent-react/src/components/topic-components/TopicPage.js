import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../Errors.js';
import ArticleCard from '../article-components/ArticleCard.js';

function TopicPage ( { user } ) {
  const { topicName } = useParams();
  const [topic, setTopic] = useState({});
  const [articles, setArticles] = useState([]);
  const [updateArticleDelete, setUpdateArticleDelete] = useState(false);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/topic/name/${topicName}`);
        const data = await response.json();
        setTopic(data);
        setArticles(data.articles);

      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, [topicName, updateArticleDelete]);

  const makeArticle = (article) => {
    return (
      <ArticleCard key={article.articleId} articleId={article.articleId} updateArticleDelete={updateArticleDelete} setUpdateArticleDelete={setUpdateArticleDelete} user={user} />
    );
  };

  return (
    <div>
      <Errors errors={errors} />
      <h1 className="d-flex flex-row justify-content-center mb-4">{topic.topicName} Articles</h1>
      <hr className="mb-4"></hr>
      {articles.sort((a, b) => (new Date(a.datePosted) < new Date(b.datePosted)) ? 1 : -1).map(article => makeArticle(article))}
    </div>
  );
}

export default TopicPage;