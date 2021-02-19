import { useState } from 'react';
import ArticleFetch from '../article-components/ArticleFetch';

const ANONYMOUS_USER = {
  "user_login_id": undefined,
  "username": "Anonymous User",
  "photo-url": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Greek_letter_uppercase_Phi.svg/1200px-Greek_letter_uppercase_Phi.svg.png",
  "country": undefined,
  "bio": undefined,
  "role": "ANONYMOUS_USER"
}

function HomePage() {
  const [user, setUser] = useState({});

  return (
    <div className="container">
      <div className="sidenav">
        <ArticleFetch />
      </div>
    </div>
  );
}

export default HomePage;