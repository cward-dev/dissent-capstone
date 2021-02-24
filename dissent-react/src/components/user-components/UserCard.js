import { useContext, useEffect, useState } from 'react';

function UserCard ({user}) {

  return (
    <div key={user.userId} className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <img src={user.photoUrl} className="user-page-img" />
      </div>
      <div className="col-7 p-3">
        <h4 className="card-title">{user.username}</h4>
        <p className="card-text">{user.country}</p>
        <p className="card-text">{user.bio}</p>
      </div>
      <div className="col text-center p-2">
        <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart-user-profile" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
      </div>
      <div className="card-footer w-100 text-white p-2 border-white">Total Posts: </div>
    </div>
  );
}

export default UserCard;