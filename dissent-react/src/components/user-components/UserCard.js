const TEST_USER = {
  userId: 1, 
  username: "testuser", 
  photoUrl: "https://static.wikia.nocookie.net/rickandmorty/images/c/ce/MortyTransparent.png/revision/latest/scale-to-width-down/340?cb=20160909031949", 
  country: "United States", 
  bio: "This is a test user.", 
  role: "basic-user", 
  posts: [], 
  feedbackTags: []
};

function UserCard ( { user } ) {

  const { userId, username, photoUrl, country, bio, role, posts, feedbackTags } = TEST_USER;


  return (
    <div key={userId} className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <img src={photoUrl} className="user-page-img" />
      </div>
      <div className="col-7 p-3">
        <h4 className="card-title">{username}</h4>
        <p className="card-text">{country}</p>
        <p className="card-text">{bio}</p>
      </div>
      <div className="col text-center p-2">
        <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart-user-profile" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
      </div>
      <div className="card-footer w-100 text-white p-2 border-white">Total Posts: {posts.length}</div>
    </div>
  );
}

export default UserCard;