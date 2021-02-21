import { useState, useEffect } from 'react';
import ArticleCard from './ArticleCard.js';
import './ArticleFeed.css';

function NewsAPIFeed ( { user } ) {

  const [articles, setArticles] = useState([]);
  const [errors, setErrors] = useState([]);
  
  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch("https://newsapi.org/v2/top-headlines?country=us&apiKey=46f374bc4801471fa5acc0956d739b7c");
        const data = await response.json();
        setArticles(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);

  const makeArticle = (article) => {
    return (
      <ArticleCard key={article.articleId} article={article} user={user} />
    );
  };

  return (
    <div>
      <div className="alert alert-dark mb-4 text-center">
        <h2>Add New Articles</h2>
      </div>
      {articles.sort((a, b) => (new Date(a.datePosted) < new Date(b.datePosted)) ? 1 : -1).map(article => makeArticle(article))}
    </div>
  );
}

export default NewsAPIFeed;