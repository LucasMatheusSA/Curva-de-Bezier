package curvadebezier;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CurvaBezier extends javax.swing.JFrame {

    ArrayList<Double> px = new ArrayList();
    ArrayList<Double> py = new ArrayList();
    int largura = 7, altura = 6, escala = 50;

    public CurvaBezier() {
        initComponents();
        pre_grafica();
    }

    private void pre_grafica() {
        BufferedImage picture = new BufferedImage(largura * escala, altura * escala, BufferedImage.TYPE_INT_RGB);

        Graphics g = picture.getGraphics();

        g = draw_grid(g);
        g = draw_ponto(g, true);
        labelshow.setIcon(new ImageIcon(picture));

    }

    private Graphics draw_grid(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, largura * escala, altura * escala);
        g.setColor(new Color(0, 0, 0, 0.1f));

        for (int i = escala; i <= largura * escala; i += escala) {
            g.fillRect(i, 0, 1, altura * escala);
        }
        for (int i = escala; i <= altura * escala; i += escala) {
            g.fillRect(0, i, largura * escala, 1);
        }
        return g;
    }

    private Graphics draw_ponto(Graphics g, boolean dibuja_poligono) {
        g.setColor(Color.blue);
        Polygon poligono = new Polygon();
        for (int i = 0; i < px.size(); i++) {
            poligono.addPoint((int) (px.get(i) * escala), (int) (altura * escala - py.get(i) * escala));
            g.fillOval((int) (px.get(i) * escala - (0.05 * escala)),
                    (int) (altura * escala - py.get(i) * escala - (0.05 * escala)),
                    (int) (0.1 * escala), (int) (0.1 * escala));
            g.drawString("Ponto " + (i + 1),
                    (int) (px.get(i) * escala) + 5,
                    (int) (altura * escala - py.get(i) * escala) - 5);
        }
        g.setColor(Color.BLUE);
        if (dibuja_poligono) {
            g.drawPolygon(poligono);
        }
        return g;
    }

    private void curvaBezier(int largura, int altura, int escala, double parametro_t) {
        BufferedImage picture
                = new BufferedImage(largura * escala, altura * escala, BufferedImage.TYPE_INT_RGB);

        Graphics g = picture.getGraphics();

        g = draw_grid(g);
        g = draw_ponto(g, true);

        g.setColor(Color.red);
        double it = 0, fpx, fpy;
        while (it <= 1) {
            fpx = funcionParametrica(px, it) * escala;
            fpy = funcionParametrica(py, it) * escala;

            g.fillRect((int) fpx, (int) (altura * escala - fpy), 1, 1);

            it += parametro_t;
        }

        labelshow.setIcon(new ImageIcon(picture));

    }

    private double funcionParametrica(ArrayList<Double> p, double t) {
        double restemp = 0;

        for (int i = 0; i < p.size(); i++) {
            restemp
                    += p.get(i) * combinacao(p.size() - 1, i)
                    * Math.pow(t, i) * Math.pow((1 - t),
                    (p.size() - 1 - i));
        }

        return restemp;
    }

    private int fatorial(int numero) {
        if (numero > 1) {
            return numero * fatorial(numero - 1);
        } else {
            return 1;
        }
    }

    private int combinacao(int n, int m) {
        return fatorial(n) / (fatorial(m) * fatorial(n - m));
    }

    private void preencher_table() {
        DefaultTableModel table_model
                = (DefaultTableModel) tablapuntos.getModel();
        table_model.setRowCount(0);
        for (int i = 0; i < px.size(); i++) {
            table_model.addRow(new Object[]{i + 1, px.get(i), py.get(i)});
        }
        pre_grafica();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        labelshow = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_criar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablapuntos = new javax.swing.JTable();
        btn_adicionar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        input_ponto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Curva de Bezier");

        labelshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelshow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(labelshow);

        btn_criar.setText("Criar curva");
        btn_criar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_criarActionPerformed(evt);
            }
        });

        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tablapuntos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ponto", "X", "Y"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablapuntos.setMaximumSize(new java.awt.Dimension(75, 64));
        tablapuntos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablapuntos.getTableHeader().setReorderingAllowed(false);
        tablapuntos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablapuntosKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablapuntos);
        if (tablapuntos.getColumnModel().getColumnCount() > 0) {
            tablapuntos.getColumnModel().getColumn(0).setResizable(false);
            tablapuntos.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablapuntos.getColumnModel().getColumn(1).setResizable(false);
            tablapuntos.getColumnModel().getColumn(1).setPreferredWidth(25);
            tablapuntos.getColumnModel().getColumn(2).setResizable(false);
            tablapuntos.getColumnModel().getColumn(2).setPreferredWidth(25);
        }

        btn_adicionar.setText("Adicionar");
        btn_adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_criar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btn_adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 4, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btn_adicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_criar)
                .addContainerGap())
        );

        jLabel2.setText("     Adicionar ponto:         ");

        input_ponto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                input_pontoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_pontoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                input_pontoKeyTyped(evt);
            }
        });

        jLabel4.setText("X,Y:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(input_ponto, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(input_ponto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_criarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_criarActionPerformed
        criar();
    }//GEN-LAST:event_btn_criarActionPerformed


    private void btn_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_btn_adicionarActionPerformed

    private void input_pontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_pontoKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_input_pontoKeyReleased

    private void input_pontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_pontoKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_input_pontoKeyTyped

    private void input_pontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_pontoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            adicionar();
        }
    }//GEN-LAST:event_input_pontoKeyPressed

    private void tablapuntosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablapuntosKeyPressed

    }//GEN-LAST:event_tablapuntosKeyPressed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CurvaBezier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CurvaBezier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CurvaBezier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CurvaBezier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CurvaBezier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_adicionar;
    private javax.swing.JButton btn_criar;
    private javax.swing.JTextField input_ponto;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelshow;
    private javax.swing.JTable tablapuntos;
    // End of variables declaration//GEN-END:variables

    private void criar() {
        if (px.size() < 2) {
            JOptionPane.showMessageDialog(null, "São necessários ao menos 2 pontos.");
        } else {
            int largura, altura, escala;
            double parametrot = 0.001;

            largura = 7;
            altura = 6;
            escala = 50;
            curvaBezier(largura, altura, escala, parametrot);
        }
    }

    private void adicionar() {
        String campo_add = input_ponto.getText().trim();
        Pattern p = Pattern.compile("([0-9]+[.]?[0-9]?),([0-9]+[.]?[0-9]?)");
        Matcher m = p.matcher(campo_add);
        if (m.matches()) {
            input_ponto.setText("");
            String[] res = m.group(0).split(",");
            px.add(Double.parseDouble(res[0]));
            py.add(Double.parseDouble(res[1]));
            preencher_table();
        } else {
            JOptionPane.showMessageDialog(null, "Inserir pontos validos.");
        }
    }

}
