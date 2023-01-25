package org.amunoz.app.models;

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
        cuenta.debito(new BigDecimal(100)); // implementamos el metodo debito y le pasamos como parametro un 100
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea null
        assertEquals(900, cuenta.getSaldo().intValue()); // compara el valor esperado con el real pero conviertiendo el real a un entero
        assertEquals("900.12345", cuenta.getSaldo().toPlainString()); // compara el valor esperado con el real
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345")); // asignamos los valores al objeto cuenta
        cuenta.credito(new BigDecimal(100)); // implementamos el metodo credito y le pasamos como parametro un 100
        assertNotNull(cuenta.getSaldo()); // verifica que el saldo no sea null
        assertEquals(1100, cuenta.getSaldo().intValue()); // compara el valor esperado con el real pero conviertiendo el real a un entero
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); // compara el valor esperado con el real
    }


}