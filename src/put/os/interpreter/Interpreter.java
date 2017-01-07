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
        this.tokens = new Vector<StringBuilder>(3);

        // MOV
        commands.put("MOV", new Command(2) {
            private int getSource(String arg) {
                try
                {
                    return Integer.parseInt(arg);
                }
                catch(NumberFormatException nfe)
                {
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

        tokens.add(0, new StringBuilder());

        // Command
        while (true) {
            char data;

            try {
                data = (char) this.pcb.readNextFromMemory();
            } catch (Exception e) {
                break;
            }

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

        for(int i = 0; i < argNeeded; i++)
        {
            tokens.add(i+1, new StringBuilder());
        }

        while(argIndex != argNeeded)
        {
            char data;

            try {
                data = (char) this.pcb.readNextFromMemory();
            } catch (Exception e) {
                break;
            }

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
        for(int i = 0; i != argNeeded; i++)
        {
            args.add(i, tokens.get(i+1).toString());
        }

        // Clear tokens
        tokens.clear();

        return commands.get(command).execute(args);
    }

    public void runAll() {
        while(nextLine()) {}
    }

}
