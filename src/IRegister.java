import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRegister extends Remote {
    boolean register( OS os ) throws RemoteException;

    ArrayList<OS> getServers() throws RemoteException;
}
