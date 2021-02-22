import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import AdminTopic from "./topic-components/AdminTopic.js";

function AdminBar ( { user } ) {
  const [adminBarSelection, setAdminBarSelection] = useState(0);

  const onTopic = () => {
    if (adminBarSelection == 1) {
      setAdminBarSelection(0);
    } else {
      setAdminBarSelection(1);
    }
  }

  const onViewUsers = () => {
    if (adminBarSelection == 2) {
      setAdminBarSelection(0);
    } else {
      setAdminBarSelection(2);
    }
  }


  
  return (
    <>
      <div className="container alert alert-secondary d-flex flex-row justify-content-end mb-4">
        <div className="mr-auto align-self-center"><h5 className="pt-1 mr-4">Admin</h5></div>
        <button className="btn btn-dark mr-2" onClick={onTopic}>Topic</button>
        <div className="ml-2" />
        <Link className="btn btn-dark mr-2" to="/article/add">Add Articles</Link>
        <div className="ml-2" />
        <button className="btn btn-dark mr-2" onClick={onViewUsers}>View Users</button>
      </div>
      {adminBarSelection == 0 ? null:
      <div className="container alert alert-secondary">
        {adminBarSelection == 1 ? <AdminTopic user={user} setAdminBarSelection={setAdminBarSelection} /> : null}
        {adminBarSelection == 2 ? <div>onViewUsers</div> : null}
      </div>
      }
    </>
  );
}

export default AdminBar;