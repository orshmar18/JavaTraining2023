package main.java.Enums;

public enum SolutionTypes {
    JSON('j'),
    XML('x'),
    MANUAL('m'),
    NULL('N');
    private final char c;

    SolutionTypes(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static SolutionTypes getByChar(char c){
        for (SolutionTypes solutionTypes : values()){
            if(solutionTypes.getChar() == c){
                return solutionTypes;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (SolutionTypes solutionTypes : values()){
            if(solutionTypes.getChar() == type){
                return true;
            }
        }
        return false;
    }
}
