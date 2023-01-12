/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class ClienteD {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarCliente(Cliente cl){
        String sql = "INSERT INTO clientes (rut, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getRut());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    
    public List ListarCliente(){
       List<Cliente> ListaCl = new ArrayList();
       String sql = "SELECT * FROM clientes";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Cliente cl = new Cliente();
               cl.setId(rs.getInt("id"));
               cl.setRut(rs.getString("rut"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               ListaCl.add(cl);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaCl;
   }
    
    public boolean EliminarCliente(int id){
       String sql = "DELETE FROM clientes WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
    
    public Cliente BuscarCliente(String rut){
    Cliente cl = new Cliente();
    String sql = "SELECT * FROM clientes WHERE rut = ?";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, rut);
        rs = ps.executeQuery();
        if(rs.next()){
            cl.setNombre(rs.getString("nombre"));
            cl.setTelefono(rs.getString("telefono"));
            cl.setDireccion(rs.getString("direccion"));
        }
    }catch(Exception e){
        System.out.println(e.toString());
    }
    return cl;
    }
    
    public int existeCliente (String rut){
        Cliente cl = new Cliente();
        String sql = "SELECT count(rut) FROM clientes WHERE rut = ?";
        con = cn.getConnection();
        try{
        ps = con.prepareStatement(sql);
        ps.setString(1, rut);
        rs = ps.executeQuery();
        
        if (rs.next()){
            return rs.getInt(1);
        }
        return 1;
        
        }catch(Exception e){
        return 1;
        
        }
    
    }
    
    public boolean ModificarCliente(Cliente cl){
        String sql = "UPDATE clientes SET rut=?, nombre=?, telefono=?, direccion=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getRut());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setInt(5, cl.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
   public DefaultTableModel buscarClientesFiltro(String buscar)
    {
        String [] nombreColumna={"id","rut","nombre","telefono","direccion"};
        String [] registros = new String[5];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);
        
        String sql ="SELECT * FROM clientes WHERE rut LIKE '%"+buscar+"%' OR nombre LIKE '%"+buscar+"%'";
        
        
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            registros[0]=rs.getString("id");
            registros[1]=rs.getString("rut");
            registros[2]=rs.getString("nombre");
            registros[3]=rs.getString("telefono");
            registros[4]=rs.getString("direccion");
            
            modelo.addRow(registros);
            }
        }catch(Exception e){
        System.out.println(e);
        }
        return modelo;
    }    
   
}
