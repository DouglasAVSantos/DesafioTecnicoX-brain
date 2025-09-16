package com.desafio.XBrain.vendedor.repository;

import com.desafio.XBrain.vendedor.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor,Long> {

    Optional<Vendedor>findByIdAndAtivoTrue(Long id);
    Optional<Vendedor>findByNomeAndSobrenomeAndAtivoTrue(String nome, String sobrenome);
    Optional<Vendedor>findAllByAtivoTrue();
}
