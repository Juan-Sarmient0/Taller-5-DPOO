package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
    private ProductoAjustado ajustado;
    private Ingrediente queso;
    private Ingrediente tocineta;
    private Ingrediente cebolla;

    @BeforeEach
    public void setUp( )
    {
        ajustado = new ProductoAjustado( new ProductoMenu( "corral", 14000 ) );
        queso = new Ingrediente( "queso mozzarella", 2500 );
        tocineta = new Ingrediente( "tocineta express", 2500 );
        cebolla = new Ingrediente( "cebolla", 1000 );
    }

    @Test
    public void testGetNombre( )
    {
        assertEquals( "corral", ajustado.getNombre( ) );
    }

    @Test
    public void testGetPrecioSinModificaciones( )
    {
        assertEquals( 14000, ajustado.getPrecio( ) );
    }

    @Test
    public void testGetPrecioConAgregados( )
    {
        ajustado.agregarIngrediente( queso );
        ajustado.agregarIngrediente( tocineta );
        ajustado.agregarIngrediente( queso );
        assertEquals( 21500, ajustado.getPrecio( ) );
    }

    @Test
    public void testGetPrecioConEliminados( )
    {
        ajustado.eliminarIngrediente( cebolla );
        assertEquals( 14000, ajustado.getPrecio( ));
    }

    @Test
    public void testGenerarTextoFacturaSinModificaciones( )
    {
        assertEquals( "corral\n" + "            14000\n", ajustado.generarTextoFactura( ) );
    }

    @Test
    public void testGenerarTextoFacturaConAgregadosYEliminados( )
    {
        ajustado.agregarIngrediente( queso );
        ajustado.eliminarIngrediente( cebolla );
        String esperado = "corral\n" + "    +queso mozzarella                2500\n" + "    -cebolla\n" + "            16500\n";
        assertEquals( esperado, ajustado.generarTextoFactura( ) );
    }
}