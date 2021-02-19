import { useState, useEffect } from 'react';
import PostFeed from './PostFeed.js';

function PostFetch( { articleId } ) {

  const [user, setUser] = useState([]);
  const [posts, setPosts] = useState([]);
  const [editPostId, setEditPostId] = useState(null);
  const [deletePostId, setDeletePostId] = useState(null);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/post/article/${articleId}`);
        const data = await response.json();
        setPosts(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);

  const editPost = async (editedPost) => {

    const updatedPost = {
      "postId": editedPost.postId,
      "title": editedPost.title,
      "description": editedPost.description,
      "source_id": editedPost.source_id,
      "author": editedPost.author,
      "postUrl": editedPost.PostUrl,
      "postImageUrl": editedPost.postImageUrl,
      "datePublished": editedPost.datePublished,
      "datePosted": editedPost.datePosted,
      "isActive": editedPost.isActive
    }

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedPost)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/post/${editedPost.postId}`, init);

      if (response.status === 204) {
        const newPosts = [...posts];
        const postIndexToEdit = posts.findIndex(post => post.postId === editPostId);
        newPosts[postIndexToEdit] = updatedPost;

        setPosts(newPosts);
        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
      } else if (response.status === 404) {
        throw new Error(["Post to edit not found."])
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"])
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  }

  const deleteById = async (postId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/post/${postId}`, { method: "DELETE" });
    
      if (response.status === 204) {
        const newPosts = posts.filter(post => post.postId !== postId);
        setPosts(newPosts);
        handleCancel();
      } else if (response.status === 404) {
        throw new Error(`Post ID #${postId} not found.`);
      } else {
        throw new Error("Something unexpected went wrong, sorry!")
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  };

  const getPostToEdit = () => {
    return posts.find(a => a.postId === editPostId);
  };

  const getPostToDelete = () => {
    return posts.find(a => a.postId === deletePostId);
  };

  const handleCancel = () => {
    setEditPostId(null);
    setDeletePostId(null);
    setErrors([]);
  }

  return (
    <PostFeed posts={posts} />
  );
}

export default PostFetch;