package com.example.MyBookShopApp.controllers.tagController;

import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.dto.TagDto;
import com.example.MyBookShopApp.exception.TagException;
import com.example.MyBookShopApp.service.tagServices.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagApiController {

    private final TagService tagService;

    @Autowired
    public TagApiController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/find-by-tag-name/{name}")
    public TagDto getTagByName(@PathVariable("name") String name) throws TagException {
        Tag tagByName = tagService.findTagByName(name);
        if (tagByName == null)
            throw new TagException("Tag с таким именем не был найден.");
        return new TagDto(tagByName);
    }
}
