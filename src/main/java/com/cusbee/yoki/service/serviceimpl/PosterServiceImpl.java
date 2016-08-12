package com.cusbee.yoki.service.serviceimpl;

<<<<<<< HEAD
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;
=======
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.poster.*;
import com.cusbee.yoki.service.StorageService;
import com.cusbee.yoki.utils.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

@Service
public class PosterServiceImpl implements StorageService {

<<<<<<< HEAD
    @Override
    public boolean writeOffOrder(Order order) {
        return false;
    }

    /**
     * takes a list of dish names
     */
    private List<String> getDishes(Order order) {
        return null;
    }
=======
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String API = "https://cusbee.joinposter.com/api/";

    private static String token = "4f665e3e5c5f15ab92749a46b0c904ab";

    @Override
    public boolean writeOffOrder(Order order) {
        List<WriteOffDish> writeOffDishes = remapDishes(order);
        PosterWriteOffModel model = new PosterWriteOffModel(1, writeOffDishes);

        //set required header for poster
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/form-data");

        HttpEntity<PosterWriteOffModel> entity = new HttpEntity<>(model, headers);
        String uriWithParams = UriComponentsBuilder.fromHttpUrl(API + "storage.createWriteOff")
                .queryParam("format", "json")
                .queryParam("token", token)
                .toUriString();
        WriteOffResponse response = restTemplate.postForObject(uriWithParams, entity, WriteOffResponse.class);
        return response.getSuccess() == 1;
    }

    /**
     * Maps dishes from order to the appropriate model
     * which can be accepted by poster
     *
     * @param order - ordered dish container
     * @return list of ready-to-use writeoff dish models
     */
    private List<WriteOffDish> remapDishes(Order order) {
        List<WriteOffDish> writeOffList = new ArrayList<>();
        List<PosterDish> posterDishes = getDishesFromPoster();
        Map<Dish, Integer> orderMap = getOrderMap(order);

        for (Map.Entry<Dish, Integer> dishEntry : orderMap.entrySet()) {
            String dishName = dishEntry.getKey().getName();
            for (PosterDish posterDish : posterDishes) {
                if (posterDish.getName().equals(dishName)) {
                    writeOffList.add(new WriteOffDish(posterDish.getId(), posterDish.getType(), dishEntry.getValue()));
                }
                break;
            }
            throw new ApplicationException(ErrorCodes.Order.COULD_NOT_WRITEOFF_DISH,
                    "Could not find dish \"" + dishName + "\" in poster. Writeoff failed");
        }
        return writeOffList;
    }

    /**
     * takes a list of dish names from poster
     */
    private List<PosterDish> getDishesFromPoster() {
        String uriWithParams = UriComponentsBuilder.fromHttpUrl(API + "menu.getProducts")
                .queryParam("format", "json")
                .queryParam("token", token)
                .toUriString();
        PosterDishResponse posterDishList = restTemplate.getForObject(uriWithParams, PosterDishResponse.class);
        return posterDishList.getPosterDishes();
    }

    /**
     * Transforms order to the Map where keys are dishes from order
     * and values are quantities of each one.
     *
     * @param order
     * @return Map with dishes and their quantity
     */
    private Map<Dish, Integer> getOrderMap(Order order) {
        Map<Dish, Integer> orderMap = new HashMap<>();
        List<Dish> dishes = order.getDishes();
        for (Dish dish : dishes) {
            if (orderMap.containsKey(dish)) {
                orderMap.put(dish, orderMap.get(dish) + 1);
            } else {
                orderMap.put(dish, 1);
            }
        }
        return orderMap;
    }


    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/form-data");
        List<WriteOffDish> list = new ArrayList<>();
        WriteOffDish dish = new WriteOffDish();
        dish.setId(3);
        dish.setType("2");
        dish.setQuantity(1);
        list.add(dish);
        PosterWriteOffModel model = new PosterWriteOffModel(1, list);
        HttpEntity<PosterWriteOffModel> entity = new HttpEntity<>(model, headers);
        String uriWithParams = UriComponentsBuilder.fromHttpUrl(API + "storage.createWriteOff")
                .queryParam("format", "json")
                .queryParam("token", token)
                .toUriString();
        String result = restTemplate.postForObject(uriWithParams, entity, String.class);
        System.out.println(result);
    }
/*
    private static void test1() {
        String uriWithParams = UriComponentsBuilder.fromHttpUrl(API + "menu.getProducts")
                .queryParam("format", "json")
                .queryParam("token", token)
                .toUriString();
        PosterDishResponse posterDishList = restTemplate.getForObject(uriWithParams, PosterDishResponse.class);
        System.out.println(posterDishList.getPosterDishes().get(1));
    }*/
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
