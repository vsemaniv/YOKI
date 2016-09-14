package com.cusbee.yoki.controller;

import java.util.List;

import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.service.DishService;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;

@ApiClass(value = "operations with dish entity")
@RestController
@RequestMapping(value = "dish")
public class DishController {

    public static final String SUCCESS = "Success";

    @Autowired
    private DishService dishService;

    @ApiOperation(value = "create new dish")
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public YokiResult<Dish> add(@RequestBody DishModel request) {
        return new YokiResult<Dish>(HttpStatus.OK, SUCCESS, dishService.saveDish(request, CrudOperation.CREATE));
    }

    @ApiOperation(value = "update dish")
    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public YokiResult<Dish> update(@RequestBody DishModel request) {
        return new YokiResult<Dish>(HttpStatus.OK, SUCCESS, dishService.saveDish(request, CrudOperation.UPDATE));
    }

    @ApiOperation(value = "remove dish")
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public YokiResult<Dish> remove(@RequestBody IdModel idModel) {
        dishService.remove(idModel.getId());
        return new YokiResult<Dish>(HttpStatus.OK, SUCCESS, null);
    }

    @ApiOperation(value = "get dish")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public YokiResult<Dish> get(@RequestParam Long id) {
        return new YokiResult<Dish>(HttpStatus.OK, SUCCESS, dishService.get(id));
    }

    @ApiOperation(value = "get all dishes")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Dish> getAll() {
        return dishService.getAll();
    }

    @ApiOperation(value = "get available dishes")
    @RequestMapping(value = "getAvailable", method = RequestMethod.GET)
    public List<Dish> getAvailable() {
        return dishService.getAvailable();
    }

    @ApiOperation(value = "deactivate dish")
    @RequestMapping(value = "deactivate", method = RequestMethod.POST)
    public YokiResult<Dish> deactivate(@RequestBody IdModel idModel) {
        Dish dish = dishService.processActivation(idModel.getId(), false);
        return new YokiResult<Dish>(HttpStatus.OK, SUCCESS, dish);
    }

    @ApiOperation(value = "activate dish")
    @RequestMapping(value = "activate", method = RequestMethod.POST)
    public YokiResult<Dish> activate(@RequestBody IdModel idModel) {
        Dish dish = dishService.processActivation(idModel.getId(), true);
        return new YokiResult<Dish>(HttpStatus.OK, SUCCESS, dish);
    }

    @ApiOperation(value = "get ingredients")
    @RequestMapping(value = "getIngredients", method = RequestMethod.GET)
    public List<Ingredient> getIngredients(@RequestBody IdModel idModel) {
        return dishService.getIngredients(idModel.getId());
    }
}
