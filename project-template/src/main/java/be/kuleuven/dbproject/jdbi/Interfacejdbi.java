package be.kuleuven.dbproject.jdbi;

public interface Interfacejdbi<T> {
    void delete(T item);
    void insert(T item);
}
