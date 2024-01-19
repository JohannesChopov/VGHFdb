package be.kuleuven.dbproject.controller.Add;

public interface AddItemController<T> {
    void initialize();
    T getNewItem();
    boolean isSubmitted();
}
