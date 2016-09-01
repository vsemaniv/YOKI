package com.cusbee.yoki.model.poster;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created on 11.08.2016.
 */

public class PosterWriteOffModel {

    @JsonProperty("write_off")
    private WriteOff writeOff;

    @JsonProperty("ingredient")
    private List<WriteOffDish> posterDishes;

    public PosterWriteOffModel(Integer storageId, List<WriteOffDish> posterDishes) {
        writeOff = new WriteOff(storageId);
        this.posterDishes = posterDishes;
    }

    private class WriteOff implements Serializable {
        private static final long serialVersionUID = 1L;

        @JsonProperty("date")
        private String date;

        @JsonProperty("storage_id")
        private Integer storageId;

        public WriteOff(Integer storageId) {
            this.storageId = storageId;
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getStorageId() {
            return storageId;
        }

        public void setStorageId(Integer storageId) {
            this.storageId = storageId;
        }
    }
}
