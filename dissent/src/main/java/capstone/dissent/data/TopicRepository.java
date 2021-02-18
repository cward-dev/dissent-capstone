package capstone.dissent.data;

import capstone.dissent.models.Topic;

import java.util.List;

public interface TopicRepository {

    public List<Topic> findAll();

    public List<Topic> findAllInactive();

    public Topic findById(int topicId);

    public Topic findInactiveByName(String topicName);

    public Topic add(Topic topic);

    public boolean edit(Topic topic);

    public boolean activateById(int topicId);

    public boolean deleteById(int topicId);
}
