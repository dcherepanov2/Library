package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @ModelAttribute("allTags")
    public List<Tag> allTags(){
        return tagService.findAll();
    }

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{slug}")
    public String getBooksFromTag(@PathVariable("slug") String slug, Model model){
        model.addAttribute("tagSlug", tagService.findTagBySlug(slug));
        model.addAttribute("tagSlugBooks", new RecommendedBooksDto(tagService.findBooksBySlug(slug,0,20)));
        return "/tags/index";
    }
}
