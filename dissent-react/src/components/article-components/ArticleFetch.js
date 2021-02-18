import { useState, useEffect } from 'react';
import ArticleFeed from './ArticleFeed';

function ArticleFetch() {

  const [articles, setArticles] = useState([]);
  const [editArticleId, setEditArticleId] = useState(null);
  const [deleteArticleId, setDeleteArticleId] = useState(null);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/article");
        const data = await response.json();
        setArticles(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);

  const addArticle = async (newArticle) => {

    const article = {
      "title": newArticle.title,
      "description": newArticle.description,
      "sourceId": newArticle.sourceId,
      "author": newArticle.author,
      "articleUrl": newArticle.articleUrl,
      "articleImageUrl": newArticle.articleImageUrl,
      "datePublished": newArticle.datePublished,
      "datePosted": newArticle.datePosted,
    }

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(article)
    };

    try {
      const response = await fetch("http://localhost:8080/api/article", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.articleId) {
          setArticles([...articles, data]);
          handleCancel();
        } else {
          setErrors(data);
        }
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  }

  const editArticle = async (editedArticle) => {

    const updatedArticle = {
      "articleId": editedArticle.articleId,
      "title": editedArticle.title,
      "description": editedArticle.description,
      "source_id": editedArticle.source_id,
      "author": editedArticle.author,
      "articleUrl": editedArticle.articleUrl,
      "articleImageUrl": editedArticle.articleImageUrl,
      "datePublished": editedArticle.datePublished,
      "datePosted": editedArticle.datePosted,
      "isActive": editedArticle.isActive
    }

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedArticle)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/article/${editedArticle.articleId}`, init);

      if (response.status === 204) {
        const newArticles = [...articles];
        const articleIndexToEdit = articles.findIndex(article => article.articleId === editArticleId);
        newArticles[articleIndexToEdit] = updatedArticle;

        setArticles(newArticles);
        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
      } else if (response.status === 404) {
        throw new Error(["Article to edit not found."])
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"])
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  }

  const deleteById = async (articleId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/article/${articleId}`, { method: "DELETE" });
    
      if (response.status === 204) {
        const newArticles = articles.filter(article => article.articleId !== articleId);
        setArticles(newArticles);
        handleCancel();
      } else if (response.status === 404) {
        throw new Error(`Article ID #${articleId} not found.`);
      } else {
        throw new Error("Something unexpected went wrong, sorry!")
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  };

  const getArticleToEdit = () => {
    return articles.find(a => a.articleId === editArticleId);
  };

  const getArticleToDelete = () => {
    return articles.find(a => a.articleId === deleteArticleId);
  };

  const handleCancel = () => {
    setEditArticleId(null);
    setDeleteArticleId(null);
    setErrors([]);
  }

  return (
    <ArticleFeed articles={articles} />
  );
}

export default ArticleFetch;