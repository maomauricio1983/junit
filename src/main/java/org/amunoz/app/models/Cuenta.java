package org.amunoz.app.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona= persona;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == null || o instanceof Cuenta) return true;
        Cuenta c = (Cuenta) o;
        return Objects.equals(persona, c.persona) && Objects.equals(saldo, c.saldo);
    }


}
