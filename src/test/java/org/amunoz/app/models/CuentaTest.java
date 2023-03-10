package org.amunoz.app.models;

import org.amunoz.app.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    // shift + F10  ejecuta toda la clase

    Cuenta cuenta;

    //se inicializa antes de cada metodo
    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        System.out.println("Iniciando metodo.");
    }

    // se ejecuta después de cada metodo
    @AfterEach
    void tearDown() {
        System.out.println("finalizando el metodo!");
    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("inicializando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("finalizando el test!");
    }

    @Test
    @DisplayName("probando nombre de la cuenta!")
    void testNombreCuenta() {
        //cuenta.setPersona("Andres");
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertNotNull(real, () -> "la cuenta NO puede ser null"); //verifica que real NO sea null
        assertEquals(esperado, real, () -> "el nombre de la cuenta NO es el esperado!");
        assertTrue(real.equals("Andres"), () -> "nombre cuenta esperada debe ser igual a  la real");
    }

    @Test
    @DisplayName("probando saldo de la cuenta!")
    void testSaldoCuenta() {
        cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo()); //verifica que el saldo NO sea null
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //prueba si el saldo es mayor de cero, "el resultado debe ser falso"
    }

    @Test
    @DisplayName("probando referencia o instancias de la cuenta, que sean iguales con el metodo Equals!")
    void testReferenciaCuenta() {
        cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta2,cuenta);  // valida que NO sean objetos iguales
        assertEquals(cuenta2, cuenta); // valida que sean objetos iguales

    }

    @Test
    @DisplayName("probando metodo debito de la cuenta!")
    void testDebitoCuenta() {
        this.cuenta.debito(new BigDecimal(100)); // implementamos el método débito y le pasamos como parámetro un 100
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea null
        assertEquals(900, cuenta.getSaldo().intValue()); // compara el valor esperado con el real pero convirtiendo el real a un entero
        assertEquals("900.12345", cuenta.getSaldo().toPlainString()); // compara el valor esperado con el real
    }

    @Test
    @DisplayName("probando metodo credito de la cuenta!")
    void testCreditoCuenta() {
        this.cuenta.credito(new BigDecimal(100)); // implementamos el método crédito y le pasamos como parámetro un 100
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea null
        assertEquals(1100, cuenta.getSaldo().intValue()); // compara el valor esperado con el real pero convirtiendo el real a un entero
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); // compara el valor esperado con el real
    }

    @Test
    @DisplayName("probando cunado hay dinero insuficiente en la cuenta!")
    void testDineroInsufivienteExceptionCuenta() {
        // bloque que puede arrojar una excepcion
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String real = exception.getMessage(); // obtenemos el mensaje de la excepción
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, real);// comparamos los mensajes de la excepción, deben ser iguales.
    }

    @Test
    @DisplayName("probando metodo transferir del banco!")
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("john Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    //@Disabled
    @DisplayName("probando varios assertEquals  con el metodo assertAll()!")
    void testRelacionBancoCuentas() {

        //fail(); // obliga a que el metodo falle

        Cuenta cuenta1 = new Cuenta("john Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(() ->
                {
                    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "El valor del saldo NO es el esperado para la cuenta2!.");
                },
                () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString(), () -> "El valor del saldo NO es el esperado para la cuenta1!.");
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size(), () -> "El numnero de cuentas NO es el esperado!."); // el banco debería tener dos cuentas asignadas
                },
                () -> {
                    assertEquals("Banco del Estado", cuenta1.getBanco().getNombre(), () -> "El nombre del banco NO es el esperado!."); // compara que el nombre del banco sea el esperado
                },
                () -> {
                    assertEquals("Andres", banco.getCuentas().stream()// obtenemos la lista cuentas
                            .filter(c -> c.getPersona().equals("Andres")) // con filter revisamos que cuenta dentro de la lista tiene como persona a Andres
                            .findFirst() //obtenemos el primero que encuentre
                            .get() // obtenemos la cuenta
                            .getPersona(), () -> "No se encuentra la persona "); // obtenemos la persona asignada a la cuenta
                },
                () -> {
                    // hace lo mismo que el de arriba
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Andres")), () -> "No se encuentra la persona");
                }
        );


    }

}