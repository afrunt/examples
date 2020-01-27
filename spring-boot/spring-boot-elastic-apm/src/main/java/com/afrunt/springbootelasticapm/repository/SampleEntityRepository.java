package com.afrunt.springbootelasticapm.repository;

import com.afrunt.springbootelasticapm.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Andrii Frunt
 */
public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {
}
