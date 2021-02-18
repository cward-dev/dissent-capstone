package capstone.dissent.data;

import capstone.dissent.models.FeedbackTag;

import java.util.List;

public interface FeedbackTagRepository {

    public List<FeedbackTag> findAll();

    public List<FeedbackTag> findAllInactive();

    public FeedbackTag findById(int feedbackTagId);

    public FeedbackTag add(FeedbackTag feedbackTag);

    public boolean edit(FeedbackTag feedbackTag);

    public boolean deleteById(int feedbackTagId);
}
