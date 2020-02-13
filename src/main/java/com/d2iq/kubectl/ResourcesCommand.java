package com.d2iq.kubectl;


import dnl.utils.text.table.TextTable;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1APIResource;
import io.kubernetes.client.models.V1APIResourceList;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(
        name = "resources",
        description = "lists resources in the k8s cluster"
)
public class ResourcesCommand implements Runnable {

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

        V1APIResourceList list;
        try {
            CoreV1Api api = new CoreV1Api(client);
            list = api.getAPIResources();

        } catch (ApiException e) {
            System.out.println("unable to get resource list");
            System.err.println(e);
            return;
        }


        if (list.getResources().size() < 1) {
            System.out.println("No resources found");
        } else {
            printTable(list.getResources());
        }
    }

    private void printTable(List<V1APIResource> resources) {
        Object[][] data = new Object[resources.size()][];
        int i = 0;
        for (V1APIResource item : resources) {
            ArrayList<Object> cols = new ArrayList<>();
            cols.add(item.getName());
            cols.add(item.isNamespaced());
            cols.add(item.getKind());
            data[i++] = cols.toArray();
        }

        String[] columnNames = {"Resource", "Namespaced", "Kind"};

        TextTable tt = new TextTable(columnNames, data);
        tt.printTable();
    }
}
