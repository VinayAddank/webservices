package org.rta.registration.controller;

import org.apache.log4j.Logger;
import org.rta.core.model.TokenModel;
import org.rta.core.model.user.LoginModel;
import org.rta.security.model.CustomUserDetail;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);
    
    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "login", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TokenModel> createAuthenticationToken(@RequestBody LoginModel loginModel)
            throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final CustomUserDetail userDetails =
                (CustomUserDetail) userDetailsService.loadUserByUsername(loginModel.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        String tokenUserName = jwtTokenUtil.getUsernameFromToken(token);
        Long tokenUserId = jwtTokenUtil.getUserIdFromToken(token);
        log.debug(loginModel.getUsername()+"::"+userDetails.getId()+"::: JWT TOKEN AFTER LOGIN CHECK:::"+tokenUserName+"::"+tokenUserId);
        String uid = RtaCryptoUtil.generateSecureToken(String.valueOf(userDetails.getId()), claimSecret);

        return ResponseEntity.ok(new TokenModel(token, uid, userDetails.getUserRoles()));
    }



    /*@RequestMapping(value = "refresh", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TokenModel> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
    
        // String username = jwtTokenUtil.getUsernameFromToken(token);
        // CustomUserDetail userDetails = (CustomUserDetail)
        // userDetailsService.loadUserByUsername(username);
        // Get user last password reset date from userDetails.
        // It should be older then token creation date.
        // If we provide /refresh service. We need to use above code.
        if (!jwtTokenUtil.isTokenExpired(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new TokenModel(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }*/
}
