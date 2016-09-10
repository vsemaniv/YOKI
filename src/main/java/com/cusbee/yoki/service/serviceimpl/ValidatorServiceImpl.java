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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        switch (operation) {
            case CREATE:
                String username = request.getUsername();
                String email = request.getEmail();
                //username validation
                if (StringUtils.isEmpty(username)
                        || Objects.isNull(request.getNewPassword())) {
                    throw new ApplicationException(HttpStatus.BAD_REQUEST,
                            "Field Username or Password still empty");
                }
                if (accountRepository.findByUsername(username) != null) {
                    throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "User with this username already exist");
                }
                //email validation
                if (StringUtils.isEmpty(email)) {
                    throw new ApplicationException(HttpStatus.BAD_REQUEST,
                            "Email field can't be empty");
                }
                if (accountRepository.getByEmail(email) != null) {
                    throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "This email is already in use");
                }
                if(CollectionUtils.isEmpty(request.getAuthorities())) {
                    throw new ApplicationException(HttpStatus.BAD_REQUEST,
                            "You should specify at least one authority!");
                }
                validateAccountFields(request, true);
                break;
            case UPDATE:
                validateAccountFields(request, false);
                break;
            default:
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "Unsupported operation");
        }
        if(CollectionUtils.isNotEmpty(request.getAuthorities())) {
            for(String authority : request.getAuthorities()) {
                if(!isEnumValid(authority, AuthorityName.class)) {
                    throw new ApplicationException(HttpStatus.BAD_REQUEST,
                            "Incorrect authority type");
                }
            }
        }
    }

    @Override
    public void validateCategorySaveRequest(CategoryModel request, CrudOperation status) {
        validateRequestNotNull(request, Category.class);
        if (StringUtils.isEmpty(request.getName())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST,
                    "Category name should not be empty!");
        }
        if (status == CrudOperation.CREATE && categoryRepository.findByName(request.getName()) != null) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST,
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
                    throw new ApplicationException(HttpStatus.BAD_REQUEST, "Dish with such name already exists");
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
        if (Objects.isNull(request.getDishes())) {
            throw new ApplicationException(
                    HttpStatus.BAD_REQUEST,
                    "List of ordered dishes is empty");
        }
        switch (operation) {
            case CREATE:
                if(request.getClient() == null) {
                    throw new ApplicationException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "There is no client assigned for this order!");
                }
                validateClientFields(request.getClient());
                break;
            case UPDATE:
                validateRequestIdNotNull(request.getId(), Order.class);
                break;
        }
    }

    @Override
    public void validateRequestNotNull(RequestModel request, Class entityClass) {
        if (Objects.isNull(request)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST,
                    entityClass.getSimpleName() + " Request is empty");
        }
    }

    @Override
    public void validateRequestIdNotNull(Long id, Class entityClass) {
        if (Objects.isNull(id)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST,
                    entityClass.getSimpleName()+" id should not be empty");
        }
    }

    @Override
    public void validateEntityNotNull(BaseEntity entity, Class entityClass) {
        if (Objects.isNull(entity)) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not find " + entityClass.getSimpleName() + " in database or failed to retrieve it");
        }
    }

    @Override
    public <E extends Enum<E>> boolean isEnumValid(String type, Class<E> enumClass) {
        if (StringUtils.isEmpty(type)) return false;
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(type.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void validateDates(DateFormat format, String... dates) {
        try {
            for (String date : dates) {
                format.parse(date);
            }
        } catch (ParseException e) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid date/time format");
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
    }

    private void validateClientFields(ClientModel request) {
        String clientPhone = request.getPhone();
        if(StringUtils.isEmpty(clientPhone)) {
            throw new ApplicationException(
                    HttpStatus.BAD_REQUEST,
                    "You should specify client's phone number!");
        }
        validateRegexClientPhoneNumber(clientPhone);
    }

    // *** REGEX VALIDATION METHODS ***
    private boolean validateRegexAccountUsername(String username) {
        Pattern pattern = Pattern.compile("^[\\p{IsAlphabetic}-_\\d]{4,25}$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid username. Username include only alphabet symbols and numbers and should be at least 5 and at last 15 symbols long");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountPassword(String password) {
        Pattern pattern = Pattern.compile("^.{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Password is too weak. It should include at least 8 symbols");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9.-]){1,20}[\\@]([a-z]){2,10}[\\.]([a-z]){2,4}$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Email is not valid");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountFirstLastName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{IsAlphabetic}-]{2,25}$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid first/lastname");
        }
        return matcher.matches();
    }

    private boolean validateRegexCategoryName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{IsAlphabetic}\\s]{2,35}$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid category name");
        }
        return matcher.matches();
    }

    private boolean validateRegexDishName(String name) {
        Pattern patter = Pattern.compile("^[\\p{IsAlphabetic}\\s\\d]{2,35}$");
        Matcher matcher = patter.matcher(name);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid dish name");
        }
        return matcher.matches();
    }

    private boolean validateRegexDishPrice(Double price) {
        Pattern pattern = Pattern.compile("^[0-9]{1,4}[\\.,]{0,1}[0-9]{0,4}$");
        Matcher matcher = pattern.matcher(price.toString());
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid dish price");
        }
        return matcher.matches();
    }

    private boolean validateRegexClientPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid phone number. It should consist of 10 digits.");
        }
        return matcher.matches();
    }
}
