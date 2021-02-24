import { useState } from 'react';
import Errors from '../../Errors.js';

function EditFeedbackTag ( { feedbackTag, setFeedbackTagToEdit, update, setUpdate, user } ) {

  const [editedFeedbackTag, setEditedFeedbackTag] = useState( {
    "feedbackTagId": feedbackTag.feedbackTagId,
    "name": feedbackTag.name,
    "colorHex": feedbackTag.colorHex,
    "active": feedbackTag.active,
  } );
  const [errors, setErrors] = useState([]);


  const handleChange = (event) => {
    const updatedFeedbackTag = {...feedbackTag};
    updatedFeedbackTag[event.target.name] = event.target.value;
    setEditedFeedbackTag(updatedFeedbackTag);
  };

  const handleEditSubmit = async (event) => {
    event.preventDefault();

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(editedFeedbackTag)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/feedback-tag/${feedbackTag.feedbackTagId}`, init);

      if (response.status === 204) {

        if (update === true) {
          setUpdate(false)
        } else {
          setUpdate(true);
        }

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
    setFeedbackTagToEdit(null);
  }

  return (
    <form onSubmit={handleEditSubmit}>
      <Errors errors={errors} />
      <div className="form-row mx-1">
        <div className="col">
          <label htmlFor="content" className="pl-2 pt-2">Edit Feedback Tag - {feedbackTag.name}</label>
        </div>
        <input type="text" className="form-control mb-3 mx-2" id="name" name="name" value={feedbackTag.name} onChange={handleChange} />
        <label htmlFor="colorHex" className="pl-2 pt-2">Color</label>
        <input type="color" id="colorHex" name ="colorHex" className="col-2 form-control ml-2 p-1" defaultValue={feedbackTag.colorHex} required onChange={handleChange}></input>
        <div className="col text-right mr-2">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark btn-sm mx-2">Submit</button>
        </div>
      </div>
    </form>
  );
}

export default EditFeedbackTag;