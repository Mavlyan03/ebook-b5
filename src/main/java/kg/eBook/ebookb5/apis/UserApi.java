package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.UserRequest;
import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.UserResponse;
import kg.eBook.ebookb5.dto.responses.userMainPage.MainPageResponse;
import kg.eBook.ebookb5.exceptions.WrongEmailException;
import kg.eBook.ebookb5.db.services.BookService;
import kg.eBook.ebookb5.db.services.PurchasedUserBooksService;
import kg.eBook.ebookb5.db.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User API", description = "User endpoints")
public class UserApi {

    private final UserService userService;
    private final PurchasedUserBooksService userBooksService;
    private final BookService bookService;

    @Operation(summary = "Update profile", description = "Update user profile")
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public SimpleResponse updateByUser(Authentication authentication,
                                       @RequestBody @Valid UserRequest userRequest,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongEmailException("Неправильный адрес электронной почты");
        }
        return userService.updateByUser(authentication, userRequest);
    }

    @Operation(summary = "Get user by id", description = "Get user with id by user&admin")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("{userId}")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @Operation(summary = "Delete user with id", description = "Delete user with id by user&admin")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("{userId}")
    public SimpleResponse deleteByUser(@PathVariable Long userId) {
        return userService.deleteByUserId(userId);
    }

    @Operation(summary = "Get user purchased history", description = "Get all purchased user books with user id")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{userId}/history")
    public Page<PurchasedUserBooksResponse> operationsHistory(@PathVariable Long userId,
                                                              @RequestParam(required = false) int page,
                                                              @RequestParam(required = false) int size) {
        return userBooksService.purchasedUserBooks(userId, page, size);
    }


    @Operation(summary = "Get all books", description = "Get all books in the main page")
    @GetMapping("/main-page")
    public MainPageResponse mainPageResponse() {
        return bookService.mainPageResponse();
    }

}

