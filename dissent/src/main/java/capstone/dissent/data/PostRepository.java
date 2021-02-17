package capstone.dissent.data;

import capstone.dissent.models.Article;
import capstone.dissent.models.Post;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository {

    public List<Post> findAll();

    public Post findById(String postId);

    public List<Post> findByParentPostId(String parentPostId);

    public List<Post> findByArticleId(String articleId);

    public List<Post> findByUserId(String userId);

    public List<Post> findByDateRange(LocalDate startDate, LocalDate endDate);

    public Post add(Post post);

    public boolean edit(Post post);

    public boolean deleteById(int postId);
}
