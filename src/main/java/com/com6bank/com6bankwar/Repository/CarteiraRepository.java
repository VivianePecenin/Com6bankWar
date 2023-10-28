package com.com6bank.com6bankwar.Repository;


import com.com6bank.com6bankwar.Models.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
