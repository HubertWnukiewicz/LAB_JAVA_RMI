import knapsack.Instance;
import knapsack.Item;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        Spis spis =new Spis();
        System.out.println("PRZED: "+ spis.getRegisteredServers().size());
        Server server=new Server("Serwer_testowy");
        server.addition(10);
        Client client=new Client("Serwer_testowy");
        ArrayList<Item> products =new ArrayList<>();
        Item item1=new Item(23,505); products.add(item1);
        Item item2=new Item(26,352); products.add(item2);
        Item item3=new Item(20,458); products.add(item3);
        Item item4=new Item(18,220); products.add(item4);
        Item item5=new Item(32,354); products.add(item5);
        Item item6=new Item(27,414); products.add(item6);
        Item item7=new Item(29,498); products.add(item7);
        Item item8=new Item(26,545); products.add(item8);
        Item item9=new Item(30,473); products.add(item9);
        Item item10=new Item(27,543); products.add(item10);

        Instance instance= new Instance(67,products);
        client.solveInstance(instance);
        System.out.println("PO: "+ spis.getRegisteredServers().size());
        Server server2=new Server("Serwer_testowy2");
        Client client2=new Client("Serwer_testowy2");
        client2.solveInstance(instance);
        System.out.println("PO2: "+ spis.getRegisteredServers().size());
        for(int i=0;i<spis.getRegisteredServers().size();i++)
        {
            System.out.println(spis.getRegisteredServers().get(i).name);
        }


    }

}
