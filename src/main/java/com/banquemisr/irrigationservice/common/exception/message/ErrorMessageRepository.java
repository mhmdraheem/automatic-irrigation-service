package com.banquemisr.irrigationservice.common.exception.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, String> {
}
