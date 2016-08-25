package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.DishType;
import com.cusbee.yoki.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
@Transactional
public class DishServiceImpl implements DishService {

    private static final Logger LOG = LoggerFactory.getLogger(DishServiceImpl.class);

    @Autowired
    private DishDao dao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ActivationService activationService;

    @Override
    @Transactional
    public Dish get(Long id) {
        validatorService.validateRequestIdNotNull(id);
        Dish dish = dao.get(id);
        validatorService.validateEntityNotNull(dish, Dish.class);
        return dish;
    }

    @Override
    public List<Dish> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Dish dish = get(id);
        dish.setCategory(null);
        dao.remove(dish);
    }


    @Override
    @Transactional
    public Dish saveDish(DishModel request, CrudOperation operation) {
        Dish dish;
        validatorService.validateDishSaveRequest(request, operation);
        switch (operation) {
            case CREATE:
                dish = new Dish();
                dish.setImages(new ArrayList<DishImage>());
                dish.setEnabled(Boolean.TRUE);
                break;
            case UPDATE:
                dish = dao.get(request.getId());
                break;
            default:
                throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST, "Invalid request");
        }
        dish.setName(request.getName());
        dish.setPrice(request.getPrice());
        dish.setDescription(request.getDescription());
        dish.setType(getDishType(request));
        Long categoryId = request.getCategoryId();
        dish.setCategory(categoryId == null ? null : categoryService.get(categoryId));
        setDishImageList(dish, request.getImageLinks());
        return dao.save(dish);
    }

    @Override
    public Dish processActivation(Long id, boolean activate) {
        Dish dish = get(id);
        activationService.processActivation(dish, activate);
        return dao.save(dish);
    }

    private DishType getDishType(DishModel request) {
        String type = request.getType();
        return validatorService.isEnumValid(type, DishType.class) ?
                DishType.valueOf(type.toUpperCase()) : DishType.ORDINARY;
    }

    private void setDishImageList(Dish dish, List<String> imageLinks) {
        List<DishImage> dishImages = dish.getImages();
        dishImages.clear();
        for(String link : imageLinks) {
            if(StringUtils.isNotEmpty(link)) {
                dishImages.add(new DishImage(link, dish));
            } else {
                LOG.warn("Attempt to save empty link to image for Dish named {{}}", dish.getName());
            }
        }
    }
}
