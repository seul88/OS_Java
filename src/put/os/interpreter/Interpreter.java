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

        public int getSource(String arg) {
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
                    case "R":
                    {
                        try {
                            return getSource(pcb.getIpc().read());
                        } catch (Exception e) {
                            return 0;
                        }
                    }
                    default:
                        return 0;
                }
            }
        }
        public boolean execute(Vector<String> arg) { return false; }
    }
    private static Map<String, Command> commands = new HashMap<>();

    private Vector<StringBuilder> tokens;
    private static boolean ignoreNextCmd = false;


    private ProcessBlockController pcb;

    public Interpreter(ProcessBlockController pcb) {
        this.pcb = pcb;
        this.tokens = new Vector<StringBuilder>(3);

        // MOV
        commands.put("MOV", new Command(2) {

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

        // MP
        commands.put("MP", new Command(2) {

            @Override
            public boolean execute(Vector<String> arg) {
                switch (arg.get(0)) {
                    case "A": {
                        Processor.A *= getSource(arg.get(1));
                        return true;
                    }
                    case "B": {
                        Processor.B *= getSource(arg.get(1));
                        return true;
                    }
                    case "C": {
                        Processor.C *= getSource(arg.get(1));
                        return true;
                    }
                    case "D": {
                        Processor.D *= getSource(arg.get(1));
                        return true;
                    }
                    case "E": {
                        Processor.E *= getSource(arg.get(1));
                        return true;
                    }
                    case "F": {
                        Processor.F *= getSource(arg.get(1));
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

        });

        // ADD
        commands.put("ADD", new Command(2) {

            @Override
            public boolean execute(Vector<String> arg) {
                switch (arg.get(0)) {
                    case "A": {
                        Processor.A += getSource(arg.get(1));
                        return true;
                    }
                    case "B": {
                        Processor.B += getSource(arg.get(1));
                        return true;
                    }
                    case "C": {
                        Processor.C += getSource(arg.get(1));
                        return true;
                    }
                    case "D": {
                        Processor.D += getSource(arg.get(1));
                        return true;
                    }
                    case "E": {
                        Processor.E += getSource(arg.get(1));
                        return true;
                    }
                    case "F": {
                        Processor.F += getSource(arg.get(1));
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

        });

        // SB
        commands.put("SB", new Command(2) {

            @Override
            public boolean execute(Vector<String> arg) {
                switch (arg.get(0)) {
                    case "A": {
                        Processor.A -= getSource(arg.get(1));
                        return true;
                    }
                    case "B": {
                        Processor.B -= getSource(arg.get(1));
                        return true;
                    }
                    case "C": {
                        Processor.C -= getSource(arg.get(1));
                        return true;
                    }
                    case "D": {
                        Processor.D -= getSource(arg.get(1));
                        return true;
                    }
                    case "E": {
                        Processor.E -= getSource(arg.get(1));
                        return true;
                    }
                    case "F": {
                        Processor.F -= getSource(arg.get(1));
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

        });

        // INC
        commands.put("INC", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {
                switch (arg.get(0)) {
                    case "A": {
                        Processor.A++;
                        return true;
                    }
                    case "B": {
                        Processor.B++;
                        return true;
                    }
                    case "C": {
                        Processor.C++;
                        return true;
                    }
                    case "D": {
                        Processor.D++;
                        return true;
                    }
                    case "E": {
                        Processor.E++;
                        return true;
                    }
                    case "F": {
                        Processor.F++;
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

        });

        // DEC
        commands.put("DEC", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {
                switch (arg.get(0)) {
                    case "A": {
                        Processor.A--;
                        return true;
                    }
                    case "B": {
                        Processor.B--;
                        return true;
                    }
                    case "C": {
                        Processor.C--;
                        return true;
                    }
                    case "D": {
                        Processor.D--;
                        return true;
                    }
                    case "E": {
                        Processor.E--;
                        return true;
                    }
                    case "F": {
                        Processor.F--;
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

        });

        // FORK
        commands.put("FORK", new Command(0) {

            @Override
            public boolean execute(Vector<String> arg) {
                pcb.fork();
                return false;
            }

        });

        // FORK
        commands.put("PIPE", new Command(0) {

            @Override
            public boolean execute(Vector<String> arg) {
                pcb.pipe();
                return true;
            }

        });

        // IF
        commands.put("IF", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {

                switch(arg.get(0))
                {
                    case "P":
                    {
                        if(pcb.getRole() != 1)
                        {
                            ignoreNextCmd = true;
                        }

                        return true;
                    }

                    case "C":
                    {
                        if(pcb.getRole() != 2)
                        {
                            ignoreNextCmd = true;
                        }

                        return true;
                    }
                }


                return false;
            }

        });

        // IF
        commands.put("IFA", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {

                if(Processor.A != getSource(arg.get(0)))
                {
                    ignoreNextCmd = true;
                }

                return true;
            }

        });

        // CLOSE
        commands.put("CLOSE", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {

                switch(arg.get(0))
                {
                    case "R":
                    {
                        pcb.getIpc().closeRead();

                        return true;
                    }

                    case "W":
                    {
                        pcb.getIpc().closeWrite();

                        return true;
                    }
                }


                return false;
            }

        });

        // WRITE
        commands.put("WRITE", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {

                StringBuffer data = new StringBuffer();
                data.append( getSource(arg.get(0)) );

                try {
                    pcb.getIpc().write(data.toString());
                } catch (Exception e) {
                    return false;
                }

                return true;
            }

        });

        // JUMP
        commands.put("JUMP", new Command(1) {

            @Override
            public boolean execute(Vector<String> arg) {

                pcb.jump(getSource(arg.get(0)));

                return true;
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

            if(data != ' ' && data != '\n'&& data != '_')
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
        if(!ignoreNextCmd) {
            System.out.print("\nWykonuje: ");
            for (StringBuilder token : tokens) {
                System.out.print(token + " ");
            }
            System.out.print("\n");
        }

        tokens.clear();

        if(ignoreNextCmd)
        {
            ignoreNextCmd = false;
            return true;
        }
        else
        {
            return commands.get(command).execute(args);
        }
    }

    public void runAll() {
        while(nextLine()) {}
    }
}
