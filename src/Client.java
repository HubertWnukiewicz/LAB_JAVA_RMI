import knapsack.Instance;
import knapsack.Item;
import knapsack.Result;

import javax.swing.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Client{
    private static IServer server;
    private static IRegister registers;
    private int id;
    public Client(String name) throws RemoteException {

            System.out.println("Klient port: "+PortFactor.getPort());
            //IClient client = (IClient) UnicastRemoteObject.exportObject(this, PortFactor.getPort());
            //Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            //server = (IServer) registry.lookup(name);
            //registers = (IRegister) registry.lookup("Spis");
            //id = server.register();

            //registry.bind("Klient", client);
        }



    public void getAllServers() throws RemoteException {
        for(int i=0;i<registers.getServers().size();i++)
        {
            System.out.println(registers.getServers().get(i).name);
        }

    }
    public void solveInstance(int alg) throws RemoteException {
        System.out.println("Rozwiązuje: "+server.getName());
        Instance instance=createRandomInstance();
        Result result=server.solve(instance,alg);

        System.out.println("Selected items: ");
        for (int i=0;i<result.getItems().size();i++)
        {
            System.out.println(i+". Value: "+result.getItems().get(i).getValue()+", weight: "+result.getItems().get(i).getWeight());
        }
        System.out.println("Final Value: "+result.calculateFinalValue());
    }
    private Instance createRandomInstance(){
        Random random=new Random();
        ArrayList<Item> products=createRandomProducts();
        int size=random.nextInt(85)+5;
        Instance instance=new Instance(size,products);

        return instance;
    }
    private ArrayList<Item> createRandomProducts(){
        Random random=new Random();
        int numberOfItems=random.nextInt(12)+2;
        ArrayList<Item> products=new ArrayList<>();
        for(int i=0;i<numberOfItems;i++)
        {
            float value=random.nextFloat()+2;
            int weight=random.nextInt(20)+2;
            Item item=new Item(weight,value);
            System.out.println("Weight: "+item.getWeight());
            System.out.println("Value: "+item.getValue());
            products.add(item);

        }
        return products;
    }
    public void changeServer(String name) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        server = (IServer) registry.lookup(name);
    }
    public static void displayMenu(Scanner reader,Client client) throws RemoteException, NotBoundException {
        while(true)
        {
            System.out.println("---------APLIKACJA KLIENTA---------");
            System.out.println("0. List all servers.");
            System.out.println("1. Name of the selected server: "+server.getName());
            System.out.println("2. Description of the selected server: "+server.getDescription());
            System.out.println("3. Change server: ");
            System.out.println("4. Solve random instance. ");
            int choice=reader.nextInt();
            if(choice==0)
            {
                for(int i=0;i<registers.getServers().size();i++)
                {
                    System.out.println(registers.getServers().get(i).name);
                }
            }
            else if(choice==3){
                System.out.println("New server name: ");
                String name=reader.next();
                client.changeServer(name);
            }else if(choice ==4){
                System.out.println("Choose algorithm: ");
                server.getListOfImplementedAlgorithms();
                System.out.println("0. Brute force. ");
                System.out.println("1. Random selection. ");
                System.out.println("2. Greedy solution. ");
                System.out.println("3. Dynamic programming. ");
                int alg=reader.nextInt();
                if(alg>=0 && alg<4) {
                    client.solveInstance(alg);
                    displayMenu(reader,client);
                }
                else
                {
                    System.out.println("Incorrect number!");
                    displayMenu(reader,client);
                }
            }
        }
    }
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner reader= new Scanner(System.in);
        Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);

        registers = (IRegister) registry.lookup("Spis");
        System.out.println("Lista dostepnych serwerow:");
        for(int i=0;i<registers.getServers().size();i++)
        {
            System.out.println(registers.getServers().get(i).name);
        }
        System.out.println("---------APLIKACJA KLIENTA---------");
        System.out.println("1. Podaj nazwe serwera");
        String name=reader.next();
        server = (IServer) registry.lookup(name);
        Client client=new Client(name);

        displayMenu(reader,client);



    }
}
