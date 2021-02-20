import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function AddReplyPost ( { parentPost, articleId, setCurrentOption, user } ) {

  const [post, setPost] = useState( {
    "parentPostId": parentPost.postId,
    "articleId": articleId,
    "dissenting": true,
    "timestamp": "2021-02-15T06:00:00",
    "content": '',
    "user": user
  } );

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedPost = {...post};
    updatedPost[event.target.name] = event.target.value;
    setPost(updatedPost);
    console.log(event.target.value);
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
      const response = await fetch("http://localhost:8080/api/post", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.postId) {
          // kinda hokey
          history.push(`/`);
          history.push(`/article/${articleId}`);
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
    setCurrentOption(0);
  }

  return (
    <form onSubmit={handleAddSubmit}>
      <hr></hr>
      <Errors errors={errors} />
      <div className="form-row">
        <div className="col-9">
          <label htmlFor="content" className="pl-2 pt-2">Reply To @{parentPost.user.username}</label>
        </div>
        <div className="col-3">
          <div className="container mb-3">
            <select id="dissenting" name="dissenting" className="form-control" required onChange={handleChange}>
              <option value="true">Dissenting</option>
              <option value="false">Accepting</option>
            </select>
          </div>
        </div>
        <textarea className="form-control mb-3" id="content" name="content" type="textarea" rows="5" onChange={handleChange} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark btn-sm mx-2">Submit</button>
        </div>
      </div>
    </form>
  );
}

export default AddReplyPost;