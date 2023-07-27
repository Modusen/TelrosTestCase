package com.vaadin.TelrosTestCase.repository;

import com.vaadin.TelrosTestCase.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Integer> {
}
