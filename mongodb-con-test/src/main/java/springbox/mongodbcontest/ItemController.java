package springbox.mongodbcontest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/item")
    public List<GroceryItem> findAll() {
        return itemRepository.findAll();
    }

    @GetMapping("/item/{id}")
    public GroceryItem findById(@PathVariable String id) {
        return itemRepository.findById(id).get();
    }

    @PostMapping("/item")
    public String save(@RequestBody ItemRequest itemRequest) {
        GroceryItem saveGroceryItem = itemRepository.save(itemRequest.toEntity());

        return saveGroceryItem.getId();
    }

    @PutMapping("item/{id}")
    public void update(@RequestBody ItemRequest itemRequest, @PathVariable String id) {
        GroceryItem groceryItem = itemRepository.findById(id).get();
        groceryItem.update(itemRequest);
        itemRepository.save(groceryItem);
    }

    @DeleteMapping("item/{id}")
    public int deleteById(@PathVariable String id) {
        itemRepository.deleteById(id);
        return 1;
    }
}
