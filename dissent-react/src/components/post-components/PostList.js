import Post from './Post.js';
import './PostList.css';

function PostList ( { posts } ) {

  const makePost = (post) => {
    return (
      <Post post={post} />
    );
  };

  return (
    <div>
      {posts.map(post => makePost(post))}
  
    <div class="row">
      <div class="col-md-12">
          {posts.map(post => makePost(post))}
          {/* <div class="media mt-4"> <img class="mr-3 rounded-circle" alt="Bootstrap Media Preview" src="https://i.imgur.com/xELPaag.jpg" />
              <div class="media-body">
                  <div class="row">
                      <div class="col-8 d-flex">
                          <h5>Shad f</h5> <span>- 2 hours ago</span>
                      </div>
                      <div class="col-4">
                          <div class="pull-right reply"> <a href="#"><span><i class="fa fa-reply"></i> reply</span></a> </div>
                      </div>
                  </div> The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33. <div class="media mt-4"> <a class="pr-3" href="#"><img class="rounded-circle" alt="Bootstrap Media Another Preview" src="https://i.imgur.com/nUNhspp.jpg" /></a>
                      <div class="media-body">
                          <div class="row">
                              <div class="col-12 d-flex">
                                  <h5>Andy flowe</h5> <span>- 5 hours ago</span>
                              </div>
                          </div> Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.
                      </div>
                  </div>
                  <div class="media mt-3"> <a class="pr-3" href="#"><img class="rounded-circle" alt="Bootstrap Media Another Preview" src="https://i.imgur.com/HjKTNkG.jpg" /></a>
                      <div class="media-body">
                          <div class="row">
                              <div class="col-12 d-flex">
                                  <h5>Simp f</h5> <span>- 5 hours ago</span>
                              </div>
                          </div> a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur
                      </div>
                  </div>
                  <div class="media mt-3"> <a class="pr-3" href="#"><img class="rounded-circle" alt="Bootstrap Media Another Preview" src="https://i.imgur.com/nAcoHRf.jpg" /></a>
                      <div class="media-body">
                          <div class="row">
                              <div class="col-12 d-flex">
                                  <h5>John Smith</h5> <span>- 4 hours ago</span>
                              </div>
                          </div> the majority have suffered alteration in some form, by injected humour, or randomised words.
                      </div>
                  </div>
              </div>
          </div> */}
      </div>
    </div>
    </div>
  );
}

export default PostList;