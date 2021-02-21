import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import AddReplyPost from "./post-components/AddReplyPost";

function AdminBar () {
  const [adminBarSelection, setAdminBarSelection] = useState(0);

  const onAddTopic = () => {
    if (adminBarSelection == 1) {
      setAdminBarSelection(0);
    } else {
    setAdminBarSelection(1);
    }
  }
  
  return (
    <>
      {adminBarSelection == 1 ? <div>hello</div> : null}
      <div className="container alert alert-secondary d-flex flex-row justify-content-center mb-4">
        <div className="align-self-center"><h5 className="pt-1 mr-4">Admin</h5></div>
        <button className="btn btn-dark mr-2" to="/">Add Topic</button>
        <button className="btn btn-dark mr-2" to="/">Edit Topic</button>
        <button className="btn btn-dark mr-2" to="/">Delete Topic</button>
        <div className="ml-4" />
        <button className="btn btn-dark mr-2" to="/">Add Articles</button>
        <button className="btn btn-dark mr-2" to="/">Delete Article</button>
        <div className="ml-4" />
        <button className="btn btn-dark mr-2" to="/">Edit User</button>
        <button className="btn btn-dark" to="/">Delete User</button>
      </div>
    </>
  );
}

export default AdminBar;