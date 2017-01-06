package put.os.interpreter;

import put.os.processes.ProcessBlockController;
import virtual.device.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Interpreter {

    private class Command {
        private int argLength;

        public Command(int argLength) { this.argLength = argLength; }
        public int getArgLength() { return argLength; }

        public boolean execute(Vector<String> arg) { return false; }
    }
    private static Map<String, Command> commands = new HashMap<>();

    private Vector<StringBuilder> tokens;

    private ProcessBlockController pcb;

    public Interpreter(ProcessBlockController pcb) {
        this.pcb = pcb;
        this.tokens.setSize(3);

        // MOV
        commands.put("MOV", new Command(2) {
            private int getSource(String arg) {
                if (arg.contains("[0-9]+")) {
                    return Integer.parseInt(arg);
                }

                switch (arg) {
                    case "A":
                        return Processor.A;
                    case "B":
                        return Processor.B;
                    case "C":
                        return Processor.C;
                    case "D":
                        return Processor.D;
                    case "E":
                        return Processor.E;
                    case "F":
                        return Processor.F;
                    default:
                        return 0;
                }
            }

            @Override
            public boolean execute(Vector<String> arg) {
                switch (arg.get(0)) {
                    case "A": {
                        Processor.A = getSource(arg.get(1));
                        return true;
                    }
                    case "B": {
                        Processor.B = getSource(arg.get(1));
                        return true;
                    }
                    case "C": {
                        Processor.C = getSource(arg.get(1));
                        return true;
                    }
                    case "D": {
                        Processor.D = getSource(arg.get(1));
                        return true;
                    }
                    case "E": {
                        Processor.E = getSource(arg.get(1));
                        return true;
                    }
                    case "F": {
                        Processor.F = getSource(arg.get(1));
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }
        });
    }

    public boolean nextLine() {

        // Command
        while (true) {
            byte data = this.pcb.readNextFromMemory();

            if(data != ' ' && data != '\n')
            {
                tokens.get(0).append(data);
            }
            else
            {
                break;
            }
        }

        String command = tokens.get(0).toString();

        // Check command
        if(!commands.containsKey(command))
        {
            return false;
        }

        // Arguments
        int argNeeded = commands.get(command).getArgLength();
        int argIndex = 0;

        while(argIndex != argNeeded)
        {
            byte data = this.pcb.readNextFromMemory();

            if(data != ' ' && data != '\n')
            {
                tokens.get(argIndex+1).append(data);
            }
            else
            {
               ++argIndex;
            }
        }

        // Create vector of arguments
        Vector<String> args = new Vector<>(argNeeded);
        for(int i = 0; i != args.size(); i++)
        {
            args.set(i, tokens.get(i+1).toString());
        }

        // Clear tokens
        for(StringBuilder str : tokens)
        {
            str.setLength(0);
        }

        return commands.get(command).execute(args);
    }

    public void runAll() {
        while(nextLine()) {}
    }

}
