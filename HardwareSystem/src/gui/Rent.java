/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author oshada kavintha
 */
public class Rent extends javax.swing.JPanel {
    DecimalFormat df = new DecimalFormat("0.00");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form Rent
     */
    Home home;
    public Rent(Home home) {
        this.home = home;
        initComponents();
        loadViewRent();
        loadPaymentType();
    }
    
    
    public void loadRentUpdate(){
        
        int selectedRow = jTable2.getSelectedRow();
        System.out.println("selected Row :"+selectedRow);
        if(selectedRow != -1){
        
        String rent_id = jTable2.getValueAt(selectedRow, 0).toString();
        String rent_date = jTable2.getValueAt(selectedRow, 2).toString();
        
        try {
            ResultSet rs = MySQL.search("SELECT `item_rent`.`qty`,`item_rent`.`rent_price_per_day`,`rental_item_stock`.`name` AS `item_name` FROM `item_rent` INNER JOIN `rental_item_stock` ON `item_rent`.`rental_item_stock_id`=`rental_item_stock`.`id` WHERE `rent_id`='"+rent_id+"' ORDER BY `rental_item_stock`.`name` ASC");
            
            DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Vector v= new Vector();
                v.add(rs.getString("item_name"));
                v.add(rs.getString("qty"));
                v.add(rs.getString("rent_price_per_day"));
                dtm.addRow(v);
            }
            jTable3.setModel(dtm);
            
            jLabel14.setText(rent_id);
            jLabel16.setText(rent_date);
            
            
            String dNow = sdf1.format(new Date());
//            Date date_today = new SimpleDateFormat("yyyy-MM-dd").parse(dNow);
//            if(x==2){
//                jDateChooser1.setDate(date_today);
//            }

            jLabel17.setText(dNow);
            
//            String d2 = sdf1.format(jDateChooser1.getDate()).toString();
            String d2 = jLabel17.getText();
//            System.out.println("d2 :"+d2);
            
            long dif_days = calDateDifference(rent_date, d2);
//            if(dif_days < 0){
//                JOptionPane.showMessageDialog(this, "Invalid Date", "warnning", JOptionPane.WARNING_MESSAGE);
//                jDateChooser1.setDate(date_today);
//                dif_days = calDateDifference(rent_date, dNow);
//            }
            
            double total = 0;
            
            if(dif_days!=0){
              for(int i=0; i < jTable3.getRowCount(); i++){
                total = total + (Double.parseDouble(jTable3.getValueAt(i, 2).toString())*dif_days);
              }
            }else{
               for(int i=0; i < jTable3.getRowCount(); i++){
                total = total + (Double.parseDouble(jTable3.getValueAt(i, 2).toString()));
               } 
            }
            jLabel25.setText(total+"");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        }
    }
    
    
    public Long calDateDifference(String date1, String date2){
        String d1 = date1;
            String[] ydm1 = d1.split("-", -2);
                String year1 = "";
                String month1 = "";
                String day1 = "";

                int fl = 0;
                for(String a : ydm1){
                    fl = fl +1;
//                    System.out.println(a);
                    if(fl == 1){
                        year1 = a;
                    }
                    if(fl == 2){
                        month1 = a;
                    }
                    if(fl == 3){
                        day1 = a;
                    }
                }
                
                String d2 = date2;
            String[] ydm2 = d2.split("-", -2);
                String year2 = "";
                String month2 = "";
                String day2 = "";

                int f2 = 0;
                for(String b : ydm2){
                    f2 = f2 +1;
//                    System.out.println(a);
                    if(f2 == 1){
                        year2 = b;
                    }
                    if(f2 == 2){
                        month2 = b;
                    }
                    if(f2 == 3){
                        day2 = b;
                    }
                }
                
        LocalDate de1 = LocalDate.of(Integer.parseInt(year1), Integer.parseInt(month1), Integer.parseInt(day1));
        LocalDate de2 = LocalDate.of(Integer.parseInt(year2), Integer.parseInt(month2), Integer.parseInt(day2));

        long days = ChronoUnit.DAYS.between(de1, de2);
        System.out.println( days );
        
        return days;
    }
    
