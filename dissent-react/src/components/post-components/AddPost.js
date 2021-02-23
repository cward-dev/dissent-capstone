import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function AddPost ( { articleId, setAddPost, user, handlePostAdded } ) {

  const [post, setPost] = useState( {
    "parentPostId": null,
    "articleId": articleId,
    "dissenting": true,
    "content": '',
    "user": user
  } );

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedPost = {...post};
    updatedPost[event.target.name] = event.target.value;
    setPost(updatedPost);
  };

  const handleAddSubmit = async (event) => {
    event.preventDefault();

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(post)
    };

    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/post`, init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.postId) {
          handlePostAdded();
          handleCancel();
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
    setAddPost(false);
  }

  return (
    <form onSubmit={handleAddSubmit}>
      <Errors errors={errors} />
      <div className="form-row mb-4">
        <div className="col-9">
          <label htmlFor="content" className="pl-2 pt-2">New Post</label>
        </div>
        <div className="col-3">
          <div className="container mb-3">
            <select id="dissenting" name="dissenting" className="form-control" required onChange={handleChange}>
              <option value="">Stance</option>
              <option value="true">Dissenting</option>
              <option value="false">Accepting</option>
            </select>
          </div>
        </div>
        <textarea className="form-control mb-3 mr-3" id="content" name="content" type="textarea" rows="5" onChange={handleChange} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark ml-2 mr-3 btn-sm">Submit</button>
        </div>
      </div>
      <hr></hr>
    </form>
  );
}

export default AddPost;