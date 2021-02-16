import './ArticleCard.css';

function ArticleCard ( { article } ) {

  const { articleId, title, description, source, author, articleUrl, articleImageUrl, datePublished, timestamp, topics, posts, feedbackTags } = article;

  return (
    <div key={articleId} className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <a href={articleUrl}><img src={articleImageUrl} class="card-img-top" /></a>
      </div>
      <div className="col p-3">
        <a href={articleUrl}><h4 className="card-title">{title}</h4></a>
        <p className="card-text">{description}</p>
        <div className="row">
          <div className="col">
            <p className="source-author-line card-text"><a className="source-link" href="http://www.google.com">{source}</a>. {author}</p>
          </div>
          <div className="col text-right">
            <p className="source-author-line card-text">{timestamp}</p>
          </div>
        </div>
      </div>
      <div className="card-footer w-100 text-muted px-1">
        <div className="row">  
          <div className="col">
            <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
          </div>
          <div className="col text-right">
            <a className="nav-link active" aria-current="page" href="/article/1"><button className="btn btn-secondary px-2 py-1">Discussion ({posts.length})</button></a>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ArticleCard;