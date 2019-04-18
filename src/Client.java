import knapsack.Instance;
import knapsack.Result;

import javax.swing.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client implements IClient{
    private IServer server;
    private IRegister registers;
    private int id;
    public Client(String name) throws RemoteException {

        try {
            System.out.println("Klient port: "+PortFactor.getPort());
            IClient client = (IClient) UnicastRemoteObject.exportObject(this, PortFactor.getPort());
            Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            server = (IServer) registry.lookup(name);
            registers = (IRegister) registry.lookup("Spis");
            //id = server.register();

            //registry.bind("Klient", client);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Administrator nie jest uruchomiony");
            System.exit(0);
        } catch (NotBoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Błąd nawiązania połaczenia");
            System.exit(0);
        }

    }

    @Override
    public void getAllServers() throws RemoteException {
        id=server.addition(10);
        System.out.println(id);

    }
    public void solveInstance(Instance instance ) throws RemoteException {
        Result result=server.solve(instance);
        for (int i=0;i<result.getItems().size();i++)
        {
            System.out.println(result.getItems().get(i).getWeight());
        }
        System.out.println("Value: "+result.calculateFinalValue());
    }

}
