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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hp
 */
public class ProductosD {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarProductos(Productos prod) {
        String sql = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getProveedor());
            ps.setInt(4, prod.getStock());
            ps.setDouble(5, prod.getPrecio());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public void ConsultarProveedor(JComboBox proveedor) {
        String sql = "SELECT nombre FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public List ListarProductos() {
        List<Productos> Listapro = new ArrayList();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos pro = new Productos();
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getString("proveedor"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecio(rs.getInt("precio"));
                Listapro.add(pro);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapro;
    }

    public boolean EliminarProductos(int id) {
        String sql = "DELETE FROM productos WHERE id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public boolean ModificarProductos(Productos pro) {
        String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setInt(5, pro.getPrecio());
            ps.setInt(6, pro.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public Productos BuscarProducto(String cod) {
        Productos producto = new Productos();
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }

    public Config BuscarDatos() {
        Config confi = new Config();
        String sql = "SELECT * FROM datos_negocio";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                confi.setId(rs.getInt("id"));
                confi.setNombre(rs.getString("nombre"));
                confi.setRut(rs.getString("rut"));
                confi.setTelefono(rs.getString("telefono"));
                confi.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return confi;
    }

    public int existeProducto(String codigo) {
        Productos producto = new Productos();
        String sql = "SELECT count(codigo) FROM productos WHERE codigo = ?";
        con = cn.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;

        } catch (Exception e) {
            return 1;

        }

    }

    public DefaultTableModel buscarProductos(String buscar)
    {
        String [] nombreColumna={"id","codigo","nombre","proveedor","stock","precio"};
        String [] registros = new String[6];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);
        
        String sql ="SELECT * FROM productos WHERE codigo LIKE '%"+buscar+"%' OR nombre LIKE '%"+buscar+"%'";
        
        
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            registros[0]=rs.getString("id");
            registros[1]=rs.getString("codigo");
            registros[2]=rs.getString("nombre");
            registros[3]=rs.getString("proveedor");
            registros[4]=rs.getString("stock");
            registros[5]=rs.getString("precio");
            modelo.addRow(registros);
            }
        }catch(Exception e){
        System.out.println(e);
        }
        return modelo;
    }    

}
