/*
COMPSCI 2210B
Rishabh Jain
February 22th, 2021
*/

// Class DictEntry represents a record to be stored in a dictionary
public class DictEntry {

    private String key;
    private int code;

    // stores the values of the parameters in the corresponding instance variables
    public DictEntry(String theKey, int theCode) {
        this.key = theKey;
        this.code = theCode;
    }

    // Returns the value of instance variable key
    public String getKey() {
        return this.key;
    }

    // Returns the value of instance variable code
    public int getCode() {
        return this.code;
    }

    // Returns true if this object and secondObject have the same key and code attributes; returns false otherwise
    public boolean isEqual(DictEntry secondObject) {
        return (this.code == secondObject.code && this.key.equals(secondObject.key));
    }
}
