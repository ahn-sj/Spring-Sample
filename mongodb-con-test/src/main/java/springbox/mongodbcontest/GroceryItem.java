package springbox.mongodbcontest;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Document("groceryitems")
public class GroceryItem {

    @Id
    private String id;

    private String name;
    private int quantity;
    private String category;

    public GroceryItem(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public void update(ItemRequest itemRequest) {
        name = itemRequest.getName();
        quantity = itemRequest.getQuantity();
        category = itemRequest.getCategory();
    }
}
