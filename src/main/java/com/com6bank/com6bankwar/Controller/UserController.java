package com.com6bank.com6bankwar.Controller;


import com.com6bank.com6bankwar.Models.Carteira;
import com.com6bank.com6bankwar.Models.Usuario;
import com.com6bank.com6bankwar.Repository.CarteiraRepository;
import com.com6bank.com6bankwar.Repository.UserRepository;
import com.com6bank.com6bankwar.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @GetMapping("/usuario")
    public List<Usuario> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable(value = "id") long userId) throws ResourceNotFoundException {

        Usuario user = repository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Usuaio não encontrado: " + userId));
        return ResponseEntity.ok().body(user);

    }

    @GetMapping("/usuario/email/{email}")
    public ResponseEntity<Usuario> getUserByEmail(@PathVariable(value = "email") String userEmail) {

        Usuario user = repository.findByEmail(userEmail);
        return ResponseEntity.ok().body(user);

    }

    @PostMapping("/usuarios/criar")
    public Usuario createUser(@Validated @RequestBody Usuario user){
        Carteira carteira = new Carteira();
        carteira.setSaldo(0.00);
        carteiraRepository.save(carteira);

        user.setSenhaFromCpf(user.getCpf());
        user.setCarteira(carteira);
        return repository.save(user);
    }

    @PutMapping("/usuarios/modificar/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable(value = "id") Long userId,
                                           @Validated @RequestBody Usuario detales) throws  ResourceNotFoundException{
        Usuario user = repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado: " + userId));
        user.setEmail(detales.getEmail());
        user.setCpf(detales.getCpf());
        user.setUsername(detales.getUsername());
        user.setSenhaFromCpf(detales.getCpf());

        final Usuario updateuser = repository.save(user);
        return ResponseEntity.ok(updateuser);


    }

    @DeleteMapping("/usuario/deletar/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception{
        Usuario user = repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado: " + userId));
        repository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
