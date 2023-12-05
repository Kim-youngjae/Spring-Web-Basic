package hello.itemservice.web;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/add";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item); // -> 상품을 저장하고 상세화면에서 보여주기 위해서
        return "basic/item";
    }

    // modelAttribute 사용해보기
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        // ModelAttribute를 추가하면 item 객체를 생성하고 모델에 넣어줄 수 있다.
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        // 클래스명의 첫 글자를 소문자로 바꾼게 이름으로 지정된다. Item -> item
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {
        // @ModelAttribute도 생략 가능 -> 역시 첫글자를 소문자로 바꿔 네이밍
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true); // -> 남는 속성 값은 쿼리 파라미터 형태로 들어가게 된다.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/item/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);
        return "basic/edit";
    }

    @PostMapping("/item/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터를 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 11000, 11));
        itemRepository.save(new Item("testB", 22000, 22));
    }
}
