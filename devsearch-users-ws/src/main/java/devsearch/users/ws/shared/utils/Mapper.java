package devsearch.users.ws.shared.utils;

public interface Mapper {

    public <D> D map(Object source, Class<D> destinationType);
}
