package ex03;

public class DownloadTask {
    private final String id;
    private final String url;

    public DownloadTask(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
