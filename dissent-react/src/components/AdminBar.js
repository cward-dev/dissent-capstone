import { useState, useEffect, React } from 'react';
import { Route, BrowserRouter as Router, Switch, Link } from 'react-router-dom';
import AddReplyPost from "./post-components/AddReplyPost";

function AdminBar () {
  return (
    <div className="container alert alert-secondary d-flex flex-row justify-content-center mb-4">
      <div className="align-self-center"><h5 className="pt-1 mr-4">Admin</h5></div>
      <Link className="btn btn-dark mr-2" to="/">Add Topic</Link>
      <Link className="btn btn-dark mr-2" to="/">Edit Topic</Link>
      <Link className="btn btn-dark mr-2" to="/">Delete Topic</Link>
      <div className="ml-4" />
      <Link className="btn btn-dark mr-2" to="/">Add Articles</Link>
      <Link className="btn btn-dark mr-2" to="/">Delete Article</Link>
      <div className="ml-4" />
      <Link className="btn btn-dark mr-2" to="/">Edit User</Link>
      <Link className="btn btn-dark" to="/">Delete User</Link>
    </div>
  );
}

export default AdminBar;