    public void loadPaymentType(){
        try{
            
            ResultSet rs =MySQL.search("SELECT * FROM `payment_type`");
            Vector v = new Vector();
            v.add("Select");
            while(rs.next()){
                v.add(rs.getString("name"));
            }
            jComboBox1.setModel(new DefaultComboBoxModel(v));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loadViewRent(){
        try {
            ResultSet rs = MySQL.search("SELECT `rent`.`id`,`rent_date`,`customer`.`name` AS `c_name` FROM `rent` INNER JOIN `customer` ON `rent`.`customer_id`=`customer`.`id` WHERE `stat`='0'");
            
            DefaultTableModel dtm = (DefaultTableModel)jTable2.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("c_name"));
                v.add(rs.getString("rent_date"));
                dtm.addRow(v);
            }
            jTable2.setModel(dtm);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void searchViewRent(String text){
        try {
            ResultSet rs = MySQL.search("SELECT `rent`.`id`,`rent`.`rent_date`,`customer`.`name` AS `c_name` FROM `rent` INNER JOIN `customer` ON `rent`.`customer_id`=`customer`.`id` WHERE `stat`='0' AND `customer`.`name` LIKE '%"+text+"%' ORDER BY `rent`.`id` DESC");
            
            DefaultTableModel dtm = (DefaultTableModel)jTable2.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("c_name"));
                v.add(rs.getString("rent_date"));
                dtm.addRow(v);
            }
            jTable2.setModel(dtm);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void resetFields(){
        jLabel8.setText("None");
        jLabel10.setText("None");
        jTextField1.setText("");
        jTextField2.setText("");
        jButton3.setEnabled(false);
        jTextField1.setEnabled(false);
        jTextField2.setEnabled(false);
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1100, 555));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Customer Select"));

        jButton1.setText("Select Customer");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Customer ID :");

        jLabel2.setText("None");

        jLabel3.setText("Customer Name :");

        jLabel4.setText("None");

        jLabel5.setText("Contact Number :");

        jLabel6.setText("None");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Add to Rent"));

        jButton2.setText("Select Item");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("ID :");

        jLabel8.setText("None");

        jLabel9.setText("Name :");

        jLabel10.setText("None");

        jLabel11.setText("Quantity :");

        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel12.setText("Price Per Day Per one :");

        jTextField2.setEnabled(false);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jButton3.setText("Add to Rent");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(14, 14, 14)
                            .addComponent(jTextField2))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Quantity", "Price Per Day"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton4.setText("Create New Rent");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Rent View"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rent ID", "Customer Name", "Rent Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Update Rent"));

        jLabel13.setText("Rent ID :");

        jLabel14.setText("None");

        jLabel15.setText("Rent Date :");

        jLabel16.setText("None");

        jLabel23.setText("Receve Date :");

        jLabel24.setText("Total :");

        jLabel25.setText("0.00");

        jLabel26.setText("Payment Type : ");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel27.setText("Payment :");

        jTextField3.setEnabled(false);
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jLabel28.setText("Balance :");

        jLabel29.setText("0.00");

