import java.sql.Timestamp;

public class Tree {
    private int id;
    private int quoteID;
    private String size;
    private int height;
    private String location;
    private String nearHouse;
    private String pictureURL1;
    private String pictureURL2;
    private String pictureURL3;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Tree() {
    }

    public Tree(int id, int quoteID, String size, int height, String location, String nearHouse, String pictureURL1,
                String pictureURL2, String pictureURL3, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.quoteID = quoteID;
        this.size = size;
        this.height = height;
        this.location = location;
        this.nearHouse = nearHouse;
        this.pictureURL1 = pictureURL1;
        this.pictureURL2 = pictureURL2;
        this.pictureURL3 = pictureURL3;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(int quoteID) {
        this.quoteID = quoteID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNearHouse() {
        return nearHouse;
    }

    public void setNearHouse(String nearHouse) {
        this.nearHouse = nearHouse;
    }

    public String getPictureURL1() {
        return pictureURL1;
    }

    public void setPictureURL1(String pictureURL1) {
        this.pictureURL1 = pictureURL1;
    }

    public String getPictureURL2() {
        return pictureURL2;
    }

    public void setPictureURL2(String pictureURL2) {
        this.pictureURL2 = pictureURL2;
    }

    public String getPictureURL3() {
        return pictureURL3;
    }

    public void setPictureURL3(String pictureURL3) {
        this.pictureURL3 = pictureURL3;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
