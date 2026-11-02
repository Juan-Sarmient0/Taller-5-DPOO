package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;

public class RestauranteTest
{
    private Restaurante restaurante;
    private File ingredientes;
    private File menu;
    private File combos;

    @BeforeEach
    public void setUp( )
    {
        restaurante = new Restaurante( );
        ingredientes = new File( "./data/ingredientes.txt" );
        menu = new File( "./data/menu.txt" );
        combos = new File( "./data/combos.txt" );
    }

    @Test
    public void testCargarInformacionRestaurante( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( ingredientes, menu, combos );
        assertEquals( 15, restaurante.getIngredientes( ).size( ) );
        assertEquals( 22, restaurante.getMenuBase( ).size( ) );
        assertEquals( 4, restaurante.getMenuCombos( ).size( ) );
        assertEquals( 22050, restaurante.getMenuCombos( ).get( 0 ).getPrecio( ) );
    }

    @Test
    public void testIniciarYCerrarPedido( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 1" );
        Pedido pedido = restaurante.getPedidoEnCurso( );
        restaurante.cerrarYGuardarPedido( );

        File factura = new File( "./facturas/factura_" + pedido.getIdPedido( ) + ".txt" );
        assertNull( restaurante.getPedidoEnCurso( ) );
        assertEquals( 1, restaurante.getPedidos( ).size( ) );
        factura.delete( );
    }

    @Test
    public void testIngredienteRepetidoException( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( ingredientes, menu, combos );
        Exception excepcion = assertThrows( IngredienteRepetidoException.class,
                ( ) -> this.restaurante.cargarInformacionRestaurante( ingredientes, menu, combos ) );
        assertEquals( "El ingrediente lechuga está repetido", excepcion.getMessage( ) );
    }

    @Test
    public void testNoHayPedidoEnCursoException( ) throws Exception
    {
        Exception excepcion = assertThrows( NoHayPedidoEnCursoException.class, ( ) -> this.restaurante.cerrarYGuardarPedido( ) );
        assertEquals( "Actualmente no hay un pedido en curso", excepcion.getMessage( ) );
    }

    @Test
    public void testYaHayUnPedidoEnCursoException( ) throws Exception
    {
        this.restaurante.iniciarPedido( "Juan", "Calle 1" );
        Exception excepcion = assertThrows( YaHayUnPedidoEnCursoException.class, ( ) -> this.restaurante.iniciarPedido( "Ana", "Calle 2" ) );
        assertEquals( "Ya existe un pedido en curso, para el cliente Juan así que no se puede crear un pedido para Ana", excepcion.getMessage( ) );
    }
    
    @Test
    public void testProductoRepetidoException( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( ingredientes, menu, combos );
        Exception excepcion = assertThrows( ProductoRepetidoException.class,
                ( ) -> this.restaurante.cargarInformacionRestaurante( menu, menu, combos ) );
        assertEquals( "El producto corral está repetido", excepcion.getMessage( ) );
    }

    @Test
    public void testComboRepetidoException( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( ingredientes, menu, combos );
        Exception excepcion = assertThrows( ProductoRepetidoException.class,
                ( ) -> this.restaurante.cargarInformacionRestaurante( menu, ingredientes, combos ) );
        assertEquals( "El producto combo corral está repetido", excepcion.getMessage( ) );
    }

    @Test
    public void testProductoFaltanteException( ) throws Exception
    {
        Exception excepcion = assertThrows( ProductoFaltanteException.class,
                ( ) -> this.restaurante.cargarInformacionRestaurante( ingredientes, ingredientes, combos ) );
        assertEquals( "El producto malteada no aparece en la información del restaurante".replace( "malteada", "corral" ), excepcion.getMessage( ) );
    }
    
    
}