        jButton5.setText("Update Rent Status");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Quantity", "Price Per Day"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel17.setText("None");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap())
        );

        jLabel30.setText("Search :");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(0, 107, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        CustomerRegistration cr = new CustomerRegistration(this);
        cr.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            jButton1.setText("Select Customer");
            jButton1.setEnabled(true);

            jLabel2.setText("None");
            jLabel4.setText("None");
            jLabel6.setText("None");
            DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
            dtm.setRowCount(0);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        RentalItemRegistration rir = new RentalItemRegistration(this);
        rir.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

 
    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        String qty = jTextField1.getText();
        String text = qty + evt.getKeyChar();
        if(!Pattern.compile("[1-9][0-9]*").matcher(text).matches()){
            evt.consume();
        }

    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        String price = jTextField2.getText();
        String text = price + evt.getKeyChar();
        if(!Pattern.compile("(0|0[.]|0[.][0-9]*)|[1-9]|[1-9][0-9]*|[1-9][0-9]*[.]|[1-9][0-9]*[.][0-9]*").matcher(text).matches()){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String i_id = jLabel8.getText().toString();
        String i_name = jLabel10.getText().toString();
        String qty = jTextField1.getText().toString();
        String price_per_day_per_one = jTextField2.getText().toString();
        
        if(!Pattern.compile("[1-9][0-9]*").matcher(qty).matches()){
            JOptionPane.showMessageDialog(this, "Invalid qty", "Warning", JOptionPane.WARNING_MESSAGE);
        }else if(!Pattern.compile("([1-9][0-9]*)|([0][.]([0]*[1-9][0-9]*))|(([1-9][0-9]*)[.]([0]*[1-9][0-9]*))").matcher(price_per_day_per_one).matches()){
            JOptionPane.showMessageDialog(this, "Invalid price", "Warning", JOptionPane.WARNING_MESSAGE);
        }else if(jLabel4.getText().equals("None")){
            JOptionPane.showMessageDialog(this, "Select Customer", "Warning", JOptionPane.WARNING_MESSAGE);
        }else{
            try{
            
            ResultSet rs = MySQL.search("SELECT * FROM `rental_item_stock` WHERE `id`='"+i_id+"'");
                rs.next();

                String availableQty = rs.getString("qty");

                if(Integer.parseInt(availableQty)<Integer.parseInt(qty)){
                    JOptionPane.showMessageDialog(this, "Quantity out of stock", "Warning", JOptionPane.WARNING_MESSAGE);
                }else{

                    DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();

                    boolean isFound=false;
                    int x=-1;
                    for(int i=0; i<dtm.getRowCount(); i++){
                        String id = jTable1.getValueAt(i, 0).toString();

                        if(id.equals(i_id)){
                            isFound=true;
                            x=i;
                            break;
                        }
                    }

                    if(isFound){

                        int option = JOptionPane.showConfirmDialog(this, "this Item is already added. do you want update?", "confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                        if(option==JOptionPane.YES_OPTION){
                            int oldQty = Integer.parseInt(jTable1.getValueAt(x, 2).toString());
                            int finalQty = oldQty + Integer.parseInt(qty);

                            //check stock

                            ResultSet rs1 = MySQL.search("SELECT * FROM `rental_item_stock` WHERE `id`='"+i_id+"'");
                            rs1.next();

                            String availableQty1 = rs1.getString("qty");

                            if(Integer.parseInt(availableQty1)<finalQty){
                                JOptionPane.showMessageDialog(this, "Quantity out of stock", "Warning", JOptionPane.WARNING_MESSAGE);
                            }else{

                                jTable1.setValueAt(String.valueOf(finalQty), x, 2);

                                double updatePricePerDayPerOne = finalQty * Double.parseDouble(price_per_day_per_one);
                                jTable1.setValueAt(String.valueOf(updatePricePerDayPerOne), x, 3);
                                JOptionPane.showMessageDialog(this, "Update Item in list", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }

                            //check stock
                        }
                        resetFields();

                    }else{
            
                            DefaultTableModel dtm1 = (DefaultTableModel)jTable1.getModel();

                            Vector v = new Vector();
                            v.add(i_id);
                            v.add(i_name);
                            v.add(qty);
                            v.add(Double.parseDouble(price_per_day_per_one) * Integer.parseInt(qty));
                            dtm1.addRow(v);
                            jTable1.setModel(dtm1);
                            
                            JOptionPane.showMessageDialog(this, "Added new Item to list", "Success", JOptionPane.INFORMATION_MESSAGE);
                            resetFields();
                    }
                }
                    
             } catch (Exception e) {
                 e.printStackTrace();
             }           
            
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String c_id = jLabel2.getText();
        
        
        if(jTable1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "please add product", "WARNING", JOptionPane.WARNING_MESSAGE);
        }else{
            long mTime = System.currentTimeMillis();
        
            String uniqueId = mTime+"";
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dNow = sdf.format(new Date());
            
            MySQL.iud("INSERT INTO `rent`(`customer_id`,`rent_date`,`user_id`,`stat`,`unique_id`) VALUES('"+c_id+"','"+dNow+"','"+SignIn.userId+"','0','"+uniqueId+"')");
            
            //insert item rent
            try {
                ResultSet rs = MySQL.search("SELECT * FROM `rent` WHERE `unique_id`='"+uniqueId+"'");
                rs.next();
                String rent_id = rs.getString("id");
                
                for(int i=0; i<jTable1.getRowCount(); i++){
                    String item_id = jTable1.getValueAt(i, 0).toString();
                    String qty = jTable1.getValueAt(i, 2).toString();
                    String price_per_day = jTable1.getValueAt(i, 3).toString();
                    
                    ResultSet rs1 = MySQL.search("SELECT * FROM `rental_item_stock` WHERE `id`='"+item_id+"'");
                    rs1.next();
                    String available_item_qty = rs1.getString("qty");
                    
                    int updatedQty = Integer.parseInt(available_item_qty) - Integer.parseInt(qty);

                    MySQL.iud("UPDATE `rental_item_stock` SET `qty`='"+updatedQty+"' WHERE `id`='"+item_id+"'");
                    
                    MySQL.iud("INSERT INTO `item_rent`(`rental_item_stock_id`,`qty`,`rent_price_per_day`,`rent_id`) VALUES('"+item_id+"','"+qty+"','"+price_per_day+"','"+rent_id+"')");
                }
                JOptionPane.showMessageDialog(this, "New rent added successful.", "success", JOptionPane.INFORMATION_MESSAGE);
                DefaultTableModel dtm1 = (DefaultTableModel)jTable1.getModel();
                dtm1.setRowCount(0);
                
                jButton1.setText("Select Customer");
                jButton1.setEnabled(true);

                jLabel2.setText("None");
                jLabel4.setText("None");
                jLabel6.setText("None");
                loadViewRent();
                home.jDesktopPane1.removeAll();
                home.jDesktopPane1.revalidate();
                home.jDesktopPane1.repaint();
                home.viewRentHome();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            //insert item rent
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int r = jTable1.getSelectedRow();

            if(r==-1){
                JOptionPane.showMessageDialog(this, "Pleace select a Item", "warning", JOptionPane.WARNING_MESSAGE);
            }else{
                int option = JOptionPane.showConfirmDialog(this, "Do you want to remove this item?", "confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(option == JOptionPane.YES_OPTION){
                    DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                    dtm.removeRow(r);

                    JOptionPane.showMessageDialog(this, "Item removed", "success", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        String text = jTextField4.getText();
        searchViewRent(text);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        loadRentUpdate();

    }//GEN-LAST:event_jTable2MouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        String text = jComboBox1.getSelectedItem().toString();

        if(text.equals("Select")){
            jTextField3.setEditable(false);
            jTextField3.setText("");
            jLabel29.setText("0.00");
            jLabel29.setForeground(Color.BLACK);
        }else{
            jTextField3.setEditable(true);
            jTextField3.setEnabled(true);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
        String price = jTextField3.getText();
        String text = price + evt.getKeyChar();
        if(!Pattern.compile("(0|0[.]|0[.][0-9]*)|[1-9]|[1-9][0-9]*|[1-9][0-9]*[.]|[1-9][0-9]*[.][0-9]*").matcher(text).matches()){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        if(jTextField3.getText().isEmpty()){

            jLabel29.setText("0.00");
            jLabel29.setForeground(Color.BLACK);

        }else{
            String total = jLabel25.getText();
            String payment = jTextField3.getText();

            double balance = Double.parseDouble(total) - Double.parseDouble(payment);
            if(balance<0){
                jLabel29.setForeground(Color.RED);
            }else{
                jLabel29.setForeground(Color.GREEN);
            }
            jLabel29.setText(df.format(balance));
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
//        String receve_date = sdf1.format(jDateChooser1.getDate()).toString();
        String receve_date = jLabel17.getText();
        String payment = jTextField3.getText();
        String rent_id = jLabel14.getText();
        String payment_method = jComboBox1.getSelectedItem().toString();
        
        
        String balance = jLabel29.getText();
        
        if(payment_method.equals("SELECT")){
            JOptionPane.showMessageDialog(this, "please select payment method", "WARNING", JOptionPane.WARNING_MESSAGE);
        }else if(!Pattern.compile("[0]|(0|0[.]|0[.][0-9]*)|[1-9]|[1-9][0-9]*|[1-9][0-9]*[.]|[1-9][0-9]*[.][0-9]*").matcher(payment).matches()){
            JOptionPane.showMessageDialog(this, "invalid payment", "WARNING", JOptionPane.WARNING_MESSAGE);
        }else{
        try {
            
        ResultSet rs2 = MySQL.search("SELECT * FROM `payment_type` WHERE  `name`='"+payment_method+"'");
           rs2.next();
           String paymentTypeId = rs2.getString("id");
           
           MySQL.iud("INSERT INTO `rental_payment`(`payment`,`balance`,`rent_id`,`payment_type_id`) VALUES('"+payment+"','"+balance+"','"+rent_id+"','"+paymentTypeId+"')");
        
           for(int i=0; i<jTable3.getRowCount(); i++){
               
               String item_name = jTable3.getValueAt(i, 0).toString();
               String rent_qty = jTable3.getValueAt(i, 1).toString();
               ResultSet rs3 = MySQL.search("SELECT `qty` FROM `rental_item_stock` WHERE `name`='"+item_name+"'");
               rs3.next();
               int u_qty = (Integer.parseInt(rs3.getString("qty")) + Integer.parseInt(rent_qty));
               
               
               MySQL.iud("UPDATE `rental_item_stock` SET `qty`='"+u_qty+"' WHERE `name`='"+item_name+"'");
               
           }
           
           MySQL.iud("UPDATE `rent` SET `receve_date`='"+receve_date+"',`stat`='1' WHERE `id`='"+rent_id+"'");
           
           JOptionPane.showMessageDialog(this, "Rent Update Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
           
           String c_name = jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString();
           String r_date = jLabel16.getText();
           
           //rent report
           InputStream filePath = GRN.class.getResourceAsStream("/reports/rent_hms.jasper");
//            String filePath = "src//reports//rent_hms.jasper";
            
//            JasperReport jr = JasperCompileManager.compileReport(filePath);
            
            HashMap parameters = new HashMap();
            parameters.put("c_name",c_name);
            parameters.put("r_date",r_date);
            parameters.put("receve_date",receve_date);
            parameters.put("tot",jLabel25.getText());
            parameters.put("p_type",payment_method);
            parameters.put("payment",payment);
            parameters.put("balance",balance);
          

            TableModel tm = jTable3.getModel();
            JRTableModelDataSource datasource = new JRTableModelDataSource(tm);

            
            JasperPrint jp = JasperFillManager.fillReport(filePath, parameters, datasource);
            
//            JasperExportManager.exportReportToHtmlFile(jp, "dasunHardware.html");
            
            JasperViewer.viewReport(jp, false);
            JasperPrintManager.printReport(jp, true);
           //rent report
           
           
           
           loadViewRent();
           jTable2.clearSelection();
           DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
           dtm.setRowCount(0);
           
           jLabel14.setText("None");
           jLabel16.setText("None");
//           jDateChooser1.setDate(null);
           jLabel17.setText("None");
           jLabel25.setText("0.00");
           jTextField3.setText("");
           jTextField3.setEnabled(false);
           jComboBox1.setSelectedIndex(0);
           jLabel29.setText("0.00");
           
           home.jDesktopPane1.removeAll();
           home.jDesktopPane1.revalidate();
           home.jDesktopPane1.repaint();
           home.viewRentHome();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }//GEN-LAST:event_jButton5ActionPerformed
   int x = 0;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
