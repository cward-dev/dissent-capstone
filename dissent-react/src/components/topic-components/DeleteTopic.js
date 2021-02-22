import { useState, useEffect } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import EditTopic from './EditTopic';
import DeleteTopic from './DeleteTopic';

function Topic ( { topic, user } ) {

  const [selection, setSelection] = useState(0);

  const { topicId, topicName, articles, active } = topic;

  const handleEditClick = () => {
    setSelection(1);
  };

  const handleDeleteClick = () => {
    setSelection(2);
  };

  return (
    <div>
      <div className="d-flex flex-row">
      {selection === 0 ? 
        <Link className="col btn btn-link p-1" to={`/t/${topicName}`}>
          <li className="list-group-item d-flex justify-content-between align-items-center">
            {topicName}
            <span className="badge badge-dark badge-pill">{articles.length}</span>
          </li>
        </Link>
        : null }
      {selection === 1 ? 
        <EditTopic  />
        : null }
      {selection === 2 ? 
        <DeleteTopic />
        : null }
      </div>
      {user.userRole === "admin" && selection === 0 ? <div className="d-flex flex-row justify-content-end p-1">
        <button className="btn btn-sm btn-secondary" onClick={handleEditClick}>Edit</button>
        <button className="btn btn-sm btn-secondary ml-2" onClick={handleDeleteClick}>Delete</button>
        </div> : null}
    </div>

  );
}

export default Topic;