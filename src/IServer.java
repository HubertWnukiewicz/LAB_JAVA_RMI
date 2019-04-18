import knapsack.Instance;
import knapsack.Result;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {

    Result solve(Instance instance) throws RemoteException;

    String getName() throws RemoteException;

}
