package manager;

public class Item {
    public String id,name,image,price;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + image + " " + price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public Item(String id, String name, String image, String price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
}
