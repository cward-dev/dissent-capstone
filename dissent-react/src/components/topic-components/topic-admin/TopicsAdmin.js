import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../../Errors.js';
import TopicAdminRow from './TopicAdminRow.js';
import AddTopic from './AddTopic.js';
import EditTopic from './EditTopic.js';
import DeleteTopic from './DeleteTopic.js';
import ReactivateTopic from './ReactivateTopic.js';

function TopicsAdmin ( { handleTopicsUpdated, user } ) {
  const [topics, setTopics] = useState([]);
  const [topicToEdit, setTopicToEdit] = useState(null);
  const [topicToDelete, setTopicToDelete] = useState(null);
  const [topicToReactivate, setTopicToReactivate] = useState(null);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/api/topic/with-inactive`);
        const data = await response.json();
        setTopics(data);
        handleTopicsUpdated();
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, [topics]);

  const makeTopicAdminRow = (topic) => {
    return (
      <TopicAdminRow key={topic.topicId} topic={topic} setTopicToEdit={setTopicToEdit} setTopicToDelete={setTopicToDelete} setTopicToReactivate={setTopicToReactivate} user={user} />
    );
  };

  return (
    <div>
      <Errors errors={errors} />
      <h1 className="d-flex flex-row justify-content-center mb-4">Topics Admin</h1>
      <hr className="mb-4"></hr>
      {!topicToEdit && !topicToDelete && !topicToReactivate ? <AddTopic /> : null}
      {topicToEdit ? <EditTopic topic={topicToEdit} setTopicToEdit={setTopicToEdit} user={user} /> : null}
      {topicToDelete ? <DeleteTopic topic={topicToDelete} setTopicToDelete={setTopicToDelete} user={user} /> : null}
      {topicToReactivate ? <ReactivateTopic topic={topicToReactivate} setTopicToReactivate={setTopicToReactivate} user={user} /> : null}
      <hr className="mt-4"></hr>
      <h2>Active</h2>
      <table className="table table-light table-striped mt-3">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Topic Name</th>
            <th scope="col"># Articles</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>
          {topics.filter(t => t.active).sort((a, b) => (a.name < b.name) ? 1 : -1).map(t => makeTopicAdminRow(t))}
        </tbody>
      </table>
      <h2 className="mt-5">Inactive</h2>
      <table className="table table-light table-striped mt-3">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Topic Name</th>
            <th scope="col">Articles</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>
          {topics.filter(t => !t.active).sort((a, b) => (a.name < b.name) ? 1 : -1).map(t => makeTopicAdminRow(t))}
        </tbody>
      </table>
    </div>
  );
}

export default TopicsAdmin;