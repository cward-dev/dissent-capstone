import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function DeletePost ( { originalPost, articleId, setCurrentOption, user } ) {

  const [post, setPost] = useState(originalPost);

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleDeleteSubmit = async (event) => {
    event.preventDefault();

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(post)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/post/${originalPost.postId}`, init);

      if (response.status === 204) {

        // kinda hokey
        history.push(`/`);
        history.push(`/article/${articleId}`);
        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleCancel = () => {
    setCurrentOption(0);
  }

  return (
    <form onSubmit={handleDeleteSubmit}>
      <Errors errors={errors} />
      <div className="form-row alert alert-danger mb-4">
        <div className="col">
          <label htmlFor="content" className="pl-2 pt-1">Are you sure you want to delete this post?</label>
        </div>
        <div className="col">
            <div className="col text-right">
              <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
              <button type="submit" className="btn btn-danger btn-sm ml-2 px-3">Delete</button>
            </div>
        </div>
      </div>
    </form>
  );
}

export default DeletePost;