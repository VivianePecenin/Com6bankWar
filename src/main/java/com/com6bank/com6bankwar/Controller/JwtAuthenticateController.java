package com.com6bank.com6bankwar.Controller;

import com.com6bank.com6bankwar.Repository.CarteiraRepository;
import com.com6bank.com6bankwar.Config.JwtTokenUtil;
import com.com6bank.com6bankwar.Models.Carteira;
import com.com6bank.com6bankwar.Models.JwtRequest;
import com.com6bank.com6bankwar.Models.JwtResponse;
import com.com6bank.com6bankwar.Models.Usuario;
import com.com6bank.com6bankwar.Service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private CarteiraRepository carteiraRepository;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("Usuario desabilitado", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciais invalidas", e);
        } catch (Exception e) {
            throw new Exception("Erro ao autenticar", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> SaveUser(@RequestBody Usuario user) throws Exception {

        Carteira carteira = new Carteira();
        carteira.setSaldo(0.00);
        carteiraRepository.save(carteira);

        user.setCarteira(carteira);

        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
