import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';

function Topic ( { topic, user } ) {

  const { topicId, topicName, articles, active } = topic;

  return (
    <Link className="btn btn-link p-1 mr-2" to={`/t/${topicName}`}>
      <li className="list-group-item d-flex justify-content-between align-items-center">
        {topicName}
        <span className="badge badge-dark badge-pill">{articles.length}</span>
      </li>
    </Link>


  );
}

export default Topic;