package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.NullPointerService;
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
    private NullPointerService npService;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController controller;

    private Category category = new Category();
    private CategoryModel request = new CategoryModel();

    @Test
    public void addCategoryTest() throws BaseException {
        when(categoryService.parseRequest(request, CrudOperation.CREATE)).thenReturn(category);
        YokiResult<Category> result = controller.add(request);
        verify(categoryService, times(1)).parseRequest(request, CrudOperation.CREATE);
        verifyNoMoreInteractions(categoryService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), category);
    }

    @Test
    public void updateCategoryTest() throws BaseException {
        when(categoryService.parseRequest(request, CrudOperation.UPDATE)).thenReturn(category);
        YokiResult<Category> result = controller.update(request);
        verify(categoryService, times(1)).parseRequest(request, CrudOperation.UPDATE);
        verifyNoMoreInteractions(categoryService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), category);
    }

    @Test
    public void removeCategoryTest() throws BaseException {
        Long categoryId = 37452L;
        YokiResult<Category> result = controller.remove(categoryId);
        verify(npService, times(1)).isNull(categoryId);
        verify(categoryService, times(1)).remove(categoryId);
        verifyNoMoreInteractions(categoryService, npService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), null);
    }

    @Test
    public void getCategoryByIdTest() throws BaseException {
        Long categoryId = 14572L;
        when(categoryRepository.findById(categoryId)).thenReturn(category);
        YokiResult<Category> result = controller.get(categoryId);
        verify(npService, times(1)).isNull(categoryId);
        verify(categoryRepository, times(1)).findById(categoryId);
        verifyNoMoreInteractions(categoryRepository, npService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), category);
    }

    @Test
    public void getAllCategoriesTest() throws BaseException {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(category);
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> result = controller.getAll();

        verify(categoryRepository, times(1)).findAll();
        assertEquals(categories, result);
    }

    @Test
    public void activateCategoryTest() throws BaseException {
        Long categoryId = 96187L;
        when(categoryService.activation(categoryId, CrudOperation.UNBLOCK)).thenReturn(category);
        YokiResult<Category> result = controller.activate(categoryId);
        verify(npService, times(1)).isNull(categoryId);
        verify(categoryService, times(1)).activation(categoryId, CrudOperation.UNBLOCK);
        verifyNoMoreInteractions(npService, categoryService);

        assertEquals(result.getData(), category);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
    }

    @Test
    public void deactivateCategoryTest() throws BaseException {
        Long categoryId = 7544492L;
        when(categoryService.activation(categoryId, CrudOperation.BLOCK)).thenReturn(category);
        YokiResult<Category> result = controller.deactivate(categoryId);
        verify(npService, times(1)).isNull(categoryId);
        verify(categoryService, times(1)).activation(categoryId, CrudOperation.BLOCK);
        verifyNoMoreInteractions(npService, categoryService);

        assertEquals(result.getData(), category);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
    }
}
