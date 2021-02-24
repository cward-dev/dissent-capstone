import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function DeleteArticle ( { article, setDeleteArticle, updateArticleDelete, setUpdateArticleDelete, user } ) {

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleDeleteSubmit = async (event) => {
    event.preventDefault();
    let mounted = true;

    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/article/${article.articleId}`, { method: "DELETE" });

      if (mounted) {
        if (response.status === 204) {
          if (window.location.pathname === `/article/${article.articleId}`) {
            history.push("/");
          }
          updateArticleDelete === true ? setUpdateArticleDelete(false) : setUpdateArticleDelete(true);
          handleCancel();
        } else if (response.status === 404) {
          throw new Error([`Post ID #${article.articleId} not found`]);
        } else {
          throw new Error(["Something unexpected went wrong, sorry!"]);
        }
      }
    } catch (error) {
      setErrors([error]);
    }

    return () => mounted = false;
  };

  const handleCancel = () => {
    setDeleteArticle(false);
  };

  return (
    <form onSubmit={handleDeleteSubmit}>
      <Errors errors={errors} />
      <div className="form-row alert alert-dark mt-1 mb-3 mx-2">
        <div className="py-1">Are you sure? Deletions are permanent.</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-danger btn-sm ml-2">Delete</button>
        </div>
      </div>
      <hr></hr>
    </form>
  );
}

export default DeleteArticle;