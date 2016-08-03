package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController controller;

    private Category category = new Category();
    private CategoryModel request = new CategoryModel();

    @Test
    public void addCategoryTest() {
        when(categoryService.saveCategory(request, CrudOperation.CREATE)).thenReturn(category);
        YokiResult<Category> result = controller.add(request);
        verify(categoryService, times(1)).saveCategory(request, CrudOperation.CREATE);
        verifyNoMoreInteractions(categoryService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), category);
    }

    @Test
    public void updateCategoryTest() {
        when(categoryService.saveCategory(request, CrudOperation.UPDATE)).thenReturn(category);
        YokiResult<Category> result = controller.update(request);
        verify(categoryService, times(1)).saveCategory(request, CrudOperation.UPDATE);
        verifyNoMoreInteractions(categoryService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), category);
    }

    @Test
    public void removeCategoryTest() {
        Long categoryId = 37452L;
        YokiResult<Category> result = controller.remove(categoryId);
        verify(categoryService, times(1)).remove(categoryId);
        verifyNoMoreInteractions(categoryService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), null);
    }

    @Test
    public void getCategoryByIdTest() {
        Long categoryId = 14572L;
        when(categoryService.get(categoryId)).thenReturn(category);
        YokiResult<Category> result = controller.get(categoryId);
        verify(categoryService, times(1)).get(categoryId);
        verifyNoMoreInteractions(categoryService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), category);
    }

    @Test
    public void getAllCategoriesTest() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(category);
        when(categoryService.getAll()).thenReturn(categories);
        List<Category> result = controller.getAll();

        verify(categoryService, times(1)).getAll();
        assertEquals(categories, result);
    }

    @Test
    public void activateCategoryTest() {
        Long categoryId = 96187L;
        when(categoryService.processActivation(categoryId, true)).thenReturn(category);
        YokiResult<Category> result = controller.activate(categoryId);
        verify(categoryService, times(1)).processActivation(categoryId, true);
        verifyNoMoreInteractions(categoryService);

        assertEquals(result.getData(), category);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
    }

    @Test
    public void deactivateCategoryTest() {
        Long categoryId = 7544492L;
        when(categoryService.processActivation(categoryId, false)).thenReturn(category);
        YokiResult<Category> result = controller.deactivate(categoryId);
        verify(categoryService, times(1)).processActivation(categoryId, false);
        verifyNoMoreInteractions(categoryService);

        assertEquals(result.getData(), category);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
    }
}
