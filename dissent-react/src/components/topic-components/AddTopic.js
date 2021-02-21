import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function AddTopic ( { articleId, setAddTopic, user } ) {

  const [topic, setTopic] = useState( {
    "topicName": ''
  } );

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedTopic = {...topic};
    updatedTopic[event.target.name] = event.target.value;
    setTopic(updatedTopic);
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
      body: JSON.stringify(topic)
    };

    try {
      const response = await fetch("http://localhost:8080/api/topic", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.topicId) {
          // kinda hokey
          history.push(`/`);
          history.push(`/t/${topicId}`);
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
    setAddTopic(false);
  }

  return (
    <form onSubmit={handleAddSubmit}>
      <Errors errors={errors} />
      <div className="form-row mb-4">
        <div className="col-9">
          <label htmlFor="content" className="pl-2 pt-2">New Topic</label>
        </div>
        <input type="text" className="form-control mb-3 mr-3" id="content" name="content" type="textarea" rows="5" onChange={handleChange} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark ml-2 mr-3 btn-sm">Submit</button>
        </div>
      </div>
      <hr></hr>
    </form>
  );
}

export default AddTopic;