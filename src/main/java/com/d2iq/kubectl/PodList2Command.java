package com.d2iq.kubectl;

import dnl.utils.text.table.TextTable;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.ProtoClient;
import io.kubernetes.client.proto.V1;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;


@CommandLine.Command(
        name = "list2",
        description = "lists pods in the cluster using the protoclient approach"
)
public class PodList2Command implements Runnable {

    @CommandLine.Option(names = {"-n", "--namespace"}, description = "namespace to use for getting pods")
    String namespace = "default";

    @Override
    public void run() {

        ProtoClient pc;
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);
            pc = new ProtoClient(client);
        } catch (IOException e) {
            System.out.println("Unable to get cluster configuration");
            System.err.println(e);
            return;
        }

        ProtoClient.ObjectOrStatus<V1.PodList> list;
        try {
            String path = String.format("/api/v1/namespaces/%s/pods", namespace);
            list = pc.list(V1.PodList.newBuilder(), path);

        } catch (ApiException | IOException e) {
            System.out.println("unable to get pod list");
            System.err.println(e);
            return;
        }

        if (list.object.getItemsList().size() < 1) {
            System.out.println("No Pods found");
        } else {
            printTable(list.object);
        }
    }

    private void printTable(V1.PodList podList) {
        Object[][] data = new Object[podList.getItemsList().size()][];
        int i = 0;
        for (V1.Pod item : podList.getItemsList()) {
            ArrayList<Object> cols = new ArrayList<>();
            cols.add(item.getMetadata().getName());
            cols.add(item.getMetadata().getNamespace());
            data[i++] = cols.toArray();
        }

        String[] columnNames = {"Pod Name", "namespace"};

        TextTable tt = new TextTable(columnNames, data);
        tt.printTable();
    }
}
