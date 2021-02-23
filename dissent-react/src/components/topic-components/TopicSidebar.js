import { useState, useEffect } from 'react';
import Topic from './Topic.js';
import AddTopic from './AddTopic.js';
import Errors from '../Errors.js';

function TopicSidebar ( { user } ) {

  const [topics, setTopics] = useState([]);
  const [adminTools, setAdminTools] = useState(false);
  const [errors, setErrors] = useState([]);

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/topic`);
      const data = await response.json();
      setTopics(data);
    } catch (error) {
      setErrors(["Something went wrong with our database, sorry!"]);
    }
  };

  useEffect(() => {
    getData();
  }, []);

  const handleUpdate = () => {
    getData();
  };

  const handleAdminTools = () => {
    if (adminTools) {
      setAdminTools(false);
    } else {
    setAdminTools(true);
    }
  };

  const makeTopic = (topic) => {
    return <Topic key={topic.topicId} topic={topic} handleUpdate={handleUpdate} adminTools={adminTools} user={user} />;
  };

  return (
    <div>
      <h5 className="text-center">Topics</h5>
      <hr></hr>
      {user.userRole === "admin" ? <button className="btn btn-dark btn-sm col mb-3" onClick={handleAdminTools}>
          Toggle Admin Tools
        </button> 
        : null}
      {user.userRole === "admin" && adminTools ? <AddTopic handleUpdate={handleUpdate} /> : null}
      <Errors errors={errors} />
      <ul className="list-group">
        {topics.sort((a, b) => (a.topicName > b.topicName) ? 1 : -1).map(topic => makeTopic(topic))}
      </ul>
    </div>
  );
}

export default TopicSidebar;