package com.henrique.nfe.repositories;

import java.util.List;

import com.henrique.nfe.entities.NotaFiscal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotasFiscaisRepository extends MongoRepository<NotaFiscal, String> {

    @Query("{$or: [{'prestador._id': ?0}, {'tomador._id': ?0}]}")
    public List<NotaFiscal> findByPrestadorIdOrTomadorId(String id);

}
