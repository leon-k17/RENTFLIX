public class CommunityPost {
    private String title;
    private String content;
    private String author;
    private String time;

    public CommunityPost(String title, String content, String author, String time) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getTime() { return time; }
}
