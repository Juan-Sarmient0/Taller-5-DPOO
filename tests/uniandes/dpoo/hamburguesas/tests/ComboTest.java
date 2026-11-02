package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
    private ProductoMenu especial;
    private ProductoMenu papas;
    private ProductoMenu gaseosa;
    private ArrayList<ProductoMenu> itemsCorral;
    private Combo comboCorral;

    @BeforeEach
    public void setUp( )
    {
        especial = new ProductoMenu( "especial", 24000 );
        papas = new ProductoMenu( "papas medianas", 5500 );
        gaseosa = new ProductoMenu( "gaseosa", 5000 );
        itemsCorral = new ArrayList<ProductoMenu>( );
        itemsCorral.add( new ProductoMenu( "corral", 14000 ) );
        itemsCorral.add( papas );
        itemsCorral.add( gaseosa );
        comboCorral = new Combo( "combo corral", 0.1, itemsCorral );
    }

    @Test
    public void testGetNombre( )
    {
        assertEquals( "combo corral", comboCorral.getNombre( ) );
    }

    @Test
    public void testGetPrecioConDescuento( )
    {
        assertEquals( 22050, comboCorral.getPrecio( ) ); 
    }

    @Test
    public void testGetPrecioConDescuentoDecimal( )
    {
        ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>( );
        items.add( especial );
        items.add( papas );
        items.add( gaseosa );
        Combo comboEspecial = new Combo( "combo especial", 0.095, items );
        assertEquals( 31222, comboEspecial.getPrecio( ) );
    }

    @Test
    public void testGetPrecioSinDescuento( )
    {
        assertEquals( 24500, new Combo( "sin descuento", 0, itemsCorral ).getPrecio( ) );
    }

    @Test
    public void testGetPrecioComboVacio( )
    {
        assertEquals( 0, new Combo( "vacio", 0.1, new ArrayList<ProductoMenu>( ) ).getPrecio( ) );
    }

    @Test
    public void testCopiaDeLaListaDeItems( )
    {
        itemsCorral.add( new ProductoMenu( "papas grandes", 6900 ) );
        assertEquals( 22050, comboCorral.getPrecio( ) );
    }

    @Test
    public void testGenerarTextoFactura( )
    {
        String esperado = "Combo combo corral\n" + " Descuento: 0.1\n" + "            22050\n";
        assertEquals( esperado, comboCorral.generarTextoFactura( ) );
    }
}