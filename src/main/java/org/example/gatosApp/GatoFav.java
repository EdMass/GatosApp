package main.java.org.example.gatosApp;

public class GatoFav {
    String id;
    String imageId;
    final String apiKey = "live_SrbwSRHOZF0Pv4X9XAtl4ynPbyPlJxNCNfXDGeDMyJVG7jPmDOcvLIZRGP3sBy4Q";
    ImageX image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ImageX getImage() {
        return image;
    }

    public void setImage(ImageX image) {
        this.image = image;
    }
}
