package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.*;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.ValidatorService;
import com.cusbee.yoki.utils.ErrorCodes;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorServiceImpl implements ValidatorService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private DishRepository dishRepository;

    @Override
    public void validateAccountSaveRequest(AccountModel request, CrudOperation operation) {
        validateRequestNotNull(request, Account.class);
        switch (operation) {
            case CREATE:
                String username = request.getUsername();
                String email = request.getEmail();
                //username validation
                if (StringUtils.isEmpty(username)
                        || Objects.isNull(request.getNewPassword())) {
                    throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
                            "Field Username or Password still empty");
                }
                if (accountRepository.findByUsername(username) != null) {
                    throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                            "User with this username already exist");
                }
                //email validation
                if (StringUtils.isEmpty(email)) {
                    throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
                            "Field Email can't be empty");
                }
                if (accountRepository.validateAccount(email) != null) {
                    throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                            "This email already used");
                }
                validateAccountFields(request, true);
                break;
            case UPDATE:
                validateAccountFields(request, false);
                break;
            default:
                throw new ApplicationException(ErrorCodes.User.BAD_REQUEST, "Unsupported operation");
        }
    }

    @Override
    public void validateCategorySaveRequest(CategoryModel request, CrudOperation status) {
        validateRequestNotNull(request, Category.class);
        if (StringUtils.isEmpty(request.getName())) {
            throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
                    "Category name should not be empty!");
        }
        if (status == CrudOperation.CREATE && categoryRepository.findByName(request.getName()) != null) {
            throw new ApplicationException(ErrorCodes.Category.ALREADY_EXIST,
                    "This category already exists");
        }
        validateRegexCategoryName(request.getName());
    }

    @Override
    public void validateIngredientSaveRequest(IngredientModel request, CrudOperation status) {
        validateRequestNotNull(request, Ingredient.class);
        switch(status) {
            case CREATE:
                if (StringUtils.isEmpty(request.getName())) {
                    throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
                            "Empty field 'name'");
                }
                if (Objects.isNull(request.getValue())) {
                    throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
                            "Empty field 'weight'");
                }
                if(ingredientRepository.findByName(request.getName()) != null){
                    throw new ApplicationException(ErrorCodes.Ingredient.ALREADY_EXIST, "This ingredient already exists");
                }
                break;
            case UPDATE:

                break;
        }
    }

    @Override
    public void validateDishSaveRequest(DishModel request, CrudOperation operation) {
        validateRequestNotNull(request, Dish.class);
        switch(operation) {
            case CREATE:
                if(dishRepository.findByName(request.getName()) != null){
                    throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Dish with such name already exists");
                }
                break;
            case UPDATE:

                break;
        }
        validateDishFields(request);
    }

    @Override
    public void validateOrderSaveRequest(OrderModel request, CrudOperation operation) {
        validateRequestNotNull(request, Order.class);
        switch(operation) {
            case CREATE:
                if (Objects.isNull(request.getDishes())) {
                    throw new ApplicationException(
                            ErrorCodes.Order.EMPTY_LIST_OF_DISHES,
                            "List of ordered dishes are empty");
                }
                if (Objects.isNull(request.getClient())) {
                    throw new ApplicationException(ErrorCodes.Order.INVALID_REQUEST, "Object Client are empty");
                }
                break;
            case UPDATE:

                break;
        }
    }

    @Override
    public void validateRequestNotNull(RequestModel request, Class entityClass) {
        if (Objects.isNull(request)) {
            throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST,
                    entityClass.getSimpleName() + " Request is empty");
        }
    }

    @Override
    public void validateRequestIdNotNull(Long id) {
        if (Objects.isNull(id)) {
            throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST_ID,
                    "Request id is empty");
        }
    }

    @Override
    public void validateEntityNotNull(BaseEntity entity, Class entityClass) {
        if (Objects.isNull(entity)) {
            throw new ApplicationException(ErrorCodes.Common.NOT_EXIST,
                    "Could not find " + entityClass.getSimpleName() + " in database or failed to retrieve it");
        }
    }

    private void validateAccountFields(AccountModel request, boolean createOperation) {
        validateRegexAccountUsername(request.getUsername());
        validateRegexAccountEmail(request.getEmail());
        validateRegexAccountFirstLastName(request.getFirstname());
        validateRegexAccountFirstLastName(request.getLastname());
        if (createOperation || StringUtils.isNotEmpty(request.getNewPassword())) {
            validateRegexAccountPassword(request.getNewPassword());
        }
    }

    private void validateDishFields(DishModel request) {
        validateRegexDishName(request.getName());
        validateRegexDishPrice(request.getPrice());
        validateRegexDishWeight(request.getWeight());
    }

    // *** REGEX VALIDATION METHODS ***
    private boolean validateRegexAccountUsername(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.INVALID_USERNAME, "Invalid username. Username include only alphabet symbols and numbers and should be at least 5 and at last 15 symbols long");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountPassword(String password) {
        Pattern pattern = Pattern.compile("^.{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.INVALID_PASSWORD, "Password is too weak. Username should include at least 8 symbols");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9.-]){1,20}[\\@]([a-z]){2,10}[\\.]([a-z]){2,4}$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.INVALID_EMAIL, "Email is not valid");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountFirstLastName(String name) {
        Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){1,15}$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.IVALID_FIRST_OR_LAST_NAME, "Invalid first/lastname");
        }
        return matcher.matches();
    }

    private boolean validateRegexCategoryName(String name) {
        Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){5,25}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean validateRegexDishName(String name) {
        Pattern patter = Pattern.compile("^([A-Z]{1}[a-z]{1,15}[\\s]{0,1})+$");
        Matcher matcher = patter.matcher(name);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid dish name");
        }
        return matcher.matches();
    }

    private boolean validateRegexDishPrice(Double price) {
        Pattern pattern = Pattern.compile("^([0-9]{2,4}[\\.,]{0,1}[0-9]{0,4})+$");
        Matcher matcher = pattern.matcher(price.toString());
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid dish price");
        }
        return matcher.matches();
    }

    private boolean validateRegexDishWeight(Double weight) {
        Pattern pattern = Pattern.compile("^([0-9]{2,4}[\\.,]{0,1}[0-9]{0,4})+$");
        Matcher matcher = pattern.matcher(weight.toString());
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid dish weight");
        }
        return matcher.matches();
    }
}
