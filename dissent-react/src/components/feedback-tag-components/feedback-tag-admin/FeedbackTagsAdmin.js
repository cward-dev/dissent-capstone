import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../../Errors.js';
import FeedbackTagAdminRow from './FeedbackTagAdminRow.js';
import AddFeedbackTag from './AddFeedbackTag.js';
import EditFeedbackTag from './EditFeedbackTag.js';
import DeleteFeedbackTag from './DeleteFeedbackTag.js';
import ReactivateFeedbackTag from './ReactivateFeedbackTag.js';

function FeedbackTagsAdmin ( { user } ) {
  const [feedbackTags, setFeedbackTags] = useState([]);
  const [update, setUpdate] = useState(false);
  const [feedbackTagToEdit, setFeedbackTagToEdit] = useState(null);
  const [feedbackTagToDelete, setFeedbackTagToDelete] = useState(null);
  const [feedbackTagToReactivate, setFeedbackTagToReactivate] = useState(null);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/api/feedback-tag/with-inactive`);
        const data = await response.json();
        setFeedbackTags(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, [update]);

  const makeFeedbackTagAdminRow = (feedbackTag) => {
    return (
      <FeedbackTagAdminRow key={feedbackTag.feedbackTagId} feedbackTag={feedbackTag} setFeedbackTagToEdit={setFeedbackTagToEdit} 
        setFeedbackTagToDelete={setFeedbackTagToDelete} setFeedbackTagToReactivate={setFeedbackTagToReactivate} user={user} />
    );
  };

  return (
    <div>
      <Errors errors={errors} />
      <h1 className="d-flex flex-row justify-content-center mb-4">Feedback Tags Admin</h1>
      <hr className="mb-4"></hr>
      {!feedbackTagToEdit && !feedbackTagToDelete && !feedbackTagToReactivate ? <AddFeedbackTag /> : null}
      {feedbackTagToEdit ? <EditFeedbackTag feedbackTag={feedbackTagToEdit} setFeedbackTagToEdit={setFeedbackTagToEdit} update={update} setUpdate={setUpdate} user={user} /> : null}
      {feedbackTagToDelete ? <DeleteFeedbackTag feedbackTag={feedbackTagToDelete} setFeedbackTagToDelete={setFeedbackTagToDelete} update={update} setUpdate={setUpdate} user={user} /> : null}
      {feedbackTagToReactivate ? <ReactivateFeedbackTag feedbackTag={feedbackTagToReactivate} setFeedbackTagToReactivate={setFeedbackTagToReactivate} update={update} setUpdate={setUpdate} user={user} /> : null}
      <hr className="mt-4"></hr>
      <h2>Active</h2>
      <table className="table table-light table-striped mt-3">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Feedback Tag Name</th>
            <th scope="col">Color</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>
          {feedbackTags.filter(ft => ft.active).sort((a, b) => (a.name > b.name) ? 1 : -1).map(ft => makeFeedbackTagAdminRow(ft))}
        </tbody>
      </table>
      <h2 className="mt-5">Inactive</h2>
      <table className="table table-light table-striped mt-3">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Feedback Tag Name</th>
            <th scope="col">Color</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>
          {feedbackTags.filter(ft => !ft.active).sort((a, b) => (a.name > b.name) ? 1 : -1).map(ft => makeFeedbackTagAdminRow(ft))}
        </tbody>
      </table>
    </div>
  );
}

export default FeedbackTagsAdmin;