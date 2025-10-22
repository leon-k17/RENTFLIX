import java.util.ArrayList;
import java.util.List;

public class CommunityPost {
    private String title, content, author, time;
    private List<String> replies;

    public CommunityPost(String title, String content, String author, String time) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
        this.replies = new ArrayList<>();
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getTime() { return time; }
    public List<String> getReplies() { return replies; }
    public void addReply(String reply) { replies.add(reply); }
}
