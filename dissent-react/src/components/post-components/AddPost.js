import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function AddPost () {
  const [articleId, setArticleId] = useState("placeholder");

  return (
    <Link className="btn btn-secondary px-2 py-1 mr-2" to={`/article/${articleId}`}>Add Post</Link>
  );
}

export default AddPost;