package pl.motokomando.healthcare.model.utils;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
