package menu;

import Enums.ESolutionTypes;

import helpFunctions.UserInputs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processSettings.ManualCase;

import static processSettings.JsonCase.jsonCase;
import static processSettings.ManualCase.manualCase;
import static processSettings.XmlCase.xmlCase;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);

    public static void StartMenu() {
        logger.info("Menu Starting");
        ESolutionTypes eSolutionTypes = UserInputs.UserInputChoiceOfCase();
        switch (eSolutionTypes){
            case MANUAL -> {
              manualCase();
            }
            case JSON -> {
                jsonCase();
            }
            case XML -> {
                xmlCase();
            }
            default -> {
                System.out.println("You Did Not Enter One Of The Options\nPlease Try Again");
                logger.warn("The User Did Not Pick One Of The Options");
                StartMenu();}
        }
        logger.info("Menu Ending");
    }
}