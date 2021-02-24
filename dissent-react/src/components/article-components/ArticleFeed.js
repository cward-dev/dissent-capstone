import ArticleCard from './ArticleCard.js';
import './ArticleFeed.css';

function ArticleFeed( { articles, updateArticleDelete, setUpdateArticleDelete, user } ) {

  const makeArticle = (article) => {
    return (
      <ArticleCard key={article.articleId} articleId={article.articleId} updateArticleDelete={updateArticleDelete} setUpdateArticleDelete={setUpdateArticleDelete} user={user} />
    );
  };

  return (
    <div>
      {articles.sort((a, b) => (new Date(a.datePublished) < new Date(b.datePublished)) ? 1 : -1).map(article => makeArticle(article))}
    </div>
  );
}

export default ArticleFeed;