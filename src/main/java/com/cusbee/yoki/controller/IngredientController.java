package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.service.IngredientService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 12.09.2016.
 */
@ApiClass("Operations with ingredients")
@RestController
@RequestMapping(value = "ingredient")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @RequestMapping(value = "create")
    public YokiResult<Ingredient> create(@RequestBody Ingredient request) {
        return new YokiResult<>(HttpStatus.OK, "Ingredient successfully created", ingredientService.addIngredient(request));
    }

    @RequestMapping(value = "update")
    public YokiResult<Ingredient> update(@RequestBody Ingredient request) {
        return new YokiResult<>(HttpStatus.OK, "Ingredient successfully updated", ingredientService.updateIngredient(request));
    }

    @RequestMapping(value = "remove")
    public YokiResult<Ingredient> remove(@RequestBody IdModel idModel) {
        ingredientService.remove(idModel.getId());
        return new YokiResult<>(HttpStatus.OK, "Ingredient successfully removed", null);
    }

    @RequestMapping(value = "getAll")
    public List<Ingredient> getAll() {
        return ingredientService.getAll();
    }
}
