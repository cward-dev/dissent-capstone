import { useState, useEffect } from 'react';
import Topic from './Topic.js';
import Errors from '../Errors.js';

function TopicSidebar ( { topicsUpdated, user } ) {

  const [topics, setTopics] = useState([]);
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
  }, [topicsUpdated]);

  const handleUpdate = () => {
    getData();
  };

  const makeTopic = (topic) => {
    return <Topic key={topic.topicId} topic={topic} handleUpdate={handleUpdate} user={user} />;
  };

  return (
    <div>
      <h5 className="text-center">Topics</h5>
      <hr></hr>
      <Errors errors={errors} />
      <ul className="list-group">
        {topics ? topics.sort((a, b) => (a.topicName > b.topicName) ? 1 : -1).map(topic => makeTopic(topic)) : null}
      </ul>
    </div>
  );
}

export default TopicSidebar;