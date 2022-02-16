package com.henrique.nfe.repositories;

import com.henrique.nfe.entities.Empresa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresasRepository extends MongoRepository<Empresa, String> {

    public Empresa findByCnpj(String cnpj);

}
