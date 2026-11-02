package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest
{
    private ProductoMenu productoMenu1;
    private ProductoMenu productoGratis;

    @BeforeEach
    public void setUp( )
    {
        productoMenu1 = new ProductoMenu( "corral", 14000 );
        productoGratis = new ProductoMenu( "agua", 0 );
    }

    @Test
    public void testGetNombre( )
    {
        assertEquals( "corral", productoMenu1.getNombre( ) );
    }

    @Test
    public void testGetPrecio( )
    {
        assertEquals( 14000, productoMenu1.getPrecio( ) );
        assertEquals( 0, productoGratis.getPrecio( ) );
    }

    @Test
    public void testGenerarTextoFactura( )
    {
        assertEquals( "corral\n" + "            14000\n", productoMenu1.generarTextoFactura( ) );
    }
}