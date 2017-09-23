/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import clases.Datos;
import clases.FormatoDecimal;
import clases.Peticiones;
import clases.Utilidades;
import static formularios.frmPrincipal.panel_center;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import static formularios.buscar_cliente.Comprueba;

/**
 *
 * @author GLARA
 */
public class frmVenta extends javax.swing.JInternalFrame {

    /**
     * Variables para realizar las transacciones con la base de datos
     */
    String nombreTabla = "clientes";
    //String[] titulos = {"Id", "Código", "Nombre Cliente", "Dirección", "Nit", "Limite Créd"};
    String[] titulos = {"Id", "Código", "Descripción del producto", "Cantidad", "Unidad", "Precio.Norm", "Precio C.Desc", "Descuento %", "Subtotal", "Precio Costo"};
    String campos = "codigo, nombre, direccion, correo, nit, telefono, fec_reg, lim_cred, estado";
    String nombreId = "idClientes";
    public static String valorId = "";

    DefaultTableModel model;
    Datos datos = new Datos();
    Peticiones peticiones = new Peticiones();
    boolean editar = false;

    /**
     * Creates new form Cliente
     */
    public frmVenta() {
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
                //cambiar_precio.setVisible(true);
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

    public void cambiarPrecio() {

        /* Variable que contendra el ID de la fila seleccionada */
        int s = 0;

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

        float cantidad = Float.parseFloat(Validar(JDcantidad.getText()));
        cantidad = (float) (Math.round((cantidad) * 100.0) / 100.0);

        float nprecios = Float.parseFloat(Validar(JDnuevoPrecio.getText()));
        nprecios = (float) (Math.round((nprecios) * 100.0) / 100.0);

        float Porcentaje = Float.parseFloat(Validar(JDdescuento.getText()));
        Porcentaje = (float) (Math.round((Porcentaje) * 100.0) / 100.0);

        float subtotal;
        subtotal = (float) (Math.round((cantidad * nprecios) * 100.0) / 100.0);

        if ((nprecios > 0) && (Porcentaje >= 0)) {

            model.setValueAt(nprecios, s, 6);
            model.setValueAt(Porcentaje, s, 7);
            model.setValueAt(subtotal, s, 8);
        }
        sumar_total();
        cambiar_precio.dispose();
    }

    public void cambiarCantidad() {

        /* Variable que contendra el ID de la fila seleccionada */
        int s = 0;

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

        float cantidad = Float.parseFloat(Validar(txtNuevacant.getText()));
        cantidad = (float) (Math.round((cantidad) * 100.0) / 100.0);

        float nprecios = Float.parseFloat(Validar(JDNuevoprecio.getText()));
        nprecios = (float) (Math.round((nprecios) * 100.0) / 100.0);

//        float Porcentaje = Float.parseFloat(Validar(JDdescuento.getText()));
//        Porcentaje = (float) (Math.round((Porcentaje) * 100.0) / 100.0);
        float subtotal;
        subtotal = (float) (Math.round((cantidad * nprecios) * 100.0) / 100.0);

        if ((cantidad > 0)) {

            model.setValueAt(cantidad, s, 3);
            model.setValueAt(nprecios, s, 6);
            model.setValueAt(subtotal, s, 8);
        }
        sumar_total();
        cambiar_cantidad.dispose();
    }

    public void calcularPrecio() {

        Float preciocondescuento, desc, dif;

        float precionormal = Float.parseFloat(Validar(JDprecioNormal.getText()));
        precionormal = (float) (Math.round((precionormal) * 100.0) / 100.0);

        float descuento = Float.parseFloat(Validar(JDdescuento.getText()));
        descuento = (float) (Math.round((descuento) * 100.0) / 100.0);

        desc = (float) (Math.round((descuento / 100) * 100.0) / 100.0);
        dif = (float) (Math.round((precionormal * desc) * 100.0) / 100.0);
        preciocondescuento = (float) (Math.round((precionormal - dif) * 100.0) / 100.0);

//        float costo = Float.parseFloat(Validar(JDcosto.getText()));
//        costo = (float) (Math.round((costo) * 100.0) / 100.0);
        // if (preciocondescuento >= costo) {
        if ((preciocondescuento > 0 && preciocondescuento <= precionormal)) {
            JDnuevoPrecio.setValue(preciocondescuento);
        } else {
            JDnuevoPrecio.setValue(0.00);
        }
//        } else {
//            JOptionPane.showInternalMessageDialog(this, "El nuevo precio es menor al costo", "Error", JOptionPane.INFORMATION_MESSAGE);
//        }
    }

    public void calcularDescuento() {

        Float desc, dif;

        float precionormal = Float.parseFloat(Validar(JDprecioNormal.getText()));
        precionormal = (float) (Math.round((precionormal) * 100.0) / 100.0);

        float nuevoprecio = Float.parseFloat(Validar(JDnuevoPrecio.getText()));
        nuevoprecio = (float) (Math.round((nuevoprecio) * 100.0) / 100.0);

//        float costo = Float.parseFloat(Validar(JDcosto.getText()));
//        costo = (float) (Math.round((costo) * 100.0) / 100.0);
        //if (nuevoprecio >= costo) {
        dif = (float) (Math.round((precionormal - nuevoprecio) * 100.0) / 100.0);
        desc = (float) (Math.round(((dif / precionormal) * 100) * 100.0) / 100.0);

        if ((desc >= 0 && nuevoprecio <= precionormal)) {
            JDdescuento.setValue(desc);
        } else {
            JDdescuento.setValue(0.00);
        }
//        } else {
//            JOptionPane.showInternalMessageDialog(this, "El nuevo precio es menor al costo", "Error", JOptionPane.INFORMATION_MESSAGE);
//        }
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
                    txtDireccion.setText(rs.getString("direccion"));
                }
                txtBusquedap.requestFocus();
                txtBusquedap.setEditable(true);
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
                    if (jCheckPrecioMayoreo.isSelected()) {
                        txtPrecio.setValue(rs.getFloat("producto.preciomayoreo"));
                    } else {
                        txtPrecio.setValue(rs.getFloat("producto.precioventa"));
                    }

                    //txtPrecio.setText("preciomayoreo");
                    txtExistencia.setValue(rs.getFloat("producto.existencia"));
                    txtCosto.setValue(rs.getFloat("producto.preciocoste"));
                    //txtImporte.setText("");

                    //txtNit.setText(rs.getString("nit"));
                    //txtNombrecliente.setText(rs.getString("nombre"));
                }
                if (cboModoIngreso.getSelectedItem().equals("x Unidad")) {
                    txtCantidad.setValue(1);
                    txtDescuento.setValue(0);

                    descuento();
                } else if (cboModoIngreso.getSelectedItem().equals("x Mayor")) {
                    txtCantidad.requestFocus();
                }

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
        Object[] registro = new Object[10];

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
        registro[9] = Float.parseFloat(txtCosto.getText());

