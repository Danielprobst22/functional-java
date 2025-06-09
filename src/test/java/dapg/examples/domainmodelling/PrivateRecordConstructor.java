package dapg.examples.domainmodelling;

public record PrivateRecordConstructor(
        String value,
        boolean valid
        ) {


    /*
    * java: invalid canonical constructor in record PrivateRecordConstructor
    *   (throws clause not allowed for canonical constructor)
    */
//    public PrivateRecordConstructor(String value, boolean valid) throws Exception {
    public PrivateRecordConstructor(String value, boolean valid) {
//        if (value == null) throw new Exception("Fuck it");
        this.value = value;
        this.valid = valid;
    }
}
