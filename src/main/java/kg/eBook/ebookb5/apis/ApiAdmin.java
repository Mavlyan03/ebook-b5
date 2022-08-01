package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.UserResponse;
import kg.eBook.ebookb5.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiAdmin {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "find all users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "find by user with id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findById(userId);
    }
}
