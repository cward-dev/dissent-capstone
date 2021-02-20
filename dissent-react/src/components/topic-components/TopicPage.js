import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../Errors.js';
import ArticleCard from '../article-components/ArticleCard.js';
import AddPost from '../post-components/AddPost.js';
import PostFeed from '../post-components/PostFeed.js';

function TopicPage ( { user } ) {
  const { topicName } = useParams();
  const [topic, setTopic] = useState({});
  const [articles, setArticles] = useState([]);
  const [errors, setErrors] = useState([]);
  const history = useHistory();

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
  }, []);

  const makeArticle = (article) => {
    return (
      <ArticleCard key={article.articleId} article={article} />
    );
  };

  return (
    <div>
      <Errors errors={errors} />
      <div className="alert alert-dark mb-4 text-center">
        <h2>{topic.topicName} Articles</h2>
      </div>
      {articles.sort((a, b) => (new Date(a.datePosted) < new Date(b.datePosted)) ? 1 : -1).map(article => makeArticle(article))}
    </div>
  );
}

export default TopicPage;