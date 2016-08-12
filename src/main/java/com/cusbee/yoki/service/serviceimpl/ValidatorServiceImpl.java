package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.AuthorityName;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.*;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.repositories.DishRepository;
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
    private DishRepository dishRepository;

    @Override
    public void validateAccountSaveRequest(AccountModel request, CrudOperation operation) {
        validateRequestNotNull(request, Account.class);
        if(!isEnumValid(request.getAuthority(), AuthorityName.class)) {
            throw new ApplicationException(ErrorCodes.User.INVALID_AUTHORITY,
                    "Incorrect authority type");
        }
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
                            "Email field can't be empty");
                }
                if (accountRepository.validateAccount(email) != null) {
                    throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                            "This email is already in use");
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
    public void validateDishSaveRequest(DishModel request, CrudOperation operation) {
        validateRequestNotNull(request, Dish.class);
        switch (operation) {
            case CREATE:
                if (dishRepository.findByName(request.getName()) != null) {
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
        if(request.getClient() == null) {
            throw new ApplicationException(
                    ErrorCodes.Order.NO_CLIENT_ASSIGNED,
                    "There is no client assigned for this order!");
        }
        validateClientFields(request.getClient());
        switch (operation) {
            case CREATE:
                if (Objects.isNull(request.getDishes())) {
                    throw new ApplicationException(
                            ErrorCodes.Order.EMPTY_LIST_OF_DISHES,
                            "List of ordered dishes is empty");
                }
                break;
            case UPDATE:
                validateRequestIdNotNull(request.getId());
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

    public <E extends Enum<E>> boolean isEnumValid(String type, Class<E> enumClass) {
        if (StringUtils.isEmpty(type)) return false;
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(type)) {
                return true;
            }
        }
        return false;
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

    private void validateClientFields(ClientModel request) {
        String clientPhone = request.getPhone();
        if(StringUtils.isEmpty(clientPhone)) {
            throw new ApplicationException(
                    ErrorCodes.Client.EMPTY_PHONE_NUMBER,
                    "You should specify client's phone number!");
        }
        validateRegexClientPhoneNumber(clientPhone);
    }

    // *** REGEX VALIDATION METHODS ***
    private boolean validateRegexAccountUsername(String username) {
        Pattern pattern = Pattern.compile("^[\\p{IsAlphabetic}-_\\d]{5,25}$");
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
        Pattern pattern = Pattern.compile("^[\\p{IsAlphabetic}-]{2,25}$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.IVALID_FIRST_OR_LAST_NAME, "Invalid first/lastname");
        }
        return matcher.matches();
    }

    private boolean validateRegexCategoryName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{IsAlphabetic}\\s\\d]{2,35}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean validateRegexDishName(String name) {
        Pattern patter = Pattern.compile("^[\\p{IsAlphabetic}\\s]{2,35}$");
        Matcher matcher = patter.matcher(name);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid dish name");
        }
        return matcher.matches();
    }

    private boolean validateRegexDishPrice(Double price) {
        Pattern pattern = Pattern.compile("^[0-9]{1,4}[\\.,]{0,1}[0-9]{0,4}$");
        Matcher matcher = pattern.matcher(price.toString());
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid dish price");
        }
        return matcher.matches();
    }

    private boolean validateRegexDishWeight(Integer weight) {
        Pattern pattern = Pattern.compile("^\\d{1,5}$");
        Matcher matcher = pattern.matcher(weight.toString());
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid dish weight");
        }
        return matcher.matches();
    }

    private boolean validateRegexClientPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.Client.INVALID_PHONE_NUMBER, "Invalid phone number. It should consist of 10 digits.");
        }
        return matcher.matches();
    }
}
