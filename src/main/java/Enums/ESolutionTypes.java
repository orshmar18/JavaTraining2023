package Enums;

public enum ESolutionTypes {
    JSON('j'),
    XML('x'),
    MANUAL('m'),
    NULL('N');
    private final char c;

    ESolutionTypes(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static ESolutionTypes getByChar(char c){
        for (ESolutionTypes solutionTypes : values()){
            if(solutionTypes.getChar() == c){
                return solutionTypes;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (ESolutionTypes solutionTypes : values()){
            if(solutionTypes.getChar() == type){
                return true;
            }
        }
        return false;
    }
}
