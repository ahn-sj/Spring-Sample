package springbox.mongodbcontest;

import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private int quantity;
    private String category;

    public GroceryItem toEntity() {
        return new GroceryItem(name, quantity, category);
    }
}
