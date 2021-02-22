import { useState, useEffect } from 'react';
import ArticleCard from './ArticleCard.js';
import './ArticleFeed.css';

function NewsAPIFeed ( { articles, user } ) {

  
  // useEffect(() => {
  //   const getData = async () => {
  //     try {
  //       const response = await fetch("");
  //       const data = await response.json();
  //       setArticles(data);
  //     } catch (error) {
  //       setErrors(["Something went wrong with our database, sorry!"]);
  //     }
  //   };
  //   getData();
  // }, []);

  const makeArticle = (article) => {

    return (
      <ArticleCard article={article} user={user} />
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