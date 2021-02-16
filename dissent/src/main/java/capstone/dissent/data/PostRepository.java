package capstone.dissent.data;

import capstone.dissent.models.Article;
import capstone.dissent.models.Post;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository {

    public List<Post> findAll();

    public Post findById(int postId);

    public List<Post> findByArticleId(int articleId);

    public List<Post> findByDateRange(LocalDate startDate, LocalDate endDate);

    public Post add(Post post);

    public boolean edit(Post post);

    public boolean deleteById(int postId);
}
