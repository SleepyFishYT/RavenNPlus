package a.b.command.commands;


import a.b.clickgui.otaku.CommandPrompt;
import a.b.command.Command;
import a.b.module.modules.client.ClientNameSpoof;

public class F3Name extends Command {
    public F3Name() {
        super("f3name", "Changes the client's name in f3", 1, 1,  new String[] {"New client name"},  new String[] {"f3n"});
    }

    public void onCall(String[] args){
        if(args.length == 0){
            this.incorrectArgs();
            return;
        }
        StringBuilder wut = new StringBuilder(args[0]);
        if(args.length > 1){
            for(int i = 2; i < args.length; i++){
                wut.append(" ").append(args[i]);
            }
        }
        ClientNameSpoof.newName = wut.toString();
        CommandPrompt.print("Set client name to " + wut);
    }
}
