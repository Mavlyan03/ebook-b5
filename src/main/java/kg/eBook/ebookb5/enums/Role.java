package kg.eBook.ebookb5.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_USER,
    ROLE_VENDOR;

    @Override
    public String getAuthority() {
        return null;
    }
}
