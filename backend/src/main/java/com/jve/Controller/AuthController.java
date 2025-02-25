package com.jve.Controller;

import com.jve.dto.LoginRequest;
import com.jve.dto.LoginResponse;
import com.jve.dto.UserDTO;
import com.jve.Entity.Especialidad;
import com.jve.Entity.User;
import com.jve.Repository.EspecialidadRepository;
import com.jve.Security.JwtTokenProvider;
import com.jve.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EspecialidadRepository especialidadRepository;



    @PostMapping("/auth/register")
    public UserDTO save(@RequestBody UserDTO userDTO){
        Especialidad especialidad = especialidadRepository.findById(userDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        return this.userService.createUser(userDTO, especialidad);
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO) {
        try {
            Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());

            Authentication authentication = this.authManager.authenticate(authDTO);
            User user = (User) authentication.getPrincipal();

            String token = this.jwtTokenProvider.generateToken(authentication);

            Long especialidadId = (user.getEspecialidad() != null) ? user.getEspecialidad().getIdEspecialidad() : null;
            String especialidadNombre = (user.getEspecialidad() != null) ? user.getEspecialidad().getNombre() : null;

            return new LoginResponse(user.getUsername(),
                    user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                    token,
                    especialidadId,
                    especialidadNombre);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales incorrectas", e);
        }
    }

}
