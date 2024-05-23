package test;

import test.Tile.Bag;

public class MainTrain {

    public static void testBag() {
        Bag b = Tile.Bag.getBag();
        Bag b1 = Tile.Bag.getBag();
        if (b1 != b)
            System.out.println("your Bag in not a Singleton (-5)");

        int[] q0 = b.getQuantities();
        q0[0] += 1;
        int[] q1 = b.getQuantities();
        if (q0[0] != q1[0] + 1)
            System.out.println("getQuantities did not return a clone (-5)");

        for (int k = 0; k < 9; k++) {
            int[] qs = b.getQuantities();
            Tile t = b.getRand();
            int i = t.letter - 'A';
            int[] qs1 = b.getQuantities();
            if (qs1[i] != qs[i] - 1)
                System.out.println("problem with getRand (-1)");

            b.put(t);
            b.put(t);
            b.put(t);

            if (b.getQuantities()[i] != qs[i])
                System.out.println("problem with put (-1)");
        }

        if (b.getTile('a') != null || b.getTile('$') != null || b.getTile('A') == null)
            System.out.println("your getTile is wrong (-2)");

    }

    private static Tile[] get(String s) {
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }

    public static void testBoard() {
        Board b = Board.getBoard();
        if (b != Board.getBoard())
            System.out.println("board should be a Singleton (-5)");

        Bag bag = Bag.getBag();
        Tile[] ts = new Tile[10];
        for (int i = 0; i < ts.length; i++)
            ts[i] = bag.getRand();

        Word w0 = new Word(ts, 0, 6, true);
        Word w1 = new Word(ts, 7, 6, false);
        Word w2 = new Word(ts, 6, 7, true);
        Word w3 = new Word(ts, -1, 7, true);
        Word w4 = new Word(ts, 7, -1, false);
        Word w5 = new Word(ts, 0, 7, true);
        Word w6 = new Word(ts, 7, 0, false);

        if (b.boardLegal(w0) || b.boardLegal(w1) || b.boardLegal(w2) || b.boardLegal(w3) || b.boardLegal(w4)
                || !b.boardLegal(w5) || !b.boardLegal(w6))
            System.out.println("your boardLegal function is wrong (-10)");

        for (Tile t : ts)
            bag.put(t);

        Word horn = new Word(get("HORN"), 7, 5, false);
        if (b.tryPlaceWord(horn) != 14)
            System.out.println("problem in placeWord for 1st word (-10)");

        Word farm = new Word(get("FA_M"), 5, 7, true);
        if (b.tryPlaceWord(farm) != 9)
            System.out.println("problem in placeWord for 2ed word (-10)");

        Word paste = new Word(get("PASTE"), 9, 5, false);
        if (b.tryPlaceWord(paste) != 25)
            System.out.println("problem in placeWord for 3ed word (-10)");

        Word mob = new Word(get("_OB"), 8, 7, false);
        int mobpoint = b.tryPlaceWord(mob);
        if (mobpoint != 18)
            System.out.println("mob point sould be 18");

        Word bit = new Word(get("BIT"), 10, 4, false);
        int bitpoint = b.tryPlaceWord(bit);
        if (bitpoint != 22)
            System.out.println("bitpoint should be 22 (-15)");

        Word bit2 = new Word(get("S_TA"), 9, 4, true);
        if (b.tryPlaceWord(bit2) != 28)
            System.out.println("SBTA should be 28 (-15)");

        Word bit3 = new Word(get("A_ONE"), 11, 3, false);
        if (b.tryPlaceWord(bit3) != 26)
            System.out.println("ATONE should be 26 (-15)");

        Word bit4 = new Word(get("WARE"), 10, 9, true);
        if (b.tryPlaceWord(bit4) != 15)
            System.out.println("BEWARE SHOULD BE 15 (-15)");

        Word bit5 = new Word(get("COOL"), 13, 4, false);
        if (b.tryPlaceWord(bit5) != 26)
            System.out.println("cool SHOULD BE 26 (-15)");

        Word bit6 = new Word(get("P_O_Y"), 10, 7, true);
        if (b.tryPlaceWord(bit6) != 79)
            System.out.println("PEOLY SHOULD BE 79 (-15)");

        Word bit8 = new Word(get("ALSTU"), 7, 0, false);
        if (b.tryPlaceWord(bit8) != 39)
            System.out.println("ALSTU SHOULD BE 39 (-15)");

        Word bit7 = new Word(get("_OAF"), 7, 0, true);
        if (b.tryPlaceWord(bit7) != 21)
            System.out.println("Aoaf SHOULD BE 21 (-15)");

        Word bit9 = new Word(get("COXL"), 11, 0, true);
        if (b.tryPlaceWord(bit9) != 207)
            System.out.println("AOAFCOXL SHOULD BE 207 (-15)");

        Word bit10 = new Word(get("GJRWQIY"), 0, 0, true);
        if (b.tryPlaceWord(bit10) != 1539)
            System.out.println("GJRWQIY SHOULD BE 1539 (-15)");

        Word bit11 = new Word(get("AETIN"), 8, 10, false);
        if (b.tryPlaceWord(bit11) != 14)
            System.out.println("AETAN SHOULD BE 14 (-15)");

        Word bit12 = new Word(get("_I"), 1, 0, false);
        if (b.tryPlaceWord(bit12) != 18)
            System.out.println("JI SHOULD BE 18 (-15)");

        Word bit13 = new Word(get("ZK"), 0, 13, true);
        if (b.tryPlaceWord(bit13) != 0)
            System.out.println("ZK SHOULD BE 0 (-15)");

        Word bit14 = new Word(get("__"), 7, 7, false);
        if (b.tryPlaceWord(bit14) != 0)
            System.out.println("PROBLEM WITH BOARD LEGAL");

        // Word bit15=new Word(get("_TDEUGHWAREUEIE"), 0, 0, false);
        // if(b.tryPlaceWord(bit15)!=706)
        // System.out.println("_TDEUGHWAREUEIE SHOULD BE 706 (-15)");

        // Word bit16=new Word(get("EIEIE"), 0, 7, true);
        // if(b.tryPlaceWord(bit16)!=48)
        // System.out.println("EIEIE SHOULD BE 48 (-15)");
        //
        // Word bit17=new Word(get("TDEUGH_"), 0, 1, false);
        // if(b.tryPlaceWord(bit17)!=39)
        // System.out.println("TDEUGH_ SHOULD BE 39 (-15)");
        //
        // Word bit18=new Word(get("G"), 0, 0, false);
        // if(b.tryPlaceWord(bit18)!=135)
        // System.out.println("G SHOULD BE 135 (-15)");
        //
        // Word bit19=new Word(get("_I"), 0, 0, true);
        // if(b.tryPlaceWord(bit19)!=9)
        // System.out.println("_I SHOULD BE 9 (-15)");
    }

    public static void main(String[] args) {
        testBag(); // 30 points
        testBoard(); // 70 points
        System.out.println("done");
    }

}