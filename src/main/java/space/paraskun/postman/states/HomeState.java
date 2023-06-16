package space.paraskun.postman.states;

import space.paraskun.postman.session.AbstractState;

public class HomeState extends AbstractState {
    @Override
    public void init() {
        System.out.println("Home state init.");
    }

    @Override
    public AbstractState action(Object... data) {
        System.out.println("Home state action.");
        return this;
    }
}
