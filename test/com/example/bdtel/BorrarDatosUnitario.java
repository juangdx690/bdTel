package com.example.bdtel;

import com.example.bdtel.DAO.MovilesDAO;
import com.example.bdtel.Modelo.Moviles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BorrarDatosUnitario {

    static MovilesDAO mDao = new MovilesDAO();
    ;
    static Moviles m = new Moviles(1, "Samsung-Juan", "Samsung", 0, 2, "android el ejido", "el ejido", 3, 4, 5, null);
    ;

    @Test
    void borrarDato() {

        assertEquals(1, mDao.borrarMovilTest(m));

    }

}