/*
 * Command Processor Test - demonstrates COMMAND PROCESSOR pattern.
 * Code-Beispiel zum Buch Patterns Kompakt, Verlag Springer Vieweg
 * Copyright 2014 Karl Eilebrecht
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dhbw_mannheim.tinf13itns.commandprocessor.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw_mannheim.tinf13itns.command.InputCommand;
import de.dhbw_mannheim.tinf13itns.command.TextComponent;
import de.dhbw_mannheim.tinf13itns.commandprocessor.InputCommandProcessor;

/**
 * Command Processor Test - demonstrates COMMAND PROCESSOR pattern.
 * @author <a href="mailto:Karl.Eilebrecht(a/t)calamanari.de">Karl Eilebrecht</a>
 */
public class CommandProcessorTest {

    /**
     * logger
     */
    protected static final Logger LOGGER = Logger.getLogger(CommandProcessorTest.class.getName());

    /**
     * Log-level for this test
     */
    private static final Level LOG_LEVEL = Level.INFO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testCommandProcessor() {

        // hint: set the log-level above to FINE to watch COMMAND PROCESSOR working.

        LOGGER.info("Test Commmand Processor ...");

        TextComponent textComponent = new TextComponent();

        InputCommandProcessor inputCommandProcessor = new InputCommandProcessor();

        List<InputCommand> commandList = new ArrayList<>();
        List<InputCommand> undoCommandList = new ArrayList<>();
        InputCommand command1=()->textComponent.append("The");
        InputCommand undoCommand1 = ()->textComponent.setLength(textComponent.length() - 3);
        commandList.add(command1);
        inputCommandProcessor.put(command1, undoCommand1);
        InputCommand command2=()->textComponent.append("quick");
        InputCommand undoCommand2 = ()->textComponent.setLength(textComponent.length() - 5);
        commandList.add(command2);
        inputCommandProcessor.put(command2, undoCommand2);
        InputCommand command3=()->textComponent.setLength(textComponent.length() - 5);
        InputCommand undoCommand3 = ()->textComponent.setLength(textComponent.length() + 5);
        commandList.add(command3);
        inputCommandProcessor.put(command3, undoCommand3);
        InputCommand command4=()->textComponent.append(" ");
        InputCommand undoCommand4 = ()->textComponent.setLength(textComponent.length() - 1);
        commandList.add(command4);
        inputCommandProcessor.put(command4, undoCommand4);
        InputCommand command5=()->textComponent.append("quick");
        InputCommand undoCommand5 = ()->textComponent.setLength(textComponent.length() - 5);
        commandList.add(command5);
        inputCommandProcessor.put(command5, undoCommand5);

//        		Arrays.asList(new InputCommand[] {
//
//        new AppendTextCommand(textComponent, "The"), new AppendTextCommand(textComponent, "quick"),
//                new DeleteTextCommand(textComponent, 1), new DeleteTextCommand(textComponent, 1),
//                new DeleteTextCommand(textComponent, 1), new DeleteTextCommand(textComponent, 1),
//                new DeleteTextCommand(textComponent, 1), new AppendTextCommand(textComponent, " "),
//                new AppendTextCommand(textComponent, "quick"), new AppendTextCommand(textComponent, " "),
//                new AppendTextCommand(textComponent, "brown"), new AppendTextCommand(textComponent, " "),
//                new AppendTextCommand(textComponent, "d"), new DeleteTextCommand(textComponent, 1),
//                new AppendTextCommand(textComponent, "f"), new AppendTextCommand(textComponent, "o"),
//                new AppendTextCommand(textComponent, "x"), new AppendTextCommand(textComponent, " "),
//                new AppendTextCommand(textComponent, "jumped over the lazy frog"),
//                new DeleteTextCommand(textComponent, 4), new AppendTextCommand(textComponent, "duck"),
//                new DeleteTextCommand(textComponent, 1), new DeleteTextCommand(textComponent, 1),
//                new DeleteTextCommand(textComponent, 1), new AppendTextCommand(textComponent, "og."), });

        
        for (int i=0; i<commandList.size(); i++) {
            inputCommandProcessor.execute(commandList.get(i));
        }
        	
        
//        assertEquals("The quick brown fox jumped over the lazy dog.", textComponent.toString());
        assertEquals("The quick", textComponent.toString());

        boolean res = false;
        do {
            res = inputCommandProcessor.undo();
        } while (res);

        assertEquals("", textComponent.toString());

        do {
            res = inputCommandProcessor.redo();
        } while (res);

        assertEquals("The quick", textComponent.toString());

        LOGGER.info("Test Commmand Processor successful!");
    }

}
