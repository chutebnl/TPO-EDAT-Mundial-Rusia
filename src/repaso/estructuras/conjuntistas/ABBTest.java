package repaso.estructuras.conjuntistas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ABBTest {
    static ABB<Integer> abb, abbe;

    @BeforeAll
    static void start() {
        abb = new ABB<>();
        abbe = new ABB<>();
    }

    @BeforeEach
    void setUp() {
        abb.vaciar();
        abb.insertar(45);
        abb.insertar(34);
        abb.insertar(13);
        abb.insertar(65);
        abb.insertar(55);
        abb.insertar(73);
        abb.insertar(96);
    }

    @AfterEach
    void tearDown() {
        abbe.vaciar();
    }

    @Test
    void insertar() {
        abb.vaciar();
        assertFalse(abb.insertar(null));
        assertTrue(abb.insertar(45));
        assertFalse(abb.insertar(45));
        assertTrue(abb.insertar(34));
        assertFalse(abb.insertar(34));
        assertTrue(abb.insertar(13));
        assertTrue(abb.insertar(65));
        assertFalse(abb.insertar(65));
        assertFalse(abb.insertar(null));
        assertTrue(abb.insertar(55));
        assertTrue(abb.insertar(73));
        assertTrue(abb.insertar(96));
        String esperado = "repaso.estructuras.conjuntistas.ABB{ raiz=45\n" +
                "45 -> 34; 65\n" +
                "34 -> 13; null\n" +
                "13 -> null; null\n" +
                "65 -> 55; 73\n" +
                "55 -> null; null\n" +
                "73 -> null; 96\n" +
                "96 -> null; null\n" +
                "}";
        assertEquals(esperado, abb.toString());
    }

    @Test
    void eliminar() {
        ABB<Integer> abbe = new ABB<>();
        ABB<Integer> abbEsperado = new ABB<>();

        assertFalse(abbe.eliminar(null));
        assertFalse(abbe.eliminar(-1));

        assertTrue(abbe.insertar(10));

        assertFalse(abbe.eliminar(-1));

        assertTrue(abbe.eliminar(10));
        assertEquals(abbEsperado, abbe);

        assertTrue(abbe.insertar(10));
        assertTrue(abbe.insertar(20));
        assertTrue(abbe.eliminar(20));
        assertTrue(abbEsperado.insertar(10));

        assertEquals(abbEsperado, abbe);
    }

    @Test
    void eliminar0() {
        abb.vaciar();
        assertTrue(abb.insertar(45));
        assertTrue(abb.eliminar(45));
        assertEquals(abbe, abb);
    }

    @Test
    void eliminar1() {
        assertTrue(abb.eliminar(45));

        assertTrue(abbe.insertar(55));
        assertTrue(abbe.insertar(34));
        assertTrue(abbe.insertar(13));
        assertTrue(abbe.insertar(65));
        assertTrue(abbe.insertar(73));
        assertTrue(abbe.insertar(96));

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar2() {
        assertTrue(abb.eliminar(45));
        assertTrue(abb.eliminar(65));

        assertTrue(abbe.insertar(55));
        assertTrue(abbe.insertar(34));
        assertTrue(abbe.insertar(13));
        assertTrue(abbe.insertar(73));
        assertTrue(abbe.insertar(96));

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar3() {
        assertTrue(abb.eliminar(73));

        assertTrue(abbe.insertar(45));
        assertTrue(abbe.insertar(34));
        assertTrue(abbe.insertar(65));
        assertTrue(abbe.insertar(13));
        assertTrue(abbe.insertar(55));
        //assertTrue(abbe.insertar(73));
        assertTrue(abbe.insertar(96));

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar4() {
        assertTrue(abb.eliminar(96));

        assertTrue(abbe.insertar(45));
        assertTrue(abbe.insertar(34));
        assertTrue(abbe.insertar(65));
        assertTrue(abbe.insertar(13));
        assertTrue(abbe.insertar(55));
        assertTrue(abbe.insertar(73));
        //assertTrue(abbe.insertar(96));

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar5() {
        assertTrue(abb.eliminar(34));

        assertTrue(abbe.insertar(45));
        // assertTrue(abbe.insertar(34));
        assertTrue(abbe.insertar(65));
        assertTrue(abbe.insertar(13));
        assertTrue(abbe.insertar(55));
        assertTrue(abbe.insertar(73));
        assertTrue(abbe.insertar(96));

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar6() {
        abb.vaciar();
        abbe.vaciar();

        abb.insertar(45);
        abb.insertar(34);
        abb.insertar(13);
        abb.insertar(65);
        abb.insertar(55);
        abb.insertar(47);
        abb.insertar(48);
        abb.insertar(57);
        abb.insertar(73);
        abb.insertar(96);

        abbe.insertar(47);
        abbe.insertar(34);
        abbe.insertar(13);
        abbe.insertar(65);
        abbe.insertar(55);
        abbe.insertar(48);
        abbe.insertar(57);
        abbe.insertar(73);
        abbe.insertar(96);

        abb.eliminar(45);

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar7() {
        abb.vaciar();
        abbe.vaciar();

        abb.insertar(45);
        abb.insertar(34);
        abb.insertar(13);
        abb.insertar(65);
        abb.insertar(55);
        abb.insertar(73);
        abb.insertar(96);

        abbe.insertar(55);
        abbe.insertar(34);
        abbe.insertar(13);
        abbe.insertar(65);
        abbe.insertar(73);
        abbe.insertar(96);

        abb.eliminar(45);

        assertEquals(abbe, abb);
    }

    @Test
    void eliminar8() {
        abb.vaciar();
        abbe.vaciar();

        abb.insertar(45);
        abb.insertar(34);
        abb.insertar(13);
        abb.insertar(65);
        abb.insertar(73);
        abb.insertar(96);

        abbe.insertar(65);
        abbe.insertar(34);
        abbe.insertar(13);
        abbe.insertar(73);
        abbe.insertar(96);

        abb.eliminar(45);

        assertEquals(abbe, abb);
    }

    @Test
    void caminoHojaMasCercana() {
        abb.vaciar();
        System.out.println(abb.caminoHojaMasCercana());

        abb.insertar(45);
        abb.insertar(34);
        abb.insertar(13);
        abb.insertar(65);
        abb.insertar(73);
        abb.insertar(96);

        abb.insertar(36);
        abb.insertar(10);
        abb.insertar(11);
        abb.insertar(8);
        abb.insertar(9);
        abb.insertar(66);
        abb.insertar(68);
        abb.insertar(50);

        // System.out.println(abb);
        System.out.println(abb.caminoHojaMasCercana());
    }

    @Test
    void pertenece() {
        assertTrue(abb.pertenece(45));
        assertTrue(abb.pertenece(34));
        assertTrue(abb.pertenece(13));
        assertTrue(abb.pertenece(65));
        assertTrue(abb.pertenece(55));
        assertTrue(abb.pertenece(73));
        assertTrue(abb.pertenece(96));
        assertFalse(abb.pertenece(10));
        assertFalse(abb.pertenece(null));
        abb.vaciar();
        assertFalse(abb.pertenece(null));
        assertFalse(abb.pertenece(1));
    }

//    @Test
//    void listar() {
//        Lista<Integer> esperado = new ListaDinamica<>(new Integer[]{13, 34, 45, 55, 65, 73, 96});
//        assertEquals(esperado, abb.listar());
//        abb.vaciar();
//        esperado.vaciar();
//        assertEquals(esperado, abb.listar());
//    }
//
//    @Test
//    void listarRango() {
//        Lista<Integer> esperado = new ListaDinamica<>(new Integer[]{13, 34, 45, 55, 65, 73, 96});
//        System.out.println(abb);
//        assertEquals(esperado, abb.listarRango(13, 96));
//        esperado = new ListaDinamica<>(new Integer[]{34, 45, 55, 65, 73});
//        assertEquals(esperado, abb.listarRango(34, 73));
//        assertEquals(esperado, abb.listarRango(30, 80));
//        esperado = new ListaDinamica<>(new Integer[]{55, 65, 73, 96});
//        assertEquals(esperado, abb.listarRango(55, 96));
//        esperado.vaciar();
//        assertEquals(esperado, abb.listarRango(35, 44));
//        abb.vaciar();
//        assertEquals(esperado, abb.listarRango(1, 2));
//    }
}