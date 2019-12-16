/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamouse;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class Move extends javax.swing.JFrame {

Robot robot;
    int port;
    String ip;

    public Move(int port, String ip) throws AWTException {
        initComponents();
        
        robot = new Robot();
        
        this.port = port;
        this.ip = ip;
        
        ServerThread st = new ServerThread(this, this.port, this.ip);
        st.start();
    }

    public Move() throws AWTException {
    }
    
    public void moveCursor(int x, int y) {
        txtX.setText("x: " + x);
        txtY.setText("y: " + y);

        robot.mouseMove(x, y);
    }

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
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Move().setVisible(true);
                } catch (AWTException ex) {
                    Logger.getLogger(Move.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel txtX;
    private static javax.swing.JLabel txtY;
    // End of variables declaration//GEN-END:variables
}

class ServerThread extends Thread {

    int port;
    String ip;
    Move move;

    public ServerThread(Move move, int port, String ip) {
        this.move = move;
        this.port = port;
        this.ip = ip; 
    }

    @Override
    public void run() {
        try {
            MulticastSocket server = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(ip);
            //getByName – Mengembalikan alamat IP yang diberikan oleh Host 
            server.joinGroup(group);
            boolean infinite = true;
            /* Server terus-menerus menerima data dan mencetak mereka */
            while (infinite) {
                byte buf[] = new byte[1024];
                DatagramPacket data = new DatagramPacket(buf, buf.length);
                server.receive(data);
                String msg = new String(data.getData()).trim();
                System.out.println(msg);
                
                String posRaw[] = msg.split("\\,", 0);
                int xPos = Integer.parseInt(posRaw[0]);
                int yPos = Integer.parseInt(posRaw[1]);
                move.moveCursor(xPos, yPos);
            }
            server.close();
        } catch (Exception e) {
        }
    }
}
