package be.kuleuven.dbproject.controller;

public interface AddItemController<T> {
    void initialize();
    T getNewItem();
    boolean isSubmitted();
}
