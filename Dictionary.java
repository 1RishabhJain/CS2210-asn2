/*
COMPSCI 2210B
Rishabh Jain
February 22th, 2021
*/

// Class Dictionary implements a dictionary using a hash table in which collisions are resolved using double hashing
public class Dictionary implements DictionaryADT {

    private int size, numElements;
    private DictEntry[] hashTable;
    private DictEntry DELETED = new DictEntry("", -1);

    // Method to calculate the primaryHashFunction
    private int primaryHashFunction(String key) {
        int k = key.length();
        int v = key.charAt(k - 1);
        // iterates through the key to calculate an insertion position
        for (int i = (k - 2); i >= 0; i--) {
            v = ((v * 33) + (int) key.charAt(i)) % size;
        }
        // returns calculated position
        return v;
    }

    // Method to calculate the secondaryHashFunction
    private int secondaryHashFunction(String key) {
        int k = key.length();
        int v = key.charAt(k - 1);
        // iterates through the key to calculate an insertion position
        for (int i = (k - 2); i >= 0; i--) {
            v = 7 - ((int) key.charAt(i) % 7);
        }
        // returns calculated position
        return v;
    }

    // This method will create an empty hash table of the size specified in the parameter
    public Dictionary(int size) {
        this.size = size;
        hashTable = new DictEntry[size];
    }

    // Inserts pair in the dictionary and throws DictionaryException if the pair is not inserted
    public int insert(DictEntry pair) throws DictionaryException {
        // calculates position from primaryHashFunction first
        int position = primaryHashFunction(pair.getKey()), count = 0;
        // variable holds boolean for collision occurring
        boolean collision = false;
        // Loop runs until an empty position for insertion is found
        while (hashTable[position] != null && hashTable[position] != DELETED) {
            // if matching key is found or if dictionary is full, throw DictionaryException
            if ((hashTable[position].getKey().equals(pair.getKey())) || numElements == size ) {
                throw new DictionaryException("Exception: Duplicate Key");
            }
            // finds next position to try insertion using the secondary hash function
            position = position + secondaryHashFunction(pair.getKey());
            // increases count for collisions
            count = count + 1;
            // error if the variable counting collisions is equal to the size of the array
            if (count == size) {
                throw new DictionaryException("Error");
            }
            // sets collision boolean to true for use in return statement
            collision = true;
        }
        // inserts at the calculated position
        hashTable[position] = pair;
        // increases the variable tracking the number of elements
        numElements = numElements + 1;
        // returns 1 or 0 depending on if a collision occurred
        if (collision) {
            return 1;
        }
        else {
            return 0;
        }
    }

    // Removes DictEntry object with given key from the dictionary and throws DictionaryException if not removed
    public void remove(String key) throws DictionaryException {
        // calculates possible position from primaryHashFunction first
        int position = primaryHashFunction(key);
        // collision counter
        int count = 0;
        // First position calculated from primary hash function is null, then key is not present
        if (hashTable[position] == null) {
            throw new DictionaryException("Key Not Found");
        }
        // If content is not null and secondary function count is less than size
        while (hashTable[position] != null && count <= size) {
            // key found
            if (hashTable[position].getKey().equals(key)) {
                // sets key to "", code to -1
                hashTable[position] = DELETED;
                // decreases number of total elements by 1
                numElements = numElements - 1;
                break;
            }
            // if key not found yet
            else {
                // throws exception if count equals size and key not found
                if (count == size) {
                    throw new DictionaryException("Key Not Found");
                }
                // Uses secondary hash function to calculate step and increments count
                position = position + secondaryHashFunction(key);
                count = count + 1;
            }
        }
    }

    // Returns the DictEntry object stored in the dictionary with the given key attribute
    public DictEntry find(String key) {
        // calculates first position with the primary function
        int position = primaryHashFunction(key);
        int count = 0;
        // First position calculated from primary hash function is null, then key is not present
        if (hashTable[position] == null) {
            return null;
        }
        // If content is not null and secondary function count is less than size
        while (hashTable[position] != null && count <= size) {
            // key found
            if (hashTable[position].getKey().equals(key)) {
                return hashTable[position];
            }
            // if key not found yet
            else {
                // returns null if count equals size and key not found
                if (count == size) {
                    return null;
                }
                // Uses secondary hash function to calculate step and increments count
                position = position + secondaryHashFunction(key);
                count = count + 1;
            }
        }
        return null;
    }

    // Returns the number of DictEntry objects stored in the dictionary
    public int numElements() {
        return numElements;
    }
}
