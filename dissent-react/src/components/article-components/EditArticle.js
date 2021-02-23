import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function EditArticle ( { article, setEditArticle, user } ) {

  const [editedArticle, setEditedArticle] = useState(article);
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleEdit = async () => {

    const newArticle = {
      "articleId": editedArticle.articleId,
      "title": editedArticle.title,
      "description": editedArticle.description,
      "author": editedArticle.author,
      "articleUrl": editedArticle.articleUrl,
      "articleImageUrl": editedArticle.articleImageUrl,
      "datePublished": editedArticle.datePublished,
      "datePosted": editedArticle.datePosted,
      "source": editedArticle.source,
      "active": editedArticle.active,
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(editedArticle)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/article/${article.articleId}`, init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.articleId) {
          setEditArticle(false);
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

  const handleCancel = () => {
    setEditArticle(false);
  }

  return (
    <form onSubmit={handleEdit}>
      <Errors errors={errors} />
      <div className="form-row alert alert-dark mt-1 mb-3 mx-2">
        <input type="text" ></input>
        <div className="py-1">Are you sure? Deletions are permanent.</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-danger btn-sm ml-2">Edit Topics</button>
        </div>
      </div>
    </form>
  );
}

export default EditArticle;