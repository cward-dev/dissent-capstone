import './UserPage.css';

function UserDisplay ( { user } ) {
  // const { userId, username, photoUrl, country, bio, role, posts, feedbackTags } = user;

  return (
    <div className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <img src="https://static.wikia.nocookie.net/rickandmorty/images/c/ce/MortyTransparent.png/revision/latest/scale-to-width-down/340?cb=20160909031949" class="user-page-img" />
      </div>
      <div className="col-7 p-3">
        <h4 className="card-title">Username</h4>
        <p className="card-text">Country</p>
        <p className="card-text">A short biography of the user's choice.</p>
      </div>
      <div className="col text-center p-2">
        <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart-user-profile" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
      </div>
      <div className="card-footer w-100 text-white p-2 border-white">Will put basic info here (total number of posts, etc)</div>
    </div>
  );
}

export default UserDisplay;