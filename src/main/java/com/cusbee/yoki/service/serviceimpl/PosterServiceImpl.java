package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.DishQuantity;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.poster.*;
import com.cusbee.yoki.service.StorageService;
import com.cusbee.yoki.utils.ErrorCodes;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosterServiceImpl implements StorageService {

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
        List<DishQuantity> orderMap = order.getDishes();

        for (DishQuantity dishQuantity : orderMap) {
            String dishName = dishQuantity.getDish().getName();
            for (PosterDish posterDish : posterDishes) {
                if (posterDish.getName().equals(dishName)) {
                    writeOffList.add(new WriteOffDish(posterDish.getId(), posterDish.getType(), dishQuantity.getQuantity()));
                }
                break;
            }
            /*throw new ApplicationException(HttpStatus.BAD_REQUEST,
                    "Could not find dish \"" + dishName + "\" in poster. Writeoff failed");*/
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
     *//*
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
    }*/
}
