package test;

import java.util.BitSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class BloomFilter {
    public BitSet bits; // array of bits
    public MessageDigest[] messageDigests; // digesting words with the specified algs (SHA1, MD5)
    public int total_bits; // total bits

    public BloomFilter(int size, String... algs) { // constructor
        this.total_bits = size;
        this.bits = new BitSet(this.total_bits);
        this.messageDigests = new MessageDigest[algs.length];

        try {
            for (int i = 0; i < algs.length; i++)
                this.messageDigests[i] = MessageDigest.getInstance(algs[i]);
        } catch (NoSuchAlgorithmException e) {
            System.out.printf("No such algorithm: %s", e.getMessage());
        }

    }

    public void add(String word) { // add a word to the bloom filter, turn on the bits that indicates the word
        for (MessageDigest digest : messageDigests) {
            byte[] bytes = digest.digest(word.getBytes());
            BigInteger intBytes = new BigInteger(bytes);
            int intValue = intBytes.intValue();
            int positiveIntValue = Math.abs(intValue);

            int On = positiveIntValue % total_bits;
            bits.set(On);
        }
    }

    public boolean contains(String word) { // checks if a word is contained inside the bloom filter by checking if all
                                           // bits are on
        for (MessageDigest digest : messageDigests) {
            byte[] bytes = digest.digest(word.getBytes());
            BigInteger intBytes = new BigInteger(bytes);
            int intValue = intBytes.intValue();
            int positiveIntValue = Math.abs(intValue);
            int On = positiveIntValue % total_bits;
            boolean isBitOn = bits.get(On);

            if (!isBitOn)
                return false;
        }
        return true;
    }

    public String toString() { // outputs a visual string presentation of this bits array
        StringBuilder str_bits = new StringBuilder();
        for (int i = 0; i < bits.length(); i++)
            str_bits.append(bits.get(i) ? 1 : 0);

        return str_bits.toString();
    }

}
