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

/**
 *
 * @author Hp
 */
public class VentaD {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    int r;
    ResultSet rs;
    public int RegistrarVenta(Venta V){
    String sql = "INSERT INTO ventas (cliente, vendedor, total) VALUES (?,?,?)";
    try{
         con = cn.getConnection();
         ps = con.prepareStatement(sql);
         ps.setString(1, V.getCliente());
         ps.setString(2, V.getVendedor());
         ps.setInt(3, V.getTotal());
         ps.execute();
         
    }catch(SQLException e){
    System.out.println(e.toString());
    }finally{
     try{
     con.close();
     }catch(SQLException e) {}
         
         }
    return r;
    }
    
    
     public int RegistrarDetalle(Detalle Dv){
       String sql = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
     
     public int IdVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
     
        public boolean ActualizarStock(int cant, String cod){
        String sql = "UPDATE productos SET stock = ? WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setString(2, cod);
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
        
        
        public List ListarVentas(){
        List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT * FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Venta vent = new Venta();
                vent.setId(rs.getInt("id"));
                vent.setCliente(rs.getString("cliente"));
                vent.setVendedor(rs.getString("vendedor"));
                vent.setTotal(rs.getInt("total"));
                
                ListaVenta.add(vent);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaVenta;
    }
}
