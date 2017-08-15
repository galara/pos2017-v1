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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;
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
    String[] titulos = {"Id", "Código", "Descripción del producto", "Precio Compra", "Cantidad", "Importe", "Existencia", "Descuento"};
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
//        tableResultados.getColumnModel().getColumn(0).setMaxWidth(0);
//        tableResultados.getColumnModel().getColumn(0).setMinWidth(0);
//        tableResultados.getColumnModel().getColumn(0).setPreferredWidth(0);
//        Utilidades.ajustarAnchoColumnas(tableResultados);
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
            txtNombre.setText("");
            txtUnidad.setText("");
            txtCantidad.setText("");
            txtPrecio.setText("");
            txtDescuento.setText("");
            txtImporte.setText("");

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
                    txtNombre.setText(rs.getString("nombre"));
                    txtUnidad.setText(rs.getString("idunidad"));
                    //txtCantidad.setText("");
                    txtPrecio.setText(rs.getString("precioventa"));
                    //txtPrecio.setText("preciomayoreo");
                    txtExistencia.setText(rs.getString("existencia"));
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

    /* Funcion para llenar la tabla cuando se busque un cliente en especifico
     por el Id */
    public void llenarFormulario(int s) {

        try {

            /* Llamamos a la funcion consultaRegistrosId la cual nos devuelve todos 
             los empleados relaciones con el id a buscar en la base de datos. 
            
             - Los datos recibidos lo guardamos en el objeto ResulSet para luego
             llenar la tabla con los registros.
            
             */
            ResultSet rs = peticiones.consultaRegistrosId(nombreTabla,
                    (String) tableResultados.getValueAt(s, 0), nombreId);

            /* Hacemos un while que mientras en rs hallan datos el ira agregando
             filas a la tabla. */
            while (rs.next()) {
                //txtCodigo.setText(rs.getString("codigo"));
                //txtNombre.setText(rs.getString("nombre"));
                txtNit.setText(rs.getString("direccion"));
                //txtCorreo.setText(rs.getString("correo"));
                //txtNit.setText(rs.getString("nit"));
                //txtTelefono.setText(rs.getString("telefono"));
                dateFecha.setDate(rs.getDate("fec_reg"));
                txtLimitecredito.setText(rs.getString("lim_cred"));
                //rbEstado.setSelected(rs.getBoolean("estado"));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
        }

    }

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
            txtTelefono.getText(), getFecha(), */ Validar(txtLimitecredito.getText())/*,
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
            txtTelefono.getText(),*/ getFecha(), Validar(txtLimitecredito.getText()),
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
        Utilidades.setEditableTexto(this.panelFormulario, false, null, true, "");
        //Utilidades.buscarBotones(this.panelBotonesformulario, false, null);

        /* Guardamos el ID de dla fila selecciona en la variable s*/
        s = tableResultados.getSelectedRow();

        /* Validamos que hallan seleccionado */
        if (s < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro");
            return;
        }

        llenarFormulario(s);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        labelCodigo6 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JFormattedTextField();
        labelCodigo7 = new javax.swing.JLabel();
        txtExistencia = new javax.swing.JFormattedTextField();
        labelBusqueda = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBusqueda = new elaprendiz.gui.textField.TextField();
        jButton1 = new javax.swing.JButton();
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
        txtLimitecredito = new javax.swing.JFormattedTextField();
        panelEncabezado = new jcMousePanel.jcMousePanel();
        labelEncabezado = new javax.swing.JLabel();
        panelBotonesformulario = new javax.swing.JPanel();
        bnGuardar = new javax.swing.JButton();
        bnCancelar = new javax.swing.JButton();

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

        txtNit.setBackground(new java.awt.Color(255, 255, 255));
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

        labelCodigo3.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo3.setText("Unidad");
        labelCodigo3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtUnidad.setEditable(false);
        txtUnidad.setBackground(new java.awt.Color(255, 255, 255));
        txtUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUnidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUnidad.setEnabled(false);
        txtUnidad.setName("descripcion"); // NOI18N
        txtUnidad.setOpaque(true);
        txtUnidad.setPreferredSize(new java.awt.Dimension(120, 21));

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombre.setEnabled(false);
        txtNombre.setName("descripcion"); // NOI18N
        txtNombre.setOpaque(true);
        txtNombre.setPreferredSize(new java.awt.Dimension(120, 21));

        labelCodigo2.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo2.setText("Descripción del producto");
        labelCodigo2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        labelCodigo4.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo4.setText("Cantidad");
        labelCodigo4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
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

        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPrecio.setName("costo"); // NOI18N
        txtPrecio.setPreferredSize(new java.awt.Dimension(70, 23));

        labelCodigo5.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo5.setText("Precio");
        labelCodigo5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        labelCodigo1.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo1.setText("Descuento");
        labelCodigo1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtDescuento.setBackground(new java.awt.Color(255, 255, 255));
        txtDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtDescuento.setName("costo"); // NOI18N
        txtDescuento.setPreferredSize(new java.awt.Dimension(70, 23));

        labelCodigo6.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo6.setText("Importe");
        labelCodigo6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        labelCodigo7.setFont(new java.awt.Font("Decker", 0, 14)); // NOI18N
        labelCodigo7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCodigo7.setText("Existencia");
        labelCodigo7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtExistencia.setEditable(false);
        txtExistencia.setBackground(new java.awt.Color(255, 255, 255));
        txtExistencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtExistencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        txtExistencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExistencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtExistencia.setEnabled(false);
        txtExistencia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtExistencia.setName("costo"); // NOI18N
        txtExistencia.setPreferredSize(new java.awt.Dimension(80, 23));

        labelBusqueda.setFont(new java.awt.Font("Decker", 1, 18)); // NOI18N
        labelBusqueda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBusqueda.setText("Código del producto:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/product.png"))); // NOI18N

        txtBusqueda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBusqueda.setOpaque(true);
        txtBusqueda.setPreferredSize(new java.awt.Dimension(250, 27));
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Decker", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        jButton1.setText("ENTER - Agregar Producto");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCodigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCodigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCodigo4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCodigo7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCodigo5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtImporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCodigo6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(labelBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(labelCodigo7)
                        .addGap(2, 2, 2)
                        .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelCodigo6)
                            .addComponent(labelCodigo1))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(labelCodigo5)
                        .addGap(2, 2, 2)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(labelCodigo4)
                            .addGap(2, 2, 2)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(labelCodigo2)
                            .addGap(2, 2, 2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(labelCodigo3)
                            .addGap(2, 2, 2)
                            .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

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
                    if(column==6  ){
                        return true;
                    }else{
                        return false;}
                    //return false;
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

            txtLimitecredito.setEditable(false);
            txtLimitecredito.setBackground(new java.awt.Color(255, 255, 255));
            txtLimitecredito.setBorder(null);
            txtLimitecredito.setForeground(new java.awt.Color(0, 102, 255));
            txtLimitecredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
            txtLimitecredito.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            txtLimitecredito.setText("10,999,999.00");
            txtLimitecredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            txtLimitecredito.setFont(new java.awt.Font("Arial", 1, 45)); // NOI18N
            txtLimitecredito.setPreferredSize(new java.awt.Dimension(80, 23));

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
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(txtLimitecredito, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(txtLimitecredito, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        txtImporte.setText(""+Double.parseDouble(txtCantidad.getText()) * Double.parseDouble(txtPrecio.getText()));
    }//GEN-LAST:event_txtCantidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnBuscar;
    private javax.swing.JButton bnCancelar;
    private javax.swing.JButton bnCrear;
    private javax.swing.JButton bnDeudores;
    private javax.swing.JButton bnEditar;
    private javax.swing.JButton bnEstadocuenta;
    private javax.swing.JButton bnGuardar;
    private javax.swing.JButton bnSuprimir;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel labelBusqueda;
    private javax.swing.JLabel labelCodigo1;
    private javax.swing.JLabel labelCodigo2;
    private javax.swing.JLabel labelCodigo3;
    private javax.swing.JLabel labelCodigo4;
    private javax.swing.JLabel labelCodigo5;
    private javax.swing.JLabel labelCodigo6;
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
    private elaprendiz.gui.textField.TextField txtBusqueda;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JFormattedTextField txtDescuento;
    private javax.swing.JFormattedTextField txtExistencia;
    private javax.swing.JFormattedTextField txtImporte;
    private javax.swing.JFormattedTextField txtLimitecredito;
    private elaprendiz.gui.textField.TextField txtNit;
    private elaprendiz.gui.textField.TextField txtNombre;
    private elaprendiz.gui.textField.TextField txtNombrecliente;
    private javax.swing.JFormattedTextField txtPrecio;
    private elaprendiz.gui.textField.TextField txtUnidad;
    // End of variables declaration//GEN-END:variables
}
