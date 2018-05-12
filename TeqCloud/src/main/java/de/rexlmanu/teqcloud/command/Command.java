/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package de.rexlmanu.teqcloud.command;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 12.05.2018 / 00:10                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public abstract class Command implements ICommand {

    private final String commandLabel;
    private final String description;

    public Command(final String commandLabel, final String description) {
        this.commandLabel = commandLabel;
        this.description = description;
    }

    @Override
    public void perform(final String[] args) {

    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getCommandLabel() {
        return this.commandLabel;
    }
}
