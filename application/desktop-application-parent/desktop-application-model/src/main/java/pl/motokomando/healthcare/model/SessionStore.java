package pl.motokomando.healthcare.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pl.motokomando.healthcare.model.utils.Token;
import pl.motokomando.healthcare.model.utils.UserInfo;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor(access = PRIVATE)
public class SessionStore {

    private static final SessionStore INSTANCE = new SessionStore();

    @Nullable
    private Token token;
    @Nullable
    private UserInfo userInfo;

    public static SessionStore getInstance() {
        return INSTANCE;
    }

}
