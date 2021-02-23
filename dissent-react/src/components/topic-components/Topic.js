import { useState, useEffect } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import EditTopic from './EditTopic';
import DeleteTopic from './DeleteTopic';

function Topic ( { topic, handleUpdate, adminTools, user } ) {

  const [selection, setSelection] = useState(0);

  const { topicId, topicName, articles, active } = topic;

  const handleEditClick = () => {
    if (selection === 1){
      setSelection(0);
    } else {
    setSelection(1);
    }
  };

  const handleTopicsUpdate = () => {
    handleUpdate();
  };

  const handleDeleteClick = () => {
    if (selection === 2){
      setSelection(0);
    } else {
    setSelection(2);
    }
  };

  return (
    <>
      <div className="d-flex flex-row align-items-center">
        <Link className="col btn btn-link p-1" to={`/t/${topicName}`}>
          <li className="list-group-item d-flex justify-content-start align-items-center">{topicName}</li>
        </Link>
      </div>
      {user.userRole === "admin" && adminTools ?
              <div className="text-right mx-2 my-1">
                <button className="btn btn-secondary btn-sm mx-2" onClick={handleEditClick}>Edit</button>
                <button className="btn btn-secondary btn-sm" onClick={handleDeleteClick}>Delete</button>
              </div>
            : null}
      {selection === 1 && adminTools ? <EditTopic topic={topic} handleTopicsUpdate={handleTopicsUpdate} handleEditClick={handleEditClick} /> : null}
      {selection === 2 && adminTools ? <DeleteTopic topic={topic} handleTopicsUpdate={handleTopicsUpdate} handleDeleteClick={handleDeleteClick} /> : null}
    </>
  );
}

export default Topic;