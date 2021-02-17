package capstone.dissent.data;

import capstone.dissent.models.Topic;

import java.util.List;

public interface TopicRepository {

    public List<Topic> findAll();

    public Topic findById(int topicId);

    public Topic add(Topic topic);

    public boolean edit(Topic topic);

    public boolean deleteById(int topicId);
}
