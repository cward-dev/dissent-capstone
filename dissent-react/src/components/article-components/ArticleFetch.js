import { useState, useEffect } from 'react';
import ArticleFeed from './ArticleFeed';

function ArticleFetch( { setMenuSelection, setAgentForAliases } ) {

  const [articles, setArticles] = useState([]);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/article");
        const data = await response.json();
        setAgents(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);

  const addArticle = async (newArticle) => {

    const article = {
      "articleId": newArticle.articleId,
      "title": newArticle.title,
      "description": newArticle.description,
      "source_id": newArticle.source_id,
      "author": newArticle.author,
      "articleUrl": newArticle.articleUrl,
      "articleImageUrl": newArticle.articleImageUrl,
      "datePublished": newArticle.datePublished,
      "datePosted": newArticle.datePosted,
      "isActive": newArticle.isActive
    }

  }

  return <ArticleFeed articles={articles} />
}