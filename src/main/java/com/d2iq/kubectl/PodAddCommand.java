package com.d2iq.kubectl;


import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodBuilder;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(
        name = "add",
        description = "Adds a pod to a k8s cluster"
)
public class PodAddCommand implements Runnable {

    @CommandLine.Parameters(index = "0")
    String name;

    @CommandLine.Option(names = {"-i", "--image"}, description = "image to use for pod")
    String image = "nginx";

    @CommandLine.Option(names = {"-n", "--namespace"}, description = "namespace to use for pod")
    String namespace = "default";

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

        try {
            CoreV1Api api = new CoreV1Api(client);
            Map<String, String> labels = new HashMap<>();
            labels.put("app", "demo");
            V1Pod pod = new V1PodBuilder()
                    .withNewMetadata()
                    .withName(name)
                    .withLabels(labels)
                    .endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName(name)
                    .withImage(image)
                    .endContainer()
                    .endSpec()
                    .build();

            api.createNamespacedPod(namespace, pod, null, null, null);

        } catch (ApiException e) {
            System.out.println("unable to get pod list");
            System.err.println(e);
            return;
        }
    }
}
