package sistemaDistribuido.rmi;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Imagen extends javax.swing.JPanel{
    private String ruta;
    public Imagen(int ancho, int alto, String ruta){
        this.setSize(ancho,alto);
        this.ruta = ruta;
    }
    
    @Override
    public void paint(Graphics g){
       Dimension medida = getSize(); 
       Image img = new ImageIcon(ruta).getImage();
       g.drawImage(img,0,0,medida.width, medida.height, null);
       setOpaque(false);
       super.paintComponent(g);
    }
}
