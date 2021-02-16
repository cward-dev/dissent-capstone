import { useState } from 'react';
import ArticleFeed from '../article-components/ArticleFeed';

const TEST_ARTICLE = {
  articleId: 1,
  source: "http://www.google.com",
  title: "Article Title",
  description: "A short description of the article.",
  source: "Google",
  author: "Author",
  articleUrl: "www.google.com",
  articleImageUrl: "https://www.buddhistdoor.net/upload/article/10179/5c3ba1b1aebccea4fd7e4483d81480e2.jpeg",
  datePublished: "2021-01-01",
  timestamp: "2021-02-15",
  topics: [],
  posts: [], 
  feedbackTags: []
};

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
  const [articles, setArticles] = useState([ TEST_ARTICLE ]);

  return (
    <div className="container">
      <div className="sidenav">
        <ArticleFeed articles={articles} />
      </div>
    </div>
  );
}

export default HomePage;