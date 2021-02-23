import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function DeleteArticle ( { article, setDeleteArticle, user } ) {

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleDeleteSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/article/${article.articleId}`, { method: "DELETE" });

      if (response.status === 204) {

        history.push("/");
        handleCancel();
      } else if (response.status === 404) {
        throw new Error([`Post ID #${article.articleId} not found`]);
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(error);
    }
  }

  const handleCancel = () => {
    setDeleteArticle(false);
  }

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
    </form>
  );
}

export default DeleteArticle;