        //registro[6] = rs.getBoolean("estado"); //getString("lim_cred");
        model.addRow(registro);
        // }
        tableResultados.setModel(model);
        Utilidades.ajustarAnchoColumnas(tableResultados);
        sumar_total();

        txIdproducto.setText("");
        txtBusquedap.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtUnidad.setText("");
        txtCantidad.setValue(null);
        txtPrecio.setValue(null);
        txtDescuento.setValue(null);
        txtImporte.setValue(null);
        txtCosto.setValue(null);
        txtExistencia.setValue(null);
        txtBusquedap.requestFocus();

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
        System.out.print("selected -" + tableResultados.getValueAt(s, 1).toString() + "\n");
        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

        //llenarFormulario(s);
    }

    private void descuento() {

        Float precd, desc, dif, subtotal;

        desc = (float) (Math.round((Float.parseFloat(txtDescuento.getText()) / 100) * 100.0) / 100.0);
        dif = (float) (Math.round((Float.parseFloat(txtPrecio.getText()) * desc) * 100.0) / 100.0);
        precd = (float) (Math.round((Float.parseFloat(txtPrecio.getText()) - dif) * 100.0) / 100.0);
        //subtotal = (Float.parseFloat(txtCantidad.getText()) * precd);
        subtotal = (float) (Math.round(((Float.parseFloat(txtCantidad.getText()) * precd)) * 100.0) / 100.0);

        txtPreciocondescuento.setText("" + precd);

        txtImporte.setText("" + subtotal);
        agregarProducto("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cambiar_precio = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        JDnuevoPrecio = new javax.swing.JFormattedTextField();
        JDdescuento = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        JDprecioNormal = new javax.swing.JFormattedTextField();
        JDcantidad = new javax.swing.JFormattedTextField();
        JDcosto = new javax.swing.JFormattedTextField();
        jPanel9 = new javax.swing.JPanel();
        JBCambiarPrecio = new javax.swing.JButton();
        JBCancelarPrecio = new javax.swing.JButton();
        cambiar_cantidad = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNuevacant = new javax.swing.JFormattedTextField();
        DPcantidadactual = new javax.swing.JFormattedTextField();
        JDNuevoprecio = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        JBCambiarCantidad = new javax.swing.JButton();
        JBCancelarCantidad = new javax.swing.JButton();
        popupCambiar = new javax.swing.JPopupMenu();
        cambiarPrecio = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        cambiarCantidad = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
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
        txtDireccion = new elaprendiz.gui.textField.TextField();
        jPanel5 = new javax.swing.JPanel();
        labelCodigo3 = new javax.swing.JLabel();
        txtUnidad = new elaprendiz.gui.textField.TextField();
        txtNombre = new elaprendiz.gui.textField.TextField();
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
        txtBusquedap = new elaprendiz.gui.textField.TextField();
        txtCodigo = new elaprendiz.gui.textField.TextField();
        txtPreciocondescuento = new javax.swing.JFormattedTextField();
        txtCosto = new javax.swing.JFormattedTextField();
        cboModoIngreso = new javax.swing.JComboBox<>();
        lblModo = new javax.swing.JLabel();
        labelBusqueda1 = new javax.swing.JLabel();
        jCheckPrecioMayoreo = new javax.swing.JCheckBox();
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

        cambiar_precio.setBackground(java.awt.Color.white);
        cambiar_precio.setMinimumSize(new java.awt.Dimension(225, 174));
        cambiar_precio.setUndecorated(true);
        cambiar_precio.setResizable(false);
        cambiar_precio.setType(java.awt.Window.Type.UTILITY);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 2, true));
        jPanel8.setMinimumSize(new java.awt.Dimension(240, 117));

        jLabel14.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel14.setText("Precio Normal:");

        jLabel15.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel15.setText("Nuevo Precio:");

        jLabel16.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel16.setText("Q");

        JDnuevoPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JDnuevoPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        JDnuevoPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JDnuevoPrecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JDnuevoPrecio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JDnuevoPrecio.setName("costo"); // NOI18N
        JDnuevoPrecio.setPreferredSize(new java.awt.Dimension(70, 23));
        JDnuevoPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JDnuevoPrecioKeyReleased(evt);
            }
        });

        JDdescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JDdescuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        JDdescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JDdescuento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JDdescuento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JDdescuento.setName("costo"); // NOI18N
        JDdescuento.setPreferredSize(new java.awt.Dimension(70, 23));
        JDdescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JDdescuentoKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel17.setText("%");

        jLabel18.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel18.setText("Descuento:");

        JDprecioNormal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JDprecioNormal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        JDprecioNormal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JDprecioNormal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JDprecioNormal.setFocusable(false);
        JDprecioNormal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JDprecioNormal.setName("costo"); // NOI18N
        JDprecioNormal.setPreferredSize(new java.awt.Dimension(70, 23));

        JDcantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JDcantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        JDcantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JDcantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JDcantidad.setFocusable(false);
        JDcantidad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JDcantidad.setName("costo"); // NOI18N
        JDcantidad.setPreferredSize(new java.awt.Dimension(70, 23));

        JDcosto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JDcosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        JDcosto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JDcosto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JDcosto.setFocusable(false);
        JDcosto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JDcosto.setName("costo"); // NOI18N
        JDcosto.setPreferredSize(new java.awt.Dimension(70, 23));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(JDcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(2, 2, 2)
                            .addComponent(JDnuevoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JDdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(JDprecioNormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JDcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDprecioNormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDnuevoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        JDcantidad.setVisible(false);
        JDcosto.setVisible(false);

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 2, true));
        jPanel9.setPreferredSize(new java.awt.Dimension(240, 67));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        JBCambiarPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit24.png"))); // NOI18N
        JBCambiarPrecio.setText("Cambiar");
        JBCambiarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCambiarPrecioActionPerformed(evt);
            }
        });
        jPanel9.add(JBCambiarPrecio);

        JBCancelarPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel24.png"))); // NOI18N
        JBCancelarPrecio.setText("Cancelar");
        JBCancelarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCancelarPrecioActionPerformed(evt);
            }
        });
        jPanel9.add(JBCancelarPrecio);

        javax.swing.GroupLayout cambiar_precioLayout = new javax.swing.GroupLayout(cambiar_precio.getContentPane());
        cambiar_precio.getContentPane().setLayout(cambiar_precioLayout);
        cambiar_precioLayout.setHorizontalGroup(
            cambiar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        cambiar_precioLayout.setVerticalGroup(
            cambiar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cambiar_precioLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cambiar_cantidad.setMinimumSize(new java.awt.Dimension(225, 174));
        cambiar_cantidad.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        cambiar_cantidad.setUndecorated(true);
        cambiar_cantidad.setResizable(false);
        cambiar_cantidad.setType(java.awt.Window.Type.UTILITY);

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 2, true));
        jPanel10.setMinimumSize(new java.awt.Dimension(240, 117));

        jLabel19.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel19.setText("Actual:");

        jLabel20.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jLabel20.setText("Nueva Cant:");

        jLabel21.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N

        txtNuevacant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNuevacant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtNuevacant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNuevacant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNuevacant.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNuevacant.setName("costo"); // NOI18N
        txtNuevacant.setPreferredSize(new java.awt.Dimension(70, 23));

        DPcantidadactual.setEditable(false);
        DPcantidadactual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        DPcantidadactual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        DPcantidadactual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DPcantidadactual.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        DPcantidadactual.setFocusable(false);
        DPcantidadactual.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        DPcantidadactual.setName("costo"); // NOI18N
        DPcantidadactual.setPreferredSize(new java.awt.Dimension(70, 23));

        JDNuevoprecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JDNuevoprecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        JDNuevoprecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JDNuevoprecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        JDNuevoprecio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JDNuevoprecio.setName("costo"); // NOI18N
        JDNuevoprecio.setPreferredSize(new java.awt.Dimension(70, 23));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JDNuevoprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(txtNuevacant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(DPcantidadactual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DPcantidadactual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevacant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(JDNuevoprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        JDNuevoprecio.setVisible(false);

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));
        jPanel11.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 2, true));
        jPanel11.setPreferredSize(new java.awt.Dimension(240, 67));
        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        JBCambiarCantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit24.png"))); // NOI18N
        JBCambiarCantidad.setText("Cambiar");
        JBCambiarCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCambiarCantidadActionPerformed(evt);
            }
        });
        jPanel11.add(JBCambiarCantidad);

        JBCancelarCantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel24.png"))); // NOI18N
        JBCancelarCantidad.setText("Cancelar");
        JBCancelarCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCancelarCantidadActionPerformed(evt);
            }
        });
        jPanel11.add(JBCancelarCantidad);

        javax.swing.GroupLayout cambiar_cantidadLayout = new javax.swing.GroupLayout(cambiar_cantidad.getContentPane());
        cambiar_cantidad.getContentPane().setLayout(cambiar_cantidadLayout);
        cambiar_cantidadLayout.setHorizontalGroup(
            cambiar_cantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        cambiar_cantidadLayout.setVerticalGroup(
            cambiar_cantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cambiar_cantidadLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        popupCambiar.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                popupCambiarComponentShown(evt);
            }
        });

        cambiarPrecio.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        cambiarPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/attributes24.png"))); // NOI18N
        cambiarPrecio.setText("Cambiar Precio");
        cambiarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarPrecioActionPerformed(evt);
            }
        });
        popupCambiar.add(cambiarPrecio);
        popupCambiar.add(jSeparator1);

        cambiarCantidad.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        cambiarCantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/calculator24.png"))); // NOI18N
        cambiarCantidad.setText("Cambiar Cantidad");
        cambiarCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarCantidadActionPerformed(evt);
            }
        });
        popupCambiar.add(cambiarCantidad);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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
                .addComponent(bnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        labelCorreo4.setText("Nit:");
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

        txtDireccion.setEditable(false);
        txtDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDireccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDireccion.setName("txtDireccion"); // NOI18N
        txtDireccion.setOpaque(true);
        txtDireccion.setPreferredSize(new java.awt.Dimension(120, 21));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(labelCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(labelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(dateFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        panelBusqueda.add(jPanel4);
        jPanel4.setBounds(10, 10, 870, 90);

        jPanel5.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.textHighlight, 1, true));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

        labelCodigo3.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCodigo3.setText("Unidad: ");
        labelCodigo3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo3);
        labelCodigo3.setBounds(400, 90, 60, 25);

        txtUnidad.setEditable(false);
        txtUnidad.setBackground(new java.awt.Color(255, 255, 255));
        txtUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUnidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUnidad.setName("descripcion"); // NOI18N
        txtUnidad.setOpaque(true);
        txtUnidad.setPreferredSize(new java.awt.Dimension(120, 21));
        jPanel5.add(txtUnidad);
        txtUnidad.setBounds(460, 90, 230, 25);

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombre.setName("descripcion"); // NOI18N
        txtNombre.setOpaque(true);
        txtNombre.setPreferredSize(new java.awt.Dimension(120, 21));
        jPanel5.add(txtNombre);
        txtNombre.setBounds(100, 90, 290, 25);

        labelCodigo4.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCodigo4.setText("Cantidad : ");
        labelCodigo4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo4);
        labelCodigo4.setBounds(530, 50, 80, 25);

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
        txtCantidad.setBounds(610, 50, 80, 25);

        txtPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPrecio.setName("costo"); // NOI18N
        txtPrecio.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel5.add(txtPrecio);
        txtPrecio.setBounds(460, 50, 70, 25);

        labelCodigo5.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCodigo5.setText("Precio : ");
        labelCodigo5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo5);
        labelCodigo5.setBounds(410, 50, 50, 25);

        labelCodigo1.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCodigo1.setText("Descuento: ");
        labelCodigo1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo1);
        labelCodigo1.setBounds(696, 50, 74, 25);

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
        txtDescuento.setBounds(770, 50, 80, 25);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/product.png"))); // NOI18N
        jPanel5.add(jLabel1);
        jLabel1.setBounds(110, 50, 20, 27);

        labelCodigo7.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCodigo7.setText("Existencia: ");
        labelCodigo7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(labelCodigo7);
        labelCodigo7.setBounds(700, 90, 70, 25);

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
        txtImporte.setBounds(10, 110, 10, 10);
        txtImporte.setVisible(false);

        txtExistencia.setEditable(false);
        txtExistencia.setBackground(new java.awt.Color(255, 255, 255));
        txtExistencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtExistencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtExistencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExistencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtExistencia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtExistencia.setName("costo"); // NOI18N
        txtExistencia.setPreferredSize(new java.awt.Dimension(80, 23));
        jPanel5.add(txtExistencia);
        txtExistencia.setBounds(770, 90, 80, 25);

        labelBusqueda.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelBusqueda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBusqueda.setText("Producto : ");
        jPanel5.add(labelBusqueda);
        labelBusqueda.setBounds(30, 90, 70, 27);

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
        txIdproducto.setBounds(10, 30, 10, 10);
        txIdproducto.setVisible(false);

        txtBusquedap.setEditable(false);
        txtBusquedap.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBusquedap.setOpaque(true);
        txtBusquedap.setPreferredSize(new java.awt.Dimension(250, 27));
        txtBusquedap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedapActionPerformed(evt);
            }
        });
        jPanel5.add(txtBusquedap);
        txtBusquedap.setBounds(100, 50, 290, 27);

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
        txtCodigo.setBounds(10, 50, 10, 10);
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
        txtPreciocondescuento.setBounds(10, 70, 10, 10);
        txtPreciocondescuento.setVisible(false);

        txtCosto.setEditable(false);
        txtCosto.setBackground(new java.awt.Color(255, 255, 255));
        txtCosto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtCosto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCosto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCosto.setEnabled(false);
        txtCosto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtCosto.setName("costo"); // NOI18N
        txtCosto.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel5.add(txtCosto);
        txtCosto.setBounds(10, 90, 10, 10);
        txtCosto.setVisible(false);

        cboModoIngreso.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cboModoIngreso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "x Unidad", "x Mayor" }));
        cboModoIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboModoIngresoActionPerformed(evt);
            }
        });
        jPanel5.add(cboModoIngreso);
        cboModoIngreso.setBounds(100, 10, 100, 25);

        lblModo.setBackground(new java.awt.Color(255, 255, 255));
        lblModo.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        lblModo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblModo.setText("Modo :");
        jPanel5.add(lblModo);
        lblModo.setBounds(40, 10, 60, 25);

        labelBusqueda1.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelBusqueda1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelBusqueda1.setText("Código : ");
        jPanel5.add(labelBusqueda1);
        labelBusqueda1.setBounds(30, 50, 70, 27);

        jCheckPrecioMayoreo.setBackground(new java.awt.Color(255, 255, 255));
        jCheckPrecioMayoreo.setFont(new java.awt.Font("Decker", 1, 14)); // NOI18N
        jCheckPrecioMayoreo.setText("Precio Mayorista");
        jPanel5.add(jCheckPrecioMayoreo);
        jCheckPrecioMayoreo.setBounds(240, 10, 150, 27);

        panelBusqueda.add(jPanel5);
        jPanel5.setBounds(10, 110, 870, 130);

        panelImage.add(panelBusqueda);
        panelBusqueda.setBounds(0, 88, 890, 250);

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
            tableResultados.setComponentPopupMenu(popupCambiar);
            tableResultados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            tableResultados.setFocusCycleRoot(true);
            tableResultados.setName("detalle_compra"); // NOI18N
            tableResultados.setRowHeight(24);
            tableResultados.setSurrendersFocusOnKeystroke(true);
            tableResultados.setUpdateSelectionOnSort(false);
            tableResultados.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tableResultadosMouseClicked(evt);
                }
            });
            scrollpaneResultados.setViewportView(tableResultados);

            panelResultados.add(scrollpaneResultados, java.awt.BorderLayout.CENTER);

            panelImage.add(panelResultados);
            panelResultados.setBounds(0, 339, 890, 240);

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
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });

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
            labelEncabezado.setText("FORMULARIO DE VENTAS");

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
        txtBusquedap.requestFocus();
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

    private void txtBusquedapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedapActionPerformed
        // TODO add your handling code here:
        buscarProducto_codigo(txtBusquedap.getText());
        //Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        //Utilidades.buscarBotones(this.panelBotonesformulario, false, null);

    }//GEN-LAST:event_txtBusquedapActionPerformed

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
        descuento();
