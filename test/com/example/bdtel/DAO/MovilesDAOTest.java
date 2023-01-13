package com.example.bdtel.DAO;

import com.example.bdtel.Modelo.Moviles;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

class MovilesDAOTest {

    static MovilesDAO mDao = new MovilesDAO();
    ;
    static Moviles m = new Moviles(1, "Samsung-Juan", "Samsung", 0, 2, "android el ejido", "el ejido", 3, 4, 5, null);
    ;




    @Test
    void borrarDato() {

        assertEquals(1, mDao.borrarMovilTest(m));

    }

    @Test
    void insertarDato() {

        assertEquals(1, mDao.anadirMovilTest(m));
        assertEquals(1, mDao.obtenerMovilesconMarcaTest(m));

    }

}