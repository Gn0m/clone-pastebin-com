package com.example.clonepastebincom.dao;

import com.example.clonepastebincom.enums.Access;
import com.example.clonepastebincom.model.Pasta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PastaRepo extends JpaRepository<Pasta, Long> {

    Pasta findPastaByHashAndAccess(String hash, Access access);

    Pasta findPastaByHash(String hash);

    List<Pasta> findPastasByExpirationAfter(LocalDateTime date, Pageable pageable);

}
