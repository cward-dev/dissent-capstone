import { useState, useEffect } from 'react';
import RawArticleCard from './RawArticleCard.js';
import './ArticleFeed.css';

function NewsAPIFeed ( { articles, user, setArticles } ) {

  
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

  const handleSetArticles = (article) => {
    const newArticles = articles.filter(a => a.articleName != article.articleName);
    setArticles(newArticles);
  };

  let nextKey = 0;
  const generateNextKey = () => {
    nextKey++;
    return nextKey;
  };

  const makeArticle = (article) => {
    return (
      <RawArticleCard key={generateNextKey()} article={article} handleSetArticles={handleSetArticles} user={user} />
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