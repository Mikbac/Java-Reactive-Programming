package pl.mikbac.examples.example100.employeeCRUD.exception;

/**
 * Created by MikBac on 6.01.2026
 */
public class EmployeeNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Employee not found [id=%d]";

    public EmployeeNotFoundException(Integer id) {
        super(EXCEPTION_MESSAGE.formatted(id));
    }

}
