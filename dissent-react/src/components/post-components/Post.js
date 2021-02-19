function Post ( { post } ) {
  return (
    <div key={post.postId}>
      {post.content}

      <div class="media"><img class="mr-3 rounded-circle" alt="Bootstrap Media Preview" src={post.user ? post.user.photoUrl : "https://avatars.slack-edge.com/2020-02-08/943465232224_8aa165e0dd959de8b928_512.png"} />
        <div class="media-body">
            <div class="row">
                <div class="col-8 d-flex">
                    <h5>Maria Smantha</h5> <span>- 2 hours ago</span>
                </div>
                <div class="col-4">
                    <div class="pull-right reply"> <a href="#"><span><i class="fa fa-reply"></i> reply</span></a> </div>
                </div>
            </div> It is a long established fact that a reader will be distracted by the readable content of a page. <div class="media mt-4"> <a class="pr-3" href="#"><img class="rounded-circle" alt="Bootstrap Media Another Preview" src="https://i.imgur.com/xELPaag.jpg" /></a>
                <div class="media-body">
                    <div class="row">
                        <div class="col-12 d-flex">
                            <h5>Simona Disa</h5> <span>- 3 hours ago</span>
                        </div>
                    </div> letters, as opposed to using 'Content here, content here', making it look like readable English.
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
      </div>
    </div>
  );
}

export default Post;