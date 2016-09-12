package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.DishQuantity;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.poster.*;
import com.cusbee.yoki.service.StorageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosterServiceImpl implements StorageService {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOG = LoggerFactory.getLogger(PosterServiceImpl.class);

    private static final String API = "https://cusbee.joinposter.com/api/";

    private static String token = "4f665e3e5c5f15ab92749a46b0c904ab";

    private static StringBuilder couldNotWriteOff = new StringBuilder();

    @Override
    public String writeOffOrder(Order order) {
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
        LOG.debug("call to" + uriWithParams, response);

        String notWrittenOff = checkCouldNotWriteOff();
        if(response.getSuccess() != null && response.getSuccess() == 1) {
            if(StringUtils.isEmpty(notWrittenOff)) {
                return "";
            } else {
                return notWrittenOff;
            }
        } else {
            return "Невідома помилка під час списання продуктів. Доведеться списувати вручну";
        }
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
        //required for building could-not-writeoff-string
        boolean flag = true;

        nextDish:
        for (DishQuantity dishQuantity : orderMap) {
            String dishName = dishQuantity.getDish().getName();
            for (PosterDish posterDish : posterDishes) {
                if (posterDish.getName().equals(dishName)) {
                    writeOffList.add(new WriteOffDish(posterDish.getId(), posterDish.getType(), dishQuantity.getQuantity()));
                    continue nextDish;
                }
            }
            //can't reach this statement if poster dishes contain current dish
            if(flag) {
                couldNotWriteOff.append("Не вдалося списати наступні страви: ").append(dishName);
                flag = false;
            } else {
                couldNotWriteOff.append(",").append(dishName);
            }
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

    private String checkCouldNotWriteOff() {
        String notWrittenOff = couldNotWriteOff.toString();
        couldNotWriteOff.delete(0, couldNotWriteOff.length());
        return notWrittenOff;
    }
}
