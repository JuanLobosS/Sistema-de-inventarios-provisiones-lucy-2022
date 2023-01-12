/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelo.login;
import Modelo.Cliente;
import Modelo.ClienteD;
import Modelo.Config;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.Productos;
import Modelo.ProductosD;
import javax.swing.JOptionPane;
import Modelo.Proveedor;
import Modelo.ProveedorD;
import Modelo.Venta;
import Modelo.VentaD;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;


import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Hp
 */
public class Principal extends javax.swing.JFrame {

    Cliente cl = new Cliente();
    ClienteD cliente = new ClienteD();
    Proveedor pr = new Proveedor();
    ProveedorD PrD = new ProveedorD();
    Productos pro = new Productos();
    ProductosD proD = new ProductosD();
    Venta v = new Venta();
    VentaD vD = new VentaD();
    Detalle Dv = new Detalle();
    Config confi = new Config();
    Eventos event = new Eventos();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int TotalPagar = 0;
    int item;

    public Principal() {
        initComponents();
        this.setTitle("SISTEMA DE VENTAS PROVISIONES LUCY");
        
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        //txtIdCliente.setVisible(false);
        AutoCompleteDecorator.decorate(cmbProveedorProd);
        txtRutV.setEnabled(false);
        txtIdprod.setVisible(false);
        txtIdV.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdCLiente.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdVenta.setVisible(false);
        ListarConfig();
        txtTelefonoClienteV.setVisible(false);
        txtDireccionClienteV.setVisible(false);
        txtIdprod.setVisible(false);
        txtIdVenta.setVisible(false);
        ListarConfig();
        txtIdV.setEnabled(false);
        txtNombreClienteV.setEnabled(false);
        txtRol.setVisible(false);
        txtRol.setText("Administrador");
        btnvuelto.setEnabled(false);
        this.jTabbedPane1.setBackgroundAt(0, new Color(255,255,153));
        
        

    }

