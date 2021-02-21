import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function DeletePost ( { originalPost, articleId, setCurrentOption, user } ) {

  const [post, setPost] = useState(originalPost);

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleDeleteSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/post/${originalPost.postId}`, { method: "DELETE" });

      if (response.status === 204) {

        // kinda hokey
        history.push(`/`);
        history.push(`/article/${articleId}`);
        handleCancel();
      } else if (response.status === 404) {
        throw new Error([`Post ID #${originalPost.postId} not found`]);
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(error);
    }
  }

  const handleCancel = () => {
    setCurrentOption(0);
  }

  return (
    <form onSubmit={handleDeleteSubmit}>
      <Errors errors={errors} />
      <div className="form-row alert alert-dark mb-4">
        <div className="py-1">Are you sure? Deletions are permanent.</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-danger btn-sm ml-2">Delete</button>
        </div>
      </div>
    </form>
  );
}

export default DeletePost;