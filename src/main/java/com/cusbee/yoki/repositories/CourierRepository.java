package com.cusbee.yoki.repositories;

import com.cusbee.yoki.entity.CourierDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository {
    @Query(value="SELECT cd.* FROM account a, client_details cd WHERE a.username = ?1", nativeQuery=true)
    CourierDetails getCourierDetailsByUsername(String username);
}
