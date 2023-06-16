package space.paraskun.postman.session;

public abstract class AbstractState {
    protected Session session;

    public abstract void init();
    public abstract AbstractState action(Object... data);

    public void setSession(Session session) {
        this.session = session;
    }
}
