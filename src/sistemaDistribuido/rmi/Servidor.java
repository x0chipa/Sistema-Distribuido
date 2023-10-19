package sistemaDistribuido.rmi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Servidor {
    

    private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el cliente
    
    public static void findAllFilesInFolder(File folder) {
        int i=0;
        for (File file : folder.listFiles()) {
            
            if (!file.isDirectory()) {
                System.out.println( (i++) + " " + file.getName());
            } else {
                findAllFilesInFolder(file);
            }
        }
    }
    
    
    public static void imprimirBytes(byte[] fileContent){
        int i = 0;
        System.out.println("Archivo:");
        for (byte b : fileContent) {
            //b = files[i];
            System.out.print(b + " ");
            if(i%10 == 0){
                System.out.println("");
            }
            i++;
        }
    }
    	
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Remote remote;
        remote = UnicastRemoteObject.exportObject(new Interfaz() {
        
            public List<byte[]> files = new ArrayList<>();
            public List<String> fileName = new ArrayList<>();
            File folder = new File("Archivos");
            
            public int llenarListaServidor() throws IOException{
                int i=0;
                byte[] fileContent;
                for (File file : folder.listFiles()) {
                    if (!file.isDirectory()) {
                        fileContent = Files.readAllBytes(file.toPath());                        
                        files.add(fileContent);
                        fileName.add(file.getName());
                        i++;
                    } else {
                        findAllFilesInFolder(file);
                    }
                }
                return i;
            }
            
            
            @Override
            public boolean cargarArchivo(byte[] fileContent, String nombre) throws RemoteException {
                String ruta = "Archivos\\";
                File file = new File(ruta+nombre);
                
                for (String name : fileName) {
                    if(nombre.equals(name))
                        return false;
                }
                
                files.add(fileContent);
                fileName.add(nombre);
                
                //GUARDAMOS EL ARCHIVO EN EL SERVIDOR
                try {
                    FileOutputStream fout = new FileOutputStream(file);
                    fout.write(fileContent);
                    System.out.println("Archivo recibido " + nombre);
                    return true;
                }catch (IOException e){
                    return false;
                }
            }
            
            @Override
            public void reemplazarArchivo(byte[] fileContent, String nombre){
                int i = 0;
                File file = new File("Archivos\\"+nombre);
                for (String name : fileName) {
                    files.add(i, fileContent);
                    if(nombre.equals(name)){
                        //GUARDAMOS EL ARCHIVO EN EL SERVIDOR
                        try {
                            FileOutputStream fout = new FileOutputStream(file);
                            fout.write(fileContent);
                            System.out.println("Archivo recibido " + nombre);
                        }catch (IOException e){
                            e.printStackTrace();
                        }   
                    }
                    i++;
                }
            }

            @Override
            public String imprimirListaArchivos() throws RemoteException {
                int i=0;
                String l = "";
                
                for (String n : fileName) {
                    l += n + ":";
                }
                return l;
                
            }
            
            
            
            
            @Override
            public int numeroArchivos(){
                int i=0;
                if(fileName.isEmpty()){
                    byte[] fileContent;
                    for (File file : folder.listFiles()) {
                        if (!file.isDirectory()) {
                            try {                        
                                fileContent = Files.readAllBytes(file.toPath());
                                files.add(fileContent);
                                fileName.add(file.getName());  
                                i++;
                            } catch (IOException ex) {
                                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            findAllFilesInFolder(file);
                        }
                    }
                    return i;
                }
                
                for (File file : folder.listFiles()) {

                    if (!file.isDirectory()) {
                        i++;
                    } else {
                        findAllFilesInFolder(file);
                    }
                }
                //return files.size();
                return i;    
            }
            
            @Override
            public byte[] descargarArchivo(int num) throws RemoteException {
                return files.get(num);
            }

            @Override
            public String nombreArchivo(int num) throws RemoteException {
                return fileName.get(num);
                
            }
            
            
        	
            
            
            
        }, 0);
        Registry registry = LocateRegistry.createRegistry(PUERTO);
       	System.out.println("Servidor prendido\nPuerto: " + String.valueOf(PUERTO));
        
        registry.bind("Archivos", remote); // Registrar calculadora
        
    }
}