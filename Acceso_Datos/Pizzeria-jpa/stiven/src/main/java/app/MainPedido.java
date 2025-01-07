package app;

import java.sql.SQLException;
import java.util.ArrayList;

import app.Controladores.ContraladorPedido;
import app.Controladores.dao.impl.JbcPedidoDao;
import app.Modelo.LineaPedido;
import app.Modelo.Pedido;
import app.Modelo.Pedido.EstadoPedido;

public class MainPedido {
    
    public static void main(String[] args) {

        ContraladorPedido contraladorPedido = new ContraladorPedido();
        
        ArrayList<LineaPedido> listaLineaPedidos = new ArrayList<>();
        JbcPedidoDao jbcPedidoDao = new JbcPedidoDao();


        Pedido pedido = new Pedido(EstadoPedido.PEDIENTE, listaLineaPedidos, null, null);

        try {

            contraladorPedido.save(pedido);
            pedido.setId(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
