package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.binotel.BinotelPostModel;
import com.cusbee.yoki.service.BinotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 30.08.2016.
 */
@Service
public class BinotelServiceImpl implements BinotelService {
    private static RestTemplate restTemplate = new RestTemplate();
    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static MessageDigest md5Encoder;
    //phone numbers to connect: our inner line e.g. 901 and client phone number
    private static Map<String, String> params = new TreeMap<>();

    private static final String KEY = "c82639-e706948";
    private static final String SECRET = "565d8d-133ae7-deca80-103da1-17f43225";
    private static final String URI_FOR_CALL = ("https://api.binotel.com/api/2.0/calls/ext-to-phone.json");

    private static final Logger LOG = LoggerFactory.getLogger(BinotelServiceImpl.class);

    static {
        try {
            md5Encoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeCall(String extNumber, String phoneNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String signature = createSignature(extNumber, phoneNumber);
        BinotelPostModel model = new BinotelPostModel(KEY, signature, extNumber, phoneNumber);
        HttpEntity<BinotelPostModel> entity = new HttpEntity<>(model, headers);
        Object response = restTemplate.postForObject(URI_FOR_CALL, entity, Object.class);
        LOG.debug("Attempt to make call via Binotel.", response);
    }

    private String createSignature(String extNumber, String phoneNumber) {
        try {
            params.put("ext_number", extNumber);
            params.put("phone_number", phoneNumber);
            String toEncode = SECRET + jsonMapper.writeValueAsString(params);
            params.clear();
            md5Encoder.update(toEncode.getBytes(), 0, toEncode.length());
            return new BigInteger(1, md5Encoder.digest()).toString(16);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during converting params to json", e);
        }
    }

    public static void main(String[] args) {
        new BinotelServiceImpl().makeCall("901", "0954865473");
    }
}
