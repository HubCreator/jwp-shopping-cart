package cart.domain;

public class Product {
    private Long id;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final ProductCategory category;

    public Product(final Long id, final String name, final String imageUrl, final int price, final ProductCategory category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }
}
