package com.project.se.repository;

import com.project.se.domain.Estate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends PagingAndSortingRepository<Estate, Integer> {

    @Query(value = "SELECT realestate_type FROM REAL_ESTATE " +
            "group by realestate_type " +
            "having realestate_type != '' and count(realestate_type) > 700 ", nativeQuery = true)
    @Cacheable("estate_type")
    public List<String> realEstateTypeList();

    @Query(value = "SELECT addr_district FROM REAL_ESTATE " +
            "group by addr_city, addr_district " +
            "HAVING addr_city = 'Hồ Chí Minh' and addr_district != '' and count(addr_district) > 1000"
            , nativeQuery = true)
    @Cacheable("HCM_district")
    public List<String> districtHCMList();

    @Query(value = "SELECT addr_district FROM REAL_ESTATE " +
            "group by addr_city, addr_district " +
            "HAVING addr_city = 'Hà Nội' and addr_district != '' and count(addr_district) > 1000"
            , nativeQuery = true)
    @Cacheable("HN_district")
    public List<String> districtHNList();

    @Query(value = "SELECT price, price_unit, date, area FROM REAL_ESTATE " +
            "WHERE addr_city = :addr_city and addr_district = :addr_district " +
            "and realestate_type = :realestate_type and transaction_type = :transaction_type ", nativeQuery = true)
    public List<Object> priceDict(@Param("addr_city") String city,
                                 @Param("addr_district") String dist,
                                 @Param("realestate_type") String realestate,
                                 @Param("transaction_type") String transaction
    );

    @Query(value = "SELECT * FROM REAL_ESTATE " +
            "WHERE addr_city like CONCAT('%',:city,'%') and addr_district like CONCAT('%',:district,'%') " +
            "and realestate_type like CONCAT('%',:estate_type,'%') " +
            "and price between :min and :max " +
            "LIMIT 20", nativeQuery = true)
    List<Estate> search(@Param("district") String district,
                        @Param("city") String city,
                        @Param("estate_type") String estate_type,
                        @Param("min") String minPrice,
                        @Param("max") String maxPrice);
}


