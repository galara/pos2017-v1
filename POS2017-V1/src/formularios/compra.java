/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import clases.AddForms;
import clases.Datos;
import clases.FormatoDecimal;
import clases.Peticiones;
import clases.Utilidades;
import com.sun.prism.paint.Color;
import static formularios.frmPrincipal.panel_center;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GLARA
 */
public class compra extends javax.swing.JInternalFrame {

    /**
     * Variables para realizar las transacciones con la base de datos
     */
    String nombreTabla = "clientes";
    //String[] titulos = {"Id", "Código", "Nombre Cliente", "Dirección", "Nit", "Limite Créd"};
    String[] titulos = {"Id", "Código", "Descripción del producto", "Cantidad", "Unidad", "Precio Normal", "Precio C.Descuento", "Descuento %", "Subtotal"};
    String campos = "codigo, nombre, direccion, correo, nit, telefono, fec_reg, lim_cred, estado";
    String nombreId = "idClientes";
    String valorId = "";

    DefaultTableModel model;
    Datos datos = new Datos();
    Peticiones peticiones = new Peticiones();
    boolean editar = false;

    /**
     * Creates new form Cliente
     */
    public compra() {
        initComponents();

        /**
         * Oculta la primer columna de la tala, la que contiene el Id
         */
        tableResultados.getColumnModel().getColumn(0).setMaxWidth(0);
        tableResultados.getColumnModel().getColumn(0).setMinWidth(0);
        tableResultados.getColumnModel().getColumn(0).setPreferredWidth(0);
        //Utilidades.ajustarAnchoColumnas(tableResultados);
        
        tableResultados.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                //sumar_total();
                cambiar_precio.setVisible(true);
                //sumartotal();
                //formatotabla();
            }
        });
    }

    /**
     * Prepara el formulario y jtable para crear un nuevo cliente (Habilita y
     * limpia los campos correspondientes
     */
    public void nuevo() {
        //Utilidades.setEditableTexto(this.panelFormulario, true, null, true, "");
        //Utilidades.setEditableTexto(this.panelBusqueda, false, null, true, "");
//        Utilidades.setEditableTexto(this.panelResultados, false, null, true, "");
//        Utilidades.buscarBotones(this.panelBotonesformulario, true, null);
//        model.setRowCount(0);
//        txtBusqueda.requestFocus();
    }

    private String Validar(String x) {
        String y;
        if (x.equals("")) {
            y = "0";
            return y;
        } else {
            y = x;
            return y;
        }
    }

    /**
     * Obtiene la fecha de un JDateChooser, y devuelve la fecha como un string
     *
     * @return fecha
     */
    private String getFecha() {

        try {
            String fecha;
            int años = dateFecha.getCalendar().get(Calendar.YEAR);
            int dias = dateFecha.getCalendar().get(Calendar.DAY_OF_MONTH);
            int mess = dateFecha.getCalendar().get(Calendar.MONTH) + 1;
            int hours = dateFecha.getCalendar().get(Calendar.HOUR_OF_DAY);
            int minutes = dateFecha.getCalendar().get(Calendar.MINUTE);
            int seconds = dateFecha.getCalendar().get(Calendar.SECOND);

            fecha = "" + años + "-" + mess + "-" + dias + " " + hours + ":" + minutes + ":" + seconds;
            return fecha;
        } catch (Exception e) {
            JOptionPane.showInternalMessageDialog(this, "Verifique la fecha");

        }
        return null;
    }

    public void sumar_total() {
        //corregir cuando hay solo unalinea da error
        if (tableResultados.getRowCount() == 0 && tableResultados.getSelectedRow() == -1) {
            //JOptionPane.showMessageDialog(null, "La tabla no contiene datos que modificar");
            txtTotal.setValue(0.00);
        } else {
            float total_actual, total_final = 0;

            for (int i = 0; i < model.getRowCount(); i++) {

                //if (tabladetallecompra.getValueAt(i, 6).toString().equals("true")) {
                total_actual = Float.parseFloat(tableResultados.getValueAt(i, 8).toString());
                total_final = total_final + total_actual;
                // }

            }
            txtTotal.setValue(Math.round(total_final * 100.0) / 100.0);
        }
    }
    
    public void modificar_precio() {

        Float ca, pr;
        int fila = tableResultados.getSelectedRow();
        ca = Float.parseFloat(Validar(tableResultados.getValueAt(fila, 3).toString()));
        pr = Float.parseFloat(Validar(tableResultados.getValueAt(fila, 4).toString()));
        //ps.setText("" + tableResultados.getSelectedRow());
        if (tableResultados.getSelectedColumn() == 6) {
        } else {
            

//            cambio.setVisible(true);
//            Cantidad.setValue(ca);
//            precio.setValue(pr);
//            cambio.setSize(319, 115);
//            cambio.setResizable(false);
//            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
//            Dimension ventana = cambio.getSize();
//            cambio.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
//            cambio.toFront();//aparece al frente
//            sumartotal();
        }
    }
    /* Funcion para llenar la tabla cuando se busque un cliente en especifico
     por el código, nombre, nit  */
    public void buscarCliente(String nombre) {

        try {
            /* Limpiamos los campos*/
            txtNombrecliente.setText("");

            /* Llamamos a la funcion consultaClientes la cual nos devuelve todos 
             los clientes relaciones con el valor a buscar en la base de datos. 
            
             - Los datos recibidos lo guardamos en el objeto ResulSet para luego
             llenar la tabla con los registros.
            
             */
            ResultSet rs = peticiones.consultaNit_Clientes(nombre);
            Object[] registro = new Object[4];

            if (rs.next()) {//verifica si esta vacio, pero desplaza el puntero al siguiente elemento
                rs.beforeFirst();//regresa el puntero al primer registro
                /* Hacemos un while que mientras en rs hallan datos el ira agregando filas a la tabla. */
                while (rs.next()) {

                    valorId = rs.getString("idClientes");
                    txtNit.setText(rs.getString("nit"));
                    txtNombrecliente.setText(rs.getString("nombre"));
                }
                txtBusqueda.requestFocus();
                txtBusqueda.setEditable(true);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
        }
    }

    /* Funcion para llenar la tabla cuando se busque un cliente en especifico
     por el código, nombre, nit  */
    public void buscarProducto_codigo(String nombre) {

        try {
            /* Limpiamos los campos*/
            txIdproducto.setText("");
            txtCodigo.setText("");
            txtNombre.setText("");
            txtUnidad.setText("");
            txtCantidad.setValue(null);
            txtPrecio.setValue(null);
            txtDescuento.setValue(null);
            txtImporte.setValue(null);
            txtExistencia.setValue(null);

            /* Llamamos a la funcion consultaClientes la cual nos devuelve todos 
             los clientes relaciones con el valor a buscar en la base de datos. 
            
             - Los datos recibidos lo guardamos en el objeto ResulSet para luego
             llenar la tabla con los registros.
            
             */
            ResultSet rs = peticiones.consultaCodigo_Producto(nombre);
            //Object[] registro = new Object[4];

            if (rs.next()) {//verifica si esta vacio, pero desplaza el puntero al siguiente elemento
                rs.beforeFirst();//regresa el puntero al primer registro
                /* Hacemos un while que mientras en rs hallan datos el ira agregando filas a la tabla. */
                while (rs.next()) {

                    txIdproducto.setText(rs.getString("producto.idproducto"));
                    txtCodigo.setText(rs.getString("producto.codigo"));
                    txtNombre.setText(rs.getString("producto.nombre"));
                    txtUnidad.setText(rs.getString("unidad.nombre"));
                    //txtCantidad.setText("");
                    txtPrecio.setValue(rs.getFloat("producto.precioventa"));
                    //txtPrecio.setText("preciomayoreo");
                    txtExistencia.setValue(rs.getFloat("producto.existencia"));
                    //txtDescuento.setText("");
                    //txtImporte.setText("");

                    //txtNit.setText(rs.getString("nit"));
                    //txtNombrecliente.setText(rs.getString("nombre"));
                }
                txtCantidad.requestFocus();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
        }
    }

    /* Funcion para llenar la tabla cuando se busque un cliente en especifico
     por el código, nombre, nit  */
    public void agregarProducto(String nombre) {

        // try {
        /* Limpiamos la tabla */
        //model.setRowCount(0);

        /* Llamamos a la funcion consultaClientes la cual nos devuelve todos 
             los clientes relaciones con el valor a buscar en la base de datos. 
            
             - Los datos recibidos lo guardamos en el objeto ResulSet para luego
             llenar la tabla con los registros.
            
         */
        //ResultSet rs = peticiones.consultaClientes(nombre);
        Object[] registro = new Object[9];

        /* Hacemos un while que mientras en rs hallan datos el ira agregando
             filas a la tabla. */
        //while (rs.next()) {
        registro[0] = txIdproducto.getText();
        registro[1] = txtCodigo.getText();
        registro[2] = txtNombre.getText();
        registro[3] = Float.parseFloat(txtCantidad.getText());
        registro[4] = txtUnidad.getText();
        registro[5] = Float.parseFloat(txtPrecio.getText());
        registro[6] = Float.parseFloat(txtPreciocondescuento.getText());
        registro[7] = Float.parseFloat(txtDescuento.getText());
        registro[8] = Float.parseFloat(txtImporte.getText());
        //registro[6] = rs.getBoolean("estado"); //getString("lim_cred");
        model.addRow(registro);
        // }
        tableResultados.setModel(model);
        Utilidades.ajustarAnchoColumnas(tableResultados);
        sumar_total();

        txIdproducto.setText("");
        txtBusqueda.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtUnidad.setText("");
        txtCantidad.setValue(null);
        txtPrecio.setValue(null);
        txtDescuento.setValue(null);
        txtImporte.setValue(null);
        txtExistencia.setValue(null);
        txtBusqueda.requestFocus();
        
        //} 
//        catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
//        }
    }

    /* Funcion para llenar la tabla cuando se busque un cliente en especifico
     por el código, nombre, nit  */
    public void llenarTabla(String nombre) {

        try {
            /* Limpiamos la tabla */
            model.setRowCount(0);

            /* Llamamos a la funcion consultaClientes la cual nos devuelve todos 
             los clientes relaciones con el valor a buscar en la base de datos. 
            
             - Los datos recibidos lo guardamos en el objeto ResulSet para luego
             llenar la tabla con los registros.
            
             */
            ResultSet rs = peticiones.consultaClientes(nombre);
            Object[] registro = new Object[7];

            /* Hacemos un while que mientras en rs hallan datos el ira agregando
             filas a la tabla. */
            while (rs.next()) {

                registro[0] = rs.getString("idClientes");
                registro[1] = rs.getString("codigo");
                registro[2] = rs.getString("nombre");
                registro[3] = rs.getString("direccion");
                registro[4] = rs.getString("nit");
                registro[5] = rs.getString("lim_cred");
                //registro[6] = rs.getBoolean("estado"); //getString("lim_cred");
                model.addRow(registro);
            }
            tableResultados.setModel(model);
            Utilidades.ajustarAnchoColumnas(tableResultados);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
        }
    }

//    /* Funcion para llenar la tabla cuando se busque un cliente en especifico
//     por el Id */
//    public void llenarFormulario(int s) {
//
//        try {
//
//            /* Llamamos a la funcion consultaRegistrosId la cual nos devuelve todos 
//             los empleados relaciones con el id a buscar en la base de datos. 
//            
//             - Los datos recibidos lo guardamos en el objeto ResulSet para luego
//             llenar la tabla con los registros.
//            
//             */
//            ResultSet rs = peticiones.consultaRegistrosId(nombreTabla,
//                    (String) tableResultados.getValueAt(s, 0), nombreId);
//
//            /* Hacemos un while que mientras en rs hallan datos el ira agregando
//             filas a la tabla. */
//            while (rs.next()) {
//                //txtCodigo.setText(rs.getString("codigo"));
//                //txtNombre.setText(rs.getString("nombre"));
//                txtNit.setText(rs.getString("direccion"));
//                //txtCorreo.setText(rs.getString("correo"));
//                //txtNit.setText(rs.getString("nit"));
//                //txtTelefono.setText(rs.getString("telefono"));
//                dateFecha.setDate(rs.getDate("fec_reg"));
//                //txtTotal.setText(rs.getString("lim_cred"));
//                //rbEstado.setSelected(rs.getBoolean("estado"));
//
//            }
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
//        }
//
//    }

    /**
     * Realiza la transacción para guardar los recistros de un nuevo cliente
     */
    private void Guardar() {

        if (Utilidades.esObligatorio(this.panelFormulario, true)) {
            JOptionPane.showInternalMessageDialog(this, "Los campos marcados son Obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] cliente = {
            /*txtCodigo.getText(), txtNombre.getText(),*/txtNit.getText(),
            /*txtCorreo.getText(), txtNit.getText(),
            txtTelefono.getText(), getFecha(), */ Validar(txtTotal.getText())/*,
            peticiones.selected(rbEstado)*/
        };

        /* Llamamos a la funcion guardarRegistros la cual recibe como parametro
         el nombre de la tabla, los campos y los valores a insertar del cliente */
        if (peticiones.guardarRegistros(nombreTabla, campos, cliente)) {
            JOptionPane.showMessageDialog(rootPane, "El registro ha sido Guardado correctamente ");
            nuevo();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se ha podido Guardar el registro, por favor verifique los datos");
        }
    }

    /**
     * Modifica el registro seleccionado
     */
    private void Modificar() {
        int s = 0;

        /* Guardamos el ID de dla fila selecciona en la variable s */
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

        if (Utilidades.esObligatorio(this.panelFormulario, true)) {
            JOptionPane.showInternalMessageDialog(this, "Los campos marcados son"
                    + " Obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = Utilidades.objectToString(tableResultados.getValueAt(s, 0));

        Object[] cliente = {
            /*txtCodigo.getText(), txtNombre.getText(),*/txtNit.getText(),
            /*txtCorreo.getText(), txtNit.getText(),
            txtTelefono.getText(),*/ getFecha(), Validar(txtTotal.getText()),
            /*peticiones.selected(rbEstado),*/ id
        };

        if (peticiones.actualizarRegistroId(nombreTabla, campos, cliente, nombreId)) {
            JOptionPane.showMessageDialog(rootPane, "El registro ha sido Modificado correctamente ");
            nuevo();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se ha podido Modificar"
                    + " registro, por favor verifique los datos");
        }
    }

    /**
     * Al dar clic sobre la tabla, llenará el formulario con el registro
     * seleccionado
     */
    private void tableMouseClicked() {

        /* Variable que contendra el ID de la fila seleccionada */
        int s = 0;

        /* Limpiamos los campos del formulario */
        //Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        //Utilidades.buscarBotones(this.panelBotonesformulario, false, null);

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

        //llenarFormulario(s);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cambiar_precio = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPrecio1 = new javax.swing.JFormattedTextField();
        txtPrecio2 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panelImage = new elaprendiz.gui.panel.PanelImage();
        panelBotones = new javax.swing.JPanel();
        bnBuscar = new javax.swing.JButton();
        bnCrear = new javax.swing.JButton();
        bnSuprimir = new javax.swing.JButton();
        bnDeudores = new javax.swing.JButton();
        bnEstadocuenta = new javax.swing.JButton();
        bnEditar = new javax.swing.JButton();
        panelBusqueda = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtNit = new elaprendiz.gui.textField.TextField();
        labelCorreo4 = new javax.swing.JLabel();
        txtNombrecliente = new elaprendiz.gui.textField.TextField();
        jButton2 = new javax.swing.JButton();
        labelFecha = new javax.swing.JLabel();
        dateFecha = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        labelCodigo3 = new javax.swing.JLabel();
        txtUnidad = new elaprendiz.gui.textField.TextField();
        txtNombre = new elaprendiz.gui.textField.TextField();
        labelCodigo2 = new javax.swing.JLabel();
        labelCodigo4 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JFormattedTextField();
        txtPrecio = new javax.swing.JFormattedTextField();
        labelCodigo5 = new javax.swing.JLabel();
        labelCodigo1 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        labelCodigo7 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JFormattedTextField();
        txtExistencia = new javax.swing.JFormattedTextField();
        labelBusqueda = new javax.swing.JLabel();
        txIdproducto = new elaprendiz.gui.textField.TextField();
        txtBusqueda = new elaprendiz.gui.textField.TextField();
        txtCodigo = new elaprendiz.gui.textField.TextField();
        txtPreciocondescuento = new javax.swing.JFormattedTextField();
        panelResultados = new javax.swing.JPanel();
        scrollpaneResultados = new javax.swing.JScrollPane();
        tableResultados = new javax.swing.JTable();
        panelFormulario = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelCorreo3 = new javax.swing.JLabel();
        rbEstado1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtTotal = new javax.swing.JFormattedTextField();
        panelEncabezado = new jcMousePanel.jcMousePanel();
        labelEncabezado = new javax.swing.JLabel();
        panelBotonesformulario = new javax.swing.JPanel();
        bnGuardar = new javax.swing.JButton();
        bnCancelar = new javax.swing.JButton();

        cambiar_precio.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        cambiar_precio.setMaximumSize(new java.awt.Dimension(236, 185));
        cambiar_precio.setMinimumSize(new java.awt.Dimension(236, 185));
        cambiar_precio.setUndecorated(true);

        jPanel1.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 1, true));
        jPanel1.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel4.setText("Precio Normal:");

        jLabel5.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel5.setText("Nuevo Precio:");

        jLabel6.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel6.setText("Q");

        txtPrecio1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPrecio1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtPrecio1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecio1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPrecio1.setName("costo"); // NOI18N
        txtPrecio1.setPreferredSize(new java.awt.Dimension(70, 23));

        txtPrecio2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPrecio2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtPrecio2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecio2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPrecio2.setName("costo"); // NOI18N
        txtPrecio2.setPreferredSize(new java.awt.Dimension(70, 23));

        jLabel8.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel8.setText("%");

        jLabel7.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel7.setText("Descuento:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 1, true));
        jPanel3.setOpaque(false);

        jButton1.setText("Cambiar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("Cancelar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout cambiar_precioLayout = new javax.swing.GroupLayout(cambiar_precio.getContentPane());
        cambiar_precio.getContentPane().setLayout(cambiar_precioLayout);
        cambiar_precioLayout.setHorizontalGroup(
            cambiar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        cambiar_precioLayout.setVerticalGroup(
            cambiar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cambiar_precioLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setName("venta"); // NOI18N
        setOpaque(true);

        panelImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.setLayout(null);

        panelBotones.setBackground(java.awt.SystemColor.controlHighlight);
        panelBotones.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelBotones.setFocusable(false);

        bnBuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        bnBuscar.setText("Buscar");
        bnBuscar.setToolTipText("");
        bnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnBuscarActionPerformed(evt);
            }
        });

        bnCrear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        bnCrear.setText("Crear");
        bnCrear.setToolTipText("");
        bnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnCrearActionPerformed(evt);
            }
        });

        bnSuprimir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bnSuprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bnSuprimir.setText("Suprimir");
        bnSuprimir.setToolTipText("");
        bnSuprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnSuprimirActionPerformed(evt);
            }
        });

        bnDeudores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bnDeudores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deudores.png"))); // NOI18N
        bnDeudores.setText("Deudores");
        bnDeudores.setToolTipText("");
        bnDeudores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnDeudoresActionPerformed(evt);
            }
        });

        bnEstadocuenta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bnEstadocuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reports.png"))); // NOI18N
        bnEstadocuenta.setText("Estado Cuenta");
        bnEstadocuenta.setToolTipText("");
        bnEstadocuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnEstadocuentaActionPerformed(evt);
            }
        });

        bnEditar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        bnEditar.setText("Editar");
        bnEditar.setToolTipText("");
        bnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bnCrear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnSuprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnDeudores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnEstadocuenta)
                .addContainerGap(264, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnSuprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnDeudores, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnEstadocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelImage.add(panelBotones);
        panelBotones.setBounds(0, 22, 890, 68);

        panelBusqueda.setBackground(new java.awt.Color(255, 255, 255));
        panelBusqueda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBusqueda.setPreferredSize(new java.awt.Dimension(890, 82));
        panelBusqueda.setLayout(null);

        jPanel4.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 1, true));
        jPanel4.setOpaque(false);

        txtNit.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNit.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNit.setName("txtNit"); // NOI18N
        txtNit.setOpaque(true);
        txtNit.setPreferredSize(new java.awt.Dimension(120, 21));
        txtNit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNitActionPerformed(evt);
            }
        });

        labelCorreo4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelCorreo4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelCorreo4.setText("Cliente:");
        labelCorreo4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNombrecliente.setEditable(false);
        txtNombrecliente.setBackground(new java.awt.Color(255, 255, 255));
        txtNombrecliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombrecliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombrecliente.setName("txtDireccion"); // NOI18N
        txtNombrecliente.setOpaque(true);
        txtNombrecliente.setPreferredSize(new java.awt.Dimension(120, 21));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search16.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        labelFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelFecha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelFecha.setText("Fecha:");
        labelFecha.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        dateFecha.setDate(Calendar.getInstance().getTime());
        dateFecha.setDateFormatString("dd/MM/yyyy");
        dateFecha.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        dateFecha.setIcon(null);
        dateFecha.setMaxSelectableDate(new java.util.Date(3093496470100000L));
        dateFecha.setMinSelectableDate(new java.util.Date(-62135744300000L));
        dateFecha.setPreferredSize(new java.awt.Dimension(120, 22));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(labelCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(txtNombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(labelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        panelBusqueda.add(jPanel4);
        jPanel4.setBounds(10, 10, 870, 60);

        jPanel5.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 1, true));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

        labelCodigo3.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo3.setText("Unidad:");
        labelCodigo3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo3);
        labelCodigo3.setBounds(420, 14, 60, 25);

        txtUnidad.setEditable(false);
        txtUnidad.setBackground(new java.awt.Color(255, 255, 255));
        txtUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUnidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUnidad.setName("descripcion"); // NOI18N
        txtUnidad.setOpaque(true);
        txtUnidad.setPreferredSize(new java.awt.Dimension(120, 21));
        jPanel5.add(txtUnidad);
        txtUnidad.setBounds(480, 14, 190, 25);

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombre.setName("descripcion"); // NOI18N
        txtNombre.setOpaque(true);
        txtNombre.setPreferredSize(new java.awt.Dimension(120, 21));
        jPanel5.add(txtNombre);
        txtNombre.setBounds(110, 70, 290, 25);

        labelCodigo2.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo2.setText("Descripción del producto");
        labelCodigo2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo2);
        labelCodigo2.setBounds(110, 50, 290, 18);

        labelCodigo4.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo4.setText("Cantidad");
        labelCodigo4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo4);
        labelCodigo4.setBounds(430, 50, 80, 18);

        txtCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCantidad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtCantidad.setName("costo"); // NOI18N
        txtCantidad.setPreferredSize(new java.awt.Dimension(80, 23));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        jPanel5.add(txtCantidad);
        txtCantidad.setBounds(430, 70, 80, 25);

        txtPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPrecio.setName("costo"); // NOI18N
        txtPrecio.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel5.add(txtPrecio);
        txtPrecio.setBounds(520, 70, 70, 25);

        labelCodigo5.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo5.setText("Precio");
        labelCodigo5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo5);
        labelCodigo5.setBounds(520, 50, 70, 18);

        labelCodigo1.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo1.setText("Descuento");
        labelCodigo1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo1);
        labelCodigo1.setBounds(600, 50, 70, 18);

        txtDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtDescuento.setName("costo"); // NOI18N
        txtDescuento.setPreferredSize(new java.awt.Dimension(70, 23));
        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });
        jPanel5.add(txtDescuento);
        txtDescuento.setBounds(600, 70, 70, 25);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/product.png"))); // NOI18N
        jPanel5.add(jLabel1);
        jLabel1.setBounds(120, 14, 20, 27);

        labelCodigo7.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo7.setText("Existencia:");
        labelCodigo7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo7);
        labelCodigo7.setBounds(690, 10, 160, 25);

        txtImporte.setEditable(false);
        txtImporte.setBackground(new java.awt.Color(255, 255, 255));
        txtImporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtImporte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtImporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImporte.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtImporte.setEnabled(false);
        txtImporte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtImporte.setName("costo"); // NOI18N
        txtImporte.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel5.add(txtImporte);
        txtImporte.setBounds(10, 80, 10, 10);
        txtImporte.setVisible(false);

        txtExistencia.setEditable(false);
        txtExistencia.setBackground(new java.awt.Color(255, 255, 255));
        txtExistencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtExistencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtExistencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExistencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtExistencia.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        txtExistencia.setName("costo"); // NOI18N
        txtExistencia.setPreferredSize(new java.awt.Dimension(80, 23));
        jPanel5.add(txtExistencia);
        txtExistencia.setBounds(690, 30, 160, 65);

        labelBusqueda.setFont(new java.awt.Font("Decker", 1, 18)); // NOI18N
        labelBusqueda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBusqueda.setText("Código :");
        jPanel5.add(labelBusqueda);
        labelBusqueda.setBounds(21, 12, 90, 27);

        txIdproducto.setEditable(false);
        txIdproducto.setBackground(new java.awt.Color(255, 255, 255));
        txIdproducto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txIdproducto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txIdproducto.setEnabled(false);
        txIdproducto.setName("txtNit"); // NOI18N
        txIdproducto.setOpaque(true);
        txIdproducto.setPreferredSize(new java.awt.Dimension(120, 21));
        txIdproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIdproductoActionPerformed(evt);
            }
        });
        jPanel5.add(txIdproducto);
        txIdproducto.setBounds(2, 12, 15, 14);
        txIdproducto.setVisible(false);

        txtBusqueda.setEditable(false);
        txtBusqueda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBusqueda.setOpaque(true);
        txtBusqueda.setPreferredSize(new java.awt.Dimension(250, 27));
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        jPanel5.add(txtBusqueda);
        txtBusqueda.setBounds(110, 14, 290, 27);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);
        txtCodigo.setName("txtNit"); // NOI18N
        txtCodigo.setOpaque(true);
        txtCodigo.setPreferredSize(new java.awt.Dimension(120, 21));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel5.add(txtCodigo);
        txtCodigo.setBounds(2, 33, 15, 14);
        txtCodigo.setVisible(false);

        txtPreciocondescuento.setEditable(false);
        txtPreciocondescuento.setBackground(new java.awt.Color(255, 255, 255));
        txtPreciocondescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPreciocondescuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtPreciocondescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPreciocondescuento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPreciocondescuento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPreciocondescuento.setName("costo"); // NOI18N
        txtPreciocondescuento.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel5.add(txtPreciocondescuento);
        txtPreciocondescuento.setBounds(10, 60, 10, 10);
        txtPreciocondescuento.setVisible(false);

        panelBusqueda.add(jPanel5);
        jPanel5.setBounds(10, 80, 870, 110);

        panelImage.add(panelBusqueda);
        panelBusqueda.setBounds(0, 88, 890, 200);

        panelResultados.setBackground(new java.awt.Color(255, 255, 255));
        panelResultados.setPreferredSize(new java.awt.Dimension(786, 402));
        panelResultados.setLayout(new java.awt.BorderLayout());

        scrollpaneResultados.setBackground(new java.awt.Color(255, 255, 255));
        scrollpaneResultados.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tableResultados.setModel(model = new DefaultTableModel(null, titulos)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    /*if(column==6  ){
                        return true;
                    }else{
                        return false;}*/
                    return false;
                }
            });
            tableResultados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            tableResultados.setFocusCycleRoot(true);
            tableResultados.setRowHeight(24);
            tableResultados.setSurrendersFocusOnKeystroke(true);
            tableResultados.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tableResultadosMouseClicked(evt);
                }
            });
            scrollpaneResultados.setViewportView(tableResultados);

            panelResultados.add(scrollpaneResultados, java.awt.BorderLayout.CENTER);

            panelImage.add(panelResultados);
            panelResultados.setBounds(0, 289, 890, 290);

            panelFormulario.setBackground(new java.awt.Color(255, 255, 255));
            panelFormulario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            panelFormulario.setLayout(null);

            jPanel2.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 1, true));
            jPanel2.setOpaque(false);

            labelCorreo3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            labelCorreo3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            labelCorreo3.setText("Pagado");
            labelCorreo3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

            rbEstado1.setBackground(new java.awt.Color(255, 255, 255));
            rbEstado1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
            rbEstado1.setSelected(true);
            rbEstado1.setEnabled(false);

            jLabel2.setFont(new java.awt.Font("Decker", 1, 48)); // NOI18N
            jLabel2.setForeground(new java.awt.Color(0, 102, 255));
            jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            jLabel2.setText("Q");

            jButton3.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
            jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search24.png"))); // NOI18N
            jButton3.setText("Consultar");

            jButton4.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
            jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/yast_printer.png"))); // NOI18N
            jButton4.setText("Re imprimir");
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }
            });

            txtTotal.setEditable(false);
            txtTotal.setBackground(new java.awt.Color(255, 255, 255));
            txtTotal.setBorder(null);
            txtTotal.setForeground(new java.awt.Color(0, 102, 255));
            txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
            txtTotal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            txtTotal.setToolTipText("");
            txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            txtTotal.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
            txtTotal.setPreferredSize(new java.awt.Dimension(80, 23));

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jButton4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton3)
                    .addGap(53, 53, 53)
                    .addComponent(labelCorreo3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(rbEstado1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(rbEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelCorreo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            );

            panelFormulario.add(jPanel2);
            jPanel2.setBounds(10, 10, 870, 62);

            panelImage.add(panelFormulario);
            panelFormulario.setBounds(0, 580, 890, 80);

            panelEncabezado.setColor1(new java.awt.Color(102, 153, 255));
            panelEncabezado.setColor2(new java.awt.Color(255, 255, 255));
            panelEncabezado.setModo(3);

            labelEncabezado.setFont(new java.awt.Font("Decker", 1, 17)); // NOI18N
            labelEncabezado.setForeground(new java.awt.Color(255, 255, 255));
            labelEncabezado.setText("COMPRA");

            javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
            panelEncabezado.setLayout(panelEncabezadoLayout);
            panelEncabezadoLayout.setHorizontalGroup(
                panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelEncabezadoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(123, Short.MAX_VALUE))
            );
            panelEncabezadoLayout.setVerticalGroup(
                panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelEncabezadoLayout.createSequentialGroup()
                    .addComponent(labelEncabezado)
                    .addGap(0, 0, Short.MAX_VALUE))
            );

            panelImage.add(panelEncabezado);
            panelEncabezado.setBounds(0, 0, 890, 22);

            panelBotonesformulario.setBackground(java.awt.SystemColor.activeCaption);
            panelBotonesformulario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            panelBotonesformulario.setLayout(new java.awt.GridBagLayout());

            bnGuardar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            bnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresar.png"))); // NOI18N
            bnGuardar.setText("Cobrar Venta");
            bnGuardar.setToolTipText("");
            bnGuardar.setMaximumSize(new java.awt.Dimension(113, 41));
            bnGuardar.setMinimumSize(new java.awt.Dimension(113, 41));
            bnGuardar.setPreferredSize(new java.awt.Dimension(151, 41));
            bnGuardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    bnGuardarActionPerformed(evt);
                }
            });
            panelBotonesformulario.add(bnGuardar, new java.awt.GridBagConstraints());

            bnCancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            bnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
            bnCancelar.setText("Cancelar Venta");
            bnCancelar.setToolTipText("");
            bnCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    bnCancelarActionPerformed(evt);
                }
            });
            panelBotonesformulario.add(bnCancelar, new java.awt.GridBagConstraints());

            panelImage.add(panelBotonesformulario);
            panelBotonesformulario.setBounds(0, 660, 890, 60);

            getContentPane().add(panelImage, java.awt.BorderLayout.CENTER);

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void bnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnBuscarActionPerformed
        // TODO add your handling code here:
        //Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        Utilidades.setEditableTexto(this.panelBusqueda, true, null, true, "");
        Utilidades.setEditableTexto(this.panelResultados, true, null, true, "");
        Utilidades.buscarBotones(this.panelBotonesformulario, false, null);
        model.setRowCount(0);
        txtBusqueda.requestFocus();
    }//GEN-LAST:event_bnBuscarActionPerformed

    private void bnSuprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnSuprimirActionPerformed
        // TODO add your handling code here:
        int resp;
        resp = JOptionPane.showInternalConfirmDialog(this, "¿Desea Eliminar el Registro?", "Pregunta", 0);
        if (resp == 0) {
            int s = 0;

            /* Guardamos el ID de dla fila selecciona en la variable s */
            s = tableResultados.getSelectedRow();

            /* Validamos que hallan seleccionado */
            if (s < 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
                return;
            }

            String id = Utilidades.objectToString(tableResultados.getValueAt(s, 0));

            if ((peticiones.eliminarRegistro(nombreTabla, "estado", nombreId, id)) > 0) {
                JOptionPane.showMessageDialog(rootPane, "El registro ha sido Eliminado correctamente ");
                nuevo();
            } else {
                JOptionPane.showMessageDialog(rootPane, "No se ha podido Eliminar el registro, por favor verifique los datos");
            }
        }
    }//GEN-LAST:event_bnSuprimirActionPerformed

    private void bnDeudoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnDeudoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bnDeudoresActionPerformed

    private void bnEstadocuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnEstadocuentaActionPerformed
        // TODO add your handling code here:       
    }//GEN-LAST:event_bnEstadocuentaActionPerformed

    private void bnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnCrearActionPerformed
        // TODO add your handling code here:  
        editar = false;
        nuevo();

    }//GEN-LAST:event_bnCrearActionPerformed

    private void bnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnGuardarActionPerformed

        if (editar == false) {
            Guardar();
        } else if (editar == true) {
            Modificar();
        }

    }//GEN-LAST:event_bnGuardarActionPerformed

    private void bnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnCancelarActionPerformed
        // TODO add your handling code here:
        Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        Utilidades.setEditableTexto(this.panelBusqueda, true, null, true, "");
        Utilidades.setEditableTexto(this.panelResultados, true, null, true, "");
        Utilidades.buscarBotones(this.panelBotonesformulario, false, null);
        //model.setRowCount(0);
    }//GEN-LAST:event_bnCancelarActionPerformed

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
        // TODO add your handling code here:
        buscarProducto_codigo(txtBusqueda.getText());
        //Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        //Utilidades.buscarBotones(this.panelBotonesformulario, false, null);

    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void tableResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableResultadosMouseClicked
        // TODO add your handling code here:
        tableMouseClicked();

    }//GEN-LAST:event_tableResultadosMouseClicked

    private void bnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnEditarActionPerformed

        int s = 0;

        /* Guardamos el ID de dla fila selecciona en la variable s */
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }
        tableMouseClicked();
        Utilidades.setEditableTexto(this.panelFormulario, true, null, false, "");
        Utilidades.buscarBotones(this.panelBotonesformulario, true, null);
        editar = true;
    }//GEN-LAST:event_bnEditarActionPerformed

    private void txtNitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNitActionPerformed
        // TODO add your handling code here:
        buscarCliente(txtNit.getText());
    }//GEN-LAST:event_txtNitActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
        txtDescuento.requestFocus();

    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        // TODO add your handling code here:

        Float precd, desc, dif, subtotal;

        desc = (float) (Math.round((Float.parseFloat(txtDescuento.getText()) / 100) * 100.0) / 100.0);
        dif = (float) (Math.round((Float.parseFloat(txtPrecio.getText()) * desc) * 100.0) / 100.0);
        precd = (float) (Math.round((Float.parseFloat(txtPrecio.getText()) - dif) * 100.0) / 100.0);
        subtotal = (Float.parseFloat(txtCantidad.getText()) * precd);

        txtPreciocondescuento.setText("" + precd);

        txtImporte.setText("" + subtotal);
        agregarProducto("");
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void txIdproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIdproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txIdproductoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        buscar_producto newfrm = new buscar_producto();
        if (newfrm == null) {
            newfrm = new buscar_producto();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cambiar_precio.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        cambiar_precio.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnBuscar;
    private javax.swing.JButton bnCancelar;
    private javax.swing.JButton bnCrear;
    private javax.swing.JButton bnDeudores;
    private javax.swing.JButton bnEditar;
    private javax.swing.JButton bnEstadocuenta;
    private javax.swing.JButton bnGuardar;
    private javax.swing.JButton bnSuprimir;
    private javax.swing.JFrame cambiar_precio;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelBusqueda;
    private javax.swing.JLabel labelCodigo1;
    private javax.swing.JLabel labelCodigo2;
    private javax.swing.JLabel labelCodigo3;
    private javax.swing.JLabel labelCodigo4;
    private javax.swing.JLabel labelCodigo5;
    private javax.swing.JLabel labelCodigo7;
    private javax.swing.JLabel labelCorreo3;
    private javax.swing.JLabel labelCorreo4;
    private javax.swing.JLabel labelEncabezado;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelBotonesformulario;
    private javax.swing.JPanel panelBusqueda;
    private jcMousePanel.jcMousePanel panelEncabezado;
    private javax.swing.JPanel panelFormulario;
    private elaprendiz.gui.panel.PanelImage panelImage;
    private javax.swing.JPanel panelResultados;
    private javax.swing.JRadioButton rbEstado1;
    private javax.swing.JScrollPane scrollpaneResultados;
    private javax.swing.JTable tableResultados;
    private elaprendiz.gui.textField.TextField txIdproducto;
    private elaprendiz.gui.textField.TextField txtBusqueda;
    private javax.swing.JFormattedTextField txtCantidad;
    private elaprendiz.gui.textField.TextField txtCodigo;
    private javax.swing.JFormattedTextField txtDescuento;
    private javax.swing.JFormattedTextField txtExistencia;
    private javax.swing.JFormattedTextField txtImporte;
    private elaprendiz.gui.textField.TextField txtNit;
    private elaprendiz.gui.textField.TextField txtNombre;
    private elaprendiz.gui.textField.TextField txtNombrecliente;
    private javax.swing.JFormattedTextField txtPrecio;
    private javax.swing.JFormattedTextField txtPrecio1;
    private javax.swing.JFormattedTextField txtPrecio2;
    private javax.swing.JFormattedTextField txtPreciocondescuento;
    private javax.swing.JFormattedTextField txtTotal;
    private elaprendiz.gui.textField.TextField txtUnidad;
    // End of variables declaration//GEN-END:variables
}
