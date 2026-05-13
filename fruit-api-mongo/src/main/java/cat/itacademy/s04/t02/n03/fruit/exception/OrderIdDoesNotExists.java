package cat.itacademy.s04.t02.n03.fruit.exception;

public class OrderIdDoesNotExists extends RuntimeException{
    public OrderIdDoesNotExists()
    {
        super("La ordre amb aquest Id no existeix");
    }
}
