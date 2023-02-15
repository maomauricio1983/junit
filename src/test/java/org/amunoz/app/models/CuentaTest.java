package org.amunoz.app.models;

import org.amunoz.app.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {


    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Mauricio", new BigDecimal("1000.12345"));
//        cuenta.setPersona("Mauricio");
        String esperado = "Mauricio";
        String real = cuenta.getPersona();
        assertNotNull(real); //verifica que real NO sea null
        assertEquals(esperado, real);
        assertTrue(real.equals("Mauricio"));
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo()); //verifica que el saldo NO sea null
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //prueba si el saldo es mayor de cero, "el resultado debe ser falso"
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta2,cuenta);  // valida que NO sean objetos iguales
        assertEquals(cuenta2, cuenta); // valida que sean objetos iguales

    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345")); // asignamos los valores al objeto cuenta
        cuenta.debito(new BigDecimal(100)); // implementamos el método débito y le pasamos como parámetro un 100
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea null
        assertEquals(900, cuenta.getSaldo().intValue()); // compara el valor esperado con el real pero convirtiendo el real a un entero
        assertEquals("900.12345", cuenta.getSaldo().toPlainString()); // compara el valor esperado con el real
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345")); // asignamos los valores al objeto cuenta
        cuenta.credito(new BigDecimal(100)); // implementamos el método crédito y le pasamos como parámetro un 100
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea null
        assertEquals(1100, cuenta.getSaldo().intValue()); // compara el valor esperado con el real pero convirtiendo el real a un entero
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); // compara el valor esperado con el real
    }

    @Test
    void testDineroInsufivienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345")); // pasamos los parámetros al objeto cuenta
        // bloque que puede arrojar una excepcion
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String real = exception.getMessage(); // obtenemos el mensaje de la excepción
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, real);// comparamos los mensajes de la excepción, deben ser iguales.
    }

    @Test
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
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("john Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

        assertEquals(2, banco.getCuentas().size()); // el banco debería tener dos cuentas asignadas
        assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()); // compara que el nombre del banco sea el esperado

        assertEquals("Andres", banco.getCuentas().stream()// obtenemos la lista cuentas
                .filter(c -> c.getPersona().equals("Andres")) // con filter revisamos que cuenta dentro de la lista tiene como persona a Andres
                .findFirst() //obtenemos el primero que encuentre
                .get() // obtenemos la cuenta
                .getPersona()); // obtenemos la persona asignada a la cuenta

        // hace lo mismo que el de arriba
        assertTrue(banco.getCuentas().stream()
                .anyMatch(c -> c.getPersona().equals("Andres")));
    }
}