package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.model.binotel.BinotelPostModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

/**
 * Created on 30.08.2016.
 */
@Service
public class BinotelServiceImpl {
    private static RestTemplate restTemplate = new RestTemplate();

    private static final String KEY = "c82639-e706948";
    private static final String SECRET = "565d8d-133ae7-deca80-103da1-17f43225";
    private static final String BINOTEL_API = "https://api.binotel.com/api/";
    private static final String API_VERSION = "2.0";

    public static void main(String[] args) throws JsonProcessingException {
        String phoneNumber = "0952107335";
        String extNumber = "0501597777"; //operator number
        String[] params = {extNumber, phoneNumber};
        Arrays.sort(params);
        ObjectMapper mapper = new ObjectMapper();
        String raw = mapper.writeValueAsString(params);
        String md5Signature = DigestUtils.md5Hex(SECRET + raw);

        String uriWithParams = UriComponentsBuilder.fromHttpUrl(BINOTEL_API + API_VERSION + "calls/ext-to-phone.json").toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Content-Length", String.valueOf(raw.length()));
        //make rest template
        BinotelPostModel model = new BinotelPostModel(KEY, md5Signature);
        restTemplate.postForObject(uriWithParams, headers, Object.class);

    }
}