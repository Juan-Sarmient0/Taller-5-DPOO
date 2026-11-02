package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.*;

public class PedidoTest
{
    private Pedido pedidoVacio;
    private Pedido pedido;
    private ProductoMenu corral;
    private ProductoMenu gaseosa;
    private Combo comboCorral;
    private ProductoAjustado ajustado;
    private File archivoTemporal;
    private File carpetaTemporal;

    @BeforeEach
    public void setUp( )
    {
        corral = new ProductoMenu( "corral", 14000 );
        gaseosa = new ProductoMenu( "gaseosa", 5000 );
        ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>( );
        items.add( corral );
        items.add( new ProductoMenu( "papas medianas", 5500 ) );
        items.add( gaseosa );
        comboCorral = new Combo( "combo corral", 0.1, items );
        ajustado = new ProductoAjustado( new ProductoMenu( "casera", 23000 ) );

        pedidoVacio = new Pedido( "Ana", "Calle 1 # 2-3" );
        pedido = new Pedido( "Juan", "Carrera 7 # 40-62" );
        pedido.agregarProducto( corral );
        pedido.agregarProducto( comboCorral );
        pedido.agregarProducto( ajustado );
    }

    @AfterEach
    public void tearDown( )
    {
        if( archivoTemporal != null ) archivoTemporal.delete( );
        if( carpetaTemporal != null ) carpetaTemporal.delete( );
    }

    @Test
    public void testGetNombreCliente( )
    {
        assertEquals( "Juan", pedido.getNombreCliente( ) );
    }

    @Test
    public void testIdsConsecutivos( )
    {
        assertEquals( pedidoVacio.getIdPedido( ) + 1, pedido.getIdPedido( ) );
        Pedido otro = new Pedido( "Luis", "Calle 100" );
        assertEquals( pedido.getIdPedido( ) + 1, otro.getIdPedido( ) );
    }

    @Test
    public void testPrecioTotalPedidoVacio( )
    {
        assertEquals( 0, pedidoVacio.getPrecioTotalPedido( ) );
    }

    @Test
    public void testPrecioTotalPedido( )
    {
        assertEquals( 70269, pedido.getPrecioTotalPedido( ) );
    }

    @Test
    public void testAgregarProducto( )
    {
        pedidoVacio.agregarProducto( gaseosa );
        assertEquals( 5950, pedidoVacio.getPrecioTotalPedido( ) );
    }

    @Test
    public void testGenerarTextoFacturaPedidoVacio( )
    {
        String esperado = "Cliente: Ana\n" + "Dirección: Calle 1 # 2-3\n" + "----------------\n" + "----------------\n"
                + "Precio Neto:  0\n" + "IVA:          0\n" + "Precio Total: 0\n";
        assertEquals( esperado, pedidoVacio.generarTextoFactura( ) );
    }

    @Test
    public void testGenerarTextoFactura( )
    {
        String esperado = "Cliente: Juan\n" + "Dirección: Carrera 7 # 40-62\n" + "----------------\n"
                + corral.generarTextoFactura( ) + comboCorral.generarTextoFactura( ) + ajustado.generarTextoFactura( )
                + "----------------\n" + "Precio Neto:  59050\n" + "IVA:          11219\n" + "Precio Total: 70269\n";
        assertEquals( esperado, pedido.generarTextoFactura( ) );
    }

    @Test
    public void testGuardarFactura( ) throws Exception
    {
        archivoTemporal = File.createTempFile( "factura_prueba", ".txt" );
        pedido.guardarFactura( archivoTemporal );
        String contenido = new String( Files.readAllBytes( archivoTemporal.toPath( ) ), StandardCharsets.UTF_8 );
        assertEquals( pedido.generarTextoFactura( ), contenido );
    }

    @Test
    public void testGuardarFacturaArchivoInvalido( ) throws Exception
    {
        carpetaTemporal = Files.createTempDirectory( "carpeta_factura" ).toFile( );
        assertThrows( FileNotFoundException.class, ( ) -> pedido.guardarFactura( carpetaTemporal ) );
    }
}