//        Float precd, desc, dif, subtotal;
//
//        desc = (float) (Math.round((Float.parseFloat(txtDescuento.getText()) / 100) * 100.0) / 100.0);
//        dif = (float) (Math.round((Float.parseFloat(txtPrecio.getText()) * desc) * 100.0) / 100.0);
//        precd = (float) (Math.round((Float.parseFloat(txtPrecio.getText()) - dif) * 100.0) / 100.0);
//        //subtotal = (Float.parseFloat(txtCantidad.getText()) * precd);
//        subtotal = (float) (Math.round(((Float.parseFloat(txtCantidad.getText()) * precd)) * 100.0) / 100.0);
//
//        txtPreciocondescuento.setText("" + precd);
//
//        txtImporte.setText("" + subtotal);
//        agregarProducto("");
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void txIdproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIdproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txIdproductoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        buscar_cliente form = new buscar_cliente();

        Comprueba = 1;
        panel_center.add(form);

        //   form.setClosable(true);
        form.setIconifiable(true);
        form.setClosable(true);
        form.setMaximizable(false);
        form.toFront();
        form.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //new cambiar_precio();
//        cambiar_preci.setLocationRelativeTo(null);
//        cambiar_preci.setVisible(true);
        //this.add(cambiar_precio);
        //productos newfrm = new productos();
        //panelImage.add(cambiar_precio);
