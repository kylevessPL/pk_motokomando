package pl.motokomando.healthcare.model.authentication;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus;

import static lombok.AccessLevel.NONE;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.NOT_AUTHENTICATED;

@Getter
@Setter
public final class AuthenticationModel {

    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObjectProperty<AuthenticationStatus> authenticationStatus = new SimpleObjectProperty<>(NOT_AUTHENTICATED);

    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus.get();
    }

    public void setAuthenticationStatus(AuthenticationStatus status) {
        authenticationStatus.set(status);
    }

}
