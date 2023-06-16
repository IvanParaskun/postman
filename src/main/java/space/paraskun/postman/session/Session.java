package space.paraskun.postman.session;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import space.paraskun.postman.model.Account;
import space.paraskun.postman.states.HomeState;

@RedisHash("sessions")
@AllArgsConstructor
public class Session {
    @Id private final Long telegramId;
    @Indexed @Reference private final Account account;
    private final StateMachine stateMachine;

    public Session(long telegramId, Account account) {
        this.telegramId = telegramId;
        this.account = account;
        this.stateMachine = new StateMachine(new HomeState());
    }

    public void action(Object... data) {
        stateMachine.action(data);
    }

    public void onShutdown() {
        System.out.println("Shutting down session for user: " + telegramId);
    }

    private class StateMachine {
        private AbstractState state;

        public StateMachine(AbstractState initState) {
            state = initState;
            state.setSession(Session.this);
            state.init();
        }

        private void action(Object... data) {
            state = state.action(data);
            state.setSession(Session.this);
            state.init();
        }
    }
}
