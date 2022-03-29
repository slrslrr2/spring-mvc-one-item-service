package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/basic/items")
public class BasicItemController {
    private final ItemRepository itemRepository = new ItemRepository();

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String add(){
        return "basic/addForm";
    }

    /**
     public String save(@ModelAttribute("item") Item item){
                                        ------ 이부분 생략하면
                                        변수명으로 Class명이 들어가고 앞글자를 대문자에서 소문자로 바꿔서 들어가진다.
                                        @ModelAttribute는 model.addAttribute("item", item)을 자동으로 해준다.
     */
    @PostMapping("/add")
    public String save(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        Item saveItem = itemRepository.save(item);
//        return "basic/item";
//        return "redirect:/basic/items/" + item.getId();
        redirectAttributes.addAttribute("itemId", saveItem.getId()); // redirect 변수명으로 사용 가능
        redirectAttributes.addAttribute("status", true); // quest 파라미터 형식으로 들어감

        // http://localhost:8080/basic/items/3?status=true
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    // 등록할때는 redirect안쓰고 수정할때 왜 redirect를 쓴거야?
    // save도 PRG로 해야한다! POST인 경우 새로고침 시 재 실행되기때문에 redirect를 사용해야함
    @PostMapping("/{itemId}/edit")
    public String editSave(@PathVariable long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
