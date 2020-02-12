package com.d2iq.kubectl;

import picocli.CommandLine;

@CommandLine.Command(
        name = "list2",
        description = "lists pods in the cluster using an unstructured approach"
)
public class PodList2Command implements Runnable{
    @Override
    public void run() {
        System.out.println("pod list2");
    }
}
