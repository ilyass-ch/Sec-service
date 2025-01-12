package com.example.demo.sec.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.JwtUtils;
import com.example.demo.sec.entities.AppRole;
import com.example.demo.sec.entities.AppUser;
import com.example.demo.sec.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {


    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/users")
    public AppUser saveUser (@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }

    @PostMapping(path = "/roles")
    public AppRole saveRole (@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser (@RequestBody RoleUserForm roleUserForm){
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }

    @GetMapping(path = "/profile")
    //@PostAuthorize("hasAuthority('CUSTOMER_MANAGER')")
    public AppUser profile(Principal pr){

        return accountService.loadUserByUsername(pr.getName());
    }


    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authToken = request.getHeader("Authorization");

        if(authToken != null && authToken.startsWith("Bearer ") )
        {
            try {
                String jwt =authToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = accountService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtils.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles" , appUser.getAppRole().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken = new HashMap<>();
                idToken.put("access-Token",jwtAccessToken);
                idToken.put("refresh-Token",jwt);
                response.setContentType("application/Json");

                new ObjectMapper().writeValue(response.getOutputStream(),idToken);

                response.setHeader("Authorization",jwtAccessToken);


            }
            catch (Exception e){

                throw e;

            }

        }
        else
        {
            throw new RuntimeException("Refresh token required");
        }

    }
}

@Data
class RoleUserForm{
    private String username;
    private String roleName;
}