//        if (newfrm == null) {
//            newfrm = new productos();
//        }
//        AddForms.adminInternalFrame(panel_center, cambiar_precio);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        NewJPanel pvc = new NewJPanel();
        JOptionPane.showInternalOptionDialog(this, pvc, "Buscar Producto: ", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, /*new Object[]{pvc.getLbAviso()},*/ null, null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void JBCambiarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCambiarPrecioActionPerformed
        // TODO add your handling code here:
        Float desc, dif;

        float precionormal = Float.parseFloat(Validar(JDprecioNormal.getText()));
        precionormal = (float) (Math.round((precionormal) * 100.0) / 100.0);

        float preciocondescuento = Float.parseFloat(Validar(JDnuevoPrecio.getText()));
        preciocondescuento = (float) (Math.round((preciocondescuento) * 100.0) / 100.0);

//        desc = (float) (Math.round((descuento / 100) * 100.0) / 100.0);
//        dif = (float) (Math.round((precionormal * desc) * 100.0) / 100.0);
//        preciocondescuento = (float) (Math.round((precionormal - dif) * 100.0) / 100.0);
        float costo = Float.parseFloat(Validar(JDcosto.getText()));
        costo = (float) (Math.round((costo) * 100.0) / 100.0);

        if (preciocondescuento < costo) {
            int resp;
            resp = JOptionPane.showInternalConfirmDialog(this, "El nuevo precio es menor al costo, desea realizar en cambio", "Pregunta", 0);
            if (resp == 0) {
                cambiarPrecio();
            }
        } else if (preciocondescuento >= costo) {
            cambiarPrecio();
        }

    }//GEN-LAST:event_JBCambiarPrecioActionPerformed

    private void JBCancelarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCancelarPrecioActionPerformed
        // TODO add your handling code here:
        JDprecioNormal.setValue(null);
        JDnuevoPrecio.setValue(null);
        JDdescuento.setValue(null);
        JDcantidad.setValue(null);

        cambiar_precio.dispose();
    }//GEN-LAST:event_JBCancelarPrecioActionPerformed

    private void JBCambiarCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCambiarCantidadActionPerformed
        // TODO add your handling code here:
        cambiarCantidad();
    }//GEN-LAST:event_JBCambiarCantidadActionPerformed

    private void JBCancelarCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCancelarCantidadActionPerformed
        // TODO add your handling code here:
        DPcantidadactual.setValue(null);
        txtNuevacant.setValue(null);
        JDNuevoprecio.setValue(null);

        cambiar_cantidad.dispose();
    }//GEN-LAST:event_JBCancelarCantidadActionPerformed

    private void cambiarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarPrecioActionPerformed
        // TODO add your handling code here:
        JDprecioNormal.setValue(null);
        JDnuevoPrecio.setValue(null);
        JDdescuento.setValue(null);
        JDcantidad.setValue(null);
        JDcosto.setValue(null);

        int s = 0;

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();
        System.out.print("---" + tableResultados.getValueAt(s, 1).toString() + "\n");
        /* Validamos que hallan seleccionado */
        if (s == -1) {
            JOptionPane.showInternalMessageDialog(this, "Debe seleccionar un registro");
            return;
        } else {
            JDprecioNormal.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 5).toString())));
            JDnuevoPrecio.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 6).toString())));
            JDdescuento.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 7).toString())));
            JDcantidad.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 3).toString())));
            JDcosto.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 9).toString())));

            Dimension pantalla = panel_center.getSize();
            //obtenemos el tamaño de la ventana
            Dimension ventana = cambiar_precio.getSize();
            //para centrar la ventana lo hacemos con el siguiente calculo
            cambiar_precio.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2 /*10*/);
            cambiar_precio.setVisible(true);
            JDnuevoPrecio.requestFocus();
        }
    }//GEN-LAST:event_cambiarPrecioActionPerformed

    private void cambiarCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarCantidadActionPerformed
        // TODO add your handling code here:

        DPcantidadactual.setValue(null);
        txtNuevacant.setValue(null);
        JDNuevoprecio.setValue(null);

        int s = 0;

        /* Limpiamos los campos del formulario */
        //Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        //Utilidades.buscarBotones(this.panelBotonesformulario, false, null);

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();
        System.out.print(s);
        /* Validamos que hallan seleccionado */
        if (s == -1) {
            JOptionPane.showInternalMessageDialog(this, "Debe seleccionar un registro");
            return;
        } else {

            DPcantidadactual.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 3).toString())));
            JDNuevoprecio.setValue(Float.parseFloat(Validar(tableResultados.getValueAt(s, 6).toString())));

            Dimension pantalla = panel_center.getSize();
            //obtenemos el tamaño de la ventana
            Dimension ventana = cambiar_cantidad.getSize();
            //para centrar la ventana lo hacemos con el siguiente calculo

            cambiar_cantidad.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2 /*10*/);
            //cambiar_cantidad.setLocationRelativeTo(null);
            txtNuevacant.requestFocus();
            cambiar_cantidad.setVisible(true);

        }

    }//GEN-LAST:event_cambiarCantidadActionPerformed

    private void popupCambiarComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_popupCambiarComponentShown
        // TODO add your handling code here:
        /* Variable que contendra el ID de la fila seleccionada */
        int s = 0;

        /* Limpiamos los campos del formulario */
        //Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        //Utilidades.buscarBotones(this.panelBotonesformulario, false, null);

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

    }//GEN-LAST:event_popupCambiarComponentShown

    private void JDdescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JDdescuentoKeyReleased
        // TODO add your handling code here:
        calcularPrecio();
    }//GEN-LAST:event_JDdescuentoKeyReleased

    private void JDnuevoPrecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JDnuevoPrecioKeyReleased
        // TODO add your handling code here:
        calcularDescuento();
    }//GEN-LAST:event_JDnuevoPrecioKeyReleased

    private void cboModoIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboModoIngresoActionPerformed

        if (cboModoIngreso.getSelectedItem() == "x Mayor") {
//            txtCantidadProducto.setText("0");
//            txtCantidadProducto.setEditable(false);
//            txtNombre_producto.setEditable(false);
//            txtStockDetalle.setEditable(false);
//            txtPrecio_producto.setEditable(false);
//            txtCod_producto.setEditable(true);
//            txtCod_producto.requestFocus();
        } else if (cboModoIngreso.getSelectedItem() == "x Unidad") {
//            txtCantidadProducto.setText("1");
//            txtCod_producto.setEditable(true);
//            txtCantidadProducto.setEditable(false);
//            txtNombre_producto.setEditable(false);
//            txtStockDetalle.setEditable(false);
//            txtPrecio_producto.setEditable(false);
//            txtCod_producto.requestFocus();
        }
    }//GEN-LAST:event_cboModoIngresoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField DPcantidadactual;
    private javax.swing.JButton JBCambiarCantidad;
    private javax.swing.JButton JBCambiarPrecio;
    private javax.swing.JButton JBCancelarCantidad;
    private javax.swing.JButton JBCancelarPrecio;
    private javax.swing.JFormattedTextField JDNuevoprecio;
    private javax.swing.JFormattedTextField JDcantidad;
    private javax.swing.JFormattedTextField JDcosto;
    private javax.swing.JFormattedTextField JDdescuento;
    private javax.swing.JFormattedTextField JDnuevoPrecio;
    private javax.swing.JFormattedTextField JDprecioNormal;
    private javax.swing.JButton bnBuscar;
    private javax.swing.JButton bnCancelar;
    private javax.swing.JButton bnCrear;
    private javax.swing.JButton bnDeudores;
    private javax.swing.JButton bnEditar;
    private javax.swing.JButton bnEstadocuenta;
    private javax.swing.JButton bnGuardar;
    private javax.swing.JButton bnSuprimir;
    private javax.swing.JMenuItem cambiarCantidad;
    private javax.swing.JMenuItem cambiarPrecio;
    private javax.swing.JDialog cambiar_cantidad;
    private javax.swing.JDialog cambiar_precio;
    private javax.swing.JComboBox<String> cboModoIngreso;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckPrecioMayoreo;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel labelBusqueda;
    private javax.swing.JLabel labelBusqueda1;
    private javax.swing.JLabel labelCodigo1;
    private javax.swing.JLabel labelCodigo3;
    private javax.swing.JLabel labelCodigo4;
    private javax.swing.JLabel labelCodigo5;
    private javax.swing.JLabel labelCodigo7;
    private javax.swing.JLabel labelCorreo3;
    private javax.swing.JLabel labelCorreo4;
    private javax.swing.JLabel labelEncabezado;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel lblModo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelBotonesformulario;
    private javax.swing.JPanel panelBusqueda;
    private jcMousePanel.jcMousePanel panelEncabezado;
    private javax.swing.JPanel panelFormulario;
    private elaprendiz.gui.panel.PanelImage panelImage;
    private javax.swing.JPanel panelResultados;
    private javax.swing.JPopupMenu popupCambiar;
    private javax.swing.JRadioButton rbEstado1;
    private javax.swing.JScrollPane scrollpaneResultados;
    private javax.swing.JTable tableResultados;
    private elaprendiz.gui.textField.TextField txIdproducto;
    public static elaprendiz.gui.textField.TextField txtBusquedap;
    private javax.swing.JFormattedTextField txtCantidad;
    private elaprendiz.gui.textField.TextField txtCodigo;
    private javax.swing.JFormattedTextField txtCosto;
    private javax.swing.JFormattedTextField txtDescuento;
    public static elaprendiz.gui.textField.TextField txtDireccion;
    private javax.swing.JFormattedTextField txtExistencia;
    private javax.swing.JFormattedTextField txtImporte;
    public static elaprendiz.gui.textField.TextField txtNit;
    private elaprendiz.gui.textField.TextField txtNombre;
    public static elaprendiz.gui.textField.TextField txtNombrecliente;
    private javax.swing.JFormattedTextField txtNuevacant;
    private javax.swing.JFormattedTextField txtPrecio;
    private javax.swing.JFormattedTextField txtPreciocondescuento;
    private javax.swing.JFormattedTextField txtTotal;
    private elaprendiz.gui.textField.TextField txtUnidad;
    // End of variables declaration//GEN-END:variables
}
