package com.com6bank.com6bankwar.Repository;

import com.com6bank.com6bankwar.Models.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamentos, Long> {
}
