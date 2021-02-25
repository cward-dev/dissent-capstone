import { useState, useEffect } from 'react';
import PostUserPage from './PostUserPage.js';
import './PostFeed.css';

function PostFeedUserPage ( { thisUser, user } ) {

  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        if (thisUser) {
          const response = await fetch(`http://localhost:8080/api/post/user/${thisUser.userId}`);
          const data = await response.json();
          setPosts(data);
        }
      } catch (error) {
        console.log("Something went wrong.")
      }
    };
    getData();
  }, [thisUser]);

  const makePost = (post) => {
    return (
      <PostUserPage key={post.postId} post={post} user={user} />
    );
  };

  return (
    <>
      <div className="pt-2 text-center">
        <hr></hr>
        <h2>Post History</h2>
        <hr></hr>
      </div>
      <div>
        {posts.sort((a, b) => (new Date(a.timestamp) < new Date(b.timestamp) ? 1 : -1)).map(post => makePost(post))}
      </div>
    </>
  );
}

export default PostFeedUserPage;