import ArticleCard from './ArticleCard.js';
import './ArticleFeed.css';

function ArticleFeed( { articles, user } ) {

  const makeArticle = (article) => {
    return (
      <ArticleCard key={article.articleId} articleId={article.articleId} user={user} />
    );
  };

  return (
    <div>
      <div className="alert alert-dark mb-4 text-center">
        <h2>Latest Articles</h2>
      </div>
      {articles.sort((a, b) => (new Date(a.datePublished) < new Date(b.datePublished)) ? 1 : -1).map(article => makeArticle(article))}
    </div>
  );
}

export default ArticleFeed;