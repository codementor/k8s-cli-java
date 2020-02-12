package com.d2iq.kubectl;


import picocli.CommandLine;


public final class Main {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    public static void main(String[] args) {

        CommandLine cmd = new CommandLine(new ExampleCommand());

        if (cmd.isUsageHelpRequested()) {
            cmd.usage(cmd.getOut());
            return;
        }

        cmd.execute(args);
    }
}
