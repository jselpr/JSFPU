/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Cliente;
import modelo.DAO;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "clienteController")
@ViewScoped
public class ClienteController {
    
    @ManagedProperty(value = "#{dao}")
    private DAO dao;

    
    
    private String orden = "id";
    private Boolean asc = true;
    private Integer paginaActual = 0;
    private Integer tamañoPagina = 4;
    
    private Long numeroRegistros;
    private Long numeroPaginas;
    private Boolean creando;
    /**
     * Creates a new instance of ClienteController
     */
    public ClienteController() {
    }
    
    private List<Integer> paginas = new ArrayList<>();

    public List<Integer> getPaginas() {
        return paginas;
    }
    
    
    
    public List<Cliente> getListado(){
        this.numeroRegistros = dao.contarTodosClientes();
        this.numeroPaginas = numeroRegistros/tamañoPagina + ((numeroRegistros%tamañoPagina!=0)?1:0);
        paginas.clear();
        for (int i = 1; i <= numeroPaginas; i++) {
            paginas.add(i);
            
        }
        return dao.buscarTodosClientes(orden, asc, tamañoPagina, paginaActual);
    }
    
    
    
    public void setOrdenacion(String orden, Boolean asc){
        this.orden = orden;
        this.asc = asc;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }

    public Integer getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(Integer paginaActual) {
        this.paginaActual = paginaActual;
    }

    public Integer getTamañoPagina() {
        return tamañoPagina;
    }

    public void setTamañoPagina(Integer tamañoPagina) {
        this.tamañoPagina = tamañoPagina;
    }
    
    

    
    
    private Cliente actual,nuevo;

    
    
    
    public String inicioEdicion(Cliente actual){
        actual.setEnEdicion(true);
        this.actual = actual;
        return "";
    }

    public Cliente getActual() {
        return actual;
    }

    public void setActual(Cliente actual) {
        this.actual = actual;
    }

    public Long getNumeroRegistros() {
        return numeroRegistros;
    }

    public Long getNumeroPaginas() {
        return numeroPaginas;
    }
    
    

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }
    
    public String borrar(Cliente c){
        FacesContext fc = FacesContext.getCurrentInstance(); //Punto de entrada al api JSF
        String mensaje;
        try {
            dao.borrar(c);
            
            mensaje="Eliminaodo correctamente "+c.getNombre();
        } catch (Exception e) {
            e.printStackTrace();
            mensaje="Problemas al  eliminar "+c.getNombre()+ " "+e.getMessage();
        }
        fc.addMessage("elFormulario",new FacesMessage(mensaje));
        return "";
    }
    
    /**
     * Permite cancelar la edición de un registro cuando lo estamos editando
     * @return devuelve una cadena vacía para actualizar la página actual
     */
    public String cancelarEdicion(){
        //actual = null;
        actual.setEnEdicion(false);
        return "";
    }
    
    public String guardarEdicion(){
        FacesContext fc = FacesContext.getCurrentInstance(); //Punto de entrada al api JSF
        String mensaje;
        try {
            dao.actualizar(actual);
            actual.setEnEdicion(false);
            mensaje="Editado correctamente "+actual.getNombre();
            actual = null;
        } catch (Exception e) {
            e.printStackTrace();
            mensaje="Problemas al  editar "+actual.getNombre()+ " "+e.getMessage();
        }
        fc.addMessage("elFormulario",new FacesMessage(mensaje));
        return "";
    }

    public Boolean getCreando() {
        return creando;
    }

    public void setCreando(Boolean creando) {
        this.creando = creando;
    }
    
    public String crear(){
        setCreando(true);
        nuevo = new Cliente();
        return "";
    }
    
    public String guardarNuevo(){
        FacesContext fc = FacesContext.getCurrentInstance(); //Punto de entrada al api JSF
        String mensaje;
        setCreando(false);
        try {
            dao.insertar(nuevo);
            mensaje="Creado correctamente "+nuevo.getNombre();
        } catch (Exception e) {
            mensaje="Problemas al crear "+nuevo.getNombre()+ " "+e.getMessage();
        }
        fc.addMessage("nuevo",new FacesMessage(mensaje));
        return "";
    }
    
    public String cancelarNuevo(){
        setCreando(false);
        return "";
    }

    public Cliente getNuevo() {
        return nuevo;
    }

    public void setNuevo(Cliente nuevo) {
        this.nuevo = nuevo;
    }
    
    
}
