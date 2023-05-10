package ex03;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

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

    public String getFilename() throws URISyntaxException {
        URI uri = new URI(url);
        return Paths.get(uri.getPath()).getFileName().toString();
    }

    @Override
    public String toString() {
        return String.format("Id = %s, URL = %s", id, url);
    }
}
