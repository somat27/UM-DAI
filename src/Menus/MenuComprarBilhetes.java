/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Menus;

import Main.Dijkstra;
import Main.Node;
import Main.Notification.Notification;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;


/**
 *
 * @author tomas
 */
public class MenuComprarBilhetes extends javax.swing.JFrame {

    private final Connection con;
    float valor = 0.0f;
    Map<String, Node<String>> nodesMap = new HashMap<>();
    Dijkstra<String> dijkstra = new Dijkstra<>();
    
    public void ComprarBilheteUnico(String corSelecionada, String quantidadeSelecionadaStr, String tipoBilhete) {
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet rs = null;

        try {
            String selectQuery = "SELECT * FROM BilhetesUnicos WHERE Linha = ?";
            updateStatement = con.prepareStatement(selectQuery);
            updateStatement.setString(1, corSelecionada);
            rs = updateStatement.executeQuery();

            if (rs.next()) {
                // If record exists, update it
                int valorAtual = rs.getInt("Quantidade_Bilhetes");
                int novoValorInt = Integer.parseInt(quantidadeSelecionadaStr);
                int novoValorTotal = valorAtual + novoValorInt;

                String updateQuery = "UPDATE BilhetesUnicos SET Quantidade_Bilhetes = ? WHERE Linha = ?";
                updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setInt(1, novoValorTotal);
                updateStatement.setString(2, corSelecionada);
                updateStatement.executeUpdate();
            } else {
                // If record doesn't exist, insert it
                String insertQuery = "INSERT INTO BilhetesUnicos (Linha, Quantidade_Bilhetes) VALUES (?, ?)";
                insertStatement = con.prepareStatement(insertQuery);
                insertStatement.setString(1, corSelecionada);
                insertStatement.setInt(2, Integer.parseInt(quantidadeSelecionadaStr));
                insertStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (updateStatement != null) updateStatement.close();
                if (insertStatement != null) insertStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void ComprarBilhetePersonalizado(String Rota) {
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet rs = null;

        try {
            String insertQuery = "INSERT INTO BilhetesPersonalizados (ID_Cliente, Rota) VALUES (?, ?)";
            insertStatement = con.prepareStatement(insertQuery);
            insertStatement.setInt(1, 1);
            insertStatement.setString(2, Rota);
            insertStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (updateStatement != null) updateStatement.close();
                if (insertStatement != null) insertStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**

     * Creates new form Login_Application
     * @param con
     */
    public MenuComprarBilhetes(Connection con) {
        initComponents();
        this.con = con;
        
        TipoDeBilhete.removeAllItems();
        TipoDeBilhete.addItem("Único");
        TipoDeBilhete.addItem("Personalizado");
        TipoDeBilhete.setSelectedItem(null); 
        
        CorLinha.setVisible(false);
        TextoCorLinha.setVisible(false);
        Quantidade.setVisible(false);
        TextoQuantidade.setVisible(false);
        Painel.setVisible(false);
    }

    private void atualizarValorTotal() { 
        String corSelecionada = (String) CorLinha.getSelectedItem();
        String quantidadeSelecionadaStr = (String) Quantidade.getSelectedItem();   
        float PrecoFinal = 0;

        if(TipoDeBilhete.getSelectedIndex() == 0){
            // Verifica se a quantidade selecionada é null
            if (quantidadeSelecionadaStr != null) {
                int quantidadeSelecionada = Integer.parseInt(quantidadeSelecionadaStr);


                Statement st = null;
                ResultSet rs = null;
                try {
                    st = con.createStatement();
                    rs = st.executeQuery("SELECT * FROM TipoBilhetes");

                    while (rs.next()) {
                        String linha = rs.getString("Linha");
                        float preco = rs.getFloat("Preco");

                        if(corSelecionada!=null && corSelecionada.equals(linha))
                            PrecoFinal = preco;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (st != null) st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                PrecoFinal = PrecoFinal * quantidadeSelecionada;

                Preco.setText(String.valueOf(PrecoFinal));
            }
        }else{
            
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        panel1 = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TipoDeBilhete = new javax.swing.JComboBox<>();
        CorLinha = new javax.swing.JComboBox<>();
        Quantidade = new javax.swing.JComboBox<>();
        Painel = new javax.swing.JPanel();
        Cartao = new javax.swing.JRadioButton();
        Multibanco = new javax.swing.JRadioButton();
        MBWay = new javax.swing.JRadioButton();
        javax.swing.JLabel TextoCorLinha3 = new javax.swing.JLabel();
        javax.swing.JLabel TextoCorLinha4 = new javax.swing.JLabel();
        Continuar = new javax.swing.JButton();
        Preco = new javax.swing.JLabel();
        javax.swing.JLabel TextoTipoBilhete = new javax.swing.JLabel();
        TextoCorLinha = new javax.swing.JLabel();
        TextoQuantidade = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 71, 103));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(440, 920));

        jPanel1.setBackground(new java.awt.Color(0, 71, 103));
        jPanel1.setForeground(new java.awt.Color(0, 71, 103));
        jPanel1.setPreferredSize(new java.awt.Dimension(426, 931));

        panel1.setBackground(new java.awt.Color(3, 125, 190));
        panel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/TUB.png"))); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/notificacao.png"))); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/do-utilizador.png"))); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/seta-esquerda.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.setMaximumSize(new java.awt.Dimension(70, 70));
        jLabel4.setMinimumSize(new java.awt.Dimension(70, 70));
        jLabel4.setPreferredSize(new java.awt.Dimension(70, 70));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        TipoDeBilhete.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        TipoDeBilhete.setToolTipText("");
        TipoDeBilhete.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        TipoDeBilhete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TipoDeBilhete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TipoDeBilheteMouseClicked(evt);
            }
        });
        TipoDeBilhete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoDeBilheteActionPerformed(evt);
            }
        });

        CorLinha.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        CorLinha.setToolTipText("");
        CorLinha.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        CorLinha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CorLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CorLinhaActionPerformed(evt);
            }
        });

        Quantidade.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        Quantidade.setToolTipText("");
        Quantidade.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Quantidade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Quantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuantidadeActionPerformed(evt);
            }
        });

        Painel.setBackground(new java.awt.Color(255, 255, 255));
        Painel.setForeground(new java.awt.Color(0, 71, 103));

        Cartao.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        Cartao.setForeground(new java.awt.Color(0, 71, 103));
        Cartao.setText("Cartão de Débito/Crédito");
        Cartao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CartaoActionPerformed(evt);
            }
        });

        Multibanco.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        Multibanco.setForeground(new java.awt.Color(0, 71, 103));
        Multibanco.setText("Multibanco");
        Multibanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Multibanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MultibancoActionPerformed(evt);
            }
        });

        MBWay.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        MBWay.setForeground(new java.awt.Color(0, 71, 103));
        MBWay.setText("MB Way ");
        MBWay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MBWay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MBWayActionPerformed(evt);
            }
        });

        TextoCorLinha3.setBackground(new java.awt.Color(0, 71, 103));
        TextoCorLinha3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        TextoCorLinha3.setForeground(new java.awt.Color(0, 71, 103));
        TextoCorLinha3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TextoCorLinha3.setText("Preço:");

        TextoCorLinha4.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        TextoCorLinha4.setForeground(new java.awt.Color(0, 71, 103));
        TextoCorLinha4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TextoCorLinha4.setText("Método de Pagamento");

        Continuar.setBackground(new java.awt.Color(0, 71, 103));
        Continuar.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        Continuar.setForeground(new java.awt.Color(255, 255, 255));
        Continuar.setText("Continuar");
        Continuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Continuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinuarActionPerformed(evt);
            }
        });

        Preco.setText("0.00");

        javax.swing.GroupLayout PainelLayout = new javax.swing.GroupLayout(Painel);
        Painel.setLayout(PainelLayout);
        PainelLayout.setHorizontalGroup(
            PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextoCorLinha4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelLayout.createSequentialGroup()
                        .addComponent(TextoCorLinha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Preco)
                        .addGap(148, 148, 148))))
            .addGroup(PainelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PainelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Multibanco)
                        .addGap(180, 180, 180))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PainelLayout.createSequentialGroup()
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MBWay, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Continuar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Cartao, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PainelLayout.setVerticalGroup(
            PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelLayout.createSequentialGroup()
                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Preco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextoCorLinha3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextoCorLinha4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(Cartao)
                .addGap(18, 18, 18)
                .addComponent(Multibanco)
                .addGap(18, 18, 18)
                .addComponent(MBWay)
                .addGap(18, 18, 18)
                .addComponent(Continuar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        TextoTipoBilhete.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        TextoTipoBilhete.setForeground(new java.awt.Color(255, 255, 255));
        TextoTipoBilhete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextoTipoBilhete.setText("Tipo de Bilhete");

        TextoCorLinha.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        TextoCorLinha.setForeground(new java.awt.Color(255, 255, 255));
        TextoCorLinha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextoCorLinha.setText("Cor da Linha");

        TextoQuantidade.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        TextoQuantidade.setForeground(new java.awt.Color(255, 255, 255));
        TextoQuantidade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextoQuantidade.setText("Quantidade");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextoTipoBilhete, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(TextoCorLinha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextoQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TipoDeBilhete, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CorLinha, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Quantidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Painel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(TextoTipoBilhete, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TipoDeBilhete, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextoCorLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(CorLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextoQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(Painel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    ButtonGroup buttonGroup = new ButtonGroup();
    private void CartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CartaoActionPerformed
        buttonGroup.add(Cartao);
        Cartao.setVisible(true);
    }//GEN-LAST:event_CartaoActionPerformed

    private void MultibancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultibancoActionPerformed
        buttonGroup.add(Multibanco);
        Multibanco.setVisible(true);
    }//GEN-LAST:event_MultibancoActionPerformed
    
    private void MBWayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MBWayActionPerformed
        buttonGroup.add(MBWay);
        MBWay.setVisible(true);
    }//GEN-LAST:event_MBWayActionPerformed

    String Caminho = null;
    private int a=0;
    
    public void calcularMelhorRota() {
        String corSelecionada = (String) CorLinha.getSelectedItem();
        String quantidadeSelecionadaStr = (String) Quantidade.getSelectedItem();
        
        if(corSelecionada != null && quantidadeSelecionadaStr != null){ 
            if(corSelecionada.equals(quantidadeSelecionadaStr) && a!=0) {
                Notification panel = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Selecione paregens distintas");
                panel.showNotification();
                Preco.setText("");
                return;
            }
            a=1;
            try (Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs2 = st.executeQuery("SELECT * FROM Paragens")) {

                //ResultSet rs2 = st.executeQuery("SELECT * FROM Paragens");
                while (rs2.next()) {
                    String nodeName = rs2.getString("Nome");
                    Node<String> node = new Node<>(nodeName);
                    nodesMap.put(nodeName, node);
                }
                rs2.beforeFirst();
                while(rs2.next()){
                    String nodeName = rs2.getString("Nome");
                    Node<String> node = nodesMap.get(nodeName);
                    String adjacentNodeName = rs2.getString("Proximo");
                    int distancia = rs2.getInt("Distancia");
                    String lines = rs2.getString("Linha"); 
                    Node<String> adjacentNode = nodesMap.get(adjacentNodeName);
                    for (String line : lines.split(",")) { 
                        node.addAdjacentNode(adjacentNode, line, distancia);
                    }
                }

                Node<String> sourceNode = nodesMap.get(corSelecionada);
                Node<String> destinationNode = nodesMap.get(quantidadeSelecionadaStr);
                if(sourceNode!=destinationNode) {
                    List<Node<String>> shortestPath = dijkstra.calculateShortestPath(sourceNode, destinationNode);
                    //Calcular preço da viagem
                    Object[] valores = dijkstra.printPaths(shortestPath);
                    //valores[0] -> Caminho
                    //valores[1] -> KM's
                    //valores[2] -> Qtd Trocas
                    //valores[3] -> Qtd Paragens
                    int totalTrocas = (int) valores[2];
                    
                    if(totalTrocas>1){
                        double resultado = (totalTrocas * 0.74) - (totalTrocas * 0.05 * 0.74);
                        DecimalFormat df = new DecimalFormat("#.##"); 
                        Preco.setText(df.format(resultado));
                        valor = (float) resultado;
                        
                        Caminho = (String) valores[0];
                    }else{
                        //andou sempre na mesma linha
                        
                        Notification panel = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Paragens pertencem a mesma linha");
                        panel.showNotification();
                        Preco.setText("");
                    }
                }
                st.close();
                rs2.close();
                nodesMap.clear();
            } catch (SQLException ex) {
                Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void TipoDeBilheteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoDeBilheteActionPerformed
        if(TipoDeBilhete.getSelectedIndex() == 0){
            TextoCorLinha.setText("Cor da Linha");
            TextoQuantidade.setText("Quantidade Bilhetes");
            CorLinha.setVisible(true);
            TextoCorLinha.setVisible(true);
            Quantidade.setVisible(true);
            TextoQuantidade.setVisible(true);
            Painel.setVisible(true);
            
            
            CorLinha.removeAllItems();
            Statement st = null;
            ResultSet rs = null;
            try {
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM TipoBilhetes");

                while (rs.next()) {
                    String linha = rs.getString("Linha");

                    CorLinha.addItem(linha);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (st != null) st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            CorLinha.setSelectedItem(null); 

            Quantidade.removeAllItems();
            Quantidade.addItem("1");
            Quantidade.addItem("3");
            Quantidade.addItem("5");
            Quantidade.addItem("10");
            Quantidade.setSelectedItem(null); 
            atualizarValorTotal();
        }else if(TipoDeBilhete.getSelectedIndex() == 1){
            TextoCorLinha.setText("Paragem Inicial");
            TextoQuantidade.setText("Paragem Final");
            CorLinha.setVisible(true);
            TextoCorLinha.setVisible(true);
            Quantidade.setVisible(true);
            TextoQuantidade.setVisible(true);
            Painel.setVisible(true);   
            CorLinha.removeAllItems();
            Quantidade.removeAllItems();
            
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Horarios")) {

                Set<String> paragensAdicionadas = new HashSet<>();

                while (rs.next()) {
                    String estacao = rs.getString("Estacao");
                    if (!paragensAdicionadas.contains(estacao)) {
                        CorLinha.addItem(estacao);
                        Quantidade.addItem(estacao);
                        paragensAdicionadas.add(estacao);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MenuBilhetes.class.getName()).log(Level.SEVERE, null, ex);
            }
            CorLinha.setSelectedIndex(-1);
            Quantidade.setSelectedIndex(-1);
            Preco.setText("");
        }
    }//GEN-LAST:event_TipoDeBilheteActionPerformed

    private void CorLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CorLinhaActionPerformed
        if(TipoDeBilhete.getSelectedIndex() == 0 && CorLinha.getSelectedItem() != null && Quantidade.getSelectedItem() != null){
            atualizarValorTotal();
        } else if(TipoDeBilhete.getSelectedIndex() == 1 && CorLinha.getSelectedItem() != null && Quantidade.getSelectedItem() != null){
            calcularMelhorRota();
        }
    }//GEN-LAST:event_CorLinhaActionPerformed

    private void QuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuantidadeActionPerformed
        if(TipoDeBilhete.getSelectedIndex() == 0 && Quantidade.getSelectedItem() != null && CorLinha.getSelectedItem() != null){
            atualizarValorTotal();
        } else if(TipoDeBilhete.getSelectedIndex() == 1 && Quantidade.getSelectedItem() != null && CorLinha.getSelectedItem() != null){
            calcularMelhorRota();
        }
    }//GEN-LAST:event_QuantidadeActionPerformed

    private boolean SotemDigito(String numero){
        for (char num : numero.toCharArray()) {
            if (!(num >= '0' && num <= '9')) {
                return false;
            }
        }
        return true; 
    }
    
    private boolean FormatoMesAno(String MesAno){
        if(MesAno.length()!=5){
            JOptionPane.showMessageDialog(null, "A data deve estar no formato indicado de modo que contenha:\n  2 digitos para o mês!\n  1 barra!\n  2 digitos para o ano!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String mes = MesAno.substring(0, 2);
        char barra = MesAno.charAt(2);
        String ano = MesAno.substring(3);
        
        if(mes.length() != 2){
            JOptionPane.showMessageDialog(null, "O Mês deve conter 2 Digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!(SotemDigito(mes))){
            JOptionPane.showMessageDialog(null, "O Mês deve conter apenas Digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        int contarMes = Integer.parseInt(mes);
        
        if(contarMes < 1 || contarMes > 12){
            JOptionPane.showMessageDialog(null, "O Mês deve estar compreendido entre 1 e 12!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(ano.length() != 2){
            JOptionPane.showMessageDialog(null, "O Ano deve conter 2 Digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!(SotemDigito(ano))){
            JOptionPane.showMessageDialog(null, "O Ano deve conter apenas Digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(barra != '/'){
            JOptionPane.showMessageDialog(null, "O 3 valor inserido deve ser uma / !", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void ContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinuarActionPerformed
        int count = 0;
        int count2 = 0;
        int count4 = 0;
        String numeroCartao = null;
        String dataValidade = null;
        String cvc2 = null;
        Boolean pagou = false;
        
        String corSelecionada = (String) CorLinha.getSelectedItem();
        String quantidadeSelecionadaStr = (String) Quantidade.getSelectedItem();
        String tipoBilhete = (String) TipoDeBilhete.getSelectedItem();
        
        if(!Multibanco.isSelected() && !Cartao.isSelected() && !MBWay.isSelected()) {
            Notification panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Selecione o método de pagamento");
            panel.showNotification();
        }
        
        if(quantidadeSelecionadaStr == null && tipoBilhete.equals("Único")) {
            Notification panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Selecione a quantidade");
            panel.showNotification();
        }
        if(corSelecionada == null && tipoBilhete.equals("Único")) {
            Notification panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Selecione a linha");
            panel.showNotification();
        }
        
        if(quantidadeSelecionadaStr == null && tipoBilhete.equals("Personalizado")) {
            Notification panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Selecione a paragem final");
            panel.showNotification();
        }
        if(corSelecionada == null && tipoBilhete.equals("Personalizado")) {
            Notification panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Selecione a paragem inicial");
            panel.showNotification();
        }
        
        if(Preco.getText().equals("") && quantidadeSelecionadaStr != null && corSelecionada != null) {
            Notification panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Erro a efetuar compra, comprar em bilhete unico");
            panel.showNotification();
            
            if(!Multibanco.isSelected() && !Cartao.isSelected() && !MBWay.isSelected()) {
                panel = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Selecione o método de pagamento");
                panel.showNotification();
            }
        }else   if (corSelecionada != null && quantidadeSelecionadaStr != null && tipoBilhete != null){
            if (Multibanco.isSelected()) {
                Random random = new Random();
                long randomN = random.nextInt(100000001);
                String mensagem = "Entidade: 00000\nReferência: " + randomN + "\nValor: " + Preco.getText() ;
                JOptionPane.showMessageDialog(null, mensagem, "Detalhes do Pagamento", JOptionPane.INFORMATION_MESSAGE);
                pagou = true;
            }else if(MBWay.isSelected()){
                while(count != 2){
                    String input = JOptionPane.showInputDialog(null, "Insira o seu contacto:");
                    if(input.length()!= 9){
                        JOptionPane.showMessageDialog(null, "O número de telémovel deve ter 9 digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
                        continue;
                    } else {
                        count++;
                    }
                    if(SotemDigito(input)){
                        count++;
                    } else {
                        JOptionPane.showMessageDialog(null, "O número de telémovel deve conter só digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    if(count == 2){
                        break;
                    } else {
                        count = 0 ;
                    }
                }
                pagou = true;
            }else if(Cartao.isSelected()){
                while(count2 != 2){
                    numeroCartao = JOptionPane.showInputDialog(null, "Digite o número do cartão de crédito (16 dígitos):");
                    if(numeroCartao == null) {
                        return;
                    }
                    if(numeroCartao.length()!= 16){
                        JOptionPane.showMessageDialog(null, "O número do cartão de crédito deve ter 16 digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
                        continue;
                    } else {
                        count2++;
                    }
                    if(SotemDigito(numeroCartao)){
                        count2++;
                    } else {
                        JOptionPane.showMessageDialog(null, "O número do cartão deve conter apenas digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    if(count2 == 2){
                        break;
                    } else {
                        count2 = 0 ;
                    }
                }
                while(true){
                    dataValidade = JOptionPane.showInputDialog(null, "Digite a data de validade (MM/YY):");
                    if(FormatoMesAno(dataValidade)){
                        break;
                    }

                }
                while(count4 != 2){
                    cvc2 = JOptionPane.showInputDialog(null, "Digite o código CVC2 (3 dígitos):");
                    if(cvc2.length()!=3){
                        JOptionPane.showMessageDialog(null, "O código CVC2 deve conter 3 digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
                        continue;
                    } else{
                        count4++;
                    }
                    if(SotemDigito(cvc2)){
                        count4++;
                    } else{
                        JOptionPane.showMessageDialog(null, "O código CVC2 deve conter apenas digitos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    if(count4 == 2){
                        break;
                    } else{
                        count4 = 0;
                    }
                }

                String mensagem = String.format("Número do cartão: %s\nData de validade: %s\nCódigo CVC2: %s", numeroCartao, dataValidade, cvc2);
                JOptionPane.showMessageDialog(null, mensagem, "Dados do Cartão de Crédito", JOptionPane.INFORMATION_MESSAGE);
                pagou = true;
            }
            if(pagou){
                if(TipoDeBilhete.getSelectedIndex() == 0){
                    ComprarBilheteUnico(corSelecionada, quantidadeSelecionadaStr, tipoBilhete);
                } else if(TipoDeBilhete.getSelectedIndex() == 1){
                    //calcularMelhorRota();
                    
                    ComprarBilhetePersonalizado(Caminho);
                }
                if(!Preco.getText().equals("")) {
                    Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Compra efetuada com sucesso");
                    panel.showNotification();
                }
            }
        }
    }//GEN-LAST:event_ContinuarActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        dispose();
        MenuBRT Voltar = new MenuBRT();
        Voltar.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void TipoDeBilheteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TipoDeBilheteMouseClicked
        CorLinha.removeAllItems();
        Quantidade.removeAllItems();
    }//GEN-LAST:event_TipoDeBilheteMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Cartao;
    private javax.swing.JButton Continuar;
    private javax.swing.JComboBox<String> CorLinha;
    private javax.swing.JRadioButton MBWay;
    private javax.swing.JRadioButton Multibanco;
    private javax.swing.JPanel Painel;
    private javax.swing.JLabel Preco;
    private javax.swing.JComboBox<String> Quantidade;
    private javax.swing.JLabel TextoCorLinha;
    private javax.swing.JLabel TextoQuantidade;
    private javax.swing.JComboBox<String> TipoDeBilhete;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Panel panel1;
    // End of variables declaration//GEN-END:variables
}
