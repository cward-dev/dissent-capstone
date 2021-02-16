import './ArticleFeed.css';

function ArticleFeed() {

  return (
    <div className="pt-4">
      <div className="card text-white bg-dark mb-4">
        <a href="http://www.google.com"><img src="https://www.dailynews.com/wp-content/uploads/2017/09/img_3776.jpg" class="card-img-top" /></a>
        <div className="card-body">
          <h5 className="card-title">Article Title</h5>
          <p className="card-text">A short description of the article.</p>
          <div className="row">
            <div className="col">
              <p className="source-author-line card-text"><a className="source-link" href="http://www.google.com">Source</a>. Author</p>
            </div>
            <div className="col">
              <ul className="list-inline text-right">
                <li className="list-inline-item">
                  <a className="nav-link active" aria-current="page" href="/about">Comments</a>
                </li>
                <li className="list-inline-item">
                  <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>


      <div className="card text-white bg-dark mb-4">
        <a href="http://www.google.com"><img src="https://newsinterpretation.com/wp-content/uploads/2020/03/news33.jpg" class="card-img-top" /></a>
        <div className="card-body">
          <h5 className="card-title">Article Title</h5>
          <p className="card-text">A short description of the article.</p>
          <div className="row">
            <div className="col">
              <p className="source-author-line card-text"><a className="source-link" href="http://www.google.com">Source</a>. Author</p>
            </div>
            <div className="col">
              <ul className="list-inline text-right">
                <li className="list-inline-item">
                  <a className="nav-link active" aria-current="page" href="/about">Comments</a>
                </li>
                <li className="list-inline-item">
                  <a className="nav-link active" href="http://www.google.com"><img className="tag-pie-chart" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ArticleFeed;