package capstone.dissent.data;

import capstone.dissent.models.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository {

    public List<Post> findAll();

    public Post findById(String postId);

    public List<Post> findByArticleId(String articleId);

    public List<Post> findByUserId(String userId);

    public List<Post> findByTimestampRange(LocalDateTime start, LocalDateTime end);

    public Post add(Post post);

    public boolean edit(Post post);

    public boolean deleteById(String postId);
}
