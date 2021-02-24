import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import UserFeed from './user-components/UserFeed';

function AdminBar ( { user } ) {
  const [adminBarSelection, setAdminBarSelection] = useState(0);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    getData();
  }, []);

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/`);
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.log("Something went wrong.")
    }
  };

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
          <button className="btn btn-dark mr-2" onClick={onViewUsers}>View Users</button>
        </div>
        {adminBarSelection == 0 ? null:
        <div className="container alert alert-secondary">
          {adminBarSelection == 2 
          ? <div>
            <UserFeed users={users} />
            </div> 
            : null}
        </div>
        }
      </>
      : null}
    </>
  );
}

export default AdminBar;