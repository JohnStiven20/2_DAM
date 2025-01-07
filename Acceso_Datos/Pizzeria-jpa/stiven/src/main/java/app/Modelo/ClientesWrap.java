package app.Modelo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientesWrap {

    private List<Cliente> clientes;

    public ClientesWrap() {
    }

    public ClientesWrap(List<Cliente> lista) {
        this.clientes = lista;
    }

    @XmlElement(name = "cliente")
    public List<Cliente> getClientes() {
        
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
