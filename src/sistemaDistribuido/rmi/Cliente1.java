package sistemaDistribuido.rmi;

import static sistemaDistribuido.rmi.Servidor.findAllFilesInFolder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
//import javax.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Cliente1 extends javax.swing.JFrame {

    private static final String IP = "localhost"; // Puedes cambiar a localhost
    //private static final String IP = "192.168.100.6";
    private static final int PUERTO = 1100;
    Interfaz interfaz;

    public Cliente1() {

        try {
            Registry registry = iniciarCliente();
            interfaz = (Interfaz) registry.lookup("Archivos");
            initComponents();
            
            Imagen imagen = pintar(totalPanel.getWidth(), totalPanel.getHeight(), "src\\calculadora\\Imagenes\\fondo.png");
            totalPanel.add(imagen);
            totalPanel.repaint();
        } catch (RemoteException ex) {
            System.out.println("Servidor fuera de linea :/");
            System.exit(0);
        } catch (NotBoundException e) {
            System.out.println("A");
        }
    }
    
    private Imagen pintar(int x, int y, String origen) {
        //Saca las medidas del panel
        int ancho = x;
        int alto = y;
        //Se crea el objeto imagen, se pasa por parametros en ancho y alto de tendrá la imagen. Además de la ruta
        Imagen imagen = new Imagen(ancho, alto, origen);
        return imagen;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        totalPanel = new javax.swing.JPanel();
        subirBoton = new javax.swing.JButton();
        descargarBoton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        totalPanel.setBackground(new java.awt.Color(242, 243, 247));

        subirBoton.setBackground(new java.awt.Color(65, 79, 140));
        ImageIcon ic = new ImageIcon("src/calculadora/Imagenes/Cargar.png");
        subirBoton.setIcon(ic);
        subirBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subirBotonActionPerformed(evt);
            }
        });

        descargarBoton.setBackground(new java.awt.Color(65, 79, 140));
        ImageIcon ico = new ImageIcon("src/calculadora/Imagenes/desCargar2.png");
        descargarBoton.setIcon(ico);
        descargarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    descargarBotonActionPerformed(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        javax.swing.GroupLayout totalPanelLayout = new javax.swing.GroupLayout(totalPanel);
        totalPanel.setLayout(totalPanelLayout);
        totalPanelLayout.setHorizontalGroup(
            totalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subirBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 487, Short.MAX_VALUE)
                .addComponent(descargarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        totalPanelLayout.setVerticalGroup(
            totalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalPanelLayout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addGroup(totalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descargarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subirBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(totalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(totalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        totalPanel.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void subirBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subirBotonActionPerformed
        File file;
        String ruta;
        byte[] fileContent;
        boolean operacion;
        int opc;
        JFileChooser ventana = new JFileChooser();
        ventana.setFileSelectionMode(JFileChooser.FILES_ONLY);
        ventana.setDialogTitle("Subir Archivo...");

        int seleccion = ventana.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            file = ventana.getSelectedFile();
            //ruta = archivo.getAbsolutePath();

            //System.out.println("SUBIR:"+ruta);
            //file = new File(ruta);
            try {
                fileContent = Files.readAllBytes(file.toPath());
                //System.out.println("nombre: " + file.getName());
                operacion = interfaz.cargarArchivo(fileContent, file.getName());
                if (operacion) {
                    JOptionPane.showMessageDialog(null,"Archivo cargado","Cliente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    opc = JOptionPane.showConfirmDialog(null, "Ya existe el archivo, desea reemplazarlo [S/n]");
                    //System.out.print("Ya existe el archivo, desea reemplazarlo [S/n]");

                    if (opc == 0) {
                        //reemplazamos el archivo
                        interfaz.reemplazarArchivo(fileContent, file.getName());
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_subirBotonActionPerformed

    private void descargarBotonActionPerformed(java.awt.event.ActionEvent evt) throws RemoteException, IOException {//GEN-FIRST:event_descargarBotonActionPerformed
        String ruta, ruta2;
        String nArchivo = "";
        JFileChooser ventana = new JFileChooser();
        ventana.setCurrentDirectory(new File("Archivos\\"));
        File folder = new File("Archivos\\");
        ruta = "Archivos\\";
        byte[] fDown = null;
        //byte[] fileContent = Files.readAllBytes(file.toPath());

        File fileDownload;
        int i = 0, pos = 0;
        boolean existe = false;

        //System.out.println("iArchivos totales " + interfaz.numeroArchivos());
        if(interfaz.numeroArchivos() != 0){
            for (int j = 0; j < interfaz.numeroArchivos(); j++){
                existe = false;
                
                
                //System.err.println(interfaz.nombreArchivo(j));
                for (File f : folder.listFiles()) {
                    
                    /*System.out.print("\nArchivo servidor: = " + interfaz.nombreArchivo(j) + " ");
                    System.out.println("archivo local = " + f.getName());*/
                    if ( f.getName().equals( interfaz.nombreArchivo(j) ) ) {
                        
                        existe = true;
                        
                        break;
                    }
                    
                    //System.out.println("noExiste = " + existe);
                }
                
                
                if (!existe) {
                    try {
                        fileDownload = new File(ruta + interfaz.nombreArchivo(j));
                        FileOutputStream fout = new FileOutputStream(fileDownload);
                        fout.write(interfaz.descargarArchivo(j));
                    } catch (IOException e) {}
                }
            }
        }

        //ELEGIMOS EL ARCHIVO PARA DESCARGAR
        ventana.setFileSelectionMode(JFileChooser.FILES_ONLY);
        ventana.setAcceptAllFileFilterUsed(false);
        ventana.setDialogTitle("Elegir archivo");

        if (ventana.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File carpeta = ventana.getSelectedFile();

            fDown = Files.readAllBytes(carpeta.toPath());

            nArchivo = carpeta.getName();

            ruta = carpeta.getAbsolutePath();

            //System.out.println("Carpeta: " + ruta);
        }

        JFileChooser ventana2 = new JFileChooser();
        ventana2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        ventana2.setAcceptAllFileFilterUsed(false);
        ventana2.setDialogTitle("Ubicacion de la descarga");

        if (ventana2.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File carpeta = ventana2.getSelectedFile(); //ubicacion de descarga

            ruta2 = carpeta.getAbsoluteFile() + File.separator + nArchivo;
            File archDescarga = new File(ruta2);

            try {
                FileOutputStream fout = new FileOutputStream(archDescarga);
                fout.write(fDown);
            } catch (IOException e) {
            }

            //System.out.println("DESCARGA: " + ruta2);

        }

    }//GEN-LAST:event_descargarBotonActionPerformed

    private Registry iniciarCliente() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(IP, PUERTO);
        return registry;
    }

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
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Cliente1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton descargarBoton;
    private javax.swing.JButton subirBoton;
    private javax.swing.JPanel totalPanel;
    // End of variables declaration//GEN-END:variables
}
