package com.cusbee.yoki.repositories;

import com.cusbee.yoki.entity.CourierDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<CourierDetails, Long> {

    @Query(value="SELECT cd.* FROM account a, courier_details cd WHERE a.username = ?1", nativeQuery=true)
    CourierDetails getCourierDetailsByUsername(String username);
}
