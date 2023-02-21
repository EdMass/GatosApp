package main.java.org.example.gatosApp;

public class Gato {
    String id;
    String url;
    final String apiKey = "live_SrbwSRHOZF0Pv4X9XAtl4ynPbyPlJxNCNfXDGeDMyJVG7jPmDOcvLIZRGP3sBy4Q" ;
    String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
