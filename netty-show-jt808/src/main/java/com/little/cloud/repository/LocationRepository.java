package com.little.cloud.repository;


import com.little.cloud.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Zpsw
 * @Date: 2019-05-15
 * @Description:
 * @Version: 1.0
 */
public interface LocationRepository extends JpaRepository<LocationEntity,Long> {
}
