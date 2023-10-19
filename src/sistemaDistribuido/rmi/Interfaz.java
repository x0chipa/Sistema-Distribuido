package sistemaDistribuido.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote {
    boolean cargarArchivo(byte[] fileContent, String nombre) throws RemoteException;
    byte[] descargarArchivo(int num) throws RemoteException;
    String nombreArchivo(int num) throws RemoteException;
    void reemplazarArchivo(byte[] fileContent, String nombre) throws RemoteException; 
    String imprimirListaArchivos()throws RemoteException;
    int numeroArchivos() throws RemoteException;
}