    public Principal(login priv) {

        initComponents();
        this.setTitle("SISTEMA DE VENTAS PROVISIONES LUCY");
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //txtIdCliente.setVisible(false);
        AutoCompleteDecorator.decorate(cmbProveedorProd);
        btnvuelto.setEnabled(false);
        txtIdV.setEnabled(false);
        txtNombreClienteV.setEnabled(false);
        txtIdV.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdCLiente.setVisible(false);
        txtIdConfig.setVisible(false);
        txtTelefonoClienteV.setVisible(false);
        txtDireccionClienteV.setVisible(false);
        txtIdprod.setVisible(false);
        txtIdVenta.setVisible(false);
        ListarConfig();

        txtRol.setVisible(false);
        txtRol.setText("Asistente");

        if (priv.getRol().equals("Asistente")) {
            btnProveedor.setEnabled(false);
            btnEditarProd.setEnabled(false);
            btnRegistrarU.setEnabled(false);
            lblVendedor.setText(priv.getNombre());
        } else {
            lblVendedor.setText(priv.getNombre());
        }
    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrD.ListarProveedor();
        modelo = (DefaultTableModel) tblProveedores.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRut();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            
            
            modelo.addRow(ob);
        }
        tblProveedores.setModel(modelo);

    }

    private void LimpiarCliente() {
        txtIdCLiente.setText("");
        txtRutCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");
    }

    public void ListarCliente() {
        List<Cliente> ListarCl = cliente.ListarCliente();
        modelo = (DefaultTableModel) tblClientes.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getRut();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        tblClientes.setModel(modelo);

    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void Registrarventa() {
        String cliente = txtNombreClienteV.getText();
        String vendedor = lblVendedor.getText();
        int monto = TotalPagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        vD.RegistrarVenta(v);

    }

    private void RegistrarDetalle() {
        int id = vD.IdVenta();
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(tblVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());
            int precio = Integer.parseInt(tblVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            vD.RegistrarDetalle(Dv);

        }

    }
    
    public java.awt.Image getIconImage(){
        java.awt.Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("src/image/acceso.png"));
        return retValue;
    }

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRutProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtIdProd.setText("");
        txtCodigoProd.setText("");
        txtDescripcionProd.setText("");
        cmbProveedorProd.setSelectedItem(null);
        txtCantidadProd.setText("");
        txtPrecioProd.setText("");
    }

    private void LimpiarClienteVenta() {
        txtRutV.setText("");
        txtNombreClienteV.setText("");
        txtTelefonoClienteV.setText("");
        txtDireccionClienteV.setText("");
        //txtIdCV.setText();

    }

    private void TotalPagar() {
        TotalPagar = 0;
        int numFila = tblVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {

            int cal = Integer.parseInt(String.valueOf(tblVenta.getModel().getValueAt(i, 4)));
            TotalPagar = TotalPagar + cal;
        }
        lblTotal.setText(String.valueOf(TotalPagar));
    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    private void LimparVenta() {
        txtCodigoV.setText("");
        txtDescripcionV.setText("");
        txtPrecioV.setText("");
        txtStockV.setText("");
        txtCantidadV.setText("");
        txtPrecioV.setText("");
        txtIdV.setText("");
    }

    public void ListarProductos() {
        List<Productos> ListarPro = proD.ListarProductos();
        modelo = (DefaultTableModel) tblProductos.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedor();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        tblProductos.setModel(modelo);

    }

    public void ListarVentas() {
        List<Venta> ListarVenta = vD.ListarVentas();
        modelo = (DefaultTableModel) tblVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getCliente();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();

            modelo.addRow(ob);
        }
        tblVentas.setModel(modelo);

    }

    private void ActualizarStock() {
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            String cod = tblVenta.getValueAt(i, 0).toString();

            int cant = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());

            pro = proD.BuscarProducto(cod);

            int StockActual = pro.getStock() - cant;
            JOptionPane.showMessageDialog(null, "Venta Realizada Generando Boleta!");
            vD.ActualizarStock(StockActual, cod);

        }
    }

    public void ListarConfig() {
        confi = proD.BuscarDatos();
        txtIdConfig.setText("" + confi.getId());
        txtIdConfig.setText("" + confi.getRut());
        txtIdConfig.setText("" + confi.getNombre());
        txtIdConfig.setText("" + confi.getTelefono());
        txtIdConfig.setText("" + confi.getDireccion());

    }
    
    public void buscar(String buscar){
    DefaultTableModel modelo = proD.buscarProductos(buscar);
    tblProductos.setModel(modelo);
            
            }
    public void buscarProv(String buscar){
    DefaultTableModel modelo = PrD.buscarProveedoresFiltro(buscar);
    tblProveedores.setModel(modelo);
            
            }
    public void buscarCli(String buscar){
    DefaultTableModel modelo = cliente.buscarClientesFiltro(buscar);
    tblClientes.setModel(modelo);
            
            }
    

    private void pdf() {
        int id = vD.IdVenta();
        try {
            FileOutputStream archivo;
            File file = new File("src/pdf/venta" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.YELLOW);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Boleta:" + id + "\n " + "Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            String rut = "8.883.298-1";
            String nom = "Provisiones Lucy";
            String tel = "981914488";
            String dir = "Las lilas 1145";

            Encabezado.addCell("");
            Encabezado.addCell("");
            Encabezado.addCell("Rut: " + rut + "\nNombre: " + nom + "\nFono: " + tel + "\nDireccion: " + dir);
            Encabezado.addCell(fecha);

            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("Dato de cliente" + "\n\n");
            doc.add(cli);

            PdfPTable tablacli = new PdfPTable(4);
            tablacli.setWidthPercentage(100);
            tablacli.getDefaultCell().setBorder(0);
            float[] Columnacli = new float[]{20f, 50f, 30f, 40f};
            tablacli.setWidths(Columnacli);
            tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Rut", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("Telefono", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("Direccion", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            tablacli.addCell(cl1);
            tablacli.addCell(cl2);
            tablacli.addCell(cl3);
            tablacli.addCell(cl4);
            tablacli.addCell(txtRutV.getText());
            tablacli.addCell(txtNombreClienteV.getText());
            tablacli.addCell(txtTelefonoClienteV.getText());
            tablacli.addCell(txtDireccionClienteV.getText());

            doc.add(tablacli);

            //productos
            PdfPTable tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] Columnapro = new float[]{10f, 50f, 15f, 20f};
            tablapro.setWidths(Columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio Unitario", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Precio Total", negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro1.setBackgroundColor(BaseColor.DARK_GRAY);
            pro2.setBackgroundColor(BaseColor.DARK_GRAY);
            pro3.setBackgroundColor(BaseColor.DARK_GRAY);
            pro4.setBackgroundColor(BaseColor.DARK_GRAY);
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            for (int i = 0; i < tblVenta.getRowCount(); i++) {
                String producto = tblVenta.getValueAt(i, 1).toString();
                String cantidad = tblVenta.getValueAt(i, 2).toString();
                String precio = tblVenta.getValueAt(i, 3).toString();
                String total = tblVenta.getValueAt(i, 4).toString();

                tablapro.addCell(cantidad);
                tablapro.addCell(producto);
                tablapro.addCell(precio);
                tablapro.addCell(total);

            }
            doc.add(tablapro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total pago: $" + TotalPagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph mensaje = new Paragraph();
            Font negr = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.YELLOW);
            mensaje.add(Chunk.NEWLINE);

            mensaje.add("MUCHAS GRACIAS POR COMPRAR EN PROVISIONES LUCY!!!");

            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }

    }

    private void limpiarTableVenta() {
        tmp = (DefaultTableModel) tblVenta.getModel();
        int fila = tblVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        txtRol = new javax.swing.JTextField();
        lblVendedor = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        btnRegistrarU = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        txtCodigoV = new javax.swing.JTextField();
        txtDescripcionV = new javax.swing.JTextField();
        txtCantidadV = new javax.swing.JTextField();
        txtPrecioV = new javax.swing.JTextField();
        txtStockV = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        btnEliminarVenta = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnGenerarV = new javax.swing.JButton();
        txtRutV = new javax.swing.JTextField();
        txtNombreClienteV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        txtTelefonoClienteV = new javax.swing.JTextField();
        txtDireccionClienteV = new javax.swing.JTextField();
        txtIdprod = new javax.swing.JTextField();
        txtIdV = new javax.swing.JTextField();
        btnIngresarCliente = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtVuelto = new javax.swing.JTextField();
        lblVuelto = new javax.swing.JLabel();
        btnvuelto = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtRutCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtTelefonoCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        txtIdCLiente = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtBuscarclienteFiltro = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtRutProveedor = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnEditarProveedor = new javax.swing.JButton();
        btnGuardarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        txtIdProveedor = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtBuscarProveedor = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCodigoProd = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDescripcionProd = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCantidadProd = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPrecioProd = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cmbProveedorProd = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnGuardarProd = new javax.swing.JButton();
        btnNuevoProd = new javax.swing.JButton();
        btnEliminarProd = new javax.swing.JButton();
        btnEditarProd = new javax.swing.JButton();
        txtIdProd = new javax.swing.JTextField();
        btnEditarCantidad = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txtDireccionConfig = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtTelefonoConfig = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtNombreConfig = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtRutConfig = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtIdConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        btnNuevaVenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("Venta");
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedores");
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProductos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/producto.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventas.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/config.png"))); // NOI18N
        btnConfig.setText("Salir");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        lblVendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/TITULO_PRINCIPAL.png"))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 102, 102));
        jLabel30.setText("Vendedor:");

        btnRegistrarU.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/registrarUS.png"))); // NOI18N
        btnRegistrarU.setText("Registrar");
        btnRegistrarU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarUActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnNuevaVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVentas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRegistrarU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 1011, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 19, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevaVenta)
                    .addComponent(btnClientes)
                    .addComponent(btnProveedor)
                    .addComponent(btnProductos)
                    .addComponent(btnVentas)
                    .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrarU, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 92, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 230));

        jTabbedPane1.setBackground(new java.awt.Color(255, 153, 51));
        jTabbedPane1.setForeground(new java.awt.Color(51, 0, 51));
        jTabbedPane1.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel1.setText("Codigo");

        jLabel2.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel2.setText("Descripcion");

        jLabel3.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel3.setText("Precio");

        jLabel4.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad");

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel6.setText("Stock");

        jLabel7.setText("jLabel1");

        jButton7.setText("jButton7");

        txtCodigoV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCodigoV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVKeyTyped(evt);
            }
        });

        txtDescripcionV.setEditable(false);
        txtDescripcionV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDescripcionV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVKeyTyped(evt);
            }
        });

        txtCantidadV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidadV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVActionPerformed(evt);
            }
        });
        txtCantidadV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVKeyTyped(evt);
            }
        });

        txtPrecioV.setEditable(false);
        txtPrecioV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtStockV.setEditable(false);
        txtStockV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tblVenta.setAutoCreateRowSorter(true);
        tblVenta.setBackground(new java.awt.Color(255, 204, 51));
        tblVenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        tblVenta.setOpaque(false);
        jScrollPane1.setViewportView(tblVenta);
        if (tblVenta.getColumnModel().getColumnCount() > 0) {
            tblVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/quitar del carrito.png"))); // NOI18N
        btnEliminarVenta.setText("Quitar de la venta");
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/total.png"))); // NOI18N
        jLabel5.setText("TOTAL A PAGAR : ");

        jLabel8.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel8.setText("RUT");

        btnGenerarV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGenerarV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/factura.png"))); // NOI18N
        btnGenerarV.setText("Generar Venta");
        btnGenerarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVActionPerformed(evt);
            }
        });

        txtRutV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutVKeyTyped(evt);
            }
        });

        txtNombreClienteV.setEditable(false);
        txtNombreClienteV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteVKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel9.setText("NOMBRE");

        lblTotal.setFont(new java.awt.Font("Lucida Sans", 1, 27)); // NOI18N
        lblTotal.setText("-----");

        btnIngresarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnIngresarCliente.setText("Ingresar Cliente Frecuente");
        btnIngresarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarClienteActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel28.setText("$");

        txtVuelto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVueltoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVueltoFocusLost(evt);
            }
        });
        txtVuelto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVueltoActionPerformed(evt);
            }
        });
        txtVuelto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVueltoKeyTyped(evt);
            }
        });

        lblVuelto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblVuelto.setText("-----");

        btnvuelto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnvuelto.setText("Calcular Vuelto");
        btnvuelto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvueltoActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel29.setText("$");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnIngresarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(466, 466, 466)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdprod, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCodigoV, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcionV, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCantidadV, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPrecioV, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtStockV, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(479, 479, 479)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton7))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtRutV, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtNombreClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTelefonoClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDireccionClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(594, 594, 594)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarVenta)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnGenerarV, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnvuelto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(443, 443, 443))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel6, jLabel7});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigoV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidadV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDescripcionV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecioV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStockV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblVuelto)
                                .addComponent(jLabel29))
                            .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(btnvuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRutV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefonoClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccionClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addComponent(btnIngresarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnGenerarV, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel6, jLabel7});

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 153));

        jLabel10.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel10.setText("RUT");

        jLabel11.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel11.setText("NOMBRE");

        jLabel12.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel12.setText("E-MAIL");

        txtRutCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutClienteFocusLost(evt);
            }
        });
        txtRutCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutClienteKeyTyped(evt);
            }
        });

        txtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyTyped(evt);
            }
        });

        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        tblClientes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUT", "NOMBRE", "TELEFONO", "EMAIL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        tblClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblClientesKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setPreferredWidth(1);
            tblClientes.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblClientes.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblClientes.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jLabel15.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel15.setText("TELEFONO");

        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });

        btnGuardarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        btnGuardarCliente.setText("Agregar Cliente");
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modificar.png"))); // NOI18N
        btnEditarCliente.setText("Modificar");
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar.png"))); // NOI18N
        btnNuevoCliente.setText("Nuevo Cliente");
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/borrar.png"))); // NOI18N
        btnEliminarCliente.setText("Eliminar");
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Indique Rut o Nombre para buscar:");

        txtBuscarclienteFiltro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscarclienteFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarclienteFiltroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRutCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtIdCLiente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel34)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtBuscarclienteFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(114, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoCliente)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(txtRutCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuardarCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdCLiente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscarclienteFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 153));

        txtRutProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRutProveedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutProveedorFocusLost(evt);
            }
        });
        txtRutProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutProveedorKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel13.setText("RUT:");

        txtNombreProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel14.setText("NOMBRE:");

        txtTelefonoProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefonoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoProveedorActionPerformed(evt);
            }
        });
        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel16.setText("TELEFONO:");

        txtDireccionProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel17.setText("EMPRESA");

        btnEditarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modificar.png"))); // NOI18N
        btnEditarProveedor.setText("Modificar Proveedor");
        btnEditarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnGuardarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        btnGuardarProveedor.setText("Guarda Proveedor");
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar.png"))); // NOI18N
        btnNuevoProveedor.setText("Nuevo Proveedor");
        btnNuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/borrar.png"))); // NOI18N
        btnEliminarProveedor.setText("Borrar Proveedor");
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        tblProveedores.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUT", "NOMBRE", "TELEFONO", "EMPRESA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProveedores);
        if (tblProveedores.getColumnModel().getColumnCount() > 0) {
            tblProveedores.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblProveedores.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblProveedores.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblProveedores.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblProveedores.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Indique Rut o Nombre :");

        txtBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRutProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtRutProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(btnNuevoProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("tab3", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 153));

        jLabel18.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel18.setText("CODIGO :");

        txtCodigoProd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCodigoProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoProdFocusLost(evt);
            }
        });
        txtCodigoProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProdKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel19.setText("DESCRIPCION :");

        txtDescripcionProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDescripcionProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProdActionPerformed(evt);
            }
        });
        txtDescripcionProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionProdKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel20.setText("CANTIDAD :");

        txtCantidadProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidadProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProdKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel21.setText("PRECIO :");

        txtPrecioProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProdKeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel22.setText("PROVEEDOR:");

        cmbProveedorProd.setEditable(true);

        tblProductos.setAutoCreateRowSorter(true);
        tblProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCION", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProductos.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblProductos.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblProductos.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btnGuardarProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGuardarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        btnGuardarProd.setText("Agregar Producto");
        btnGuardarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProdActionPerformed(evt);
            }
        });

        btnNuevoProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevoProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar.png"))); // NOI18N
        btnNuevoProd.setText("Nuevo Producto");
        btnNuevoProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProdActionPerformed(evt);
            }
        });

        btnEliminarProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/borrar.png"))); // NOI18N
        btnEliminarProd.setText("Borrar Producto");
        btnEliminarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarProd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProdActionPerformed(evt);
            }
        });

        btnEditarProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEditarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modificar.png"))); // NOI18N
        btnEditarProd.setText("Modificar Producto");
        btnEditarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarProd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditarProd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProdActionPerformed(evt);
            }
        });

        btnEditarCantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/updatestock.png"))); // NOI18N
        btnEditarCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCantidadActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Indique Codigo o Descripcion :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(txtCodigoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcionProd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(txtCantidadProd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEditarCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbProveedorProd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnEditarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(btnEliminarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 17, Short.MAX_VALUE))))
                            .addComponent(btnNuevoProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(28, 28, 28)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCodigoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtDescripcionProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20)
                            .addComponent(txtCantidadProd)
                            .addComponent(btnEditarCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(cmbProveedorProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(btnNuevoProd)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 153));

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL VENTA"
            }
        ));
        tblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblVentas);
        if (tblVentas.getColumnModel().getColumnCount() > 0) {
            tblVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnPdfVentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf (1).png"))); // NOI18N
        btnPdfVentas.setText("ABRIR BOLETA SELECCIONADA");
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(btnPdfVentas)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("tab5", jPanel6);

        jLabel23.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel23.setText("DIRECCION:");

        jLabel24.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel24.setText("TELEFONO:");

        jLabel25.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel25.setText("NOMBRE:");

        jLabel26.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel26.setText("RUT:");

        jLabel27.setFont(new java.awt.Font("Lucida Sans", 1, 36)); // NOI18N
        jLabel27.setText("DATOS DE NEGOCIO");

        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar.png"))); // NOI18N
        btnActualizarConfig.setText("Actualizar Datos");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 283, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(250, 250, 250))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRutConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(btnActualizarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(txtRutConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btnActualizarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab6", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 1010, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVActionPerformed

    private void txtDescripcionProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProdActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        String rut = txtRutCliente.getText();
        rut = rut.toUpperCase();
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        if (cliente.existeCliente(rut) == 0) {
            if (!"".equals(txtRutCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDireccionCliente.getText())) {

                rut = rut.toUpperCase();
                rut = rut.replace(".", "");
                rut = rut.replace("-", "");
                cl.setRut(rut);
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDireccionCliente.getText());
                cliente.RegistrarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Registrado");
                LimpiarCliente();
                LimpiarTable();
                ListarCliente();
                btnEditarCliente.setEnabled(false);
                btnEliminarCliente.setEnabled(false);
                btnGuardarCliente.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "ERROR, El cliente ya existe");
            txtRutCliente.setText("");
            txtNombreCliente.setText("");
            txtTelefonoCliente.setText("");
            txtDireccionCliente.setText("");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed

        String rut = txtRutProveedor.getText();
        rut = rut.toUpperCase();
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        if (cliente.existeCliente(rut) == 0) {
            if (!"".equals(txtRutProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
                pr.setRut(rut);
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                PrD.RegistrarProveedor(pr);
                JOptionPane.showMessageDialog(null, "ERROR, Proveedor ya existe");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
                btnEditarCliente.setEnabled(false);
                btnEliminarCliente.setEnabled(false);
                btnGuardarCliente.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "ERROR, Usuario ya existe");
        }

    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        btnGuardarProveedor.setEnabled(false);
        txtIdProveedor.setEnabled(false);
        txtRutProveedor.setEnabled(false);
        txtNombreProveedor.setEnabled(false);
        txtTelefonoProveedor.setEnabled(false);
        txtDireccionProveedor.setEnabled(false);
        LimpiarTable();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void tblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresMouseClicked
        txtRutProveedor.setEnabled(false);
        txtRutProveedor.setEnabled(false);
        txtNombreProveedor.setEnabled(true);
        txtTelefonoProveedor.setEnabled(true);
        txtDireccionProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnEditarProveedor.setEnabled(true);
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnGuardarProveedor.setEnabled(false);
        int fila = tblProveedores.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(tblProveedores.getValueAt(fila, 0).toString());
        txtRutProveedor.setText(tblProveedores.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(tblProveedores.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(tblProveedores.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(tblProveedores.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tblProveedoresMouseClicked

    private void txtTelefonoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        LimpiarProveedor();
        txtIdProveedor.setEnabled(true);
        txtRutProveedor.setEnabled(true);
        txtNombreProveedor.setEnabled(true);
        txtTelefonoProveedor.setEnabled(true);
        txtDireccionProveedor.setEnabled(true);
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        btnGuardarProveedor.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrD.EliminarProveedor(id);
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnGuardarProveedor.setEnabled(true);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRutProveedor.getText()) && !"".equals(txtNombreProveedor.getText()) && !"".equals(txtTelefonoProveedor.getText()) && !"".equals(txtDireccionProveedor.getText())) {
                pr.setRut(txtRutProveedor.getText());
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrD.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnGuardarProveedor.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnGuardarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProdActionPerformed

        if (proD.existeProducto(txtCodigoProd.getText()) == 0) {
            if (!"".equals(txtCodigoProd.getText()) && !"".equals(txtDescripcionProd.getText()) && !"".equals(cmbProveedorProd.getSelectedItem()) && !"".equals(txtCantidadProd.getText()) && !"".equals(txtPrecioProd.getText())) {
                pro.setCodigo(txtCodigoProd.getText());
                pro.setNombre(txtDescripcionProd.getText());
                pro.setProveedor(cmbProveedorProd.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txtCantidadProd.getText()));
                pro.setPrecio(Integer.parseInt(txtPrecioProd.getText()));
                proD.RegistrarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Registrado con exito!");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();

            } else {
                JOptionPane.showMessageDialog(null, "Campos Vacios, porfavor llenar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "ERROR, Codigo existente");
            txtCodigoProd.setText("");
        }
    }//GEN-LAST:event_btnGuardarProdActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        btnEditarCantidad.setEnabled(false);
        if (txtRol.equals("Asistente")) {
            btnProveedor.setEnabled(false);
            btnEditarProd.setEnabled(false);
            btnGuardarProd.setEnabled(false);
            btnNuevoProd.setEnabled(false);
            btnEditarProd.setEnabled(false);
            btnEliminarProd.setEnabled(false);

        } else {
            jTabbedPane1.setSelectedIndex(3);
            txtIdProd.setEnabled(false);
            txtCodigoProd.setEnabled(false);
            txtDescripcionProd.setEnabled(false);
            cmbProveedorProd.setEnabled(false);
            txtCantidadProd.setEnabled(false);
            txtPrecioProd.setEnabled(false);
            LimpiarTable();
            ListarProductos();
            proD.ConsultarProveedor(cmbProveedorProd);
        }

    }//GEN-LAST:event_btnProductosActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        txtCodigoProd.setEnabled(false);
        btnEditarCantidad.setEnabled(true);
        txtDescripcionProd.setEnabled(true);
        cmbProveedorProd.setEnabled(true);
        txtCantidadProd.setEnabled(true);
        txtPrecioProd.setEnabled(true);
        txtCantidadProd.setEnabled(false);
        btnEditarProd.setEnabled(true);
        btnEliminarProd.setEnabled(true);
        btnGuardarProd.setEnabled(false);
        int fila = tblProductos.rowAtPoint(evt.getPoint());
        txtIdProd.setText(tblProductos.getValueAt(fila, 0).toString());
        txtCodigoProd.setText(tblProductos.getValueAt(fila, 1).toString());
        txtDescripcionProd.setText(tblProductos.getValueAt(fila, 2).toString());
        cmbProveedorProd.setSelectedItem(tblProductos.getValueAt(fila, 3).toString());
        txtCantidadProd.setText(tblProductos.getValueAt(fila, 4).toString());
        txtPrecioProd.setText(tblProductos.getValueAt(fila, 5).toString());
        txtCodigoProd.setEnabled(false);
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProdActionPerformed
        if (!"".equals(txtIdProd.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProd.getText());
                proD.EliminarProductos(id);
                LimpiarTable();

                ListarProductos();
                LimpiarProductos();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnGuardarProveedor.setEnabled(true);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarProdActionPerformed

    private void btnEditarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProdActionPerformed
        if ("".equals(txtIdProd.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodigoProd.getText()) && !"".equals(txtDescripcionProd.getText()) && !"".equals(txtCantidadProd.getText()) && !"".equals(txtPrecioProd.getText())) {
                pro.setCodigo(txtCodigoProd.getText());
                pro.setNombre(txtDescripcionProd.getText());
                pro.setProveedor(cmbProveedorProd.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txtCantidadProd.getText()));
                pro.setPrecio(Integer.parseInt(txtPrecioProd.getText()));
                pro.setId(Integer.parseInt(txtIdProd.getText()));
                proD.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnGuardarProveedor.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarProdActionPerformed

    private void txtCodigoVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoV.getText())) {
                String cod = txtCodigoV.getText();
                pro = proD.BuscarProducto(cod);
                if (pro.getNombre() != null) {
                    txtIdProd.setText("" + pro.getId());
                    txtDescripcionV.setText("" + pro.getNombre());
                    txtPrecioV.setText("" + pro.getPrecio());
                    txtStockV.setText("" + pro.getStock());
                    txtCantidadV.requestFocus();
                } else {
                    LimparVenta();
                    txtCodigoV.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoV.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVKeyPressed

    private void txtCantidadVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadV.getText())) {
                int cod = Integer.parseInt(txtCodigoV.getText());
                String descripcion = txtDescripcionV.getText();
                int cant = Integer.parseInt(txtCantidadV.getText());
                int precio = Integer.parseInt(txtPrecioV.getText());
                int total = cant * precio;
                int stock = Integer.parseInt(txtStockV.getText());
                if (stock >= cant) {
                    item = item + 1;
                    DefaultTableModel tmp = (DefaultTableModel) tblVenta.getModel();

                    for (int i = 0; i < tblVenta.getRowCount(); i++) {
                        if (tblVenta.getValueAt(i, 1).equals(txtDescripcionV.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    tblVenta.setModel(tmp);
                    TotalPagar();

                    LimparVenta();

                    txtCodigoV.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        try {
            modelo = (DefaultTableModel) tblVenta.getModel();
            modelo.removeRow(tblVenta.getSelectedRow());
            TotalPagar();
            txtCodigoV.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SELECCIONE UN OBJETO DE LA TABLA PARA QUITAR DE LA VENTA");
        }
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        txtIdCLiente.setEnabled(false);
        txtRutCliente.setEnabled(false);
        txtNombreCliente.setEnabled(false);
        txtTelefonoCliente.setEnabled(false);
        txtDireccionCliente.setEnabled(false);
        LimpiarTable();
        ListarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        LimpiarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void tblClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClientesKeyPressed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        txtRutCliente.setEnabled(false);
        txtNombreCliente.setEnabled(true);
        txtTelefonoCliente.setEnabled(true);
        txtDireccionCliente.setEnabled(true);
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = tblClientes.rowAtPoint(evt.getPoint());
        txtIdCLiente.setText(tblClientes.getValueAt(fila, 0).toString());
        txtRutCliente.setText(tblClientes.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(tblClientes.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(tblClientes.getValueAt(fila, 3).toString());
        txtDireccionCliente.setText(tblClientes.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        txtIdCLiente.setEnabled(true);
        txtRutCliente.setEnabled(true);
        txtNombreCliente.setEnabled(true);
        txtTelefonoCliente.setEnabled(true);
        txtDireccionCliente.setEnabled(true);

        LimpiarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCLiente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCLiente.getText());
                cliente.EliminarCliente(id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void txtRutVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutVKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String vRut = txtRutV.getText();
            if (validarRut(vRut)) {

                if (!"".equals(txtRutV.getText())) {
                    vRut = vRut.toUpperCase();
                    vRut = vRut.replace(".", "");
                    vRut = vRut.replace("-", "");
                    cl = cliente.BuscarCliente(vRut);
                    if (cl.getNombre() != null) {
                        txtNombreClienteV.setText("" + cl.getNombre());
                        txtTelefonoClienteV.setText("" + cl.getTelefono());
                        txtDireccionClienteV.setText("" + cl.getDireccion());

                    } else {
                        txtRutV.setText("");
                        JOptionPane.showMessageDialog(null, "El cliente no existe");
                    }
                }
            } else {
                txtRutV.setText("");
                JOptionPane.showMessageDialog(null, "RUT INVALIDO");
            }
        }
    }//GEN-LAST:event_txtRutVKeyPressed

    private void btnGenerarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVActionPerformed
        if (tblVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteV.getText())) {
                Registrarventa();
                RegistrarDetalle();
                ActualizarStock();
                pdf();
                limpiarTableVenta();
                LimpiarClienteVenta();
                lblTotal.setText("-----");
            } else if ("".equals(txtNombreClienteV.getText())) {
                Registrarventa();
                RegistrarDetalle();
                ActualizarStock();
                pdf();
                limpiarTableVenta();
                LimpiarClienteVenta();
                lblTotal.setText("-----");
            } else {

            }

        } else {

            JOptionPane.showMessageDialog(null, "No hay productos en la venta");
        }

    }//GEN-LAST:event_btnGenerarVActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        txtRutV.setEnabled(false);
        txtNombreClienteV.setEnabled(false);

    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        ListarConfig();
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void txtCodigoVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVKeyTyped

    private void txtDescripcionVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVKeyTyped

    private void txtCantidadVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVKeyTyped

    private void txtRutVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutVKeyTyped
        event.rutKeyPress(evt, txtRutCliente);
    }//GEN-LAST:event_txtRutVKeyTyped

    private void txtNombreClienteVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteVKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteVKeyTyped

    private void txtRutClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutClienteKeyTyped
        event.rutKeyPress(evt, txtRutCliente);
    }//GEN-LAST:event_txtRutClienteKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyTyped
        event.direccionKeyPress(evt);
    }//GEN-LAST:event_txtDireccionClienteKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtRutProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutProveedorKeyTyped
        event.rutKeyPress(evt, txtRutCliente);
    }//GEN-LAST:event_txtRutProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped
        event.direccionKeyPress(evt);
    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtCodigoProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProdKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoProdKeyTyped

    private void txtDescripcionProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionProdKeyTyped
        event.direccionKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionProdKeyTyped

    private void txtCantidadProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProdKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadProdKeyTyped

    private void txtPrecioProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProdKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtPrecioProdKeyTyped

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTable();
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void tblVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasMouseClicked
        int fila = tblVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(tblVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tblVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed

        try {
            int id = Integer.parseInt(txtIdVenta.getText());

            File file = new File("src/pdf/venta" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        Login ini = new Login();
        ini.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnConfigActionPerformed

    private void txtRutClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String vRut = txtRutCliente.getText();
            if (validarRut(vRut)) {

            } else {
                txtRutCliente.setText("");
                JOptionPane.showMessageDialog(null, "RUT INVALIDO");
            }
        }
    }//GEN-LAST:event_txtRutClienteKeyPressed

    private void txtRutClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutClienteFocusLost
        String vRut = txtRutCliente.getText();
        if (validarRut(vRut)) {

        } else {
            txtRutCliente.setText("");
            JOptionPane.showMessageDialog(null, "RUT INVALIDO");
        }
    }//GEN-LAST:event_txtRutClienteFocusLost

    private void txtRutProveedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutProveedorFocusLost
        String vRut = txtRutProveedor.getText();
        if (validarRut(vRut)) {

        } else {
            txtRutProveedor.setText("");
            JOptionPane.showMessageDialog(null, "RUT INVALIDO");
        }
    }//GEN-LAST:event_txtRutProveedorFocusLost

    private void txtRutProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutProveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String vRut = txtRutProveedor.getText();
            if (validarRut(vRut)) {

            } else {
                txtRutProveedor.setText("");
                JOptionPane.showMessageDialog(null, "RUT INVALIDO");
            }
        }
    }//GEN-LAST:event_txtRutProveedorKeyPressed

    private void btnNuevoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProdActionPerformed
        btnEditarCantidad.setEnabled(false);
        txtIdProd.setText("");
        txtCodigoProd.setText("");
        txtDescripcionProd.setText("");

        txtCantidadProd.setText("");
        txtPrecioProd.setText("");

        txtIdProd.setEnabled(true);
        txtCodigoProd.setEnabled(true);
        txtDescripcionProd.setEnabled(true);
        cmbProveedorProd.setEnabled(true);
        txtCantidadProd.setEnabled(true);
        txtPrecioProd.setEnabled(true);
        btnEditarProd.setEnabled(false);
        btnEliminarProd.setEnabled(false);
        btnGuardarProd.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProdActionPerformed

    private void btnIngresarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarClienteActionPerformed
        txtRutV.setEnabled(true);
        txtNombreClienteV.setEnabled(true);

    }//GEN-LAST:event_btnIngresarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        if ("".equals(txtIdCLiente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRutCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDireccionCliente.getText())) {
                cl.setRut(txtRutCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDireccionCliente.getText());
                cl.setId(Integer.parseInt(txtIdCLiente.getText()));
                cliente.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarTable();
                ListarCliente();
                LimpiarCliente();
                btnEditarCliente.setEnabled(false);
                btnEliminarCliente.setEnabled(false);
                btnGuardarCliente.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void txtVueltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVueltoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVueltoActionPerformed

    private void txtVueltoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVueltoKeyTyped
       
    }//GEN-LAST:event_txtVueltoKeyTyped

    private void btnvueltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvueltoActionPerformed
        int num1 = Integer.parseInt(txtVuelto.getText());
       int num2 =  Integer.parseInt(lblTotal.getText());
       
       try{
       if (num1 >= num2){
       int Vuelto = (num1 - num2);
        lblVuelto.setText(String.valueOf(Vuelto));
       }else{
        JOptionPane.showMessageDialog(null, "Verifique Numero Ingresado");
       }}catch(Exception e){
           JOptionPane.showMessageDialog(null, "Debe ingresar un monto a la venta");
       }
        
        
    }//GEN-LAST:event_btnvueltoActionPerformed

    private void btnEditarCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCantidadActionPerformed
        txtCantidadProd.setEnabled(true);
    }//GEN-LAST:event_btnEditarCantidadActionPerformed

    private void txtVueltoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVueltoFocusGained
        btnvuelto.setEnabled(true);
    }//GEN-LAST:event_txtVueltoFocusGained

    private void txtVueltoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVueltoFocusLost
        
    }//GEN-LAST:event_txtVueltoFocusLost

    private void txtCodigoProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProdFocusLost
        if (proD.existeProducto(txtCodigoProd.getText()) == 0) {
            
            } else {
            JOptionPane.showMessageDialog(null, "ERROR, Codigo existente");
            txtCodigoProd.setText("");
        }
        
    }//GEN-LAST:event_txtCodigoProdFocusLost

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
        
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtBuscarProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyTyped
       buscar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarProveedorKeyTyped

    private void txtBuscarProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyPressed
        buscarProv(txtBuscarProveedor.getText());
    }//GEN-LAST:event_txtBuscarProveedorKeyPressed

    private void txtBuscarProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyReleased
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarProveedorKeyReleased

    private void txtBuscarclienteFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarclienteFiltroKeyPressed
       buscarCli(txtBuscarclienteFiltro.getText());
    }//GEN-LAST:event_txtBuscarclienteFiltroKeyPressed

    private void btnRegistrarUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUActionPerformed
        Registro reg = new Registro();
                reg.setVisible(true);
                dispose();
    }//GEN-LAST:event_btnRegistrarUActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCantidad;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProd;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProd;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnGenerarV;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProd;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnIngresarCliente;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoProd;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnRegistrarU;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnvuelto;
    private javax.swing.JComboBox<String> cmbProveedorProd;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JLabel lblVuelto;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTable tblVentas;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarProveedor;
    private javax.swing.JTextField txtBuscarclienteFiltro;
    private javax.swing.JTextField txtCantidadProd;
    private javax.swing.JTextField txtCantidadV;
    private javax.swing.JTextField txtCodigoProd;
    private javax.swing.JTextField txtCodigoV;
    private javax.swing.JTextField txtDescripcionProd;
    private javax.swing.JTextField txtDescripcionV;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionClienteV;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtIdCLiente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdV;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdprod;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteV;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioProd;
    private javax.swing.JTextField txtPrecioV;
    private javax.swing.JTextField txtRol;
    private javax.swing.JTextField txtRutCliente;
    private javax.swing.JTextField txtRutConfig;
    private javax.swing.JTextField txtRutProveedor;
    private javax.swing.JTextField txtRutV;
    private javax.swing.JTextField txtStockV;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoClienteV;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtVuelto;
    // End of variables declaration//GEN-END:variables
}
