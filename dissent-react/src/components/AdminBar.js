import AddReplyPost from "./post-components/AddReplyPost";

function AdminBar () {
  return (
    <div className="container alert alert-secondary">
      <ul class="list-group list-group-horizontal">
        <div>
          <li class="list-group-item">Add Topic</li>
          <li class="list-group-item">Edit Topic</li>
          <li class="list-group-item">Delete Topic</li>
        </div>
        <div>
          <li class="list-group-item ml-4">Add Articles</li>
          <li class="list-group-item">Delete Article</li>     
        </div>
        <div>
          <li class="list-group-item ml-4">User</li>
        </div>
    </ul>
    </div>
  );
}

export default AdminBar;