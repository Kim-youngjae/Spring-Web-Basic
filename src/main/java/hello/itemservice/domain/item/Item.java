package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price; // 가격이 입력 안되는 경우를 위해 Integer 사용 -> null 허용
    private Integer quantity; // 가격과 사용 이유 동일

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}