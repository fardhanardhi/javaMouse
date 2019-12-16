/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamouse;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.Timer;

/**
 *
 * @author adan
 */
public class Detect extends javax.swing.JFrame {
    int port;
    String ip;

    public Detect(int port, String ip) {
        initComponents();
        
        this.port = port;
        this.ip = ip;
        
        tm.start();
    }

    public Detect() {
    }
    
    Timer tm = new Timer(50, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Double posX = MouseInfo.getPointerInfo().getLocation().getX()/2;
            Double posY = MouseInfo.getPointerInfo().getLocation().getY()/2;

            txtX.setText("x: " + posX);
            txtY.setText("y: " + posY);

            String dataS = String.valueOf(posX.intValue() + "," + posY.intValue());

            System.out.println(dataS);

            try {
                MulticastSocket multicastSocket = new MulticastSocket(port);
                InetAddress group = InetAddress.getByName(ip);

                multicastSocket.joinGroup(group);

                System.out.println("Type a message for the server:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                DatagramPacket data = new DatagramPacket(dataS.getBytes(), 0, dataS.length(), group, port);

                multicastSocket.send(data);

                multicastSocket.close();
            } catch (Exception x) {
                System.out.println("Gagal");
            }
        }
    });

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtX = new javax.swing.JLabel();
        txtY = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtX.setText("x:");

        txtY.setText("y:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtX)
                    .addComponent(txtY))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtY)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Detect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Detect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Detect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Detect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Detect().setVisible(true);             
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel txtX;
    private static javax.swing.JLabel txtY;
    // End of variables declaration//GEN-END:variables
}
