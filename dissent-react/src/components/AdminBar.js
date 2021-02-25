import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import UserFeed from './user-components/UserFeed';

function AdminBar ( { user } ) {
  const [adminBarSelection, setAdminBarSelection] = useState(0);
  const [users, setUsers] = useState([]);

  const onViewUsers = () => {
    if (adminBarSelection == 2) {
      setAdminBarSelection(0);
    } else {
      setAdminBarSelection(2);
    }
  }

  return (
    <>
      {user.hasRole("ROLE_ADMIN") ?
      <>
        <div className="container alert alert-secondary d-flex flex-row justify-content-end mb-4">
          <div className="mr-auto align-self-center"><h5 className="pt-1 mr-4">Admin</h5></div>
          <Link className="btn btn-dark mr-2" to="/admin/topics">Topics</Link>
          <Link className="btn btn-dark mr-2" to="/admin/feedback-tags">Feedback Tags</Link>
          <Link className="btn btn-dark mr-2" to="/admin/article/add">Add Articles</Link>
          <Link className="btn btn-dark mr-2" to="/admin/users">View Users</Link>
        </div>
      </>
      : null}
    </>
  );
}

export default AdminBar;