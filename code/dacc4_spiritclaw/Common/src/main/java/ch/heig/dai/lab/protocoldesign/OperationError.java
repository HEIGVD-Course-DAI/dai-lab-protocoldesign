package ch.heig.dai.lab.protocoldesign;

public enum OperationError {
    /*
    - INVOP : invalid operation
    - INVNB : invalid number of arguments
    - DIV0 : division by zero
    - INVARG : invalid argument
    - UNKERR : unknown error
    - BADREF : use of `?` as a number but no previous operation
     */
    INVOP,
    INVNB,
    DIV0,
    INVARG,
    UNKERR,
    BADREF;

    public String message = null;

    static OperationError fromString(String str) {
        return switch (str) {
            case "INVOP" -> INVOP;
            case "INVNB" -> INVNB;
            case "DIV0" -> DIV0;
            case "INVARG" -> INVARG;
            case "UNKERR" -> UNKERR;
            case "BADREF" -> BADREF;
            default -> throw new IllegalArgumentException("Unknown error: " + str);
        };
    }
}
