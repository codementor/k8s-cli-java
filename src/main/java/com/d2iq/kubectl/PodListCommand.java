package com.d2iq.kubectl;

import dnl.utils.text.table.TextTable;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;


/**
 * PodListCommand is a command for listing pods in a kubernetes cluster.  This class is an example and requires
 * standard kubeconfig setup to work.
 *
 */
@CommandLine.Command(
        name = "list",
        description = "lists pods in the cluster using a structured approach"
)
public class PodListCommand implements Runnable {
    @Override
    public void run() {

        ApiClient client;
        try {
            client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);
        } catch (IOException e) {
            System.out.println("Unable to get cluster configuration");
            System.err.println(e);
            return;
        }

        V1PodList list;
        try {
            CoreV1Api api = new CoreV1Api(client);
            list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        } catch (ApiException e) {
            System.out.println("unable to get pod list");
            System.err.println(e);
            return;
        }

        if (list.getItems().size() < 1) {
            System.out.println("No Pods found");
        } else {
            printTable(list);
        }
    }

    /**
     * Prints the table of pods discovered
     *
     * @param list PodList of pods
     */
    private static void printTable(V1PodList list) {
        Object[][] data = new Object[list.getItems().size()][];
        int i = 0;
        for (V1Pod item : list.getItems()) {
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
