package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.CategoryService;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.discriptions.MessageManager;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info(MessageManager.receivedPost, "/categories");
        return categoryService.createCategory(newCategoryDto);
    }

    @PatchMapping("/admin/categories/{catId}")
    public CategoryDto updateCategory(@PathVariable @Positive Long catId,
                                      @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info(MessageManager.receivedPatch, "/categories", catId);
        return categoryService.updateCategory(catId, newCategoryDto);
    }

    @DeleteMapping(value = "/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive Long catId) {
        log.info(MessageManager.receivedDelete, "/categories", catId);
        categoryService.deleteCategory(catId);
    }
}