import knapsack.Instance;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    void getAllServers() throws RemoteException;
}
