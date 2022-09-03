package com.example.MyBookShopApp.controllers.userController;


import com.example.MyBookShopApp.data.user.Status;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.AuthenticationRequestDto;
import com.example.MyBookShopApp.dto.RegistrationRequestDto;
import com.example.MyBookShopApp.dto.ResponseVerificationDto;
import com.example.MyBookShopApp.exception.VerificationException;
import com.example.MyBookShopApp.security.jwt.JwtTokenProvider;
import com.example.MyBookShopApp.service.senders.MailSender;
import com.example.MyBookShopApp.service.senders.ValidationService;
import com.example.MyBookShopApp.service.userServices.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@Api(description = "Контроллер авторизации в системе")
@ComponentScan("com.example.MyBookShopApp")
public class AuthenticationRestControllerV1 {

//    private final AuthenticationManager authenticationManager;
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    private final UserService userService;
//
//    private final MailSender mailSender;
//
//    private final ValidationService validationService;
//
//    @Autowired
//    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, MailSender mailSender, ValidationService validationService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.userService = userService;
//        this.mailSender = mailSender;
//        this.validationService = validationService;
//    }

//    @PostMapping("login")
//    @Operation(
//            summary = "POST запрос на выдачу токена авторизации",
//            description = "Позволяет авторизоваться в системе. В body передается AuthenticationRequestDto, подробнее об body, которое нужно передать: читать в Models документации"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Пароль и логин верные, выдача токена", response = ApiResponse.class,
//                    examples = @Example(value =
//                            {
//                                    @ExampleProperty(mediaType = "application/json", value = "{\n   \"username\": \"user_user\",\n" +
//                                            "   \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyX3VzZXIiLCJyb2xlcyI6W10sImlhdCI6MTY0MDYzMjAyMywiZXhwIjoxNjQwNjM1NjIzfQ.PYRxatMgvkvsV4Kgru6lsf4KNVci5UOy1-NjMWQoQ-0\"\n}")
//                            }
//                    )),
//            @ApiResponse(code = 400, message = "В случае, если один из параметров не прошел валидацию", response = ApiResponse.class,
//                    examples = @Example(value =
//                            {
//                                    @ExampleProperty(mediaType = "application/json", value = "{\n" +
//                                            "    \"state: \": \"400\",\n" +
//                                            "    \"message\": \"Invalid username or password\"\n" +
//                                            "}")
//                            }
//                    )),
//            @ApiResponse(code = 401, message = "Не возвращается, так как этот метод разрешен для всех пользователей"),
//            @ApiResponse(code = 403, message = "Не возвращается, так как этот метод разрешен для всех пользователей"),
//            @ApiResponse(code = 404, message = "В случае, если в базе нету передаваемого в теле логина"
//                    , response = ApiResponse.class, examples = @Example(value =
//                    {
//                            @ExampleProperty(
//                                    mediaType = "application/json",
//                                    value = "{\n" +
//                                            "    \"state: \": \"404\",\n" +
//                                            "    \"message\": \"User with username: null not found\"\n" +
//                                            "}")
//                    })
//            )
//    })
//    @Async
//    public CompletableFuture<ResponseEntity<Map<Object, Object>>> login(@RequestBody @Valid AuthenticationRequestDto requestDto
//                                                                                           ,BindingResult bindingResult) {
//        String message = "Invalid username or password";
//        try {
//            String errors = validationService.validate(bindingResult);
//            if(!errors.equals(""))
//                throw new BadCredentialsException((message = errors));
//            Map<Object, Object> response = new LinkedHashMap<>();
//            String username = requestDto.getUsername();
//            User user = userService.findByUsername(username);
//            if (user == null){
//                user = userService.register(new User(
//                        username,
//                        null,
//                        null,
//                        requestDto.getEmail(),
//                        requestDto.getPassword()));
//                response.put("username", username);
//                response.put("registration", "Successful, please check your email");
//                mailSender.sendMessage(user);
//            }
//            else {
//                if(user.getStatus().equals(Status.NOT_ACTIVE)){
//                    response.put("username", username);
//                    response.put("registration", "Successful, please check your email.");
//                    userService.generateCode(user);
//                    mailSender.sendMessage(user);
//                    return CompletableFuture.completedFuture(ResponseEntity.ok(response));
//                }
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
//                String token = jwtTokenProvider.createToken(username, user.getRoles());
//                response.put("username", username);
//                response.put("token", token);
//            }
//            return CompletableFuture.completedFuture(ResponseEntity.ok(response));
//        } catch (UsernameNotFoundException e1) {
//            throw new UsernameNotFoundException("User with username: " + requestDto.getUsername() + " not found");
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException(message);
//        }
//    }

//    @PostMapping("/reg")
//    @Async
//    public CompletableFuture<ResponseEntity<Map<Object, Object>>> registration(@RequestBody @Valid RegistrationRequestDto requestDto
//                                                                               , BindingResult bindingResult) {
//        Map<Object, Object> response = new LinkedHashMap<>();
//        String username = requestDto.getUsername();
//        User user = userService.findByUsername(username);
//        String valid = validationService.validate(bindingResult);
//        if (!valid.equals(""))
//            throw new BadCredentialsException(valid);
//        if (user == null) {
//            //TODO: додлелать!!!!!
////            user = userService.register(new User(
////                    username,
////                    requestDto.getEmail());
//            response.put("username", username);
//            response.put("registration", "Successful, please check your email.");
//            mailSender.sendMessage(user);
//             return CompletableFuture.completedFuture(ResponseEntity.ok(response));
//        }
//        if(user.getStatus().equals(Status.NOT_ACTIVE)){
//            response.put("username", username);
//            response.put("registration", "Successful, please check your email.");
//            mailSender.sendMessage(user);
//            return CompletableFuture.completedFuture(ResponseEntity.ok(response));
//        }
//        throw new BadCredentialsException("User with name: " + username + "all ready exists");
//    }

//    @PostMapping("/logout")
//    @Async
//    public CompletableFuture<ResponseEntity<Map<Object, Object>>> logout(HttpServletRequest request) {
//        Map<Object, Object> responseDto = new LinkedHashMap<>();
//        String bearerToken = request.getHeader("Authorization");
//        responseDto.put("token", bearerToken);
//        if (request.getHeader("Authorization") == null) {
//            responseDto.put("block", "false");
//            return CompletableFuture.completedFuture(ResponseEntity.ok(responseDto));
//        }
//        if (userService.logoutToken(bearerToken) != null) {
//            responseDto.put("block", "true");
//            return CompletableFuture.completedFuture(ResponseEntity.ok(responseDto));
//        }
//        throw new BadCredentialsException("Uncorrectly Token");
//    }
//
//    @GetMapping("verification/{username}/{code}")
//    public CompletableFuture<ResponseEntity<ResponseVerificationDto>> verification(
//            @PathVariable("username") String username,
//            @PathVariable("code") String code)
//            throws VerificationException {
//        ResponseVerificationDto response = userService.verification(username, code);
//        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
//    }
}
