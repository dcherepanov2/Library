package com.example.MyBookShopApp.controllers.tagController;

import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.dto.CartPostponedCounterDto;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.tagServices.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;


    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @ModelAttribute("countPostponed")
    public CartPostponedCounterDto cartPostponedCounterDto(@CookieValue(name = "cartContents", required = false) String cartContents,
                                                           @CookieValue(name = "keptContents", required = false) String keptContents) {
        CartPostponedCounterDto cartPostponedCounterDto = new CartPostponedCounterDto();
        if (cartContents != null && !cartContents.equals(""))
            cartPostponedCounterDto.setCountCart(cartContents.split("/").length);
        if (keptContents != null && !keptContents.equals(""))
            cartPostponedCounterDto.setCountPostponed(keptContents.split("/").length);
        return cartPostponedCounterDto;
    }

    @ModelAttribute("allTags")
    public List<Tag> allTags() {
        List<Tag> list = tagService.findAll();
        Collections.shuffle(list);
        return list;
    }

    @GetMapping("/{slug}")
    public String getBooksFromTag(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("tagSlug", tagService.findTagBySlug(slug));
        model.addAttribute("tagSlugBooks", new RecommendedBooksDto(tagService.findBooksBySlug(slug, 0, 20)));
        return "/tags/index";
    }
}
