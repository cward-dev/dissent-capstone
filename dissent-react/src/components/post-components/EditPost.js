import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function EditPost ( { originalPost, articleId, setCurrentOption, user, handlePostAdded } ) {

  const [post, setPost] = useState(originalPost);

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedPost = {...post};
    updatedPost[event.target.name] = event.target.value;
    setPost(updatedPost);
  };

  const handleEditSubmit = async (event) => {
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
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/post/${originalPost.postId}`, init);

      if (response.status === 204) {
        handlePostAdded();
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
    <form onSubmit={handleEditSubmit}>
      <hr></hr>
      <Errors errors={errors} />
      <div className="form-row">
        <div className="col-9">
          <label htmlFor="content" className="pl-2 pt-2">Edit Post</label>
        </div>
        <div className="col-3">
          <div className="container mb-3">
            <select id="dissenting" name="dissenting" className="form-control" required onChange={handleChange} defaultValue={originalPost.dissenting}>
              <option value="">None</option>
              <option value="true">Dissenting</option>
              <option value="false">Accepting</option>
            </select>
          </div>
        </div>
        <textarea className="form-control mb-3 mr-3" id="content" name="content" type="textarea" rows="3" onChange={handleChange} defaultValue={originalPost.content} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark btn-sm ml-2 mr-3 px-3">Edit</button>
        </div>
      </div>
    </form>
  );
}

export default EditPost;