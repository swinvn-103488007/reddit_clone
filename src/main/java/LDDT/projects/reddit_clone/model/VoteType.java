package LDDT.projects.reddit_clone.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);
    private int direction;
    VoteType(int direction) {}
    public Integer getDirection() {
        return direction;
    }
}
