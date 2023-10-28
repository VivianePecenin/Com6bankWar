package com.com6bank.com6bankwar.Repository;

import com.com6bank.com6bankwar.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "Select * from usuario where email = :email", nativeQuery = true)
    Usuario findByEmail(String email);

    @Query(value = "Select * from usuario where cpf = :cpf", nativeQuery = true)
    Usuario findByCpf(String cpf);
    @Query(value = "Select * from usuario where username = :username", nativeQuery = true)
    Usuario findByUsername(String username);
}
