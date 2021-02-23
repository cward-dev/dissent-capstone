import { useState, useEffect } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import EditTopic from './EditTopic';
import DeleteTopic from './DeleteTopic';

function Topic ( { topic, user } ) {

  return (
    <div className="d-flex flex-row align-items-center">
      <Link className="col btn btn-link p-1" to={`/t/${topic.topicName}`}>
        <li className="list-group-item d-flex justify-content-start align-items-center">{topic.topicName}</li>
      </Link>
    </div>
  );
}

export default Topic;